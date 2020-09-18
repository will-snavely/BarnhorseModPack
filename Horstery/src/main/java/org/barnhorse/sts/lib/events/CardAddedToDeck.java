package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.model.Card;

public class CardAddedToDeck extends GameEvent {
    public static String key = "card_added";

    public Card card;

    public CardAddedToDeck(AbstractCard card) {
        super(key, "Card added to master deck");
        this.card = new Card(card);
    }
}

