/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class FilenameUtils extends org.apache.commons.io.FilenameUtils {
    
    /**
     * 
     */
    public FilenameUtils() {
    }
    
    /**
     * 
     * @param path
     * @return 
     */
    public static String getExtension(final Path path) {
        if (path == null) {
            return null;
        }
        return getExtension(path.toString());
    }

    /**
     * 
     * @param path
     * @return 
     */
    public static String getExtension(final File path) {
        if (path == null) {
            return null;
        }
        return getExtension(path.toString());
    }

    /**
     * 
     * @param path
     * @return 
     */
    public static Path removeExtension(final Path path) {
        return Paths.get(removeExtension(path.toString()));
    }

    /**
     * 
     * @param file
     * @return 
     */
    public static File removeExtension(final File file) {
        return new File(removeExtension(file.toString()));
    }

    /**
     * 
     * @param path
     * @return 
     */
    public static String getDottedExtension(final CharSequence path) {
        final String str = path == null ? null : path.toString();
        return toDottedExtension(getExtension(str));
    }

    /**
     * 
     * @param path
     * @return 
     */
    @Deprecated
    private static String old_getDottedExtensionV1(final String path) {
        final String ext = getExtension(path);
        if (ext.length() != 0) {
            return "." + ext;
        }
        return ext;
    }
    
    /**
     * 
     * @param file
     * @return 
     */
    public static String getDottedExtension(final File file) {
        return getDottedExtension(file.getPath());
    }

    /**
     * 
     * @param file
     * @return 
     */
    public static String getDottedExtension(final Path file) {
        return getDottedExtension(file.toString());
    }
    
    /**
     * 
     * @param path
     * @return 
     */
    public static String getBaseName(final Path path) {
        Objects.requireNonNull(path, "Path");
        final String name = path.getFileName().toString();
        return getBaseName(name);
    }
    
    /**
     * 
     * @param path
     * @return 
     */
    public static String getBaseName(final File path) {
        Objects.requireNonNull(path, "File");
        final String name = path.getName();
        return getBaseName(name);
    }

    /**
     * 
     * @param dir
     * @param dottedExtension
     * @return 
     */
    public static Path newUUIDPath(final Path dir, final CharSequence dottedExtension) {
        return Paths.get(dir.toString(), newUUIDName(null, dottedExtension));
    }
    
    /**
     * 
     * @param dir
     * @param prefix
     * @param suffix
     * @return 
     */
    public static Path newUUIDPath(final Path dir, final CharSequence prefix, final CharSequence suffix) {
        return Paths.get(dir.toString(), newUUIDName(prefix, suffix));
    }

    /**
     * 
     * @param dir
     * @param dottedExtension
     * @return 
     */
    public static File newUUIDFile(final File dir, final CharSequence dottedExtension) {
        return new File(dir, newUUIDName(null, dottedExtension));
    }
    
    /**
     * 
     * @param dir
     * @param prefix
     * @param suffix
     * @return 
     */
    public static File newUUIDFile(final File dir, final CharSequence prefix, final CharSequence suffix) {
        return new File(dir, newUUIDName(prefix, suffix));
    }

    /**
     * 
     * @param dottedExtension
     * @return 
     */
    public static String newUUIDName(final CharSequence dottedExtension) {
        return newUUIDName(null, dottedExtension);
    }
    
    /**
     * 
     * @param prefix
     * @param suffix
     * @return 
     */
    public static String newUUIDName(final CharSequence prefix, final CharSequence suffix) {
        return StringUtils.toEmptyString(prefix) +
                UUID.randomUUID().toString().replace("-", "") + 
                StringUtils.toEmptyString(suffix);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static String toDottedExtension(final CharSequence cs) {
        if (StringUtils.isEmpty(cs)) {
            return StringUtils.EMPTY;
        }
        if (cs.charAt(0) == '.') {
            return cs.toString();
        }
        return "." + cs.toString();
    }

}
