package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import javax.smartcardio.Card;
import javax.sound.sampled.Line;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.cards.CardGroup",
        method = "removeCard",
        paramtypez = {AbstractCard.class}
)
public class CardGroupRemoveCard {
    @SpireInsertPatch(locator = MyLocator.class)
    public static void Insert(
            Object thisRef,
            AbstractCard card) {
        CardGroup me = (CardGroup) thisRef;
        switch (me.type) {
            case MASTER_DECK:
                System.out.println("Removed from master deck: " + card.name);
            default:
                break;
        }
    }

    private static class MyLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
/*            return new int[]{
                    ctMethodToPatch.getMethodInfo().getLineNumber(0)
            };*/
            return LineFinder.findInOrder(ctMethodToPatch, new Matcher.MethodCallMatcher(
                    AbstractCard.class,
                    "onRemoveFromMasterDeck"
            ));
        }
    }
}

