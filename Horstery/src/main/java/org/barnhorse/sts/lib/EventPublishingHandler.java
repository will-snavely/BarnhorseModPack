package org.barnhorse.sts.lib;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class EventPublishingHandler implements GameEventHandler {
    @Override
    public void battleStart(AbstractRoom room) {
    }

    @Override
    public void battleEnd(AbstractRoom room) {
    }

    @Override
    public void cardPlayed(UseCardAction action) {
        System.err.println(action);
    }

    @Override
    public void cardObtained(AbstractCard card) {
    }

    @Override
    public void cardRemovedFromDeck(AbstractCard card) {
    }
}
