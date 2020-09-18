package org.barnhorse.sts.lib;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.barnhorse.sts.lib.events.CardRemovedFromDeck;
import org.barnhorse.sts.lib.events.CardUsed;
import org.barnhorse.sts.lib.events.GameEvent;

import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class EventPublisher {
    private Thread loggerThread;
    private BlockingQueue<GameEvent> eventQueue;
    private Writer eventWriter;

    public EventPublisher(Writer writer) {
        assert writer != null;
        this.eventQueue = new LinkedBlockingDeque<>();
        this.eventWriter = writer;
    }

    public void start() {
        if (this.loggerThread == null || !this.loggerThread.isAlive()) {
            this.spawnLoggerThread();
        }
    }

    private void spawnLoggerThread() {
        this.loggerThread = new Thread(new EventLoggerThread(eventQueue, eventWriter));
        this.loggerThread.start();
    }

    public void publishEvent(GameEvent event) {
        try {
            event.timestamp = System.currentTimeMillis();
            this.eventQueue.put(event);
        } catch (InterruptedException e) {
            // TODO: Figure out what to do with this exception
            e.printStackTrace();
        }
    }
}
