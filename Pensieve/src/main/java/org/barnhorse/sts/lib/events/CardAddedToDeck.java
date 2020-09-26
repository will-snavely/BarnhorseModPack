package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.model.Card;

public class CardAddedToDeck extends GameEvent {
    public final static String key = "card_added";

    public Card card;

    public CardAddedToDeck() {
        super(key, "Card added to master deck");
    }

    public CardAddedToDeck(AbstractCard card) {
        this();
        this.card = new Card(card);
    }
}

