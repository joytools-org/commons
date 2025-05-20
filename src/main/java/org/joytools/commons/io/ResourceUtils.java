/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class ResourceUtils {

    /**
     * 
     * @param clazz
     * @param extension
     * @return
     * @throws IOException 
     */
    public static String siblingResourcePathWithExtension(final Class clazz, final String extension) throws IOException {
        return siblingResourcePathWithSuffix(clazz, "." + StringUtils.stripStart(extension, "."));
    }

    /**
     * 
     * @param clazz
     * @param suffix
     * @return
     * @throws IOException 
     */
    public static String siblingResourcePathWithSuffix(final Class clazz, final String suffix) throws IOException {
        Objects.requireNonNull(clazz, "Class");
        final String path = StringUtils.replace(clazz.getName(), ".", "/");
        if (StringUtils.isEmpty(suffix)) {
            return path;
        }
        return path + suffix;
    }

    /**
     * 
     * @param clazz
     * @param name
     * @return
     * @throws IOException 
     */
    public static String siblingResourcePathByName(final Class clazz, final String name) throws IOException {
        Objects.requireNonNull(clazz, "Class");
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("No name specified");
        }
        final String path = StringUtils.replace(clazz.getName(), ".", "/");
        return StringUtils.substringBeforeLast(path, "/") + "/" + name;
    }

    /**
     * 
     * @param classLoader
     * @param path
     * @return
     * @throws IOException 
     */
    public static InputStream openResource(final ClassLoader classLoader, final String path) throws IOException {
        Objects.requireNonNull(classLoader, "Class Loader");
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("No or empty path specified");
        }
        final URL url = classLoader.getResource(path);
        if (url == null) {
            throw new IOException("Resource not found: " + path);
        }
        return url.openStream();
    }
    
    /**
     * 
     * @param classLoader
     * @param path
     * @return
     * @throws IOException 
     */
    public static String loadResourceAsString(final ClassLoader classLoader, final String path) throws IOException {
        try (final InputStream in = openResource(classLoader, path)) {
            return IOUtils.toString(in); 
        }        
    }

    /**
     * 
     * @param classLoader
     * @param path
     * @return
     * @throws IOException 
     */
    public static byte[] loadResourceAsByteArray(final ClassLoader classLoader, final String path) throws IOException {
        try (final InputStream in = openResource(classLoader, path)) {
            return IOUtils.toByteArray(in); 
        }        
    }
    
    /**
     * 
     * @param clazz
     * @return
     * @throws MalformedURLException 
     */
    public static URL getClassURL(final Class clazz) throws MalformedURLException {
        final String className = "/" + clazz.getName().replace('.', '/') + ".class";
        return clazz.getResource(className);
    }

    /**
     * 
     * @param clazz
     * @return
     * @throws MalformedURLException 
     */
    public static URL getManifestURL(final Class clazz) throws MalformedURLException {
        final String classPath = getClassURL(clazz).toString();
        if (!classPath.toLowerCase().startsWith("jar")) {
          throw new IllegalArgumentException("Not a jar file: " + classPath);
        }
        final String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";
        return new URL(manifestPath);
    }
    

}
