package org.barnhorse.sts.mod;

enum StorageEngine {
    FILE,
    NITRITE
}

public class Configuration {
    private String eventLogDirectory;
    private StorageEngine storageEngine;

    public Configuration() {
    }

    public String getEventLogDirectory() {
        return this.eventLogDirectory;
    }

    public StorageEngine getStorageEngine() {
        return this.storageEngine;
    }

    public void setEventLogDirectory(String eventLogDirectory) {
        this.eventLogDirectory = eventLogDirectory;
    }

    public void setStorageEngine(StorageEngine storageEngine) {
        this.storageEngine = storageEngine;
    }
}
