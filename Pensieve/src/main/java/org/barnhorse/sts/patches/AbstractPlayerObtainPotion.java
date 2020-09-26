package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;

public class AbstractPlayerObtainPotion {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "obtainPotion",
            paramtypez = {int.class, AbstractPotion.class}
    )
    public static class Overload1 {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(
                AbstractPlayer thisRef,
                int slot,
                AbstractPotion potion) {
            PatchEventManager.dispatchPotionObtained(potion);
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "obtainPotion",
            paramtypez = {AbstractPotion.class}
    )
    public static class Overload2 {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert(
                AbstractPlayer thisRef,
                AbstractPotion potion) {
            PatchEventManager.dispatchPotionObtained(potion);
        }
    }

    private static class MyLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractPotion.class,
                    "setAsObtained"
            );
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}