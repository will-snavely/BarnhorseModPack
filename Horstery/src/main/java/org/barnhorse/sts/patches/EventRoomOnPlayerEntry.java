package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = EventRoom.class,
        method = "onPlayerEntry"
)
public class EventRoomOnPlayerEntry {
    @SpireInsertPatch(locator = ReturnLocator.class)
    public static void Insert(EventRoom thisRef) {
        PatchEventManager.dispatchEnterEvent(thisRef.event);
    }

    private static class MyLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractEvent.class,
                    "onEnterRoom");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
