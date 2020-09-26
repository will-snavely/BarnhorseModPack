package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.barnhorse.sts.lib.model.Relic;

public class RelicObtained extends GameEvent {
    public final static String key = "relic_obtained";

    public Relic relic;

    public RelicObtained() {
        super(key, "Obtained a new relic");
    }

    public RelicObtained(AbstractRelic relic) {
        this();
        this.relic = new Relic(relic);
    }
}
