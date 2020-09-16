package org.barnhorse.sts.lib;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public interface GameEventHandler {
    void battleStart(AbstractRoom room);

    void battleEnd(AbstractRoom room);

    void cardPlayed(UseCardAction action);

    void cardObtained(AbstractCard card);

    void cardRemovedFromDeck(AbstractCard card);
}
