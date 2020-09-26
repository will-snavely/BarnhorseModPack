package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.shop.StorePotion;

public class ShopPotion {
    public Potion potion;
    public int price;

    public ShopPotion() {
    }

    public ShopPotion(StorePotion potion) {
        this.potion = new Potion(potion.potion);
        this.price = potion.price;
    }
}
