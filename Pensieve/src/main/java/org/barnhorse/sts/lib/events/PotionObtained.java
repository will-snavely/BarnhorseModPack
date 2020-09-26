package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.potions.AbstractPotion;
import org.barnhorse.sts.lib.model.Potion;

public class PotionObtained extends GameEvent {
    public final static String key = "potion_obtained";

    public Potion potion;

    public PotionObtained() {
        super(key, "Potion obtained");
    }

    public PotionObtained(AbstractPotion potion) {
        this();
        this.potion = new Potion(potion);
    }
}
