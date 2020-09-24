package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.model.Card;

public class PurchasePurge extends GameEvent {
    public final static String key = "purchase_purge";

    public Card card;
    public int price;

    public PurchasePurge() {
        super(key, "Purged a card at the shop");
    }

    public PurchasePurge(AbstractCard card, int price) {
        this();
        this.card = new Card(card);
        this.price = price;
    }
}
