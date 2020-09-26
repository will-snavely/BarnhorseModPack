package org.barnhorse.sts.lib;

import org.barnhorse.sts.lib.consumer.EventConsumer;
import org.barnhorse.sts.lib.events.GameEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class EventPublisher {
    private Thread loggerThread;
    private BlockingQueue<GameEvent> eventQueue;
    private EventConsumer consumer;

    public EventPublisher(EventConsumer consumer) {
        assert consumer != null;
        this.eventQueue = new LinkedBlockingDeque<>();
        this.consumer = consumer;
    }

    public void start() {
        if (this.loggerThread == null || !this.loggerThread.isAlive()) {
            this.spawnLoggerThread();
        }
    }

    private void spawnLoggerThread() {
        this.loggerThread = new Thread(new EventLoggerThread(eventQueue, consumer));
        this.loggerThread.start();
    }

    public void publishEvent(GameEvent event) {
        try {
            this.eventQueue.put(event);
        } catch (InterruptedException e) {
            // TODO: Figure out what to do with this exception
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            this.loggerThread.interrupt();
            this.loggerThread.join();
            this.consumer.tearDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
