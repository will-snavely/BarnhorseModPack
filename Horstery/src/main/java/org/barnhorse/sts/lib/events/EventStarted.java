package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.events.AbstractEvent;
import org.barnhorse.sts.lib.model.Event;

public class EventStarted extends GameEvent {
    public final static String key = "event_started";
    public Event event;

    public EventStarted() {
        super(key, "An event was started");
    }

    public EventStarted(AbstractEvent event) {
        this();
        this.event = new Event(event);
    }
}
