package org.barnhorse.sts.lib.events.model;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.barnhorse.sts.lib.util.ReflectionHelper;

import java.util.HashMap;
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
        this.moveSet = (Map<Integer, String>) ReflectionHelper
                .tryGetFieldValue(monster, "moveSet", true)
                .orElse(null);
        this.escaped = monster.escaped;
        this.escapeNext = monster.escapeNext;
        this.type = monster.type.name();
        this.cannotEscape = monster.cannotEscape;
    }
}
