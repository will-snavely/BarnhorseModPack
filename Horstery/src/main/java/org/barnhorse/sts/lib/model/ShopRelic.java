package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.shop.StoreRelic;

public class ShopRelic {
    public Relic relic;
    public int price;

    public ShopRelic() {
    }

    public ShopRelic(StoreRelic relic) {
        this.relic = new Relic(relic.relic);
        this.price = relic.price;
    }
}
