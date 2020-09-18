package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.events.model.Card;

public class CardRemovedFromDeck extends GameEvent {
    public static String key = "card_removed";

    public Card card;

    public CardRemovedFromDeck(AbstractCard card) {
        super(key, "Card Removed");
        this.card = new Card(card);
    }
}
