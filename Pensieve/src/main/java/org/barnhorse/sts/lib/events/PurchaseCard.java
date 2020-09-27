package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.model.Card;

public class PurchaseCard extends GameEvent {
    public final static String key = "purchase_card";

    public Card card;
    public int price;

    public PurchaseCard() {
        super(key, "Purchased a card");
    }

    public PurchaseCard(AbstractCard card, int price) {
        this();
        this.card = new Card(card);
        this.price = price;
    }
}
