/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.List;

import java.util.function.Function;

/**
 *
 * @author AndreaR
 * @param <F>
 * @param <T>
 */
public abstract class FileFunction<F, T> implements Function<F, T> {
    
    /**
     * 
     */
    protected FileFunction() {
    }

    /**
     * 
     * @return 
     */
    public static FileFunction<File, String> readFileToString() {
        return READ_FILE_TO_STRING;
    }
    
    /**
     * 
     * @return 
     */
    public static FileFunction<File, List<String>> readLines() {
        return READ_LINES;
    }

    /**
     * 
     * @param f
     * @return 
     */
    private final static FileFunction<File, String> READ_FILE_TO_STRING = new FileFunction<File, String>() {
        @Override
        public String apply(final File input) {
            try {
                return FileUtils.readFileToString(input, Charset.defaultCharset());
            } catch (final IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    };
    
    /**
     * 
     * @param f
     * @return 
     */
    private final static FileFunction<File, List<String>> READ_LINES = new FileFunction<File, List<String>>() {
        @Override
        public List<String> apply(final File input) {
            try {
                return FileUtils.readLines(input);
            } catch (final IOException ex) {
                throw new UncheckedIOException(ex);
            }
        }
    };

}
