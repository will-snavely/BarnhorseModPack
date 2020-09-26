package org.barnhorse.sts.lib.consumer;

import org.barnhorse.sts.lib.events.GameEvent;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.File;

public class NitriteConsumer extends EventConsumer {
    private File file;
    private Nitrite db;
    private ObjectRepository<GameEvent> eventStore;

    public NitriteConsumer(File file) {
        assert file != null;
        this.file = file;
    }

    @Override
    public void setup() {
        this.db = Nitrite.builder()
                .filePath(this.file)
                .openOrCreate();
        this.eventStore = db.getRepository(GameEvent.class);
    }

    @Override
    public void tearDown() {
        this.eventStore.close();
        this.db.close();
    }

    @Override
    public void accept(GameEvent gameEvent) {
        assert this.eventStore != null;
        this.eventStore.insert(gameEvent);
    }
}
