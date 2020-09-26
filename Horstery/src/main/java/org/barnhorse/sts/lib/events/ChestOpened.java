package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import org.barnhorse.sts.lib.model.Reward;

import java.util.ArrayList;
import java.util.List;

public class ChestOpened extends GameEvent {
    public final static String key = "chest_opened";

    public List<Reward> rewards;
    public String chestKind;

    public ChestOpened() {
        super(key, "Opened a chest");
    }

    public ChestOpened(AbstractChest chest, List<RewardItem> rewards) {
        this();
        if (chest != null) {
            this.chestKind = chest.getClass().getName();
        }
        if (rewards != null) {
            this.rewards = new ArrayList<>();
            for (RewardItem item : rewards) {
                this.rewards.add(new Reward(item));
            }
        }
    }
}
