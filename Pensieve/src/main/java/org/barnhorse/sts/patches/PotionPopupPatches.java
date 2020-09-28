package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import javassist.CtBehavior;
import org.barnhorse.sts.lib.util.ReflectionHelper;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;

public class PotionPopupPatches {
    @SpirePatch(
            clz = PotionPopUp.class,
            method = "updateTargetMode"
    )
    public static class UpdateTargetMode {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(PotionPopUp thisRef) {
            AbstractPotion potion = ReflectionHelper
                    .<AbstractPotion>tryGetFieldValue(thisRef, "potion", true)
                    .orElse(null);
            AbstractMonster target = ReflectionHelper
                    .<AbstractMonster>tryGetFieldValue(thisRef, "hoveredMonster", true)
                    .orElse(null);
            PatchEventManager.dispatchPotionUsed(potion, target);
        }
    }

    @SpirePatch(
            clz = PotionPopUp.class,
            method = "updateInput"
    )
    public static class UpdateInput {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(PotionPopUp thisRef) {
            AbstractPotion potion = ReflectionHelper
                    .<AbstractPotion>tryGetFieldValue(thisRef, "potion", true)
                    .orElse(null);
            PatchEventManager.dispatchPotionUsed(potion, null);
        }
    }

    private static class MyLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractPotion.class,
                    "use"
            );
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
