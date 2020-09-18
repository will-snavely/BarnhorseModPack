package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.model.Card;

public class CardDraw extends GameEvent {
    public static String key = "card_draw";

    public Card card;

    public CardDraw(AbstractCard card) {
        super(key, "Card drawn");
        this.card = new Card(card);
    }
}
