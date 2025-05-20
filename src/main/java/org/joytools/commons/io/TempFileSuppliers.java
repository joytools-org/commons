/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TempFileSuppliers {

    /**
     * 
     * @param dir
     * @param prefix
     * @param suffix
     * @return
     * @throws IOException 
     */
    static TempFileSupplier forFile0(final File dir, final CharSequence prefix, final CharSequence suffix) throws IOException {
        final List<File> tempFiles = new ArrayList<>();
        return new TempFileSupplier() {
            @Override
            public File get() {
                try {
                    final File tempFile = File.createTempFile(StringUtils.toString(prefix), StringUtils.toString(suffix), dir);
                    tempFile.deleteOnExit();
                    tempFiles.add(tempFile);
                    return tempFile;
                } catch (final IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            }

            @Override
            public void close() throws IOException {
                tempFiles.forEach(FileUtils::deleteQuietly);
            }
        };
    }

    /**
     * 
     * @param dir
     * @param prefix
     * @param suffix
     * @return
     * @throws IOException 
     */
    static TempFileSupplier forDirectory0(final File dir, final CharSequence prefix, final CharSequence suffix) throws IOException {
        final List<File> tempDirs = new ArrayList<>();
        return new TempFileSupplier() {
            @Override
            public File get() {
                try {
                    final File tempDir = File.createTempFile(StringUtils.toString(prefix), StringUtils.toString(suffix), dir);
                    tempDir.delete();
                    if (!tempDir.mkdir()) {
                        throw new IOException("Could not create temp directory " + tempDir);
                    }
                    tempDirs.add(tempDir);
                    return tempDir;
                } catch (final IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            }

            @Override
            public void close() throws IOException {
                tempDirs.forEach(dir -> {
                    try {
                        FileUtils.deleteDirectory(dir);
                    } catch (final Exception ioe) {
                        // Ignore
                    }
                });
            }
        };
    }
    
    /**
     * 
     */
    private final static Builder ON_DEFAULT_BUILDER = new Builder() {
        @Override
        protected File dir() {
            return null;
        }
    };
      
    /**
     * 
     * @return 
     */
    public static Builder onDefault() {
        return ON_DEFAULT_BUILDER;
    }

    /**
     * 
     * @param dir
     * @return 
     */
    public static Builder on(final File dir) {
        if (dir == null) {
            return onDefault();
        }
        return new Builder() {
            @Override
            protected File dir() {
                return dir;
            }
        };
    }
    
    /**
     * 
     * @param path
     * @return 
     */
    public static Builder on(final CharSequence path) {
        if (StringUtils.isEmpty(path)) {
            return onDefault();
        }
        return on(new File(path.toString()));
    }

    /**
     * 
     * @param path
     * @return 
     */
    public static Builder on(final Path path) {
        if (path == null) {
            return onDefault();
        }
        return on(path.toFile());
    }

    /**
     * 
     */
    public abstract static class Builder {
        
        Builder() {
        }
        
        public TempFileSupplier forDirectory(final CharSequence prefix) throws IOException {
            return forDirectory0(dir(), prefix, null);
        }

        public TempFileSupplier forDirectory(final CharSequence prefix, final CharSequence suffix) throws IOException {
            return forDirectory0(dir(), prefix, suffix);
        }
        
        public TempFileSupplier forFile(final CharSequence prefix) throws IOException {
            return forFile0(dir(), prefix, null);
        }

        public TempFileSupplier forFile(final CharSequence prefix, final CharSequence suffix) throws IOException {
            return forFile0(dir(), prefix, suffix);
        }

        abstract protected File dir();
        
    }
    
}
