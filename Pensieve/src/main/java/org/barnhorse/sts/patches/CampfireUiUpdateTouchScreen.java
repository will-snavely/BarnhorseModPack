package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import javassist.CtBehavior;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;

@SpirePatch(
        clz = CampfireUI.class,
        method = "updateTouchscreen"
)
public class CampfireUiUpdateTouchScreen {
    @SpireInsertPatch(locator = MyLocator.class)
    public static void Insert(CampfireUI thisRef) {
        PatchEventManager.dispatchRestRoomOptionSelected(thisRef.touchOption);
    }

    private static class MyLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractCampfireOption.class,
                    "useOption");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
