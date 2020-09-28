package org.barnhorse.sts.mod;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ShowMoveNameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barnhorse.sts.lib.EventPublisher;
import org.barnhorse.sts.lib.consumer.ConsoleConsumer;
import org.barnhorse.sts.lib.consumer.EventConsumer;
import org.barnhorse.sts.lib.consumer.FileConsumer;
import org.barnhorse.sts.lib.consumer.NitriteConsumer;
import org.barnhorse.sts.lib.events.*;
import org.barnhorse.sts.lib.model.RelicEffect;
import org.barnhorse.sts.lib.model.UpgradeSource;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.dispatch.PatchEventSubscriber;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@SpireInitializer
public class Pensieve implements
        basemod.interfaces.OnStartBattleSubscriber,
        basemod.interfaces.RelicGetSubscriber,
        basemod.interfaces.PostDrawSubscriber,
        basemod.interfaces.PostDungeonInitializeSubscriber,
        basemod.interfaces.StartGameSubscriber,
        PatchEventSubscriber {

    public final static int SCHEMA_VERSION = 0;

    enum ModState {
        IN_RUN,
        OUT_OF_RUN,
        ERROR
    }

    public static final Logger logger = LogManager.getLogger(Pensieve.class.getName());

    private static final Path modWorkingPath = Paths
            .get("mods", "etc", "barnhorse", "pensieve")
            .toAbsolutePath();
    private static final Path runsPath = modWorkingPath.resolve("runs");
    private static final Path archivePath = runsPath.resolve("archive");
    private static final Path configPath = modWorkingPath.resolve("config.json");
    private static Predicate<GameEvent> defaultEventFilter;

    static {
        defaultEventFilter = e -> e.actNumber < 1;
    }

    private EventPublisher publisher;
    private Configuration config;
    private ModState state;
    private RunId currentRunId;

    public Pensieve(Configuration config) {
        this.config = config;
        this.state = ModState.OUT_OF_RUN;
        this.publisher = null;
    }

    public void dispose() {
        this.shutDownLogger();
    }

    private static void applyDefaults(Configuration config) {
        if (config.getStorageEngine() == null) {
            config.setStorageEngine(StorageEngine.NITRITE);
        }

        if (config.getEventLogDirectory() == null ||
                config.getEventLogDirectory().trim().isEmpty()) {
            config.setEventLogDirectory(runsPath.toString());
        }

        if (config.getArchiveDirectory() == null ||
                config.getArchiveDirectory().trim().isEmpty()) {
            config.setArchiveDirectory(archivePath.toString());
        }
    }

    private static Configuration createConfiguration() {
        Configuration config = new Configuration();
        File configFile = configPath.toFile();
        if (configFile.exists()) {
            try {
                Gson gson = new Gson();
                config = gson.fromJson(new FileReader(configFile), Configuration.class);
            } catch (IOException ioe) {
                logger.error("Failed to load configuration file at: " + configFile.toPath(), ioe);
                logger.error("The mod will use default settings.");
            }
        }
        applyDefaults(config);
        return config;
    }

    private static void createDirectories(Configuration config) {
        Path[] paths = {
                Paths.get(config.getEventLogDirectory()),
                Paths.get(config.getArchiveDirectory())
        };

        for (Path path : paths) {
            File file = path.toFile();
            file.mkdirs();
            if (!file.exists()) {
                throw new RuntimeException("Failed to create directory at: " + path);
            }
        }
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        Configuration config;

        try {
            config = createConfiguration();
            createDirectories(config);
        } catch (Exception e) {
            logger.error("Failed to initialize Pensieve mod.", e);
            return;
        }

        Pensieve mod = new Pensieve(config);
        BaseMod.subscribe(mod);
        PatchEventManager.subscribe(mod);
    }

    private Path getRunEventPath(RunId runId) {
        assert runId != null;
        String fileName = String.format("%s.run", runId.toString());
        return Paths.get(this.config.getEventLogDirectory(), fileName);
    }

    private EventConsumer createEventConsumer(RunId runId, boolean clobber)
            throws ConsumerCreationFailed {
        File eventFile;
        StorageEngine engine = this.config.getStorageEngine();
        switch (engine) {
            case FILE:
            case NITRITE:
                eventFile = this.getRunEventPath(runId).toFile();
                if (eventFile.exists() && clobber) {
                    if (!eventFile.delete()) {
                        throw new ConsumerCreationFailed("Failed to clobber event file.");
                    }
                }
                if (engine == StorageEngine.NITRITE) {
                    return new NitriteConsumer(eventFile);
                } else if (engine == StorageEngine.FILE) {
                    return new FileConsumer(eventFile);
                } else {
                    throw new ConsumerCreationFailed("Unexpected storage engine: " + engine);
                }
            case CONSOLE:
                return new ConsoleConsumer(runId);
            default:
                throw new ConsumerCreationFailed("Unexpected storage engine: " + engine);
        }
    }

    private void shutDownLogger() {
        if (this.publisher != null) {
            this.publisher.stop();
            this.publisher = null;
        }
    }

    private void enterRun(boolean clobber) {
        if (this.state == ModState.ERROR) {
            logger.warn("Tried to enter a run in the ERROR state, refusing to do so.");
        }

        if (this.state == ModState.IN_RUN) {
            logger.warn("Tried to enter a run, but one is already active.");
            return;
        }

        this.currentRunId = new RunId(
                Settings.seed,
                AbstractDungeon.player);

        this.state = ModState.IN_RUN;

        if (this.publisher == null) {
            try {
                EventConsumer consumer = this.createEventConsumer(this.currentRunId, clobber);
                this.publisher = new EventPublisher(consumer);
                consumer.setup();
                this.publisher.start();
            } catch (ConsumerCreationFailed e) {
                logger.error("Failed to start an event publisher.", e);
                this.enterErrorState();
            }
        }
    }

    private void exitRun() {
        if (this.state == ModState.ERROR) {
            logger.warn("Tried to exit a run in the ERROR state, refusing to do so.");
        }

        if (this.state == ModState.OUT_OF_RUN) {
            logger.warn("Tried to exit a run, but we're not in one.");
            return;
        }

        this.state = ModState.OUT_OF_RUN;
        this.currentRunId = null;
        this.shutDownLogger();
    }

    private void enterErrorState() {
        logger.error("The mod is entering the error state. Please inform a dev.");
        this.state = ModState.ERROR;
    }

    public void publishEvent(GameEvent event) {
        publishEvent(event, defaultEventFilter);
    }

    public void publishEvent(GameEvent event, Predicate<GameEvent> filter) {
        assert event != null;

        if (this.publisher == null) {
            logger.warn("Tried to publish an event, but no publisher is active, skipping...");
            logger.warn("\tEvent: {}", event.key);
            return;
        }

        if (this.state == ModState.ERROR) {
            logger.warn("Tried to publish an event, but the mod is in an error state, skipping...");
            logger.warn("\tEvent: {}", event.key);
            return;
        }

        if (this.state == ModState.OUT_OF_RUN) {
            logger.warn("Tried to publish an event, but the mod is not in a run, skipping...");
            logger.warn("\tEvent: {}", event.key);
            return;
        }

        event.timestamp = System.currentTimeMillis();
        event.floorNumber = AbstractDungeon.floorNum;
        event.actNumber = AbstractDungeon.actNum;

        if (filter != null && filter.test(event)) {
            logger.info("Tried to publish an event, but it was caught in the filter.");
            logger.info(String.format("\tEvent: %s", event.key));
        }

        this.publisher.publishEvent(event);
    }

    public void publishOneOffEvent(GameEvent event, RunId runId) {
        assert event != null;
        assert runId != null;

        EventConsumer consumer = null;
        try {
            consumer = this.createEventConsumer(runId, false);
            consumer.setup();
            consumer.accept(event);
        } catch (Exception e) {
            logger.error("Failed to log one-off event: " + event.key, e);
        } finally {
            if (consumer != null) {
                consumer.tearDown();
            }
        }
    }

    private void archiveRun(RunId runId) throws IOException {
        StorageEngine engine = this.config.getStorageEngine();
        switch (engine) {
            case FILE:
            case NITRITE:
                Path currentLogPath = this.getRunEventPath(runId);
                if (!currentLogPath.toFile().exists()) {
                    logger.error(
                            "Tried to archive run '{}', but it doesn't exist!",
                            currentLogPath.toString()
                    );
                }

                String eventFileName = currentLogPath.getFileName().toString();
                String archiveFileName = String.format(
                        "%s_%d_archive.run",
                        eventFileName.replace(".run", ""),
                        System.currentTimeMillis()
                );

                Path archiveDir = Paths.get(this.config.getArchiveDirectory());
                archiveDir.resolve(archiveFileName);
                Path archivePath = archiveDir.resolve(archiveFileName);
                Files.copy(currentLogPath, archivePath, StandardCopyOption.REPLACE_EXISTING);
            case CONSOLE:
                break;
            default:
                logger.error("Unexpected storage engine: " + engine);
                break;
        }
    }

    private void deleteRun(RunId id) {
        assert id != null;
        StorageEngine engine = this.config.getStorageEngine();
        switch (engine) {
            case FILE:
            case NITRITE:
                File runFile = this.getRunEventPath(id).toFile();
                if (!runFile.delete()) {
                    throw new RuntimeException(
                            "Failed to delete run file at: " + runFile.getAbsolutePath()
                    );
                }
            case CONSOLE:
                break;
            default:
                logger.error("Unexpected storage engine: " + engine);
                break;
        }
    }

    @Override
    public void receivePostDungeonInitialize() {
        this.enterRun(true);
        publishEvent(RunStart.fromGameSettings());
    }

    @Override
    public void receiveStartGame() {
        this.enterRun(false);
    }

    @Override
    public void onAbandonRun(AbstractPlayer player) {
        RunId runId;

        if (state == ModState.OUT_OF_RUN) {
            SaveFile saveFile = SaveAndContinue.loadSaveFile(player.chosenClass);
            runId = new RunId(saveFile.seed, player);
            publishOneOffEvent(new RunAbandoned(), runId);
        } else {
            runId = this.currentRunId;
            publishEvent(new RunAbandoned());
            this.exitRun();
        }

        try {
            this.archiveRun(runId);
            this.deleteRun(runId);
        } catch (Exception e) {
            logger.error("Failed to archive current run!", e);
        }
    }

    @Override
    public void onSaveAndQuit() {
        publishEvent(new SaveAndQuit());    
        this.exitRun();
    }

    @Override
    public void onPlayerTurnStart(AbstractPlayer player) {
        publishEvent(new PlayerTurnStart(player, this.config.isVerbose()));
    }

    @Override
    public void onPlayerDamaged(AbstractPlayer player, DamageInfo info, int actualDamage) {
        this.publishEvent(new PlayerDamaged(player, info, actualDamage));
    }

    @Override
    public void onMonsterDamaged(AbstractMonster monster, DamageInfo info, int actualDamage) {
        this.publishEvent(new MonsterDamaged(monster, info, actualDamage));
    }

    @Override
    public void onBlockGained(
            AbstractCreature creature,
            int amount,
            int startingBlock,
            int endingBlock,
            int delta) {
        publishEvent(new BlockGained(creature, amount, startingBlock, endingBlock, delta));
    }

    @Override
    public void onBlockLost(
            AbstractCreature creature,
            int amount,
            int startingBlock,
            int endingBlock,
            int delta) {
        if (amount > 0 || delta != 0) {
            publishEvent(new BlockLost(creature, amount, startingBlock, endingBlock, delta));
        }
    }

    @Override
    public void onMonsterDied(AbstractMonster monster) {
        publishEvent(new MonsterDied(monster));
    }

    @Override
    public void onPlayerDied(AbstractPlayer player, List<GameOverStat> stats) {
        if (this.state == ModState.IN_RUN) {
            RunId runId = this.currentRunId;
            publishEvent(new PlayerDied(player, stats));
            this.exitRun();
            try {
                this.archiveRun(runId);
                this.deleteRun(runId);
            } catch (Exception e) {
                logger.error("Failed to archive current run!", e);
            }
        }
    }

    @Override
    public void onPlayerVictory(AbstractPlayer player, List<GameOverStat> stats) {
        publishEvent(new PlayerVictory(player, stats));
        this.exitRun();
        try {
            this.archiveRun(this.currentRunId);
            this.deleteRun(this.currentRunId);
        } catch (Exception e) {
            logger.error("Failed to archive current run!", e);
        }
    }

    @Override
    public void onEnterShop(ShopScreen shop) {
        publishEvent(new EnterShop(AbstractDungeon.player, shop));
    }

    @Override
    public void onPurchaseCard(AbstractCard card, int price) {
        publishEvent(new PurchaseCard(card, price));
    }

    @Override
    public void onPurgeCard(AbstractCard card, int price) {
        publishEvent(new PurchasePurge(card, price));
    }

    @Override
    public void onPurchasePotion(StorePotion potion) {
        publishEvent(new PurchasePotion(potion));
    }

    @Override
    public void onPurchaseRelic(StoreRelic relic) {
        publishEvent(new PurchaseRelic(relic));
    }

    @Override
    public void onRelicTriggered(AbstractRelic relic, Map<RelicEffect, Integer> summary) {
        publishEvent(new RelicTriggered(relic, summary));
    }

    @Override
    public void onCardBottled(AbstractCard card) {
        publishEvent(new CardBottled(card));
    }

    @Override
    public void onEventEntered(AbstractEvent event) {
        publishEvent(new EventStarted(event));
    }

    @Override
    public void onQuestionMarkResolved(EventHelper.RoomResult result) {
        publishEvent(new QuestionMarkResolved(result));
    }

    @Override
    public void onPotionObtained(AbstractPotion potion) {
        publishEvent(new PotionObtained(potion));
    }

    @Override
    public void onPotionUsed(AbstractPotion potion, AbstractCreature target) {
        publishEvent(new PotionUsed(potion, target));
    }

    @Override
    public void onKeyObtained(ObtainKeyEffect.KeyColor color) {
        publishEvent(new KeyObtained(color));
    }

    @Override
    public void onEnemyTurnStart() {
        publishEvent(new EnemyTurnStart(AbstractDungeon.getCurrRoom()));
    }

    @Override
    public void onRoomPhaseChange(AbstractRoom room, AbstractRoom.RoomPhase lastPhase, AbstractRoom.RoomPhase curPhase) {
        if (lastPhase == AbstractRoom.RoomPhase.COMBAT && curPhase == AbstractRoom.RoomPhase.COMPLETE) {
            publishEvent(new CombatComplete(room));
        }
    }

    @Override
    public void dispatchRewardsReceived(ArrayList<RewardItem> rewards) {
        publishEvent(new RewardsReceived(rewards));
    }

    @Override
    public void dispatchMapGenerated() {
        publishEvent(new MapGenerated(AbstractDungeon.map, AbstractDungeon.bossKey));
    }

    @Override
    public void dispatchVisitMapNode(MapRoomNode currMapNode) {
        publishEvent(new VisitMapNode(currMapNode));
    }

    @Override
    public void dispatchEnterRestRoom(RestRoom room) {
        publishEvent(new EnterRestRoom(room));
    }

    @Override
    public void dispatchRestRoomOptionSelected(AbstractCampfireOption option) {
        publishEvent(new RestRoomOptionSelected(option));
    }

    @Override
    public void dispatchCardUpgraded(AbstractCard card, UpgradeSource source) {
        publishEvent(new CardUpgraded(card, source));
    }

    @Override
    public void dispatchGoldGained(int amount) {
        publishEvent(new GoldGained(amount, AbstractDungeon.player.gold));
    }

    @Override
    public void dispatchChestOpened(AbstractChest chest, ArrayList<RewardItem> rewards) {
        publishEvent(new ChestOpened(chest, rewards));
    }

    @Override
    public void dispatchBossChestOpened(ArrayList<AbstractRelic> relics) {
        publishEvent(new BossChestOpened(relics));
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        publishEvent(new BattleStart(room));
    }

    @Override
    public void onGameActionStart(AbstractGameAction action) {
        if (action != null) {
            if (action instanceof ShowMoveNameAction) {
                ShowMoveNameAction moveAction = (ShowMoveNameAction) action;
                publishEvent(new EnemyMove(moveAction));
            }
        }
    }

    @Override
    public void onGameActionDone(AbstractGameAction action) {
    }

    @Override
    public void onCardObtained(AbstractCard card) {
        publishEvent(new CardAddedToDeck(card));
    }

    @Override
    public void onCardRemoved(CardGroup deck, AbstractCard card) {
        publishEvent(new CardRemovedFromDeck(card));
    }

    @Override
    public void onCardUsed(AbstractPlayer player, AbstractCard card, AbstractMonster monster, int energyOnUse) {
        publishEvent(new CardUsed(card, monster));
    }

    @Override
    public void receiveRelicGet(AbstractRelic abstractRelic) {
        publishEvent(new RelicObtained(abstractRelic));
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {
        publishEvent(new CardDraw(abstractCard));
    }

    @Override
    public void onDispose() {
        this.dispose();
    }
}
