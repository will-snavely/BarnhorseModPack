package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import org.barnhorse.sts.lib.model.Card;
import org.barnhorse.sts.lib.model.Creature;

public class CardUsed extends GameEvent {
    public final static String key = "card_used";

    public Card card;
    public Creature target;

    public CardUsed() {
        super(key, "Card played during combat");
    }

    public CardUsed(AbstractCard card, AbstractCreature target) {
        this();
        if (card != null) {
            this.card = new Card(card);
        }
        if (target != null) {
            this.target = new Creature(target);
        }
    }
}
