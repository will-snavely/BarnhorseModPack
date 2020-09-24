package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.barnhorse.sts.lib.model.Creature;
import org.barnhorse.sts.lib.model.Monster;

public class MonsterDamaged extends GameEvent {
    public final static String key = "monster_damaged";

    public int baseDamage;
    public int outputDamage;
    public int actualDamage;
    public boolean isModified;
    public DamageInfo.DamageType damageType;
    public Creature source;
    public Monster target;

    public MonsterDamaged() {
        super(key, "A monster was damaged");
    }

    public MonsterDamaged(AbstractMonster monster, DamageInfo info, int actualDamage) {
        this();
        this.baseDamage = info.base;
        this.outputDamage = info.output;
        this.actualDamage = actualDamage;
        this.isModified = info.isModified;
        this.damageType = info.type;
        if (info.owner != null) {
            this.source = new Creature(info.owner);
        }
        this.target = new Monster(monster);
    }
}
