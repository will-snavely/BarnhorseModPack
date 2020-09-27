package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.actions.GameActionManager",
        method = "getNextAction"
)
public class GameActionManagerGetNextAction {
    @SpireInsertPatch(locator = MyLocator.class)
    public static void Insert(GameActionManager thisRef) {
        PatchEventManager.dispatchPlayerTurnStart(AbstractDungeon.player);
    }

    private static class MyLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractPlayer.class,
                    "applyStartOfTurnRelics");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}

