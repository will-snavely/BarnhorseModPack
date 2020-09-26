package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.model.Card;

public class CardDraw extends GameEvent {
    public final static String key = "card_draw";

    public Card card;

    public CardDraw() {
        super(key, "Card drawn");
    }

    public CardDraw(AbstractCard card) {
        this();
        this.card = new Card(card);
    }
}
