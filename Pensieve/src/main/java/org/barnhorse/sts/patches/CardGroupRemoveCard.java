package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;
import org.barnhorse.sts.patches.util.PatchUtil;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.cards.CardGroup",
        method = "removeCard",
        paramtypez = {AbstractCard.class}
)
public class CardGroupRemoveCard {
    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert(
            CardGroup thisRef,
            AbstractCard card) {
        if (thisRef.type == CardGroup.CardGroupType.MASTER_DECK) {
            PatchEventManager.dispatchCardRemoved(thisRef, card);
        }
    }
}

