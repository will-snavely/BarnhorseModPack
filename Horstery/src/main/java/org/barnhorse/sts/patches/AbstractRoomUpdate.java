package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;
import org.barnhorse.sts.patches.util.ReturnLocator;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "update"
)
public class AbstractRoomUpdate {

    private static AbstractRoom.RoomPhase lastSeenPhase = null;
    private static AbstractRoom lastSeenRoom = null;

    public static void updateState(AbstractRoom room) {
        AbstractRoom.RoomPhase currentPhase = room.phase;

        if (!room.equals(lastSeenRoom)) {
            PatchEventManager.dispatchRoomPhaseChange(room, null, currentPhase);
        } else {
            if (currentPhase != lastSeenPhase) {
                PatchEventManager.dispatchRoomPhaseChange(room, lastSeenPhase, currentPhase);
            }
        }

        lastSeenPhase = currentPhase;
        lastSeenRoom = room;
    }

    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void InsertTop(AbstractRoom thisRef) {
        updateState(thisRef);
    }

    @SpireInsertPatch(locator = ReturnLocator.class)
    public static void InsertReturn(AbstractRoom thisRef) {
        updateState(thisRef);
    }
}