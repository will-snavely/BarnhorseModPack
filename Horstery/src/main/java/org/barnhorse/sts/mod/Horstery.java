package org.barnhorse.sts.mod;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barnhorse.sts.lib.EventPublisher;
import org.barnhorse.sts.lib.events.*;
import org.barnhorse.sts.lib.util.ReflectionHelper;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.dispatch.PatchEventSubscriber;
import sun.security.krb5.Config;

import java.io.*;

@SpireInitializer
public class Horstery implements
        basemod.interfaces.OnStartBattleSubscriber,
        basemod.interfaces.PostBattleSubscriber,
        basemod.interfaces.RelicGetSubscriber,
        PatchEventSubscriber {
    public static final Logger logger = LogManager.getLogger(Horstery.class.getName());

    private EventPublisher eventPublisher;

    private static final String configPath = "mods" + File.separator
            + "config" + File.separator
            + "barnhorse" + File.separator
            + "horstery" + File.separator
            + "config.json";

    private static Configuration config;

    public Horstery(Writer eventWriter) {
        this.eventPublisher = new EventPublisher(eventWriter);
        eventPublisher.start();
    }

    public static void initialize() {
        config = new Configuration();

        File configFile = new File(configPath);
        if (configFile.exists()) {
            try {
                Gson gson = new Gson();
                config = gson.fromJson(new FileReader(configFile), Configuration.class);
            } catch (IOException ioe) {
                logger.error("Failed to load configuration file at: " + configPath);
            }
        }
        try {
            Writer eventWriter = new BufferedWriter(new FileWriter(config.getEventLogPath()));
            Horstery mod = new Horstery(eventWriter);
            BaseMod.subscribe(mod);
            PatchEventManager.subscribe(mod);
        } catch (IOException e) {
            e.printStackTrace();
            AbstractCard c;
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        eventPublisher.publishEvent(new BattleStart(room));
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
    }

    // EndOfTurnAction
    // EnableEndTurnButtonAction

    @Override
    public void onGameActionStart(AbstractGameAction action) {
        if (action != null) {
            logger.info("Game action started: " + action.getClass().getName());
            if (action instanceof UseCardAction) {
                UseCardAction useCardAction = (UseCardAction) action;
                AbstractCard card = ReflectionHelper
                        .<AbstractCard>tryGetFieldValue(useCardAction, "targetCard", true)
                        .orElse(null);
                if (card == null) {
                    logger.warn("Failed to determine which card was played.");
                }
                eventPublisher.publishEvent(new CardUsed(card, useCardAction.target));
            }
        }
    }

    @Override
    public void onGameActionDone(AbstractGameAction action) {

    }

    @Override
    public void onCardObtained(AbstractCard card) {
        eventPublisher.publishEvent(new CardAddedToDeck(card));
    }

    @Override
    public void onCardRemoved(CardGroup deck, AbstractCard card) {
        eventPublisher.publishEvent(new CardRemovedFromDeck(card));
    }

    @Override
    public void receiveRelicGet(AbstractRelic abstractRelic) {
        eventPublisher.publishEvent(new RelicAdded(abstractRelic));
    }
}
