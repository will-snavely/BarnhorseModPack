package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.barnhorse.sts.lib.model.Room;

public class BattleStart extends GameEvent {
    public static String key = "battle_start";
    public Room room;

    public BattleStart(AbstractRoom room) {
        super(key, "A new battle started");
        this.room = new Room(room);
    }
}
