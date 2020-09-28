package org.barnhorse.sts.mod;

enum StorageEngine {
    FILE,
    NITRITE,
    CONSOLE
}

public class Configuration {
    private String eventLogDirectory;
    private String archiveDirectory;
    private StorageEngine storageEngine;
    private boolean verbose;

    public Configuration() {
    }

    public String getEventLogDirectory() {
        return this.eventLogDirectory;
    }

    public String getArchiveDirectory() {
        return this.archiveDirectory;
    }

    public StorageEngine getStorageEngine() {
        return this.storageEngine;
    }

    public boolean isVerbose() {
        return this.verbose;
    }

    public void setEventLogDirectory(String eventLogDirectory) {
        this.eventLogDirectory = eventLogDirectory;
    }

    public void setArchiveDirectory(String archiveDirectory) {
        this.archiveDirectory = archiveDirectory;
    }

    public void setStorageEngine(StorageEngine storageEngine) {
        this.storageEngine = storageEngine;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
