package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import org.barnhorse.sts.lib.model.Creature;
import org.barnhorse.sts.lib.util.ReflectionHelper;

public class DamageAll extends GameEvent {
    public final static String key = "damage_all";

    public int[] damage;
    public int baseDamage;
    public DamageInfo.DamageType damageType;
    public Creature source;
    public Creature target;

    public DamageAll() {
        super(key, "All enemies were damaged");
    }

    public DamageAll(DamageAllEnemiesAction action) {
        this();

        this.damage = action.damage;
        this.damageType = action.damageType;
        this.baseDamage = ReflectionHelper
                .<Integer>tryGetFieldValue(action, "baseDamage", true)
                .orElse(-1);

        if (action.source != null) {
            this.source = new Creature(action.source);
        }
        if (action.target != null) {
            this.target = new Creature(action.target);
        }
    }
}
