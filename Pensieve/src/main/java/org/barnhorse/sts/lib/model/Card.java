package org.barnhorse.sts.lib.model;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import org.barnhorse.sts.lib.util.ReflectionHelper;

import java.util.List;

public class Card {
    public int baseBlock;
    public int baseDamage;
    public int baseDiscard;
    public int baseDraw;
    public int baseHeal;
    public int baseMagicNumber;
    public int block;
    public int chargeCost;
    public AbstractCard.CardColor color;
    public int cost;
    public int costForTurn;
    public int damage;
    protected DamageInfo.DamageType damageType;
    public DamageInfo.DamageType damageTypeForTurn;
    public int discard;
    public boolean dontTriggerOnUseCard;
    public int draw;
    public int energyOnUse;
    public boolean exhaust;
    public boolean exhaustOnFire;
    public boolean exhaustOnUseOnce;
    public boolean freeToPlayOnce;
    public int heal;
    public boolean ignoreEnergyOnUse;
    public boolean inBottleFlame;
    public boolean inBottleLightning;
    public boolean inBottleTornado;
    public boolean isBlockModified;
    public boolean isCostModified;
    public boolean isCostModifiedForTurn;
    public boolean isDamageModified;
    public boolean isEthereal;
    public boolean isInAutoplay;
    public boolean isInnate;
    public boolean isLocked;
    public boolean isMagicNumberModified;
    public boolean isMultiDamage;
    public int magicNumber;
    public int misc;
    public int[] multiDamage;
    public String name;
    public int price;
    public boolean purgeOnUse;
    public AbstractCard.CardRarity rarity;
    public boolean retain;
    public boolean returnToHand;
    public boolean selfRetain;
    public boolean shuffleBackIntoDrawPile;
    public List<AbstractCard.CardTags> tags;
    public AbstractCard.CardTarget target;
    public int timesUpgraded;
    public AbstractCard.CardType type;
    public boolean upgraded;
    public boolean upgradedBlock;
    public boolean upgradedCost;
    public boolean upgradedDamage;
    public boolean upgradedMagicNumber;
    public String uuid;

    public Card() {
    }

    public Card(AbstractCard card) {
        this.baseBlock = card.baseBlock;
        this.baseDamage = card.baseDamage;
        this.baseDiscard = card.baseDiscard;
        this.baseDraw = card.baseDraw;
        this.baseHeal = card.baseHeal;
        this.baseMagicNumber = card.baseMagicNumber;
        this.block = card.block;
        this.chargeCost = card.chargeCost;
        this.color = card.color;
        this.cost = card.cost;
        this.costForTurn = card.costForTurn;
        this.damage = card.damage;
        this.damageType = ReflectionHelper
                .<DamageInfo.DamageType>tryGetFieldValue(card, "damageType", true)
                .orElse(DamageInfo.DamageType.NORMAL);
        this.damageTypeForTurn = card.damageTypeForTurn;
        this.discard = card.discard;
        this.dontTriggerOnUseCard = card.dontTriggerOnUseCard;
        this.draw = card.draw;
        this.energyOnUse = card.energyOnUse;
        this.exhaust = card.exhaust;
        this.exhaustOnFire = card.exhaustOnFire;
        this.exhaustOnUseOnce = card.exhaustOnUseOnce;
        this.freeToPlayOnce = card.freeToPlayOnce;
        this.heal = card.heal;
        this.ignoreEnergyOnUse = card.ignoreEnergyOnUse;
        this.inBottleFlame = card.inBottleFlame;
        this.inBottleLightning = card.inBottleLightning;
        this.inBottleTornado = card.inBottleTornado;
        this.isBlockModified = card.isBlockModified;
        this.isCostModified = card.isCostModified;
        this.isCostModifiedForTurn = card.isCostModifiedForTurn;
        this.isDamageModified = card.isDamageModified;
        this.isEthereal = card.isEthereal;
        this.isInAutoplay = card.isInAutoplay;
        this.isInnate = card.isInnate;
        this.isLocked = card.isLocked;
        this.isMagicNumberModified = card.isMagicNumberModified;
        this.isMultiDamage = ReflectionHelper
                .<Boolean>tryGetFieldValue(card, "isMultiDamage", true)
                .orElse(false);
        this.magicNumber = card.magicNumber;
        this.misc = card.misc;
        this.multiDamage = card.multiDamage;
        this.name = card.name;
        this.price = card.price;
        this.purgeOnUse = card.purgeOnUse;
        this.rarity = card.rarity;
        this.retain = card.retain;
        this.returnToHand = card.returnToHand;
        this.selfRetain = card.selfRetain;
        this.shuffleBackIntoDrawPile = card.shuffleBackIntoDrawPile;
        this.tags = card.tags;
        this.target = card.target;
        this.timesUpgraded = card.timesUpgraded;
        this.type = card.type;
        this.upgraded = card.upgraded;
        this.upgradedBlock = card.upgradedBlock;
        this.upgradedCost = card.upgradedCost;
        this.upgradedDamage = card.upgradedDamage;
        this.upgradedMagicNumber = card.upgradedMagicNumber;
        this.uuid = card.uuid.toString();
    }
}
