package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.barnhorse.sts.lib.util.ReflectionHelper;

import java.util.Map;

public class Monster extends Creature {
    public Map<Integer, String> moveSet;
    public boolean escaped;
    public boolean escapeNext;
    public boolean cannotEscape;
    public boolean isMultiDmg;

    public int intentDmg;
    public int intentBaseDmg;
    public int intentMultiAmt;

    public String type;
    public String moveName;
    public String intent;

    public Monster(AbstractMonster monster) {
        super(monster);

        this.moveSet = ReflectionHelper
                .<Map<Integer, String>>tryGetFieldValue(monster, "moveSet", true)
                .orElse(null);

        this.escaped = monster.escaped;
        this.escapeNext = monster.escapeNext;
        this.cannotEscape = monster.cannotEscape;
        this.isMultiDmg = ReflectionHelper
                .<Boolean>tryGetFieldValue(monster, "isMultiDmg", true)
                .orElse(false);

        this.intentDmg = monster.getIntentDmg();
        this.intentBaseDmg = monster.getIntentBaseDmg();
        this.intentMultiAmt = ReflectionHelper
                .<Integer>tryGetFieldValue(monster, "intentMultiAmt", true)
                .orElse(-1);

        this.type = monster.type.name();
        this.moveName = monster.moveName;
        this.intent = monster.intent.name();
    }
}
