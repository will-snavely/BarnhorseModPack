package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "generateRoom"
)
public class AbstractDungeonGenerateRoom {
    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert(
            AbstractDungeon thisRef,
            EventHelper.RoomResult result) {
        PatchEventManager.dispatchRollQuestionMark(result);
    }
}

