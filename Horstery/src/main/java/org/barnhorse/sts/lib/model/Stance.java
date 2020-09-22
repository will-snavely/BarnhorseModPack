package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.stances.AbstractStance;

public class Stance {
    public String name;

    public Stance() {
    }

    public Stance(AbstractStance stance) {
        this.name = stance.name;
    }
}
