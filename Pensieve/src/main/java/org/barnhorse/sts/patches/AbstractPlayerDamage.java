package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage"
)
public class AbstractPlayerDamage {
    @SpireInsertPatch(locator = CallHealthBarUpdated.class)
    public static void Insert(
            AbstractPlayer thisRef,
            DamageInfo info) {
        PatchEventManager.dispatchPlayerDamaged(thisRef, info, thisRef.lastDamageTaken);
    }

    private static class CallHealthBarUpdated extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractPlayer.class,
                    "healthBarUpdatedEvent"
            );
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}