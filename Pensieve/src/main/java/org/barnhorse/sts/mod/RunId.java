package org.barnhorse.sts.mod;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.SeedHelper;

public class RunId {
    private Long seed;
    private AbstractPlayer.PlayerClass playerClass;

    public RunId(Long seed, AbstractPlayer player) {
        this.seed = seed;
        this.playerClass = player.chosenClass;
    }

    @Override
    public String toString() {
        String seedString = SeedHelper.getString(this.seed);
        String playerClass = this.playerClass.name();
        return String.format("%s_%s.run", seedString, playerClass);
    }
}
