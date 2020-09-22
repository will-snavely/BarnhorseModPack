package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.barnhorse.sts.lib.model.Room;

public class BattleStart extends GameEvent {
    public final static String key = "battle_start";
    public Room room;

    public BattleStart() {
        super(key, "A new battle started");
    }

    public BattleStart(AbstractRoom room) {
        this();
        this.room = new Room(room);
    }
}
