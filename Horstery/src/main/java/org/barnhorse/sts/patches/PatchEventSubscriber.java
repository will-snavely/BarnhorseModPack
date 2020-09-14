package org.barnhorse.sts.patches;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface PatchEventSubscriber {
    void onGameActionStart(AbstractGameAction action);

    void onGameActionDone(AbstractGameAction action);

    void onCardObtained(AbstractCard card);

    void onCardRemoved(AbstractCard card);
}
