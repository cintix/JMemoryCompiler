/*
 */
package dk.cintix.memorycompiler.classloaders;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author migo
 */
public class DynamicClassloader extends URLClassLoader {

    /**
     *
     * @param classLoader
     */
    public DynamicClassloader(URLClassLoader classLoader) {
        super(classLoader.getURLs());
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }
}
