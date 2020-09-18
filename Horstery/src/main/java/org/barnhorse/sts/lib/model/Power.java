package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.powers.AbstractPower;

public class Power {
    public String name;
    public String ID;
    public String type;
    public int priority;
    public int amount;

    public Power(AbstractPower power) {
        this.name = power.name;
        this.ID = power.ID;
        this.type = power.type.name();
        this.priority = power.priority;
        this.amount = power.amount;
    }
}
