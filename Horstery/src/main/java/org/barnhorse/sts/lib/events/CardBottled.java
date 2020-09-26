package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.model.Card;

public class CardBottled extends GameEvent {
    public final static String key = "card_bottled";
    public Card card;

    public CardBottled() {
        super(key, "A card was bottled");
    }

    public CardBottled(AbstractCard card) {
        this();
        this.card = new Card(card);
    }
}
