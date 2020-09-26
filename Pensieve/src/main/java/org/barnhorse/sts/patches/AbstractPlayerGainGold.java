package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import org.barnhorse.sts.lib.model.RelicEffect;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;

import java.util.Collections;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "gainGold"
)
public class AbstractPlayerGainGold {
    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert(
            AbstractPlayer thisRef,
            int amount) {
        if (thisRef.hasRelic("Ectoplasm")) {
            PatchEventManager.dispatchRelicTriggered(
                    thisRef.getRelic("Ectoplasm"),
                    Collections.singletonMap(RelicEffect.LoseGold, amount));
        } else {
            PatchEventManager.dispatchGoldGained(amount);
        }
    }
}
