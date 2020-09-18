package org.barnhorse.sts.lib.events;

public abstract class GameEvent {
    public String key;
    public String name;
    public long timestamp;

    public GameEvent(String key, String name) {
        this.key = key;
        this.name = name;
    }
}
