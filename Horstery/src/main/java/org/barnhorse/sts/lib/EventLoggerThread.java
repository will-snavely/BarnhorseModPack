package org.barnhorse.sts.lib;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.concurrent.BlockingQueue;

public class EventLoggerThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(EventLoggerThread.class.getName());

    private final BlockingQueue<GameEvent> eventQueue;
    private final PrintWriter logWriter;
    private String eventFilePath;
    private final Gson gson;

    // Note: caller is responsible for cleaning up the Writer resource.
    public EventLoggerThread(BlockingQueue<GameEvent> eventQueue, Writer writer) {
        assert eventQueue != null;

        this.eventQueue = eventQueue;
        this.logWriter = new PrintWriter(writer);
        this.gson = new Gson();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                this.processEvent(this.eventQueue.take());
            } catch (InterruptedException e) {
                logger.info("Event Logging Thread Interrupted!");
                Thread.currentThread().interrupt();
            }
        }
    }

    private void processEvent(GameEvent event) {
        this.logWriter.println(this.gson.toJson(event));
        this.logWriter.flush();
    }
}