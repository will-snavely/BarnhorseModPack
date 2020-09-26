package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.events.AbstractEvent;
import org.barnhorse.sts.lib.util.ReflectionHelper;


public class Event {
    public String id;

    public Event() {
    }

    public Event(AbstractEvent event) {
        this.id = ReflectionHelper
                .<String>tryGetFieldValue(event, "ID", true)
                .orElse(null);

    }
}