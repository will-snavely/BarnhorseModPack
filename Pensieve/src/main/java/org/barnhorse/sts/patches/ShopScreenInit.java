package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.shop.ShopScreen;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = ShopScreen.class,
        method = "init"
)
public class ShopScreenInit {
    @SpireInsertPatch(locator = ReturnLocator.class)
    public static void Insert(ShopScreen thisRef) {
        PatchEventManager.dispatchEnterShop(thisRef);
    }
}