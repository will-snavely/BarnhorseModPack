package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "generateMap"
)
public class AbstractDungeonGenerateMap {
    @SpireInsertPatch(locator = ReturnLocator.class)
    public static void Insert() {
        PatchEventManager.dispatchMapGenerated();
    }
}
