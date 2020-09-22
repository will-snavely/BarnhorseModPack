package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "useCard"
)
public class AbstractPlayerUseCard {
    @SpireInsertPatch(locator = MyLocator.class)
    public static void Insert(
            AbstractPlayer thisRef,
            AbstractCard card,
            AbstractMonster monster,
            int energyOnUse) {
        PatchEventManager.dispatchCardUsed(thisRef, card, monster, energyOnUse);
    }

    private static class MyLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractCard.class,
                    "use"
            );
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }

}
