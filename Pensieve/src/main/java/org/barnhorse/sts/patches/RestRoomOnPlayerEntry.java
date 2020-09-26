package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.rooms.RestRoom;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = RestRoom.class,
        method = "onPlayerEntry"
)
public class RestRoomOnPlayerEntry {
    @SpireInsertPatch(locator = ReturnLocator.class)
    public static void Insert(RestRoom thisRef) {
        PatchEventManager.dispatchEnterRestRoom(thisRef);
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
