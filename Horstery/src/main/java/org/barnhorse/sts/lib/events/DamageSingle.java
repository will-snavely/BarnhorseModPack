package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import org.barnhorse.sts.lib.model.Creature;
import org.barnhorse.sts.lib.util.ReflectionHelper;

public class DamageSingle extends GameEvent {
    public final static String key = "damage_single_target";

    public int baseDamage;
    public int finalDamage;
    public boolean isModified;
    public DamageInfo.DamageType damageType;
    public Creature source;
    public Creature target;

    public DamageSingle() {
        super(key, "A single target was damaged");
    }

    public DamageSingle(DamageAction action) {
        this();
        DamageInfo info = ReflectionHelper
                .<DamageInfo>tryGetFieldValue(action, "info", true)
                .orElse(null);
        this.baseDamage = info.base;
        this.finalDamage = info.output;
        this.isModified = info.isModified;
        this.damageType = info.type;
        if (action.source != null) {
            this.source = new Creature(action.source);
        }
        if (action.target != null) {
            this.target = new Creature(action.target);
        }
    }
}
