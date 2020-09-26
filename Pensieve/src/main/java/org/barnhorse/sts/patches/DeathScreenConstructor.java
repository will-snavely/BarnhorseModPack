package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = DeathScreen.class,
        method = SpirePatch.CONSTRUCTOR
)
public class DeathScreenConstructor {
    @SpireInsertPatch(locator = MyLocator.class)
    public static void InsertBeforeSubmitVictory(DeathScreen thisRef) {

    }

    @SpireInsertPatch(locator = ReturnLocator.class)
    public static void Insert(DeathScreen thisRef) {
        if (thisRef.isVictory) {
            PatchEventManager.dispatchPlayerVictory(AbstractDungeon.player, thisRef.stats);
        } else {
            PatchEventManager.dispatchPlayerDied(AbstractDungeon.player, thisRef.stats);
        }
    }

    private static class MyLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    DeathScreen.class,
                    "submitVictoryMetrics"
            );
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
