package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.barnhorse.sts.lib.model.Monster;

import java.util.List;
import java.util.stream.Collectors;

public class EnemyTurnStart extends GameEvent {
    public final static String key = "enemy_turn_start";
    List<Monster> monsters;

    public EnemyTurnStart() {
        super(key, "The enemy started a new turn");
    }

    public EnemyTurnStart(AbstractRoom room) {
        this();
        this.monsters = room.monsters.monsters.stream()
                .map(Monster::new)
                .collect(Collectors.toList());
    }
}
