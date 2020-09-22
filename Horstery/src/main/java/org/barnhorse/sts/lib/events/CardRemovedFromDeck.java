package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.model.Card;

public class CardRemovedFromDeck extends GameEvent {
    public final static String key = "card_removed";

    public Card card;

    public CardRemovedFromDeck() {
        super(key, "Card removed from master deck");
    }

    public CardRemovedFromDeck(AbstractCard card) {
        this();
        this.card = new Card(card);
    }
}
