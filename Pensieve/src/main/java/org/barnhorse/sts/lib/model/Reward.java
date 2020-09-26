package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;

public class Reward {
    public RewardItem.RewardType type;
    public int goldAmt;
    public int bonusGold;
    public String text;
    public Relic relic;
    public Potion potion;
    public ArrayList<Card> cards;

    public Reward() {
    }

    public Reward(RewardItem item) {
        this.type = item.type;
        this.goldAmt = item.goldAmt;
        this.bonusGold = item.bonusGold;
        this.text = item.text;

        if (item.relic != null) {
            this.relic = new Relic(item.relic);
        }
        if (item.potion != null) {
            this.potion = new Potion(item.potion);
        }

        if (item.cards != null) {
            cards = new ArrayList<>();
            for (AbstractCard card : item.cards) {
                cards.add(new Card(card));
            }
        }
    }
}
