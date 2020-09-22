package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.barnhorse.sts.lib.model.Relic;

public class RelicAdded extends GameEvent {
    public final static String key = "relic_added";

    public Relic relic;

    public RelicAdded() {
        super(key, "Obtained a new relic.");
    }

    public RelicAdded(AbstractRelic relic) {
        this();
        this.relic = new Relic(relic);
    }
}
