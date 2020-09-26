package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.options.AbandonConfirmPopup;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;

@SpirePatch(
        clz = AbandonConfirmPopup.class,
        method = "abandonRun"
)
public class AbandonConfirmPopupAbandonRun {
    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert(AbandonConfirmPopup thisRef, AbstractPlayer player) {
        PatchEventManager.dispatchAbandonedRun(player);
    }
}
