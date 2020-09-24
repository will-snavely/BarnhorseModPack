package org.barnhorse.sts.mod;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barnhorse.sts.lib.EventPublisher;
import org.barnhorse.sts.lib.consumer.EventConsumer;
import org.barnhorse.sts.lib.consumer.FileConsumer;
import org.barnhorse.sts.lib.consumer.NitriteConsumer;
import org.barnhorse.sts.lib.events.*;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.dispatch.PatchEventSubscriber;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.function.Predicate;

@SpireInitializer
public class Horstery implements
        basemod.interfaces.OnStartBattleSubscriber,
        basemod.interfaces.RelicGetSubscriber,
        basemod.interfaces.PostDrawSubscriber,
        basemod.interfaces.PostDungeonInitializeSubscriber,
        basemod.interfaces.StartGameSubscriber,
        PatchEventSubscriber {

    enum ModState {
        IN_RUN,
        OUT_OF_RUN,
        ERROR
    }

    public static final Logger logger = LogManager.getLogger(Horstery.class.getName());

    private static final Path modWorkingPath = Paths
            .get("mods", "etc", "barnhorse", "horstery")
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
    private Throwable error;

    public Horstery(Configuration config) {
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

    private static EventConsumer getEventConsumer(StorageEngine storageEngine, File dest) {
        switch (storageEngine) {
            case FILE:
                return new FileConsumer(dest);
            case NITRITE:
                return new NitriteConsumer(dest);
            default:
                throw new RuntimeException("Failed to initialize a consumer.");
        }
    }

    public static void initialize() {
        Configuration config;

        try {
            config = createConfiguration();
            createDirectories(config);
        } catch (Exception e) {
            logger.error("Failed to initialize Horstery mod.", e);
            return;
        }

        Horstery mod = new Horstery(config);
        BaseMod.subscribe(mod);
        PatchEventManager.subscribe(mod);
    }

    private Path getRunEventPath(Long seed, AbstractPlayer player) {
        assert seed != null;
        assert player != null;

        String seedString = SeedHelper.getString(seed);
        String playerClass = player.chosenClass.name();
        String fileName = String.format("%s_%s.run", seedString, playerClass);
        return Paths.get(this.config.getEventLogDirectory(), fileName);
    }

    private EventConsumer createEventConsumer(File eventFile) {
        return getEventConsumer(this.config.getStorageEngine(), eventFile);
    }

    private void shutDownLogger() {
        if (this.publisher != null) {
            this.publisher.stop();
            this.publisher = null;
        }
    }

    private void enterRun(Long seed, AbstractPlayer player) {
        if (this.state == ModState.ERROR) {
            logger.warn("Tried to enter a run in the ERROR state, refusing to do so.");
        }

        if (this.state == ModState.IN_RUN) {
            logger.warn("Tried to enter a run, but one is already active.");
            return;
        }

        this.state = ModState.IN_RUN;

        if (this.publisher == null) {
            File eventFile = this.getRunEventPath(seed, player).toFile();
            if (eventFile.exists()) {
                if (!eventFile.delete()) {
                    logger.error("Failed to clobber event file!");
                    this.enterErrorState();
                }
            }

            EventConsumer consumer = this.createEventConsumer(eventFile);
            this.publisher = new EventPublisher(consumer);
            consumer.setup();
            this.publisher.start();
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
        this.shutDownLogger();
    }

    private void enterErrorState() {
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
            if (this.error != null) {
                logger.warn("\tError: {}", this.error.getMessage());
            }
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

    public void publishOneOffEvent(GameEvent event, File file) {
        assert event != null;
        assert file != null;

        EventConsumer consumer = null;
        try {
            consumer = this.createEventConsumer(file);
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

    private void archiveRun(Long seed, AbstractPlayer player) throws IOException {
        Path currentLogPath = this.getRunEventPath(seed, player);
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
    }

    private void deleteRun(Long seed, AbstractPlayer player) {
        File runFile = this.getRunEventPath(seed, player).toFile();
        if (!runFile.delete()) {
            throw new RuntimeException(
                    "Failed to delete run file at: " + runFile.getAbsolutePath()
            );
        }
    }

    @Override
    public void receivePostDungeonInitialize() {
        this.enterRun(Settings.seed, AbstractDungeon.player);
        publishEvent(RunStart.fromGameSettings());
    }

    @Override
    public void receiveStartGame() {
        this.enterRun(Settings.seed, AbstractDungeon.player);
    }

    @Override
    public void onAbandonRun(AbstractPlayer player) {
        Long seed;

        if (state == ModState.OUT_OF_RUN) {
            SaveFile saveFile = SaveAndContinue.loadSaveFile(player.chosenClass);
            seed = saveFile.seed;
            File runFile = getRunEventPath(saveFile.seed, player).toFile();
            publishOneOffEvent(new RunAbandoned(), runFile);
        } else {
            seed = Settings.seed;
            publishEvent(new RunAbandoned());
            this.exitRun();
        }

        try {
            this.archiveRun(seed, player);
            this.deleteRun(seed, player);
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
            publishEvent(new PlayerDied(player, stats));
            this.exitRun();
            try {
                this.archiveRun(Settings.seed, player);
                this.deleteRun(Settings.seed, player);
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
            this.archiveRun(Settings.seed, player);
            this.deleteRun(Settings.seed, player);
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
    public void receiveOnBattleStart(AbstractRoom room) {
        publishEvent(new BattleStart(room));
    }

    @Override
    public void onGameActionStart(AbstractGameAction action) {
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
        publishEvent(new RelicAdded(abstractRelic));
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
