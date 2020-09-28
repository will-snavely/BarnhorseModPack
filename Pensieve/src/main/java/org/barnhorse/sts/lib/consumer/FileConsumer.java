package org.barnhorse.sts.lib.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barnhorse.sts.lib.events.GameEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileConsumer extends EventConsumer {
    public static final Logger logger = LogManager.getLogger(FileConsumer.class.getName());

    private PrintWriter printWriter;
    private File file;
    private ObjectMapper objectMapper;

    public FileConsumer(File file) {
        assert file != null;
        this.file = file;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void setup() {
        try {
            this.printWriter = new PrintWriter(new FileWriter(this.file, true));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    public void tearDown() {
        this.printWriter.flush();
        this.printWriter.close();
    }

    @Override
    public void accept(GameEvent gameEvent) {
        assert this.printWriter != null;
        try {
            this.printWriter.println(objectMapper.writeValueAsString(gameEvent));
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize game event.", e);
        }
    }
}
