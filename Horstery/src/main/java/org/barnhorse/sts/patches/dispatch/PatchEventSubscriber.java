package org.barnhorse.sts.patches.dispatch;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public interface PatchEventSubscriber {
    void onGameActionStart(AbstractGameAction action);

    void onGameActionDone(AbstractGameAction action);

    void onCardObtained(AbstractCard card);

    void onCardRemoved(CardGroup deck, AbstractCard card);
}
