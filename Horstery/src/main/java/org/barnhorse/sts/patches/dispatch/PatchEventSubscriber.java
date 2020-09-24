package org.barnhorse.sts.patches.dispatch;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;

import java.util.List;

public interface PatchEventSubscriber {
    void onGameActionStart(AbstractGameAction action);

    void onGameActionDone(AbstractGameAction action);

    void onCardObtained(AbstractCard card);

    void onCardRemoved(CardGroup deck, AbstractCard card);

    void onCardUsed(AbstractPlayer player, AbstractCard card, AbstractMonster monster, int energyOnUse);

    void onDispose();

    void onAbandonRun(AbstractPlayer player);

    void onSaveAndQuit();

    void onPlayerTurnStart(AbstractPlayer player);

    void onPlayerDamaged(AbstractPlayer player, DamageInfo info, int actualDamage);

    void onMonsterDamaged(AbstractMonster monster, DamageInfo info, int actualDamage);

    void onBlockGained(AbstractCreature thisRef,
                       int blockAmount,
                       int startingBlock,
                       int endingBlock,
                       int totalBlockGained);

    void onBlockLost(AbstractCreature thisRef,
                     int blockAmount,
                     int startingBlock,
                     int endingBlock,
                     int totalBlockLost);

    void onMonsterDied(AbstractMonster monster);

    void onPlayerDied(AbstractPlayer player, List<GameOverStat> stats);

    void onPlayerVictory(AbstractPlayer Player, List<GameOverStat> stats);

    void onEnterShop(ShopScreen shop);

    void onPurchaseCard(AbstractCard card, int price);

    void onPurgeCard(AbstractCard selected, int price);

    void onPurchasePotion(StorePotion potion);

    void onPurchaseRelic(StoreRelic relic);
}
