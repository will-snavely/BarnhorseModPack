package org.barnhorse.sts.patches.util;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import javassist.CtBehavior;
import javassist.bytecode.BadBytecode;

public class ReturnLocator extends SpireInsertLocator  {
    public int[] Locate(CtBehavior ctMethodToPatch) throws BadBytecode {
        return PatchUtil.allReturns(ctMethodToPatch);

    }
}
