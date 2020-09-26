package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = ObtainKeyEffect.class,
        method = SpirePatch.CONSTRUCTOR
)
public class ObtainKeyEffectCtor {
    @SpireInsertPatch(locator = ReturnLocator.class)
    public static void Insert(ObtainKeyEffect thisRef, ObtainKeyEffect.KeyColor color) {
        PatchEventManager.dispatchKeyObtained(color);
    }
}