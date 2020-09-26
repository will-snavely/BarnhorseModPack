package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.core.AbstractCreature;
import org.barnhorse.sts.lib.model.Creature;

public class BlockLost extends GameEvent {
    public final static String key = "block_lost";

    public int amount;
    public int startingBlock;
    public int endingBlock;
    public int delta;
    public Creature target;

    public BlockLost() {
        super(key, "A creature lost block");
    }

    public BlockLost(
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
