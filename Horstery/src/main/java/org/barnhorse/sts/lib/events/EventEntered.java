package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.events.AbstractEvent;
import org.barnhorse.sts.lib.model.Event;

public class EventEntered extends GameEvent {
    public final static String key = "event_entered";
    public Event event;

    public EventEntered() {
        super(key, "An event was entered");
    }

    public EventEntered(AbstractEvent event) {
        this();
        this.event = new Event(event);
    }
}
