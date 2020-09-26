package org.barnhorse.sts.patches.util;

import javassist.CtBehavior;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Opcode;

import java.util.ArrayList;

public class PatchUtil {
    public static int[] topOfMethod(CtBehavior method) {
        return new int[]{method.getMethodInfo().getLineNumber(0)};
    }

    private static boolean isReturn(int opcode) {
        return opcode == Opcode.RETURN
                || opcode == Opcode.ARETURN
                || opcode == Opcode.DRETURN
                || opcode == Opcode.FRETURN
                || opcode == Opcode.IRETURN
                || opcode == Opcode.LRETURN;
    }

    public static int[] allReturns(CtBehavior method) throws BadBytecode {
        MethodInfo methodInfo = method.getMethodInfo();
        CodeIterator codeIterator = methodInfo
                .getCodeAttribute()
                .iterator();

        ArrayList<Integer> positions = new ArrayList<>();
        while (codeIterator.hasNext()) {
            int pos = codeIterator.next();
            int opcode = codeIterator.byteAt(pos);
            if (isReturn(opcode)) {
                positions.add(pos);
            }
        }

        int[] result = new int[positions.size()];
        for (int ii = 0; ii < positions.size(); ii++) {
            result[ii] = methodInfo.getLineNumber(positions.get(ii));
        }
        return result;
    }
}


