package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.screens.DeathScreen;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "loseBlock",
        paramtypez = {int.class, boolean.class}
)
public class AbstractCreatureLoseBlock {
    private static int startingBlock;

    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void InsertTop(
            AbstractCreature thisRef,
            int blockAmount) {
        startingBlock = thisRef.currentBlock;
    }

    @SpireInsertPatch(locator = ReturnLocator.class)
    public static void InsertBottom(
            AbstractCreature thisRef,
            int blockAmount) {
        PatchEventManager.dispatchBlockLost(
                thisRef,
                blockAmount,
                startingBlock,
                thisRef.currentBlock);
    }
}
