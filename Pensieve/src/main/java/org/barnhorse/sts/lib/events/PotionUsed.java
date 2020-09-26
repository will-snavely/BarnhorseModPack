package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import org.barnhorse.sts.lib.model.Creature;
import org.barnhorse.sts.lib.model.Potion;

public class PotionUsed extends GameEvent {
    public final static String key = "potion_used";

    public Potion potion;
    public Creature target;

    public PotionUsed() {
        super(key, "Potion used");
    }

    public PotionUsed(AbstractPotion potion, AbstractCreature target) {
        this();
        this.potion = new Potion(potion);
        if (target != null) {
            this.target = new Creature(target);
        }
    }
}
