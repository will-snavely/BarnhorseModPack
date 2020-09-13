package org.barnhorse.sts.patches;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

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

    public static void dispatchCardObtained(AbstractCard card) {
        subscribers.forEach(sub -> sub.onCardObtained(card));
    }
}
