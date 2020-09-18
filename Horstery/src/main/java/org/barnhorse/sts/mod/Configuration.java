package org.barnhorse.sts.mod;

import java.io.File;

public class Configuration {
    private String eventLogPath;
    private String eventLogArchivePath;

    public Configuration() {
    }

    public String getEventLogPath() {
        if (this.eventLogPath == null) {
            return "saves" + File.separator + "eventLog";
        }
        return this.eventLogPath;
    }

    public String getEventLogArchivePath() {
        if (this.eventLogPath == null) {
            return "runs";
        }
        return this.eventLogArchivePath;
    }
}
