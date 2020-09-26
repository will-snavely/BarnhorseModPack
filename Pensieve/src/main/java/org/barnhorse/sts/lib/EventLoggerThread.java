package org.barnhorse.sts.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barnhorse.sts.lib.events.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class EventLoggerThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(EventLoggerThread.class.getName());

    private final BlockingQueue<GameEvent> eventQueue;
    private Consumer<GameEvent> consumer;

    public EventLoggerThread(BlockingQueue<GameEvent> eventQueue, Consumer<GameEvent> consumer) {
        assert eventQueue != null;
        this.eventQueue = eventQueue;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                this.consumer.accept(this.eventQueue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        logger.info("Interrupting the EventLogger thread.");
        this.onInterrupt();
    }

    private void onInterrupt() {
        List<GameEvent> remaining = new ArrayList<>();
        this.eventQueue.drainTo(remaining);
        for (GameEvent event : remaining) {
            this.consumer.accept(event);
        }
    }
}