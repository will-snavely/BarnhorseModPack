package org.barnhorse.sts.lib;

import com.badlogic.gdx.Game;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import jdk.jfr.Event;

import java.io.Writer;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class EventPublisher implements GameEventHandler {
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

    private void publishEvent(GameEvent event) {
        try {
            this.eventQueue.put(event);
        } catch (InterruptedException e) {
            // TODO: Figure out what to do with this exception
            e.printStackTrace();
        }
    }

    @Override
    public void battleStart(AbstractRoom room) {
        this.publishEvent(new GameEvent("Battle Start"));
    }

    @Override
    public void battleEnd(AbstractRoom room) {
        this.publishEvent(new GameEvent("Battle End"));
    }

    @Override
    // TODO: Can't use UseCardAction here
    public void cardPlayed(UseCardAction action) {
        this.publishEvent(new GameEvent("Used Card"));
        System.err.println(action);
    }

    @Override
    public void cardObtained(AbstractCard card) {
        this.publishEvent(new GameEvent("Card Obtained: " + card.name));
    }

    @Override
    public void cardRemovedFromDeck(AbstractCard card) {
        this.publishEvent(new GameEvent("Card Removed"));
    }
}
