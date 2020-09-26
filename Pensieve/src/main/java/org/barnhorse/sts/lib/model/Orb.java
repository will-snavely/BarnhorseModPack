package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class Orb {
    public String name;

    public Orb() {
    }

    public Orb(AbstractOrb orb) {
        this.name = orb.name;
    }
}
