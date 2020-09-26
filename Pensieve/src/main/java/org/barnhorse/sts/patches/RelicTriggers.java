package org.barnhorse.sts.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ImpulseAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.unique.GamblingChipAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import javassist.CtBehavior;
import org.barnhorse.sts.lib.util.ReflectionHelper;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;
import org.barnhorse.sts.lib.model.RelicEffect;
import org.barnhorse.sts.patches.util.ReturnLocator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RelicTriggers {
    @SpirePatch(
            clz = Abacus.class,
            method = "onShuffle"
    )
    public static class Abacus_onShuffle {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int amt = ReflectionHelper.
                    <Integer>tryGetFieldValue(thisRef, "BLOCK_AMT", true)
                    .orElse(-1);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainBlock, amt));
        }
    }

    @SpirePatch(
            clz = Akabeko.class,
            method = "atBattleStart"
    )
    public static class Akabeko_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int amt = ReflectionHelper.
                    <Integer>tryGetFieldValue(thisRef, "VIGOR", true)
                    .orElse(-1);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Wrath, amt));
        }
    }

    @SpirePatch(
            clz = Anchor.class,
            method = "atBattleStart"
    )
    public static class Anchor_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int amt = ReflectionHelper.
                    <Integer>tryGetFieldValue(thisRef, "BLOCK_AMT", true)
                    .orElse(-1);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainBlock, amt));
        }
    }

    @SpirePatch(
            clz = AncientTeaSet.class,
            method = "atTurnStart"
    )
    public static class AncientTeaSet_atTurnStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            int amt = ReflectionHelper.
                    <Integer>tryGetFieldValue(thisRef, "ENERGY_AMT", true)
                    .orElse(-1);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergy, amt));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainEnergyAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = ArtOfWar.class,
            method = "atTurnStart"
    )
    public static class ArtOfWar_atTurnStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergy, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainEnergyAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Astrolabe.class,
            method = "onEquip"
    )
    public static class Astrolabe_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }
    }

    @SpirePatch(
            clz = BagOfMarbles.class,
            method = "atBattleStart"
    )
    public static class BagOfMarbles_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int monsterCount = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.ApplyVulnerable, monsterCount));
        }
    }

    @SpirePatch(
            clz = BagOfPreparation.class,
            method = "atBattleStart"
    )
    public static class BagOfPreparation_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Draw, 2));
        }
    }

    @SpirePatch(
            clz = BirdFacedUrn.class,
            method = "onUseCard"
    )
    public static class BirdFacedUrn_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            int amt = ReflectionHelper.
                    <Integer>tryGetFieldValue(thisRef, "HEAL_AMT", true)
                    .orElse(-1);
            amt = Math.min(
                    amt,
                    AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Heal, amt));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(HealAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = BlackBlood.class,
            method = "onVictory"
    )
    public static class BlackBlood_onVictory {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int amt = Math.min(
                    12,
                    AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Heal, amt));
        }
    }

    @SpirePatch(
            clz = BlackStar.class,
            method = "onVictory"
    )
    public static class BlackStar_onVictory {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainRelic, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        BlackStar.class,
                        "flash"
                );
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = BloodVial.class,
            method = "atBattleStart"
    )
    public static class BloodVial_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int amt = ReflectionHelper.
                    <Integer>tryGetFieldValue(thisRef, "HEAL_AMOUNT", true)
                    .orElse(-1);
            amt = Math.min(
                    amt,
                    AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Heal, amt));
        }
    }

    @SpirePatch(
            clz = BloodyIdol.class,
            method = "onGainGold"
    )
    public static class BloodyIdol_onGainGold {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int amt = ReflectionHelper.
                    <Integer>tryGetFieldValue(thisRef, "HEAL_AMOUNT", true)
                    .orElse(-1);
            amt = Math.min(
                    amt,
                    AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Heal, amt));
        }
    }

    @SpirePatch(
            clz = BlueCandle.class,
            method = "onUseCard"
    )
    public static class BlueCandle_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            Map<RelicEffect, Integer> effects = new HashMap<>();
            effects.put(RelicEffect.LoseHP, 1);
            effects.put(RelicEffect.ExhaustCard, 1);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, effects);
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(LoseHPAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Boot.class,
            method = "onAttackToChangeDamage"
    )
    public static class Boot_onAttackToChangeDamage {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef, DamageInfo info, int damageAmount) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.DamageBuff, 5 - damageAmount));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = BottledFlame.class,
            method = "update"
    )
    public static class BottledFlame_update {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(BottledFlame thisRef) {
            PatchEventManager.dispatchCardBottled(thisRef, thisRef.card);
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        BottledFlame.class,
                        "initializeTips"
                );
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = BottledFlame.class,
            method = "atBattleStart"
    )
    public static class BottledFlame_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.CardUnbottled, 1));
        }
    }

    @SpirePatch(
            clz = BottledLightning.class,
            method = "update"
    )
    public static class BottledLightning_update {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(BottledLightning thisRef) {
            PatchEventManager.dispatchCardBottled(thisRef, thisRef.card);
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        BottledLightning.class,
                        "initializeTips"
                );
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = BottledLightning.class,
            method = "atBattleStart"
    )
    public static class BottledLightning_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.CardUnbottled, 1));
        }
    }

    @SpirePatch(
            clz = BottledTornado.class,
            method = "update"
    )
    public static class BottledTornado_update {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(BottledTornado thisRef) {
            PatchEventManager.dispatchCardBottled(thisRef, thisRef.card);
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        BottledTornado.class,
                        "initializeTips"
                );
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = BottledTornado.class,
            method = "atBattleStart"
    )
    public static class BottledTornado_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.CardUnbottled, 1));
        }
    }

    @SpirePatch(
            clz = Brimstone.class,
            method = "atTurnStart"
    )
    public static class Brimstone_atTurnStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int monsterCount = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            Map<RelicEffect, Integer> effects = new HashMap<>();
            effects.put(RelicEffect.PlayerStrengthGain, 2);
            effects.put(RelicEffect.MonsterStrengthGain, monsterCount);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, effects);
        }
    }

    @SpirePatch(
            clz = BronzeScales.class,
            method = "atBattleStart"
    )
    public static class BronzeScales_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Thorns, 3));
        }
    }

    @SpirePatch(
            clz = BurningBlood.class,
            method = "onVictory"
    )
    public static class BurningBlood_onVictory {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int amt = Math.min(
                    6,
                    AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Heal, amt));
        }
    }

    @SpirePatch(
            clz = BustedCrown.class,
            method = "onEquip"
    )
    public static class BustedCrown_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlot, 1));
        }
    }

    @SpirePatch(
            clz = BustedCrown.class,
            method = "changeNumberOfCardsInReward"
    )
    public static class BustedCrown_changeNumberOfCardsInReward {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.CardRewardLost, 2));
        }
    }

    @SpirePatch(
            clz = CallingBell.class,
            method = "onEquip"
    )
    public static class CallingBell_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            Map<RelicEffect, Integer> effects = new HashMap<>();
            effects.put(RelicEffect.GainRelic, 3);
            effects.put(RelicEffect.GainCurse, 1);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, effects);
        }
    }

    @SpirePatch(
            clz = CaptainsWheel.class,
            method = "atTurnStart"
    )
    public static class CaptainsWheel_atTurnStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainBlock, 18));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainBlockAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Cauldron.class,
            method = "onEquip"
    )
    public static class Cauldron_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.PotionReward, 5));
        }
    }

    @SpirePatch(
            clz = CentennialPuzzle.class,
            method = "wasHPLost"
    )
    public static class CentennialPuzzle_wasHPLost {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Draw, 3));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(DrawCardAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = CeramicFish.class,
            method = "onObtainCard"
    )
    public static class CeramicFish_onObtainCard {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainGold, 9));
        }
    }

    @SpirePatch(
            clz = ChampionsBelt.class,
            method = "onTrigger"
    )
    public static class ChampionsBelt_onTrigger {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.ApplyWeak, 1));
        }
    }

    @SpirePatch(
            clz = CharonsAshes.class,
            method = "onExhaust"
    )
    public static class CharonsAshes_onExhaust {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int totalDmg = 0;
            int baseDmg = 3;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                totalDmg += Math.min(baseDmg, m.currentHealth);
            }
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Damage, totalDmg));
        }
    }

    @SpirePatch(
            clz = CloakClasp.class,
            method = "onPlayerEndTurn"
    )
    public static class CloakClasp_onPlayerEndTurn {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int handSize = AbstractDungeon.player.hand.group.size();
            if (handSize > 0) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.GainBlock, handSize));
            }
        }
    }

    @SpirePatch(
            clz = ClockworkSouvenir.class,
            method = "atBattleStart"
    )
    public static class ClockworkSouvenir_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainArtifact, 1));
        }
    }

    @SpirePatch(
            clz = CoffeeDripper.class,
            method = "onEquip"
    )
    public static class CoffeeDripper_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlot, 1));
        }
    }

    @SpirePatch(
            clz = CoffeeDripper.class,
            method = "canUseCampfireOption"
    )
    public static class CoffeeDripper_canUseCampfireOption {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.NoRest, 1));
        }
    }

    @SpirePatch(
            clz = Courier.class,
            method = "onEnterRoom"
    )
    public static class Courier_onEnterRoom {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, AbstractRoom room) {
            if (room instanceof ShopRoom) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
            }
        }
    }

    @SpirePatch(
            clz = CrackedCore.class,
            method = "atPreBattle"
    )
    public static class CrackedCore_atPreBattle {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.ChannelLightning, 1));
        }
    }

    @SpirePatch(
            clz = CultistMask.class,
            method = "atBattleStart"
    )
    public static class CultistMask_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }
    }

    @SpirePatch(
            clz = CursedKey.class,
            method = "onEquip"
    )
    public static class CursedKey_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlot, 1));
        }
    }

    @SpirePatch(
            clz = CursedKey.class,
            method = "onChestOpen"
    )
    public static class CursedKey_onChestOpen {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainCurse, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(ShowCardAndObtainEffect.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Damaru.class,
            method = "atTurnStart"
    )
    public static class Damaru_atTurnStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainMantra, 1));
        }
    }

    @SpirePatch(
            clz = DarkstonePeriapt.class,
            method = "onObtainCard"
    )
    public static class DarkstonePeriapt_onObtainCard {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, AbstractCard card) {
            if (card.color == AbstractCard.CardColor.CURSE) {
                int amt = 6;
                if (ModHelper.isModEnabled("Hoarder")) {
                    amt *= 3;
                }
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.GainMantra, amt));
            }
        }
    }

    @SpirePatch(
            clz = DataDisk.class,
            method = "atBattleStart"
    )
    public static class DataDisk_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainFocus, 1));
        }
    }

    @SpirePatch(
            clz = DeadBranch.class,
            method = "onExhaust"
    )
    public static class DeadBranch_onExhaust {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GenerateRandomCard, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(MakeTempCardInHandAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = DollysMirror.class,
            method = "update"
    )
    public static class DollysMirror_update {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.MirrorCard, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(ShowCardAndObtainEffect.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Duality.class,
            method = "onUseCard"
    )
    public static class Duality_onUseCard {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, AbstractCard card) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.GainDexterity, 1));
            }
        }
    }

    @SpirePatch(
            clz = DuVuDoll.class,
            method = "atBattleStart"
    )
    public static class DuVuDoll_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int str = ((AbstractRelic) thisRef).counter;
            if (str > 0) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.GainStrength, str));
            }
        }
    }

    @SpirePatch(
            clz = Ectoplasm.class,
            method = "onEquip"
    )
    public static class Ectoplasm_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlot, 1));
        }
    }

    @SpirePatch(
            clz = EmotionChip.class,
            method = "atTurnStart"
    )
    public static class EmotionChip_atTurnStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(ImpulseAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = EmptyCage.class,
            method = "onEquip"
    )
    public static class EmptyCage_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.RemoveCard, 2));
        }
    }

    @SpirePatch(
            clz = Enchiridion.class,
            method = "atPreBattle"
    )
    public static class Enchiridion_atPreBattle {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainRandomPower, 1));
        }
    }

    @SpirePatch(
            clz = EternalFeather.class,
            method = "onEnterRoom"
    )
    public static class EternalFeather_onEnterRoom {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, AbstractRoom room) {
            if (room instanceof RestRoom) {
                int amt = Math.min(
                        AbstractDungeon.player.masterDeck.size() / 5 * 3,
                        AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);

                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.Heal, amt));
            }
        }
    }

    @SpirePatch(
            clz = FaceOfCleric.class,
            method = "onVictory"
    )
    public static class FaceOfCleric_onVictory {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainMaxHP, 1));
        }
    }

    @SpirePatch(
            clz = FossilizedHelix.class,
            method = "atBattleStart"
    )
    public static class FossilizedHelix_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainBuffer, 1));
        }
    }

    @SpirePatch(
            clz = FrozenCore.class,
            method = "onPlayerEndTurn"
    )
    public static class FrozenCore_onPlayerEndTurn {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.ChannelFrost, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(Frost.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = FrozenEgg2.class,
            method = "onObtainCard"
    )
    public static class FrozenEgg2_onObtainCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.UpgradeCard, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = FusionHammer.class,
            method = "onEquip"
    )
    public static class FusionHammer_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlot, 1));
        }
    }

    @SpirePatch(
            clz = FusionHammer.class,
            method = "canUseCampfireOption"
    )
    public static class FusionHammer_canUseCampfireOption {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.NoSmith, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        SmithOption.class,
                        "updateUsability");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = GamblingChip.class,
            method = "atTurnStartPostDraw"
    )
    public static class GamblingChip_atTurnStartPostDraw {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GamblingChipAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Girya.class,
            method = "atBattleStart"
    )
    public static class Girya_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Girya thisRef) {
            if (thisRef.counter > 0) {
                PatchEventManager.dispatchRelicTriggered(thisRef,
                        Collections.singletonMap(RelicEffect.GainStrength, 1));
            }
        }
    }

    @SpirePatch(
            clz = GremlinHorn.class,
            method = "onMonsterDeath"
    )
    public static class GremlinHorn_onMonsterDeath {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            Map<RelicEffect, Integer> effects = new HashMap<>();
            effects.put(RelicEffect.Draw, 1);
            effects.put(RelicEffect.GainEnergy, 1);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, effects);
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = GremlinMask.class,
            method = "atBattleStart"
    )
    public static class GremlinMask_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainWeak, 1));
        }
    }

    @SpirePatch(
            clz = HandDrill.class,
            method = "onBlockBroken"
    )
    public static class HandDrill_onBlockBroken {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.ApplyVulnerable, 1));
        }
    }

    @SpirePatch(
            clz = HappyFlower.class,
            method = "atTurnStart"
    )
    public static class HappyFlower_atTurnStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergy, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainEnergyAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = HolyWater.class,
            method = "atBattleStartPreDraw"
    )
    public static class HolyWater_atBattleStartPreDraw {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainMiracle, 3));
        }
    }

    @SpirePatch(
            clz = HornCleat.class,
            method = "atTurnStart"
    )
    public static class HornCleat_atTurnStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainBlock, 14));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainBlockAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = HoveringKite.class,
            method = "onManualDiscard"
    )
    public static class HoveringKite_onManualDiscard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergy, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainEnergyAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = IncenseBurner.class,
            method = "atTurnStart"
    )
    public static class IncenseBurner_atTurnStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainIntangible, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(ApplyPowerAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = InkBottle.class,
            method = "onUseCard"
    )
    public static class InkBottle_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Draw, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(DrawCardAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Inserter.class,
            method = "atTurnStart"
    )
    public static class Inserter_atTurnStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainOrbSlot, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(IncreaseMaxOrbAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Kunai.class,
            method = "onUseCard"
    )
    public static class Kunai_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainDexterity, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(DexterityPower.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Lantern.class,
            method = "atTurnStart"
    )
    public static class Lantern_atTurnStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergy, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainEnergyAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = LetterOpener.class,
            method = "onUseCard"
    )
    public static class LetterOpener_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            int totalDmg = 0;
            int baseDmg = 5;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                totalDmg += Math.min(baseDmg, m.currentHealth);
            }
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Damage, totalDmg));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(DamageAllEnemiesAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = LizardTail.class,
            method = "onTrigger"
    )
    public static class LizardTail_onTrigger {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int healAmt = AbstractDungeon.player.maxHealth / 2;
            if (healAmt < 1) {
                healAmt = 1;
            }
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Heal, healAmt));
        }
    }

    @SpirePatch(
            clz = MagicFlower.class,
            method = "onPlayerHeal"
    )
    public static class MagicFlower_onPlayerHeal {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef, int healAmount) {
            int healBuff = MathUtils.round((float) healAmount * 1.5F);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.HealBuff, healBuff - healAmount));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        MagicFlower.class,
                        "flash"
                );
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Mango.class,
            method = "onEquip"
    )
    public static class Mango_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainMaxHP, 14));
        }
    }

    @SpirePatch(
            clz = MarkOfPain.class,
            method = "onEquip"
    )
    public static class MarkOfPain_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlot, 1));
        }
    }

    @SpirePatch(
            clz = MarkOfPain.class,
            method = "atBattleStart"
    )
    public static class MarkOfPain_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainWounds, 2));
        }
    }

    @SpirePatch(
            clz = MarkOfTheBloom.class,
            method = "onPlayerHeal"
    )
    public static class MarkOfTheBloom_onPlayerHeal {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, int healAmount) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.HealReduction, healAmount));
        }
    }

    @SpirePatch(
            clz = Matryoshka.class,
            method = "onChestOpen"
    )
    public static class Matryoshka_onChestOpen {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainRelic, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = MawBank.class,
            method = "onEnterRoom"
    )
    public static class MawBank_onEnterRoom {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainGold, 12));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        AbstractPlayer.class,
                        "gainGold"
                );
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = MawBank.class,
            method = "onSpendGold"
    )
    public static class MawBank_onSpendGold {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(MawBank thisRef) {
            if (!thisRef.usedUp) {
                PatchEventManager.dispatchRelicTriggered(thisRef,
                        Collections.singletonMap(RelicEffect.UsedUp, -1));
            }
        }
    }

    @SpirePatch(
            clz = MeatOnTheBone.class,
            method = "onTrigger"
    )
    public static class MeatOnTheBone_onTrigger {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            int amt = Math.min(
                    12,
                    AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Heal, amt));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        AbstractPlayer.class,
                        "heal"
                );
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = MedicalKit.class,
            method = "onUseCard"
    )
    public static class MedicalKit_onUseCard {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, AbstractCard card) {
            if (card.type == AbstractCard.CardType.STATUS) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.ExhaustCard, 1));
            }
        }
    }

    @SpirePatch(
            clz = Melange.class,
            method = "onShuffle"
    )
    public static class Melange_onShuffle {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Scry, 3));
        }
    }

    @SpirePatch(
            clz = MembershipCard.class,
            method = "onEnterRoom"
    )
    public static class MembershipCard_onEnterRoom {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, AbstractRoom room) {
            if (room instanceof ShopRoom) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
            }
        }
    }

    @SpirePatch(
            clz = MercuryHourglass.class,
            method = "atTurnStart"
    )
    public static class MercuryHourglass_atTurnStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int totalDamage = 0;
            int baseDmg = 3;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                totalDamage += Math.min(baseDmg, m.currentHealth);
            }
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Damage, totalDamage));
        }
    }

    @SpirePatch(
            clz = MoltenEgg2.class,
            method = "onObtainCard"
    )
    public static class MoltenEgg2_onObtainCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.UpgradeCard, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = MummifiedHand.class,
            method = "onUseCard"
    )
    public static class MummifiedHand_onUseCard {
        static boolean inCall = false;
        static AbstractRelic relic;

        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void InsertTop(MummifiedHand thisRef) {
            inCall = true;
            relic = thisRef;
        }

        @SpireInsertPatch(locator = ReturnLocator.class)
        public static void InsertReturn() {
            inCall = false;
            relic = null;
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "setCostForTurn"
    )
    public static class AbstractCardMummifiedHandHelper {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(AbstractCard thisRef) {
            if (MummifiedHand_onUseCard.inCall) {
                PatchEventManager.dispatchRelicTriggered(
                        MummifiedHand_onUseCard.relic,
                        Collections.singletonMap(RelicEffect.ReduceCardCost, thisRef.costForTurn));
            }
        }
    }

    @SpirePatch(
            clz = MutagenicStrength.class,
            method = "atBattleStart"
    )
    public static class MutagenicStrength_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainStrengthForTurn, 3));
        }
    }

    @SpirePatch(
            clz = Necronomicon.class,
            method = "onEquip"
    )
    public static class Necronomicon_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainCurse, 1));
        }
    }

    @SpirePatch(
            clz = Necronomicon.class,
            method = "onUseCard"
    )
    public static class Necronomicon_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.NecroDoubleCard, 1)
            );
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = NeowsLament.class,
            method = "atBattleStart"
    )
    public static class NeowsLament_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(NeowsLament thisRef) {
            if (thisRef.counter > 0) {
                int totalDamage = 0;
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    totalDamage += m.maxHealth - 1;
                }
                PatchEventManager.dispatchRelicTriggered(thisRef,
                        Collections.singletonMap(RelicEffect.NeowsLament, totalDamage));
            }

        }
    }

    @SpirePatch(
            clz = NilrysCodex.class,
            method = "onPlayerEndTurn"
    )
    public static class NilrysCodex_onPlayerEndTurn {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }
    }

    @SpirePatch(
            clz = NinjaScroll.class,
            method = "atBattleStartPreDraw"
    )
    public static class NinjaScroll_atBattleStartPreDraw {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }
    }

    @SpirePatch(
            clz = NlothsMask.class,
            method = "onChestOpenAfter"
    )
    public static class NlothsMask_onChestOpenAfter {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        AbstractRoom.class,
                        "removeOneRelicFromRewards");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = NuclearBattery.class,
            method = "atPreBattle"
    )
    public static class NuclearBattery_atPreBattle {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.ChannelPlasma, 1));
        }
    }

    @SpirePatch(
            clz = Nunchaku.class,
            method = "onUseCard"
    )
    public static class Nunchaku_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergy, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainEnergyAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = OddlySmoothStone.class,
            method = "atBattleStart"
    )
    public static class OddlySmoothStone_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainDexterity, 1));
        }
    }

    @SpirePatch(
            clz = OldCoin.class,
            method = "onEquip"
    )
    public static class OldCoin_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainGold, 300));
        }
    }


    @SpirePatch(
            clz = OrangePellets.class,
            method = "onUseCard"
    )
    public static class OrangePellets_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            int debuffCount = 0;
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p.type == AbstractPower.PowerType.DEBUFF) {
                    debuffCount++;
                }
            }
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.RemoveDebuff, debuffCount));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RemoveDebuffsAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Orichalcum.class,
            method = "onPlayerEndTurn"
    )
    public static class Orichalcum_onPlayerEndTurn {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainBlock, 6));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainBlockAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = OrnamentalFan.class,
            method = "onUseCard"
    )
    public static class OrnamentalFan_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainBlock, 4));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainBlockAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Orrery.class,
            method = "onEquip"
    )
    public static class Orrery_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.CardReward, 5));
        }
    }

    @SpirePatch(
            clz = PandorasBox.class,
            method = "onEquip"
    )
    public static class PandorasBox_onEquip {
        @SpireInsertPatch(locator = ReturnLocator.class)
        public static void Insert(Object thisRef) {
            int count = ReflectionHelper
                    .<Integer>tryGetFieldValue(thisRef, "count", true)
                    .orElse(-1);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.PandorasBox, count));
        }
    }

    @SpirePatch(
            clz = Pantograph.class,
            method = "atBattleStart"
    )
    public static class Pantograph_atBattleStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            int amt = Math.min(
                    25,
                    AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Heal, amt));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(HealAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Pear.class,
            method = "onEquip"
    )
    public static class Pear_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainMaxHP, 10));
        }
    }

    @SpirePatch(
            clz = PenNib.class,
            method = "onUseCard"
    )
    public static class PenNib_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(ApplyPowerAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = PhilosopherStone.class,
            method = "onEquip"
    )
    public static class PhilosopherStone_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlot, 1));
        }
    }

    @SpirePatch(
            clz = PhilosopherStone.class,
            method = "atBattleStart"
    )
    public static class PhilosopherStone_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int monsterCount = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.MonsterStrengthGain, monsterCount));
        }
    }

    @SpirePatch(
            clz = PhilosopherStone.class,
            method = "onSpawnMonster"
    )
    public static class PhilosopherStone_onSpawnMonster {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.MonsterStrengthGain, 1));
        }
    }

    @SpirePatch(
            clz = Pocketwatch.class,
            method = "atTurnStartPostDraw"
    )
    public static class Pocketwatch_atTurnStartPostDraw {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Draw, 3));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(DrawCardAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Pocketwatch.class,
            method = "onPlayCard"
    )
    public static class Pocketwatch_onPlayCard {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Pocketwatch thisRef) {
            if (thisRef.counter == 3) {
                PatchEventManager.dispatchRelicTriggered(thisRef,
                        Collections.singletonMap(RelicEffect.UsedUpThisTurn, -1));
            }
        }
    }

    @SpirePatch(
            clz = PotionBelt.class,
            method = "onEquip"
    )
    public static class PotionBelt_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainPotionSlot, 2));
        }
    }

    @SpirePatch(
            clz = PreservedInsect.class,
            method = "atBattleStart"
    )
    public static class PreservedInsect_atBattleStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            int totalDamage = 0;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                totalDamage += (m.maxHealth - m.currentHealth);
            }
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Damage, totalDamage));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = PrismaticShard.class,
            method = "onEquip"
    )
    public static class PrismaticShard_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }
    }

    @SpirePatch(
            clz = PureWater.class,
            method = "atBattleStartPreDraw"
    )
    public static class PureWater_atBattleStartPreDraw {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainMiracle, 1));
        }
    }

    @SpirePatch(
            clz = RedMask.class,
            method = "atBattleStart"
    )
    public static class RedMask_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int monsterCount = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.ApplyWeak, monsterCount));
        }
    }

    @SpirePatch(
            clz = RedSkull.class,
            method = "atBattleStart"
    )
    public static class RedSkull_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            if (AbstractDungeon.player.isBloodied) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.GainStrength, 3));
            }
        }
    }

    @SpirePatch(
            clz = RedSkull.class,
            method = "onBloodied"
    )
    public static class RedSkull_onBloodied {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(RedSkull thisRef) {
            Boolean isActive = ReflectionHelper
                    .<Boolean>tryGetFieldValue(thisRef, "isActive", true)
                    .orElse(null);
            boolean isCombat = AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
            if (isActive != null && isActive && isCombat) {
                PatchEventManager.dispatchRelicTriggered(thisRef,
                        Collections.singletonMap(RelicEffect.GainStrength, 3));
            }
        }
    }

    @SpirePatch(
            clz = RedSkull.class,
            method = "onNotBloodied"
    )
    public static class RedSkull_onNotBloodied {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(RedSkull thisRef) {
            Boolean isActive = ReflectionHelper
                    .<Boolean>tryGetFieldValue(thisRef, "isActive", true)
                    .orElse(null);
            boolean isCombat = AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
            if (isActive != null && !isActive && isCombat) {
                PatchEventManager.dispatchRelicTriggered(thisRef,
                        Collections.singletonMap(RelicEffect.LoseStrength, 3));
            }
        }
    }

    @SpirePatch(
            clz = RingOfTheSerpent.class,
            method = "onEquip"
    )
    public static class RingOfTheSerpent_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.HandSizeIncrease, 1));
        }
    }

    @SpirePatch(
            clz = RunicCapacitor.class,
            method = "atTurnStart"
    )
    public static class RunicCapacitor_atTurnStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainOrbSlot, 3));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(IncreaseMaxOrbAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = RunicDome.class,
            method = "onEquip"
    )
    public static class RunicDome_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlot, 1));
        }
    }

    @SpirePatch(
            clz = Shuriken.class,
            method = "onUseCard"
    )
    public static class Shuriken_onUseCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainStrength, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = SlaversCollar.class,
            method = "beforeEnergyPrep"
    )
    public static class SlaversCollar_beforeEnergyPrep {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlotForFight, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        SlaversCollar.class,
                        "beginLongPulse"
                );
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Sling.class,
            method = "atBattleStart"
    )
    public static class Sling_atBattleStart {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainStrength, 2));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = SmilingMask.class,
            method = "onEnterRoom"
    )
    public static class SmilingMask_onEnterRoom {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, AbstractRoom room) {
            if (room instanceof ShopRoom) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
            }
        }
    }

    @SpirePatch(
            clz = SnakeRing.class,
            method = "atBattleStart"
    )
    public static class SnakeRing_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Draw, 2));
        }
    }

    @SpirePatch(
            clz = SneckoEye.class,
            method = "atPreBattle"
    )
    public static class SneckoEye_atPreBattle {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }
    }

    @SpirePatch(
            clz = Sozu.class,
            method = "onEquip"
    )
    public static class Sozu_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlot, 1));
        }
    }

    @SpirePatch(
            clz = SsserpentHead.class,
            method = "onEnterRoom"
    )
    public static class SsserpentHead_onEnterRoom {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, AbstractRoom room) {
            if (room instanceof EventRoom) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.GainGold, 50));
            }
        }
    }

    @SpirePatch(
            clz = StoneCalendar.class,
            method = "onPlayerEndTurn"
    )
    public static class StoneCalendar_onPlayerEndTurn {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            int totalDamage = 0;
            int baseDmg = 52;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                totalDamage += Math.min(baseDmg, m.currentHealth);
            }
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainMantra, totalDamage));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Strawberry.class,
            method = "onEquip"
    )
    public static class Strawberry_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainMaxHP, 7));
        }
    }

    @SpirePatch(
            clz = StrikeDummy.class,
            method = "atDamageModify"
    )
    public static class StrikeDummy_atDamageModify {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, float damage, AbstractCard card) {
            int buff = card.hasTag(AbstractCard.CardTags.STRIKE) ? 3 : 0;
            if (buff > 0) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.DamageBuff, buff));
            }
        }
    }

    @SpirePatch(
            clz = Sundial.class,
            method = "onShuffle"
    )
    public static class Sundial_onShuffle {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergy, 2));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = SymbioticVirus.class,
            method = "atPreBattle"
    )
    public static class SymbioticVirus_atPreBattle {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.ChannelDarkness, 1));
        }
    }

    @SpirePatch(
            clz = TeardropLocket.class,
            method = "atBattleStart"
    )
    public static class TeardropLocket_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.EnterCalm, 1));
        }
    }

    @SpirePatch(
            clz = TheSpecimen.class,
            method = "onMonsterDeath"
    )
    public static class TheSpecimen_onMonsterDeath {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef, AbstractMonster monster) {
            int amount = monster.getPower("Poison").amount;
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.TransferPoison, amount));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = ThreadAndNeedle.class,
            method = "atBattleStart"
    )
    public static class ThreadAndNeedle_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainPlatedArmor, 4));
        }
    }

    @SpirePatch(
            clz = Tingsha.class,
            method = "onManualDiscard"
    )
    public static class Tingsha_onManualDiscard {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Damage, 3));
        }
    }

    @SpirePatch(
            clz = TinyHouse.class,
            method = "onEquip"
    )
    public static class TinyHouse_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            Map<RelicEffect, Integer> effects = new HashMap<>();
            effects.put(RelicEffect.PotionReward, 1);
            effects.put(RelicEffect.CardReward, 1);
            effects.put(RelicEffect.UpgradeCard, 1);
            effects.put(RelicEffect.GainGold, 50);
            effects.put(RelicEffect.GainMaxHP, 5);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, effects);
        }
    }

    @SpirePatch(
            clz = Toolbox.class,
            method = "atBattleStartPreDraw"
    )
    public static class Toolbox_atBattleStartPreDraw {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, null);
        }
    }

    @SpirePatch(
            clz = Torii.class,
            method = "onAttacked"
    )
    public static class Torii_onAttacked {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef, DamageInfo info, int amount) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.DamageReduction, amount - 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = ToughBandages.class,
            method = "onManualDiscard"
    )
    public static class ToughBandages_onManualDiscard {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainBlock, 3));
        }
    }

    @SpirePatch(
            clz = ToxicEgg2.class,
            method = "onObtainCard"
    )
    public static class ToxicEgg2_onObtainCard {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef, AbstractCard card) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.UpgradeCard, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = ToyOrnithopter.class,
            method = "onUsePotion"
    )
    public static class ToyOrnithopter_onUsePotion {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            int amt = Math.min(
                    5,
                    AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Heal, amt));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(HealAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = TungstenRod.class,
            method = "onLoseHpLast"
    )
    public static class TungstenRod_onLoseHpLast {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, int damageAmount) {
            if (damageAmount > 0) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.DamageReduction, 1));
            }
        }
    }

    @SpirePatch(
            clz = TwistedFunnel.class,
            method = "atBattleStart"
    )
    public static class TwistedFunnel_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            int totalPoison = 0;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDead && !m.isDying) {
                    totalPoison += 4;
                }
            }
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainMantra, totalPoison));
        }
    }

    @SpirePatch(
            clz = UnceasingTop.class,
            method = "onRefreshHand"
    )
    public static class UnceasingTop_onRefreshHand {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.Draw, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(DrawCardAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Vajra.class,
            method = "atBattleStart"
    )
    public static class Vajra_atBattleStart {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainStrength, 1));
        }
    }

    @SpirePatch(
            clz = VelvetChoker.class,
            method = "onEquip"
    )
    public static class VelvetChoker_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergySlot, 1));
        }
    }

    @SpirePatch(
            clz = VelvetChoker.class,
            method = "onPlayCard"
    )
    public static class VelvetChoker_onPlayCard {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            if (((AbstractRelic) thisRef).counter == 5) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.Choked, 1));
            }
        }
    }

    @SpirePatch(
            clz = VioletLotus.class,
            method = "onChangeStance"
    )
    public static class VioletLotus_onChangeStance {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.GainEnergy, 1));
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.NewExprMatcher(GainEnergyAction.class);
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = Waffle.class,
            method = "onEquip"
    )
    public static class Waffle_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            Map<RelicEffect, Integer> effects = new HashMap<>();
            effects.put(RelicEffect.GainMaxHP, 7);
            effects.put(RelicEffect.Heal,
                    AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef, effects);
        }
    }

    @SpirePatch(
            clz = WarPaint.class,
            method = "onEquip"
    )
    public static class WarPaint_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.UpgradeCard, 2));
        }
    }

    @SpirePatch(
            clz = WarpedTongs.class,
            method = "atTurnStartPostDraw"
    )
    public static class WarpedTongs_atTurnStartPostDraw {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            boolean anyCardsUnupgraded = AbstractDungeon.player.hand.group
                    .stream()
                    .anyMatch(card -> !card.upgraded);
            int amt = anyCardsUnupgraded ? 1 : 0;
            if (amt > 0) {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.UpgradeCard, amt));
            }
        }
    }

    @SpirePatch(
            clz = Whetstone.class,
            method = "onEquip"
    )
    public static class Whetstone_onEquip {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef) {
            PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                    Collections.singletonMap(RelicEffect.UpgradeCard, 2));
        }
    }

    @SpirePatch(
            clz = WristBlade.class,
            method = "atDamageModify"
    )
    public static class WristBlade_atDamageModify {
        @SpireInsertPatch(locator = FirstLineLocator.class)
        public static void Insert(Object thisRef, float damage, AbstractCard c) {
            if (c.costForTurn != 0 && (!c.freeToPlayOnce || c.cost == -1)) {
                return;
            } else {
                PatchEventManager.dispatchRelicTriggered((AbstractRelic) thisRef,
                        Collections.singletonMap(RelicEffect.DamageBuff, 4));
            }
        }
    }
}