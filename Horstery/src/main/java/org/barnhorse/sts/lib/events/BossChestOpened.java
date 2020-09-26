package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.barnhorse.sts.lib.model.Relic;

import java.util.ArrayList;
import java.util.List;

public class BossChestOpened extends GameEvent {
    public final static String key = "boss_chest_opened";

    public List<Relic> relicChoices;

    public BossChestOpened() {
        super(key, "Opened a boss chest");
    }

    public BossChestOpened(ArrayList<AbstractRelic> relics) {
        this();
        if (relics != null) {
            this.relicChoices = new ArrayList<>();
            for (AbstractRelic relic : relics) {
                this.relicChoices.add(new Relic(relic));
            }
        }

    }
}
