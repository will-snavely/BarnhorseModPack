package org.barnhorse.sts.lib.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barnhorse.sts.lib.events.GameEvent;
import org.barnhorse.sts.mod.RunId;

public class ConsoleConsumer extends EventConsumer {
    public static final Logger logger = LogManager.getLogger(ConsoleConsumer.class.getName());
    private ObjectMapper objectMapper;
    private RunId runId;

    public ConsoleConsumer(RunId id) {
        this.objectMapper = new ObjectMapper();
        this.runId = id;
    }

    @Override
    public void setup() {
    }

    @Override
    public void tearDown() {
    }

    @Override
    public void accept(GameEvent event) {
        try {
            logger.info("{} -- {}", this.runId, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize game event.", e);

        }
    }
}
