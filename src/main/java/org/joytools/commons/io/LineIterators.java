/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.LineIterator;
import static org.joytools.commons.lang.ObjectUtils.coalesce;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class LineIterators {
      
    /**
     * 
     * @return 
     */
    public static LineIterator empty() {
        return of(StringUtils.EMPTY);
    }

    /**
     * 
     * @param reader
     * @return 
     */
    public static LineIterator of(final Reader reader) {
        return new LineIterator(reader);
    }
 
    /**
     * 
     * @param text
     * @return 
     */
    public static LineIterator of(final CharSequence text) {
        final CharSequence cs = coalesce(text, StringUtils.EMPTY);
        if (cs instanceof String str) {
            return of(new StringReader(str));
        }
        return of(new CharSequenceReader(text));
    }

    /**
     * 
     * @param input
     * @return 
     */
    public static LineIterator of(final InputStream input) {
        return of(new InputStreamReader(input));
    }

    /**
     * 
     * @param input
     * @param encoding
     * @return 
     */
    public static LineIterator of(final InputStream input, final String encoding) {
        return of(new InputStreamReader(input, Charsets.toCharset(encoding)));
    }

    /**
     * 
     * @param input
     * @param encoding
     * @return 
     */
    public static LineIterator of(final InputStream input, final Charset encoding) {
        return of(new InputStreamReader(input, encoding));
    }
    
    /**
     * 
     * @param file
     * @return
     * @throws IOException 
     */
    public static LineIterator of(final File file) throws IOException {
        return of(file, (String)null);
    }

    /**
     * 
     * @param file
     * @return
     * @throws IOException 
     */
    public static LineIterator of(final Path file) throws IOException {
        return of(file.toFile(), (String)null);
    }

    /**
     * 
     * @param file
     * @param encoding
     * @return
     * @throws IOException 
     */
    public static LineIterator of(final Path file, final String encoding) throws IOException {
        return of(file.toFile(), encoding);
    }

    /**
     * 
     * @param file
     * @param encoding
     * @return
     * @throws IOException 
     */
    public static LineIterator of(final Path file, final Charset encoding) throws IOException {
        return of(file.toFile(), encoding);
    }

    /**
     * 
     * @param file
     * @param encoding
     * @return
     * @throws IOException 
     */
    public static LineIterator of(final File file, final String encoding) throws IOException {
        InputStream in = null;
        try {
            in = FileUtils.openInputStream(file);
            return of(in, encoding);
        } catch (final IOException | RuntimeException ex) {
            IOUtils.closeQuietly(in);
            throw ex;
        }
    }
    
    /**
     * 
     * @param file
     * @param encoding
     * @return
     * @throws IOException 
     */
    public static LineIterator of(final File file, final Charset encoding) throws IOException {
        InputStream in = null;
        try {
            in = FileUtils.openInputStream(file);
            return of(in, encoding);
        } catch (final IOException | RuntimeException ex) {
            IOUtils.closeQuietly(in);
            throw ex;
        }
    }

}
