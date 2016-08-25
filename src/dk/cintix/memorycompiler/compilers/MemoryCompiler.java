package dk.cintix.memorycompiler.compilers;


import dk.cintix.memorycompiler.classloaders.DynamicClassloader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/*
 */
/**
 *
 * @author migo
 */
public class MemoryCompiler {

    public static <T> T getInstance(String path, String className, Class[] constructorArguments, Object[] constructorObjects) {
        File file = new File(path);
        try {
            URL url = new URL("file", "", fixPath(file.getAbsolutePath(), file.isDirectory()));

            URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            DynamicClassloader loader = new DynamicClassloader(urlClassLoader);            
            loader.addURL(url);
            
            Class thisClass = loader.loadClass(className);
            
            T customType = (T) thisClass.getDeclaredConstructor(constructorArguments).newInstance(constructorObjects);
            return customType;
        } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MemoryCompiler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Create new a class 
     * 
     * @param path
     * @param className
     * @param code
     * @return 
     */
    public static boolean compileClass(String path, String className, String code) {
        try {
            JavaFileObject javaFileObject = new InMemoryJavaFileObject(className, code);
            Iterable<? extends JavaFileObject> files = Arrays.asList(javaFileObject);

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, Locale.ENGLISH, null);
            Iterable options = Arrays.asList("-d", path);

            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, files);
            Boolean result = task.call();
            if (result == true) {
                System.out.println("Succeeded");
            }

            return result;
        } catch (Exception ex) {
            Logger.getLogger(MemoryCompiler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * MemoryFileObject
     */
    private static class InMemoryJavaFileObject extends SimpleJavaFileObject {

        private String contents = null;

        /**
         * 
         * @param className
         * @param contents
         * @throws Exception 
         */
        public InMemoryJavaFileObject(String className, String contents) throws Exception {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.contents = contents;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return contents;
        }
    }

    /**
     * 
     * @param path
     * @param isDirectory
     * @return 
     */
    private static String fixPath(String path, boolean isDirectory) {
        String p = path;
        if (File.separatorChar != '/') {
            p = p.replace(File.separatorChar, '/');
        }
        if (!p.startsWith("/")) {
            p = "/" + p;
        }
        if (!p.endsWith("/") && isDirectory) {
            p = p + "/";
        }
        return p;
    }

}
