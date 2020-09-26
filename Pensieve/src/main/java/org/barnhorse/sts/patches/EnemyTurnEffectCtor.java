package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.EnemyTurnEffect;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = EnemyTurnEffect.class,
        method = SpirePatch.CONSTRUCTOR
)
public class EnemyTurnEffectCtor {
    @SpireInsertPatch(locator = ReturnLocator.class)
    public static void Insert() {
        PatchEventManager.dispatchEnemyTurnStart();
    }
}
