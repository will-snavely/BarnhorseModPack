package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.List;
import java.util.stream.Collectors;

public class Room {
    public List<Monster> monsters;
    public String symbol;

    public Room() {
    }

    public Room(AbstractRoom room) {
        this.symbol = room.getMapSymbol();
        if (room.monsters != null) {
            this.monsters = room.monsters.monsters
                    .stream()
                    .map(Monster::new)
                    .collect(Collectors.toList());
        }
    }
}
