package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.helpers.CardHelper",
        method = "obtain"
)
public class CardHelperObtain {

    @SpireInsertPatch(locator = MyLocator.class)
    public static void Insert(
            String name, AbstractCard.CardRarity rarity, AbstractCard.CardColor color) {
        System.out.println(name);
    }

    private static class MyLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            return new int[]{
                    ctMethodToPatch.getMethodInfo().getLineNumber(0)
            };
        }
    }
}

