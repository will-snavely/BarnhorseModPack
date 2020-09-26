package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "addBlock"
)
public class AbstractCreatureAddBlock {
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
        PatchEventManager.dispatchBlockGained(
                thisRef,
                blockAmount,
                startingBlock,
                thisRef.currentBlock);
    }
}
