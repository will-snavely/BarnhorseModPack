package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import org.barnhorse.sts.lib.model.Player;

public class PlayerTurnStart extends GameEvent {
    public final static String key = "player_turn_start";
    Player player;

    public PlayerTurnStart() {
        super(key, "The player started a new turn");
    }

    public PlayerTurnStart(AbstractPlayer player) {
        this();
        this.player = new Player(player);
    }
}
