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

import java.util.ArrayList;
import java.util.List;

public class PatchEventManager {
    private static final List<PatchEventSubscriber> subscribers = new ArrayList<>();

    private PatchEventManager() {
    }

    public static void subscribe(PatchEventSubscriber sub) {
        subscribers.add(sub);
    }

    public static void dispatchGameActionStart(AbstractGameAction action) {
        subscribers.forEach(sub -> sub.onGameActionStart(action));
    }

    public static void dispatchGameActionDone(AbstractGameAction action) {
        subscribers.forEach(sub -> sub.onGameActionDone(action));
    }

    public static void dispatchCardObtained(CardGroup deck, AbstractCard card) {
        subscribers.forEach(sub -> sub.onCardObtained(card));
    }

    public static void dispatchCardRemoved(CardGroup deck, AbstractCard card) {
        subscribers.forEach(sub -> sub.onCardRemoved(deck, card));
    }

    public static void dispatchCardUsed(AbstractPlayer player, AbstractCard card, AbstractMonster monster, int energyOnUse) {
        subscribers.forEach(sub -> sub.onCardUsed(player, card, monster, energyOnUse));
    }

    public static void dispatchGameDisposed() {
        subscribers.forEach(sub -> sub.onDispose());

    }

    public static void dispatchAbandonedRun(AbstractPlayer player) {
        subscribers.forEach(sub -> sub.onAbandonRun(player));
    }

    public static void dispatchSaveAndQuit() {
        subscribers.forEach(sub -> sub.onSaveAndQuit());
    }

    public static void dispatchPlayerTurnStart(AbstractPlayer player) {
        subscribers.forEach(sub -> sub.onPlayerTurnStart(player));
    }

    public static void dispatchPlayerDamaged(AbstractPlayer player, DamageInfo info, int actualDamage) {
        subscribers.forEach(sub -> sub.onPlayerDamaged(player, info, actualDamage));
    }

    public static void dispatchMonsterDamaged(AbstractMonster monster, DamageInfo info, int actualDamage) {
        subscribers.forEach(sub -> sub.onMonsterDamaged(monster, info, actualDamage));
    }

    public static void dispatchBlockGained(
            AbstractCreature creature,
            int amount,
            int startingBlock,
            int endingBlock) {
        subscribers.forEach(sub -> sub.onBlockGained(
                creature,
                amount,
                startingBlock,
                endingBlock,
                endingBlock - startingBlock));
    }

    public static void dispatchBlockLost(
            AbstractCreature creature,
            int amount,
            int startingBlock,
            int endingBlock) {
        subscribers.forEach(sub -> sub.onBlockLost(
                creature,
                amount,
                startingBlock,
                endingBlock,
                endingBlock - startingBlock));
    }

    public static void dispatchMonsterDied(AbstractMonster monster) {
        subscribers.forEach(sub -> sub.onMonsterDied(monster));
    }

    public static void dispatchPlayerDied(AbstractPlayer player, List<GameOverStat> stats) {
        subscribers.forEach(sub -> sub.onPlayerDied(player, stats));
    }

    public static void dispatchPlayerVictory(AbstractPlayer player, List<GameOverStat> stats) {
        subscribers.forEach(sub -> sub.onPlayerVictory(player, stats));
    }

    public static void dispatchEnterShop(ShopScreen shop) {
        subscribers.forEach(sub -> sub.onEnterShop(shop));
    }

    public static void dispatchPurchaseChard(AbstractCard card, int price) {
        subscribers.forEach(sub -> sub.onPurchaseCard(card, price));
    }

    public static void dispatchCardPurged(AbstractCard selected, int price) {
        subscribers.forEach(sub -> sub.onPurgeCard(selected, price));
    }
}
