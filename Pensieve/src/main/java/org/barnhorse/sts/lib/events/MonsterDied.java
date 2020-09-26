package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.barnhorse.sts.lib.model.Monster;

public class MonsterDied extends GameEvent {
    public final static String key = "monster_died";

    public Monster monster;

    public MonsterDied() {
        super(key, "A monster died");
    }

    public MonsterDied(AbstractMonster monster) {
        this();
        this.monster = new Monster(monster);
    }
}
