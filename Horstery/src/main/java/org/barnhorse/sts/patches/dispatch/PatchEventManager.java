package org.barnhorse.sts.patches.dispatch;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

public class PatchEventManager {
    private static final List<PatchEventSubscriber> subscribers = new ArrayList<>();

    private PatchEventManager() {
    }

    public static void subscribe(PatchEventSubscriber sub) {
        subscribers.add(sub);
    }

    public static void dispatchGameActionStart(AbstractGameAction action) {
        subscribers.forEach(sub -> sub.onGameActionStart(action));
    }

    public static void dispatchGameActionDone(AbstractGameAction action) {
        subscribers.forEach(sub -> sub.onGameActionDone(action));
    }

    public static void dispatchCardObtained(CardGroup deck, AbstractCard card) {
        subscribers.forEach(sub -> sub.onCardObtained(card));
    }

    public static void dispatchCardRemoved(CardGroup deck, AbstractCard card) {
        subscribers.forEach(sub -> sub.onCardRemoved(deck, card));
    }

    public static void dispatchCardUsed(AbstractPlayer player, AbstractCard card, AbstractMonster monster, int energyOnUse) {
        subscribers.forEach(sub -> sub.onCardUsed(player, card, monster, energyOnUse));
    }

    public static void dispatchGameDisposed() {
        subscribers.forEach(sub -> sub.onDispose());

    }

    public static void dispatchAbandonedRun(AbstractPlayer player) {
        subscribers.forEach(sub -> sub.onAbandonRun(player));
    }

    public static void dispatchSaveAndQuit() {
        subscribers.forEach(sub -> sub.onSaveAndQuit());
    }

    public static void dispatchPlayerTurnStart(AbstractPlayer player) {
        subscribers.forEach(sub -> sub.onPlayerTurnStart(player));
    }
}
