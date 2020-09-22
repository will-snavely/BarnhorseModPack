package org.barnhorse.sts.mod;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

@SpireInitializer
public class Horstery implements
        basemod.interfaces.OnStartBattleSubscriber,
        basemod.interfaces.PostBattleSubscriber,
        basemod.interfaces.RelicGetSubscriber,
        basemod.interfaces.PostDrawSubscriber,
        basemod.interfaces.PostDungeonInitializeSubscriber,
        basemod.interfaces.StartGameSubscriber,
        PatchEventSubscriber {
    public static final Logger logger = LogManager.getLogger(Horstery.class.getName());

    private EventPublisher publisher;
    private Configuration config;
    private boolean loggingDisabled;

    private static final Path modWorkingPath = Paths
            .get("mods", "etc", "barnhorse", "horstery")
            .toAbsolutePath();
    private static final Path runsPath = modWorkingPath.resolve("runs");
    private static final Path configPath = modWorkingPath.resolve("config.json");

    private static Predicate<GameEvent> defaultEventFilter;

    static {
        defaultEventFilter = e -> e.actNumber < 1;
    }

    public Horstery(Configuration config) {
        this.config = config;
        this.loggingDisabled = false;
    }

    public void dispose() {
        if (this.publisher != null) {
            this.publisher.stop();
        }
    }

    private static void applyDefaults(Configuration config) {
        if (config.getStorageEngine() == null) {
            config.setStorageEngine(StorageEngine.NITRITE);
        }

        if (config.getEventLogDirectory() == null ||
                config.getEventLogDirectory().trim().isEmpty()) {
            config.setEventLogDirectory(runsPath.toString());
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

    private static void createRunsDirectory(Path path) {
        File runsDir = path.toFile();
        runsDir.mkdirs();
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
            createRunsDirectory(Paths.get(config.getEventLogDirectory()));
        } catch (Exception e) {
            logger.error("Failed to initialize Horstery mod.", e);
            return;
        }

        Horstery mod = new Horstery(config);
        BaseMod.subscribe(mod);
        PatchEventManager.subscribe(mod);
    }


    private File getRunEventFile() {
        assert Settings.seed != null;
        assert AbstractDungeon.player != null;

        String seedString = SeedHelper.getString(Settings.seed);
        String playerClass = AbstractDungeon.player.chosenClass.name();
        String fileName = String.format("%s_%s.run", playerClass, seedString);
        return Paths.get(this.config.getEventLogDirectory(), fileName).toFile();
    }

    private EventConsumer createEventConsumer(File eventFile) {
        return getEventConsumer(this.config.getStorageEngine(), eventFile);
    }


    public void publishEvent(GameEvent event) {
        publishEvent(event, false, defaultEventFilter);
    }

    public void publishEvent(
            GameEvent event,
            boolean clobberEventFile,
            Predicate<GameEvent> filter) {
        if (this.loggingDisabled) {
            logger.debug("loggingDisabled == true, so skipping publishEvent");
            return;
        }

        event.timestamp = System.currentTimeMillis();
        event.floorNumber = AbstractDungeon.floorNum;
        event.actNumber = AbstractDungeon.actNum;

        if (filter != null && filter.test(event)) {
            return;
        }

        if (this.publisher == null) {
            File eventFile = getRunEventFile();
            if (eventFile.exists() && clobberEventFile) {
                if (!eventFile.delete()) {
                    logger.error("Failed to clobber event file monkaS!");
                    this.loggingDisabled = true;
                }
            }
            EventConsumer consumer = this.createEventConsumer(eventFile);
            this.publisher = new EventPublisher(consumer);
            consumer.setup();
            this.publisher.start();
        }

        this.publisher.publishEvent(event);
    }

    @Override
    public void receivePostDungeonInitialize() {
        publishEvent(RunStart.fromGameSettings(), true, defaultEventFilter);
    }

    @Override
    public void receiveStartGame() {
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        publishEvent(new BattleStart(room));
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
    }

    @Override
    public void onGameActionStart(AbstractGameAction action) {
    }

    @Override
    public void onGameActionDone(AbstractGameAction action) {
        if (action == null) {
            return;
        }
        if (action instanceof EnableEndTurnButtonAction) {
            publishEvent(new PlayerTurnStart(AbstractDungeon.player));
        }
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
