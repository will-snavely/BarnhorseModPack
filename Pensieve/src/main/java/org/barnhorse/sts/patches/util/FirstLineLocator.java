package org.barnhorse.sts.patches.util;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import javassist.CtBehavior;

public class FirstLineLocator extends SpireInsertLocator {
    public int[] Locate(CtBehavior ctMethodToPatch) {
        return PatchUtil.topOfMethod(ctMethodToPatch);
    }
}