package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.Arrays;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.vfx.FastCardObtainEffect",
        method = SpirePatch.CONSTRUCTOR
)
public class FastCardObtainEffectInit {

    @SpireInsertPatch(locator = MyLocator.class)
    public static void Insert(
            Object obj,
            AbstractCard card) {
        System.out.println("here: " + card);
    }

    private static class MyLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.ConstructorCallMatcher(
                    "com.megacrit.cardcrawl.vfx.AbstractGameEffect"
            );
            return Arrays.stream(LineFinder.findAllInOrder(ctMethodToPatch, matcher))
                    .map(x -> x + 1)
                    .toArray();
        }
    }
}

