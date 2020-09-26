package org.barnhorse.sts.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import org.barnhorse.sts.patches.dispatch.PatchEventManager;
import org.barnhorse.sts.patches.util.FirstLineLocator;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.actions.GameActionManager",
        method = "update"
)
public class GameActionManagerUpdate {
    public static AbstractGameAction lastActionSeen = null;

    @SpireInsertPatch(locator = FirstLineLocator.class)
    public static void Insert(Object obj) {
        GameActionManager actionManager = (GameActionManager) obj;
        if (actionManager != null && actionManager.currentAction != null) {
            if (lastActionSeen == null || !lastActionSeen.equals(actionManager.currentAction)) {
                PatchEventManager.dispatchGameActionStart(actionManager.currentAction);
            }
            if (actionManager.currentAction.isDone) {
                PatchEventManager.dispatchGameActionDone(actionManager.currentAction);
            }
            lastActionSeen = actionManager.currentAction;
        }
    }
}

