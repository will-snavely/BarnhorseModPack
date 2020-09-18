package org.barnhorse.sts.lib.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import org.barnhorse.sts.lib.events.model.Card;
import org.barnhorse.sts.lib.events.model.Creature;

public class CardUsed extends GameEvent {
    public static String key = "card_used";

    public Card card;
    public Creature target;

    public CardUsed(AbstractCard card, AbstractCreature target) {
        super(key, "Card Used");
        if (card != null) {
            this.card = new Card(card);
        }
        if (target != null) {
            this.target = new Creature(target);
        }
    }
}
