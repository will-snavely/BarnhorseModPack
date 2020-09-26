package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.barnhorse.sts.lib.model.Room;

public class CombatComplete extends GameEvent {
    public final static String key = "combat_complete";
    public Room room;

    public CombatComplete() {
        super(key, "Combat has completed");
    }

    public CombatComplete(AbstractRoom room) {
        this();
        this.room = new Room(room);
    }
}
