package org.barnhorse.sts.lib.events;

public class GoldGained extends GameEvent {
    public final static String key = "gold_gained";

    public int amount;

    public GoldGained() {
        super(key, "Gold gained");
    }

    public GoldGained(int amount, int newTotal) {
        this();
        this.amount = amount;
    }
}
