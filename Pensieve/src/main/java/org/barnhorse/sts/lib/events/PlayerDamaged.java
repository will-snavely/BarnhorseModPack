package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import org.barnhorse.sts.lib.model.Creature;

public class PlayerDamaged extends GameEvent {
    public final static String key = "player_damaged";

    public int baseDamage;
    public int outputDamage;
    public int actualDamage;
    public boolean isModified;
    public DamageInfo.DamageType damageType;
    public Creature source;

    public PlayerDamaged() {
        super(key, "The player was damaged");
    }

    public PlayerDamaged(AbstractPlayer player, DamageInfo info, int actualDamage) {
        this();
        this.baseDamage = info.base;
        this.outputDamage = info.output;
        this.actualDamage = actualDamage;
        this.isModified = info.isModified;
        this.damageType = info.type;
        if (info.owner != null) {
            this.source = new Creature(info.owner);
        }
    }
}
