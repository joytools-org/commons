/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.text;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
abstract class AbstractTextLines implements Iterable<String> {
    
    public abstract <T> T to(final Function<? super Iterable<String>, T> function);
 
    public abstract int size();
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public abstract AbstractTextLines add(final CharSequence cs);
    
    public abstract AbstractTextLines add(final CharSequence... items);
    
    public abstract AbstractTextLines add(final Iterable<? extends CharSequence> items);
    
    public abstract AbstractTextLines add(final Iterator<? extends CharSequence> items);

    public abstract java.util.List<String> asList();

    protected static boolean containsLineSeparator(final CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        return StringUtils.containsAny(cs, System.lineSeparator());
    }
    
    /**
     * 
     * @param <A>
     * @param out
     * @return 
     * @throws IOException 
     */
    public final <A extends Appendable> A print(final A out) throws IOException {
        boolean first = true;
        for (final var line : this) {
            if (first) {
                first = false;
            } else {
                out.append(System.lineSeparator());
            }
            out.append(line);
        }
        return out;
    }
    
    /**
     * 
     * @param <A>
     * @param out
     * @return 
     * @throws IOException 
     */
    public final <A extends Appendable> A println(final A out) throws IOException {
        print(out);
        if (!isEmpty()) {
            out.append(System.lineSeparator());
        }
        return out;
    }
    
}
