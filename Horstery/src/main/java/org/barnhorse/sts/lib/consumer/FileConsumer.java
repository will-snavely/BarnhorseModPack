package org.barnhorse.sts.lib.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.barnhorse.sts.lib.events.GameEvent;

import java.io.*;

public class FileConsumer extends EventConsumer {
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
            this.printWriter = new PrintWriter(new FileWriter(this.file));
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
            e.printStackTrace();
        }
    }
}
