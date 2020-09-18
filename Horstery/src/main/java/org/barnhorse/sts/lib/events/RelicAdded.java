package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RelicAdded extends GameEvent {
    public static String key = "relic_added";

    public RelicAdded(AbstractRelic relic) {
        super(key, "Obtained a new relic.");
    }
}
