package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.barnhorse.sts.lib.util.ReflectionHelper;

public class Card {
    public String cardName;
    public String uuid;
    public String type;

    public int baseDamage;
    public int baseBlock;
    public int baseDraw;
    public int baseHeal;
    public int baseDiscard;
    public int block;
    public int cost;
    public int costForTurn;
    public int damage;
    public int magicNumber;

    // It's a little annoying to use a List<Integer> here, since
    // converting from a primitive array to a List of boxed values
    // is rather verbose. So just use an int[] instead.
    public int[] multiDamage;

    public boolean isDamageModified;
    public boolean isBlockModified;
    public boolean exhaust;
    public boolean isMultiDamage;
    public boolean upgraded;

    public Card(AbstractCard card) {
        this.cardName = card.name;
        this.uuid = card.uuid.toString();
        this.type = card.type.name();

        this.baseDamage = card.baseDamage;
        this.baseBlock = card.baseBlock;
        this.baseHeal = card.baseHeal;
        this.baseDraw = card.baseDraw;
        this.baseDiscard = card.baseDiscard;
        this.block = card.block;
        this.cost = card.cost;
        this.costForTurn = card.costForTurn;
        this.damage = card.damage;
        this.multiDamage = card.multiDamage;
        this.magicNumber = card.magicNumber;

        this.upgraded = card.upgraded;
        this.exhaust = card.exhaust;
        this.isDamageModified = card.isDamageModified;
        this.isBlockModified = card.isBlockModified;
        this.isMultiDamage = ReflectionHelper
                .<Boolean>tryGetFieldValue(card, "isMultiDamage", true)
                .orElse(false);
    }

}
