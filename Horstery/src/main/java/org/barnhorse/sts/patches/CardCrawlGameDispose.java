package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;

@SpirePatch(
        clz = CardCrawlGame.class,
        method = "dispose"
)
public class CardCrawlGameDispose {
    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert() {
        PatchEventManager.dispatchGameDisposed();
    }
}