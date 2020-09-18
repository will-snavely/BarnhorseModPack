package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.barnhorse.sts.lib.events.model.Monster;
import org.barnhorse.sts.lib.events.model.Room;

import java.util.List;
import java.util.stream.Collectors;

public class BattleStart extends GameEvent {
    public static String key = "battle_start";
    public List<Monster> monsters;
    public Room room;

    public BattleStart(AbstractRoom room) {
        super(key, "Battle Started");
        this.room = new Room(room);
        this.monsters = room.monsters.monsters
                .stream()
                .map(Monster::new)
                .collect(Collectors.toList());
    }
}
