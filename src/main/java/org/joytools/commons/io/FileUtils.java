/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * 
     */
    public FileUtils() {
    }

    /**
     * 
     * @param file
     * @return
     * @throws IOException 
     */
    public static File getExistingFile(final File file) throws IOException {
        checkedFile(file);
        if (!file.exists()) {
            throw new FileNotFoundException(file.toString());
        }
        if (!file.isFile()) {
            throw new IOException(file + " is not a file.");
        }
        return file;
    }
    
    /**
     * 
     * @param f
     * @param minSize
     * @param maxSize
     * @return
     * @throws IOException 
     */
    public static File checkFileLength(final File f, final long minSize, final long maxSize) throws IOException {
        final long size = f.length();
        if ((size >= minSize && size <= maxSize) ||
                (size >= maxSize && size <= minSize)) {
            return f;
        }
        throw new IOException("Length of file " + f + " (" + size + " bytes) " +
                "is not in the allowed range [" + minSize + " to " + maxSize + " bytes]");
    }


    /**
     * 
     * @param path
     * @return
     * @throws IOException 
     */
    public static File getExistingFile(final Path path) throws IOException {
        Objects.requireNonNull(path, "Path");
        return getExistingFile(path.toFile());
    }

    /**
     * 
     * @param path
     * @return
     * @throws IOException 
     */
    public static File getExistingFile(final CharSequence path) throws IOException {
        checkedPath(path);
        return getExistingFile(new File(path.toString()));
    }
    
    /**
     * 
     * @param parent
     * @param child
     * @return
     * @throws IOException 
     */
    public static File getExistingFile(
            final CharSequence parent,
            final CharSequence child) throws IOException {
        checkedPath(parent);
        return getExistingFile(new File(StringUtils.toString(parent)), child);
    }

    /**
     * 
     * @param parent
     * @param child
     * @return
     * @throws IOException 
     */
    public static File getExistingFile(final File parent,
            final CharSequence child) throws IOException {
        checkedDirectory(parent);
        checkedPath(child);
        return getExistingFile(new File(parent, child.toString()));
    }

    /**
     * 
     * @param parent
     * @param child
     * @return
     * @throws IOException 
     */
    public static File getOrMkDir(final File parent, final String child) throws IOException {
        return getOrMkDir(new File(parent, child));
    }

    /**
     * 
     * @param dir
     * @return
     * @throws IOException 
     */
    public static File getOrMkDir(final File dir) throws IOException {
        checkedDirectory(dir);
        if (dir.exists() && dir.isDirectory()) {
            return dir;
        }
        dir.mkdir();
        return getExistingDirectory(dir);
    }

    /**
     * 
     * @param parent
     * @param child
     * @return
     * @throws IOException 
     */
    public static File getOrMkDirs(final File parent, final String child) throws IOException {
        return getOrMkDirs(new File(parent, child));
    }

    /**
     * 
     * @param dir
     * @return
     * @throws IOException 
     */
    public static File getOrMkDirs(final File dir) throws IOException {
        checkedDirectory(dir);
        if (dir.exists() && dir.isDirectory()) {
            return dir;
        }
        dir.mkdirs();
        return getExistingDirectory(dir);
    }

    /**
     * 
     * dir file
     * @param dir
     * @return
     * @throws IOException 
     */
    public static File getExistingDirectory(final File dir) throws IOException {
        checkedDirectory(dir);
        if (!dir.exists()) {
            throw new FileNotFoundException(dir.toString());
        }
        if (!dir.isDirectory()) {
            throw new IOException(dir + " is not a directory.");
        }
        return dir;
    }
    
    /**
     * 
     * @param path
     * @return 
     * @throws java.io.IOException
     */
    public static File getExistingDirectory(final Path path) throws IOException {
        Objects.requireNonNull("path", "Path");
        return getExistingDirectory(path.toFile());
    }

    /**
     * 
     * @param path
     * @return
     * @throws IOException 
     */
    public static File getExistingDirectory(final CharSequence path) throws IOException {
        checkedPath(path);
        return getExistingDirectory(new File(path.toString()));
    }
    
    /**
     * 
     * @param parent
     * @param child
     * @return
     * @throws IOException 
     */
    public static File getExistingDirectory(
            final CharSequence parent,
            final CharSequence child) throws IOException {
        checkedPath(parent);
        return getExistingDirectory(new File(StringUtils.toString(parent)), child);
    }
    
    /**
     * 
     * @param parent
     * @param child
     * @return
     * @throws IOException 
     */
    public static File getExistingDirectory(
            final File parent,
            final CharSequence child) throws IOException {
        checkedDirectory(parent);
        checkedPath(child);
        return getExistingDirectory(new File(parent, StringUtils.toString(child)));
    }

    /**
     * 
     * @param f
     * @return 
     */
    protected static File checkedFile(final File f) {
        if (f == null) {
            throw new IllegalArgumentException("Invalid null file");
        }
        return f;
    }

    /**
     * 
     * @param f
     * @return 
     */
    protected static File checkedDirectory(final File f) {
        if (f == null) {
            throw new IllegalArgumentException("Invalid null directory");
        }
        return f;
    }

    /**
     * 
     * @param <T>
     * @param f
     * @return 
     */
    protected static <T extends CharSequence> T checkedPath(final T f) {
        if (StringUtils.isEmpty(f)) {
            throw new IllegalArgumentException("Invalid null or empty path");
        }
        return f;
    }
    
    /**
     * 
     * @param file
     * @param relativeTo
     * @return 
     */
    public static String relativePath(final File file,
            final File relativeTo) {
        if (relativeTo == null || !relativeTo.isDirectory()) {
            return file.getPath();
        }
        String trimPath;
        String absPath;
        try {
            trimPath = relativeTo.getCanonicalPath();
            absPath = file.getCanonicalPath();
        } catch (final IOException e) {
            return file.getPath();
        }
        if (absPath.startsWith(trimPath)) {
            absPath = absPath.substring(trimPath.length(), absPath.length());
            if (absPath.length() > 0 && absPath.charAt(0) == File.separatorChar) {
                absPath = absPath.substring(1, absPath.length());
            }
        }
        return absPath;
    }
    
    /**
     * 
     * @param baseDir
     * @param pathname
     * @return 
     */
    public static File resolveFile(final File baseDir, final String pathname) {
        Objects.requireNonNull(baseDir, "baseDir");
        final File f = new File(pathname);
        if (f.isAbsolute()) {
            return f;
        }
        return new File(baseDir, pathname).getAbsoluteFile();
    }
    
    /**
     * 
     * @param destFile
     * @param tempNewFile
     * @param skipOnSameContent
     * @return
     * @throws IOException 
     */
    public static boolean replaceByRename(final File destFile, 
            final File tempNewFile, 
            final boolean skipOnSameContent) throws IOException {
            // final Function<File, File> archiver) throws IOException {
        Objects.requireNonNull(destFile, "Destination file");
        Objects.requireNonNull(tempNewFile, "Temporary new file");
        if (!tempNewFile.exists() || !tempNewFile.isFile()) {
            throw new IOException("File " + tempNewFile + " does not exist or is a directory");
        }
        try {
            final String destCouldNotBeReplaced = String.format("Destination file %s could not be replaced", destFile.toString());
            
            // Destination file does not exists
            if (!destFile.exists()) {
                if (!tempNewFile.renameTo(destFile)) {
                    throw new IOException(destCouldNotBeReplaced);
                } 
                return true;
            }

            if (!destFile.isFile()) {
                throw new IOException("Destination file " + destFile + " is a directory");
            }
            if (skipOnSameContent && FileUtils.contentEquals(destFile, tempNewFile)) {
                return false;
            }
            if (!destFile.canWrite()) {
                throw new IOException("Destination file " + destFile + " could not be written");
            }
            final File previousDestFile = FilenameUtils.newUUIDFile(destFile.getParentFile(), 
                    FilenameUtils.getDottedExtension(destFile));
            if (!destFile.renameTo(previousDestFile)) {
                throw new IOException(destCouldNotBeReplaced);
            }
            if (!tempNewFile.renameTo(destFile)) {
                previousDestFile.renameTo(destFile);
                throw new IOException(destCouldNotBeReplaced);
            } 
            previousDestFile.delete();
            return true;
        } finally {
            if (tempNewFile.exists()) {
                deleteQuietly(tempNewFile);
            }
        }
    }
    
}
