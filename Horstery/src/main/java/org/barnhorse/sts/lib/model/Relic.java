package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Relic {
    public int cost;

    public String name;
    public String description;
    public String flavorText;
    public String imgUrl;

    public boolean isSeen;
    public boolean isObtained;
    public boolean energyBased;
    public boolean usedUp;

    public Relic(AbstractRelic relic) {
        this.cost = relic.cost;
        this.name = relic.name;
        this.description = relic.description;
        this.flavorText = relic.flavorText;
        this.imgUrl = relic.imgUrl;
        this.isSeen = relic.isSeen;
        this.isObtained = relic.isObtained;
        this.energyBased = relic.energyBased;
        this.usedUp = relic.usedUp;
    }
}
