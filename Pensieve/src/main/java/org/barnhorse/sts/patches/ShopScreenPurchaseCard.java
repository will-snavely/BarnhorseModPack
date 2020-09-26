package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = ShopScreen.class,
        method = "purchaseCard"
)
public class ShopScreenPurchaseCard {
    @SpireInsertPatch(locator = MyLocator.class)
    public static void Insert(ShopScreen thisRef, AbstractCard purchased) {
        PatchEventManager.dispatchPurchaseChard(purchased, purchased.price);
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