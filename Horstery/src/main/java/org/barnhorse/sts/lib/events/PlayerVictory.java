package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.GameOverStat;
import org.barnhorse.sts.lib.model.Player;
import org.barnhorse.sts.lib.model.Stat;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerVictory extends GameEvent {
    public final static String key = "player_victory";

    public Player player;
    public List<Stat> stats;

    public PlayerVictory() {
        super(key, "The player was victorious");
    }

    public PlayerVictory(AbstractPlayer player, List<GameOverStat> stats) {
        this();
        this.player = new Player(player);
        this.stats = stats.stream()
                .map(Stat::new)
                .filter(stat -> stat.value != null)
                .collect(Collectors.toList());
    }
}
