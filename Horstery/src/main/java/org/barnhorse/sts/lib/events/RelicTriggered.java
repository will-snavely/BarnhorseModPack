package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.barnhorse.sts.lib.model.Relic;
import org.barnhorse.sts.patches.util.RelicEffect;

import java.util.HashMap;
import java.util.Map;

public class RelicTriggered extends GameEvent {
    public final static String key = "relic_activated";

    public Relic relic;
    public Map<RelicEffect, Integer> summary;

    public RelicTriggered() {
        super(key, "A relic was activated");
    }

    public RelicTriggered(AbstractRelic relic, Map<RelicEffect, Integer> summary) {
        this();
        this.relic = new Relic(relic);
        this.summary = new HashMap<>(summary);
    }
}
