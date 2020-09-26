package org.barnhorse.sts.lib.events;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.barnhorse.sts.mod.Pensieve;
import org.dizitart.no2.objects.Id;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class GameEvent {
    @Id
    public long eventId;
    public String key;
    public String desc;
    public long timestamp;
    public int actNumber;
    public int floorNumber;
    public long schema;

    public GameEvent(String key, String description) {
        this.eventId = UUID.randomUUID().getMostSignificantBits();
        this.key = key;
        this.desc = description;
        this.schema = Pensieve.SCHEMA_VERSION;
    }
}
