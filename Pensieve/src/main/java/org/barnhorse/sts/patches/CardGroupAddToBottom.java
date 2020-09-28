package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.cards.CardGroup",
        method = "addToBottom",
        paramtypez = {AbstractCard.class}
)
public class CardGroupAddToBottom {
    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert(
            CardGroup thisRef,
            AbstractCard card) {
        if (thisRef.type == CardGroup.CardGroupType.MASTER_DECK) {
            PatchEventManager.dispatchCardObtained(thisRef, card);
        }
    }
}

