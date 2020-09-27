package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "die",
        paramtypez = {boolean.class}
)
public class AbstractMonsterDie {
    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert(AbstractMonster thisRef) {
        PatchEventManager.dispatchMonsterDied(thisRef);
    }
}
