package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import javassist.CtBehavior;
import org.barnhorse.sts.lib.model.UpgradeSource;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;

import java.util.ArrayList;

public class UpgradeTriggers {
    @SpirePatch(
            clz = CampfireSmithEffect.class,
            method = "update"
    )
    public static class CampfireSmithEffect_update {
        @SpireInsertPatch(locator = MyLocator.class)
        public static void Insert() {
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                PatchEventManager.dispatchCardUpgraded(card, UpgradeSource.Smithing);
            }
        }

        private static class MyLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(
                        ArrayList.class,
                        "clear"
                );
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}
