package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.DeathScreen;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "damage"
)
public class AbstractMonsterDamage {
    @SpireInsertPatch(locator = CallHealthBarUpdated.class)
    public static void InsertAfterDamageApplied(
            AbstractMonster thisRef,
            DamageInfo info) {
        PatchEventManager.dispatchMonsterDamaged(thisRef, info, thisRef.lastDamageTaken);
    }

    private static class CallHealthBarUpdated extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractMonster.class,
                    "healthBarUpdatedEvent"
            );
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
