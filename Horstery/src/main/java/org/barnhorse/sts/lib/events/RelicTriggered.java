package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.barnhorse.sts.lib.model.Relic;
import org.barnhorse.sts.lib.util.ReflectionHelper;

public class RelicTriggered extends GameEvent {
    public final static String key = "relic_activated";

    public Relic relic;

    public RelicTriggered() {
        super(key, "A relic was activated");
    }

    public RelicTriggered(AbstractRelic relic) {
        this();
        this.relic = new Relic(relic);
    }

    public RelicTriggered(RelicAboveCreatureAction action) {
        this();
        ReflectionHelper.
                <AbstractRelic>tryGetFieldValue(action, "relic", true)
                .ifPresent(r -> this.relic = new Relic(r));
    }
}
