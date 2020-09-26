package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;

@SpirePatch(
        clz = ConfirmPopup.class,
        method = "effect"
)
public class ConfirmPopupEffect {
    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert(ConfirmPopup thisRef) {
        if (thisRef.type == ConfirmPopup.ConfirmType.ABANDON) {
            PatchEventManager.dispatchAbandonedRun(AbstractDungeon.player);
        }
        if (thisRef.type == ConfirmPopup.ConfirmType.EXIT) {
            PatchEventManager.dispatchSaveAndQuit();
        }
    }

    private static class MyLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.ConstructorCallMatcher(DeathScreen.class);
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
