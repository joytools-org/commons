/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.text.TextStringBuilder;
import org.joytools.commons.io.LineIterators;
import java.io.UncheckedIOException;
import org.apache.commons.io.LineIterator;
import static org.joytools.commons.lang.ObjectUtils.concat;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TextLinesBuffer extends AbstractTextLines {
    
    /**
     * 
     */
    protected TextLinesBuffer() {
    }
    
    /**
     * 
     * @return 
     */
    public static TextLinesBuffer empty() {
        return new TextLinesBuffer();
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static TextLinesBuffer of(final CharSequence cs) {
        return empty().add(cs);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static TextLinesBuffer of(final CharSequence... cs) {
        return empty().add(cs);
    }
    
    /**
     * 
     * @param cs
     * @return 
     */
    public static TextLinesBuffer of(final TextLinesBuffer cs) {
        return empty().add(cs);
    }

    /**
     * 
     * @param itr
     * @return 
     */
    public static TextLinesBuffer of(final Iterable<? extends CharSequence> itr) {
        return empty().add(itr);
    }

    /**
     * 
     * @param itr
     * @return 
     */
    public static TextLinesBuffer of(final Iterator<? extends CharSequence> itr) {
        return empty().add(itr);
    }

    /**
     * 
     * @return 
     */
    public TextLinesBuffer add() {
        return add(StringUtils.EMPTY);
    }
    
    /**
     * 
     * @param obj
     * @return 
     */
    public TextLinesBuffer addToString(final Object obj) {
        if (obj == null) {
            return this;
        }
        return add(obj.toString());
    }

    /**
     * 
     * @param obj
     * @return 
     */
    public TextLinesBuffer addObject(final Object obj) {
        if (obj == null) {
            return this;
        }
        if (obj instanceof TextLinesBuffer buf) {
            return add(buf);
        }
        if (obj instanceof Iterable itr) {
            return add(itr);
        }
        if (obj instanceof Iterator itr) {
            return add(itr);
        }
        return add(obj.toString());
    }

    /**
     * 
     * @param condition
     * @param ifTrue
     * @return 
     */
    public TextLinesBuffer addIf(final boolean condition,
            final CharSequence ifTrue) {
        if (condition) {
            return add(ifTrue);
        }
        return this;
    }

    /**
     * 
     * @param condition
     * @param ifTrue
     * @param ifFalse
     * @return 
     */
    public TextLinesBuffer addIf(final boolean condition,
            final CharSequence ifTrue,
            final CharSequence ifFalse) {
        return add(condition ? ifTrue : ifFalse);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    @Override
    public TextLinesBuffer add(final CharSequence cs) {
        if (cs == null) {
            return this;
        }
        cachedToString = null;
        if (containsLineSeparator(cs)) {
            try (final LineIterator itr = LineIterators.of(cs)) {
                while (itr.hasNext()) {
                    lines.add(itr.next());
                }
            } catch (final IOException ex) {
                throw new UncheckedIOException(ex);
            }
        } else {
            lines.add(cs.toString());
        }
        return this;
    }
    
    /**
     * 
     * @param items
     * @return 
     */
    public TextLinesBuffer add(final CharSequence... items) {
        if (items == null) {
            return this;
        }
        for (final CharSequence cs : items) {
            add(cs);
        }
        return this;
    }

    /**
     * 
     * @param items
     * @return 
     */    
    public TextLinesBuffer addConcat(final CharSequence... items) {
        return add(concat((Object[])items));
    }
    
    /**
     * 
     * @param template
     * @param args
     * @return 
     */
    public TextLinesBuffer addPropertyString(
            final CharSequence template, 
            final CharSequence... args) {
        return add(PropertyStrings.of(template, args).resolve());
    }

    /**
     * 
     * @param template
     * @param args
     * @return 
     */
    public TextLinesBuffer addFormat(final CharSequence template, final Object... args) {
        return add(String.format(StringUtils.toString(template), args));
    }
    
    /**
     * 
     * @param itr
     * @return 
     */
    @Override
    public TextLinesBuffer add(final Iterable<? extends CharSequence> itr) {
        if (itr == null) {
            return this;
        }
        if (itr instanceof TextLinesBuffer buf) {
            return add(buf);
        }
        for (final CharSequence cs : itr) {
            add(cs);
        }
        return this;
    }
    
    /**
     * 
     * @param itr
     * @return 
     */
    public TextLinesBuffer add(final Iterator<? extends CharSequence> itr) {
        if (itr == null) {
            return this;
        }
        while (itr.hasNext()) {
            add(itr.next());
        }
        return this;
    }

    /**
     * 
     * @param other
     * @return 
     */
    public TextLinesBuffer add(final TextLinesBuffer other) {
        if (other == null || other.isEmpty()) {
            return this;
        }
        cachedToString = null;
        lines.addAll(other.lines);
        return this;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int size() {
        return lines.size();
    }

    /**
     * 
     * @return 
     */
    public TextLinesBuffer clear() {
        cachedToString = null;
        lines.clear();
        return this;
    }

    /**
     * 
     * @return 
     */
    public TextLines build() {
        return TextLines.of(lines);
    }
    
    /**
     * 
     * @param <T>
     * @param function
     * @return 
     */
    @Override
    public <T> T to(final Function<? super Iterable<String>, T> function) {
        return function.apply(readOnlyLines);
    }
    
    /**
     * 
     * @return 
     */
    public StringBuilder toStringBuilder() {
        return new StringBuilder(toString());
    }

    /**
     * 
     * @return 
     */
    public TextStringBuilder toTextStringBuilder() {
        return new TextStringBuilder(toString());
    }

    /**
     * 
     * @return 
     */
    @Override
    public List<String> asList() {
        return readOnlyLines;
    }
    
    /**
     * 
     * @return 
     */
    public TextLinesBuffer trim() {
        int index = lines.size() - 1;
        while (index >= 0 && lines.get(index).isEmpty()) {
            cachedToString = null;
            lines.remove(index);
            index--;
        }
        while (!lines.isEmpty() && lines.get(0).isEmpty()) {
            cachedToString = null;
            lines.remove(0);
        }
        return this;
    }
    
    /**
     * 
     * @param mapper
     * @return 
     */
    public TextLinesBuffer map(final Function<? super String, String> mapper) {
        Objects.requireNonNull(mapper, "Function");
        cachedToString = null;
        final List<String> oldLines = new ArrayList(lines);
        lines.clear();
        for (final String s : oldLines) {
            add(mapper.apply(s));
        }
        return this;
    }

    /**
     * 
     * @param filter
     * @return 
     */
    public TextLinesBuffer retain(final Predicate<? super String> filter) {
        Objects.requireNonNull(filter, "Predicate");
        return remove0(filter.negate());
    }

    /**
     * 
     * @param filter
     * @return 
     */
    public TextLinesBuffer remove(final Predicate<? super String> filter) {
        Objects.requireNonNull(filter, "Predicate");
        return remove0(filter);
    }

    /**
     * 
     * @param filter
     * @return 
     */
    private TextLinesBuffer remove0(final Predicate<? super String> filter) {
        for (final Iterator<String> itr = lines.iterator(); itr.hasNext(); ) {
            final String s = itr.next();
            if (filter.test(s)) {
                cachedToString = null;
                itr.remove();
            }
        }
        return this;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        if (cachedToString == null) {
            cachedToString = StringUtils.join(lines, System.lineSeparator());
        }
        return cachedToString;
    }

    /**
     * 
     * @return 
     */    
    @Override
    public Iterator<String> iterator() {
        return readOnlyLines.listIterator();
    }

    /**
     * 
     * @return 
     */
    public ListIterator<String> listIterator() {
        return readOnlyLines.listIterator();
    }

    /**
     * 
     */
    private String cachedToString;
    
    /**
     * 
     */
    private final List<String> lines = new ArrayList();
    
    /**
     * 
     */
    private final List<String> readOnlyLines = Collections.unmodifiableList(lines);
    
}
