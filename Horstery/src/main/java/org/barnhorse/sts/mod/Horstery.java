package org.barnhorse.sts.mod;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barnhorse.sts.lib.EventPublisher;
import org.barnhorse.sts.lib.events.CardAddedToDeck;
import org.barnhorse.sts.lib.events.CardRemovedFromDeck;
import org.barnhorse.sts.lib.events.CardUsed;
import org.barnhorse.sts.lib.util.ReflectionHelper;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.dispatch.PatchEventSubscriber;

import java.io.*;

@SpireInitializer
public class Horstery implements
        basemod.interfaces.OnStartBattleSubscriber,
        basemod.interfaces.PostBattleSubscriber,
        PatchEventSubscriber {
    public static final Logger logger = LogManager.getLogger(Horstery.class.getName());

    private EventPublisher eventPublisher;

    private static final String eventLog = "saves" + File.separator + "testLog";

    public Horstery(Writer eventWriter) {
        this.eventPublisher = new EventPublisher(eventWriter);
        eventPublisher.start();
    }

    public static void initialize() {
        try {
            Writer eventWriter = new BufferedWriter(new FileWriter(eventLog));
            Horstery mod = new Horstery(eventWriter);
            BaseMod.subscribe(mod);
            PatchEventManager.subscribe(mod);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
    }

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
}
