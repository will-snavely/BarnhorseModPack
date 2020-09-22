package org.barnhorse.sts.patches.util;

import javassist.CtBehavior;

public class PatchUtil {
    public static int[] topOfMethod(CtBehavior method) {
        return new int[]{method.getMethodInfo().getLineNumber(0)};
    }
}


