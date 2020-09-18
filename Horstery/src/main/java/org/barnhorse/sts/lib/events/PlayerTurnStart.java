package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import org.barnhorse.sts.lib.model.Player;

public class PlayerTurnStart extends GameEvent {
    public static String key = "player_turn_start";
    Player player;

    public PlayerTurnStart(AbstractPlayer player) {
        super(key, "The player started a new turn");
        this.player = new Player(player);
    }
}
