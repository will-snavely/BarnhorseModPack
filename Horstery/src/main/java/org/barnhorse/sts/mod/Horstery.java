package org.barnhorse.sts.mod;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.exordium.Cleric;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barnhorse.sts.lib.EventPublishingHandler;
import org.barnhorse.sts.lib.GameEventHandler;
import org.barnhorse.sts.patches.PatchEventManager;
import org.barnhorse.sts.patches.PatchEventSubscriber;

@SpireInitializer
public class Horstery implements
        basemod.interfaces.OnStartBattleSubscriber,
        basemod.interfaces.PostBattleSubscriber,
        PatchEventSubscriber {
    public static final Logger logger = LogManager.getLogger(Horstery.class.getName());

    private GameEventHandler handler;

    public Horstery() {
        handler = new EventPublishingHandler();
        Cleric e;
    }

    public static void initialize() {
        Horstery mod = new Horstery();
        BaseMod.subscribe(mod);
        PatchEventManager.subscribe(mod);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        this.handler.battleStart(abstractRoom);
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        this.handler.battleEnd(abstractRoom);
    }

    @Override
    public void onGameActionStart(AbstractGameAction action) {
        if (action != null) {
            logger.info("Game action started: " + action.getClass().getName());
            if (action instanceof UseCardAction) {
                UseCardAction useCardAction = (UseCardAction) action;
                handler.cardPlayed(useCardAction);
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
        handler.cardObtained(card);
    }

    @Override
    public void onCardRemoved(AbstractCard card) {
        // Stub
    }
}
