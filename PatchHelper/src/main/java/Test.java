import com.hubspot.jinjava.Jinjava;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Test {
    public static final Jinjava jinjava = new Jinjava();

    public static void genPatch(Path classPath, String template)
            throws IOException {
        BufferedInputStream fin
                = new BufferedInputStream(new FileInputStream(classPath.toFile()));
        ClassFile cf = new ClassFile(new DataInputStream(fin));
        String className = cf.getName();
        String[] parts = className.split("\\.");
        String baseName = parts[parts.length - 1];

        for (Object o : cf.getMethods()) {
            MethodInfo mi = (MethodInfo) o;
            String methodName = mi.getName();
            if (methodName.startsWith("at") || methodName.startsWith("on")) {
                Map<String, Object> context = new HashMap<>();
                context.put("imports", Arrays.asList(cf.getName()));
                context.put("targetClass", baseName + ".class");
                context.put("targetMethod", methodName);
                context.put("patchClassName", baseName + "_" + methodName);
                String renderedTemplate = jinjava.render(template, context);
                System.out.println(renderedTemplate);
                System.out.println();
            }
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL resource = Test.class.getResource("PatchTemplate.java");
        String template = new String(Files.readAllBytes(Paths.get(resource.toURI())));
        Path path = Paths.get(args[0]);
        String[] contents = path.toFile().list();

        for (int i = 0; i < contents.length; i++) {
            Path member = path.resolve(contents[i]);
            if (member.getFileName().toString().endsWith(".class")) {
                genPatch(member, template);
            }
        }
    }
}
