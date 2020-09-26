package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.shop.StorePotion;
import org.barnhorse.sts.lib.model.Potion;

public class PurchasePotion extends GameEvent {
    public final static String key = "purchase_potion";

    public Potion potion;
    public int price;

    public PurchasePotion() {
        super(key, "Purchased a potion");
    }

    public PurchasePotion(StorePotion potion) {
        this();
        this.potion = new Potion(potion.potion);
        this.price = potion.price;
    }
}
