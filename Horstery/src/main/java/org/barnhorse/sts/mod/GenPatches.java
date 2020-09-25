package org.barnhorse.sts.mod;

import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenPatches {
    public static void main(String[] args) throws IOException {


        Path path = Paths.get(args[0]);
        //List of all files and directories
        String[] contents = path.toFile().list();
        System.out.println("List of files and directories in the specified directory:");
        for (int i = 0; i < contents.length; i++) {
            Path member = path.resolve(contents[i]);

            if (member.getFileName().toString().endsWith(".class")) {
                System.out.println(member);
                BufferedInputStream fin
                        = new BufferedInputStream(new FileInputStream(member.toFile()));
                ClassFile cf = new ClassFile(new DataInputStream(fin));
                System.out.println(cf.getName());
                for (Object o : cf.getMethods()) {
                    MethodInfo mi = (MethodInfo) o;
                    System.out.println("\t" + mi.getName());
                }
            }
        }
    }
}
