package org.barnhorse.sts.lib.events;

public abstract class GameEvent {
    public String key;
    public String desc;
    public long timestamp;

    public GameEvent(String key, String description) {
        this.key = key;
        this.desc = description;
    }
}
