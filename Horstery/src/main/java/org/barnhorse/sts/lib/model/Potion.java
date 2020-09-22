package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.potions.AbstractPotion;

public class Potion {
    public String name;

    public Potion() {
    }

    public Potion(AbstractPotion potion) {
        this.name = potion.name;
    }
}
