package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.model.Card;
import org.barnhorse.sts.lib.model.UpgradeSource;

public class CardUpgraded extends GameEvent {
    public final static String key = "card_upgraded";
    public Card card;
    public UpgradeSource source;

    public CardUpgraded() {
        super(key, "A card was upgraded");
    }

    public CardUpgraded(AbstractCard card, UpgradeSource source) {
        this();
        this.card = new Card(card);
        this.source = source;
    }
}
