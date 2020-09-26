package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.shop.StoreRelic;
import org.barnhorse.sts.lib.model.Relic;

public class PurchaseRelic extends GameEvent {
    public final static String key = "purchase_relic";

    public Relic relic;
    public int price;

    public PurchaseRelic() {
        super(key, "Purchased a relic");
    }

    public PurchaseRelic(StoreRelic relic) {
        this();
        this.relic = new Relic(relic.relic);
        this.price = relic.price;
    }
}
