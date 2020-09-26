package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.ReturnLocator;

public class ChestTriggers {
    @SpirePatch(
            clz = AbstractChest.class,
            method = "open"
    )
    public static class AbstractChest_open {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(AbstractChest thisRef, boolean bossChest) {
            PatchEventManager.dispatchChestOpened(
                    thisRef,
                    AbstractDungeon.combatRewardScreen.rewards);
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        CombatRewardScreen.class,
                        "open"
                );
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = BossRelicSelectScreen.class,
            method = "open"
    )
    public static class BossRelicSelectScreen_open {
        @SpireInsertPatch(locator = ReturnLocator.class)
        public static void Insert(BossRelicSelectScreen thisRef) {
            PatchEventManager.dispatchBossChestOpened(thisRef, thisRef.relics);
        }
    }
}
