package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.blights.AbstractBlight;

public class Blight {
    public String name;

    public Blight() {
    }

    public Blight(AbstractBlight blight) {
        this.name = blight.name;
    }
}
