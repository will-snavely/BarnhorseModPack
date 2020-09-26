package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.screens.GameOverStat;

public class Stat {
    public String label;
    public String description;
    public String value;

    public Stat() {
    }

    public Stat(GameOverStat stat) {
        this.label = stat.label;
        this.description = stat.description;
        this.value = stat.value;
    }
}
