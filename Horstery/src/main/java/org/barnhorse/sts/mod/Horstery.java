package org.barnhorse.sts.mod;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.events.exordium.Cleric;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barnhorse.sts.lib.EventPublisher;
import org.barnhorse.sts.patches.PatchEventManager;
import org.barnhorse.sts.patches.PatchEventSubscriber;

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
        this.eventPublisher.battleStart(abstractRoom);
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        this.eventPublisher.battleEnd(abstractRoom);
    }

    @Override
    public void onGameActionStart(AbstractGameAction action) {
        if (action != null) {
            logger.info("Game action started: " + action.getClass().getName());
            if (action instanceof UseCardAction) {
                UseCardAction useCardAction = (UseCardAction) action;
                eventPublisher.cardPlayed(useCardAction);
            }
        }
    }

    @Override
    public void onGameActionDone(AbstractGameAction action) {
        if (action != null) {
            System.err.println("Game action done: " + action.getClass().getName());
        }
    }

    @Override
    public void onCardObtained(AbstractCard card) {
        eventPublisher.cardObtained(card);
    }

    @Override
    public void onCardRemoved(AbstractCard card) {
        eventPublisher.cardRemovedFromDeck(card);
    }
}
