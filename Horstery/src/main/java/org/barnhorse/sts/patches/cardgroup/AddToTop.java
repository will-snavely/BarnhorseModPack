package org.barnhorse.sts.patches.cardgroup;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.PatchUtil;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.cards.CardGroup",
        method = "addToTop",
        paramtypez = {AbstractCard.class}
)
public class AddToTop {
    @SpireInsertPatch(locator = MyLocator.class)
    public static void Insert(
            CardGroup thisRef,
            AbstractCard card) {
        if (thisRef.type == CardGroup.CardGroupType.MASTER_DECK) {
            PatchEventManager.dispatchCardObtained(thisRef, card);
        }
    }

    private static class MyLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) {
            return PatchUtil.topOfMethod(ctMethodToPatch);
        }
    }
}

