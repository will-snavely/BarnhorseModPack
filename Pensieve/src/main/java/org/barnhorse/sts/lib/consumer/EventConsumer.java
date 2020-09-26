package org.barnhorse.sts.lib.consumer;

import org.barnhorse.sts.lib.events.GameEvent;

import java.util.function.Consumer;

public abstract class EventConsumer implements Consumer<GameEvent> {
    public abstract void setup();

    public abstract void tearDown();
}
