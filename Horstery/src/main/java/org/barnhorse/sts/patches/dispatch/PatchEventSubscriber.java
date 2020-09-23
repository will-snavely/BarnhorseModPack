package org.barnhorse.sts.patches.dispatch;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface PatchEventSubscriber {
    void onGameActionStart(AbstractGameAction action);

    void onGameActionDone(AbstractGameAction action);

    void onCardObtained(AbstractCard card);

    void onCardRemoved(CardGroup deck, AbstractCard card);

    void onCardUsed(AbstractPlayer player, AbstractCard card, AbstractMonster monster, int energyOnUse);

    void onDispose();

    void onAbandonRun(AbstractPlayer player);

    void onSaveAndQuit();

    void onPlayerTurnStart(AbstractPlayer player);
}
