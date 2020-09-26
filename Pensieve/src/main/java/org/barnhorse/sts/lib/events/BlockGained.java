package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.core.AbstractCreature;
import org.barnhorse.sts.lib.model.Creature;

public class BlockGained extends GameEvent {
    public final static String key = "block_gained";

    public int amount;
    public int startingBlock;
    public int endingBlock;
    public int delta;
    public Creature target;

    public BlockGained() {
        super(key, "A creature gained block");
    }

    public BlockGained(
            AbstractCreature creature,
            int amount,
            int startingBlock,
            int endingBlock,
            int delta) {
        this();
        this.amount = amount;
        this.startingBlock = startingBlock;
        this.endingBlock = endingBlock;
        this.delta = delta;
        this.target = new Creature(creature);
    }
}
