package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.ReflectionHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.options.AbandonConfirmPopup;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = CombatRewardScreen.class,
        method = "setupItemReward"
)
public class CombatRewardScreenSetupItemRewards {
    @SpireInsertPatch(locator = ReturnLocator.class)
    public static void Insert(CombatRewardScreen thisRef) {
        PatchEventManager.dispatchRewardsRecieved(thisRef.rewards);
    }
}
