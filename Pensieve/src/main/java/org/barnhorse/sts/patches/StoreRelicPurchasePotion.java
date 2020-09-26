package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.shop.StorePotion;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;

@SpirePatch(
        clz = StorePotion.class,
        method = "purchasePotion"
)
public class StoreRelicPurchasePotion {
    @SpireInsertPatch(locator = MyLocator.class)
    public static void Insert(StorePotion thisRef) {
        PatchEventManager.dispatchPurchasePotion(thisRef);
    }

    private static class MyLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractPlayer.class,
                    "loseGold"
            );
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}