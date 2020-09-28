package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.ShopScreen;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;

@SpirePatch(
        clz = ShopScreen.class,
        method = "purgeCard"
)
public class ShopScreenPurchasePurge {
    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert() {
        AbstractCard selected =
                AbstractDungeon.gridSelectScreen.selectedCards
                        .stream()
                        .findFirst()
                        .orElse(null);
        if (selected != null) {
            PatchEventManager.dispatchCardPurged(selected, ShopScreen.actualPurgeCost);
        }
    }
}