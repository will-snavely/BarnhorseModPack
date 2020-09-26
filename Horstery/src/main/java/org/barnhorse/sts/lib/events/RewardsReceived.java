package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.rewards.RewardItem;
import org.barnhorse.sts.lib.model.Reward;

import java.util.ArrayList;
import java.util.List;

public class RewardsReceived extends GameEvent {
    public final static String key = "rewards_received";
    public List<Reward> rewards;

    public RewardsReceived() {
        super(key, "Rewards received");
    }

    public RewardsReceived(ArrayList<RewardItem> rewards) {
        this();

        if (rewards != null) {
            this.rewards = new ArrayList<>();
            for (RewardItem item : rewards) {
                this.rewards.add(new Reward(item));
            }
        }
    }
}
