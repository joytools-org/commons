/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.text;

import io.vavr.collection.Vector;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.io.LineIterator;
import org.joytools.commons.function.Functions;
import org.joytools.commons.function.Predicates;
import org.joytools.commons.io.LineIterators;
import static org.joytools.commons.lang.ObjectUtils.concat;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TextLines extends AbstractTextLines {

    private TextLines(final Vector<String> lines) {
        this.lines = lines;
    }

    public TextLinesBuffer builder() {
        return new TextLinesBuffer().add(this);
    }

    private final static TextLines EMPTY = new TextLines(Vector.empty());
    
    public static TextLines empty() {
        return EMPTY;
    }
    
    public static TextLines of(final TextLines lines) {
        return lines == null ? EMPTY : lines;
    }

    public static TextLines of(final TextLinesBuffer buf) {
        return buf == null ? empty() : new TextLines(Vector.ofAll(buf.asList()));
    }
    
    public static TextLines of(final Iterable<String> itr) {
        return itr == null ? empty() : new TextLines(Vector.ofAll(itr)
                .filter(Predicates.isNotNull()));
    }

    public static TextLines of(final Iterator<String> itr) {
        return empty().add(itr);
    }

    public static TextLines of(final CharSequence cs) {
        return empty().add(cs);
    }

    public static TextLines of(final CharSequence... items) {
        return empty().add(items);
    }

    @Override
    public TextLines add(final CharSequence cs) {
        if (cs == null) {
            return this;
        }
        if (containsLineSeparator(cs)) {
            final List<String> l = new ArrayList();
            try (final LineIterator itr = LineIterators.of(cs)) {
                while (itr.hasNext()) {
                    l.add(itr.next());
                }
                return new TextLines(lines.appendAll(l));
            } catch (final IOException ex) {
                throw new UncheckedIOException(ex);
            }
        } else {
            return new TextLines(lines.append(cs.toString()));
        }
    }
    
    /**
     * 
     * @param items
     * @return 
     */
    @Override
    public TextLines add(final CharSequence... items) {
        if (items == null || items.length == 0) {
            return this;
        }
        return add(Arrays.asList(items));
    }

    /**
     * 
     * @param items
     * @return 
     */
    @Override
    public TextLines add(final Iterable<? extends CharSequence> items) {
        if (items == null) {
            return this;
        }
        return add(items.iterator());
    }

    /**
     * 
     * @param items
     * @return 
     */
    @Override
    public TextLines add(final Iterator<? extends CharSequence> items) {
        if (items == null) {
            return this;
        }
        final List<String> l = new ArrayList();
        while (items.hasNext()) {
            final CharSequence cs = items.next();
            if (cs != null) {
                if (containsLineSeparator(cs)) {
                    try (final LineIterator itr = LineIterators.of(cs)) {
                        while (itr.hasNext()) {
                            l.add(itr.next());
                        }
                        return new TextLines(lines.appendAll(l));
                    } catch (final IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                } else {
                    l.add(cs.toString());
                }
            }
        }
        return new TextLines(lines.appendAll(l));
    }

    /**
     * 
     * @param other
     * @return 
     */
    public TextLines add(final TextLines other) {
        if (other == null || other.isEmpty()) {
            return this;
        }
        return new TextLines(lines.appendAll(other.lines));
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    public TextLines add(final TextLinesBuffer other) {
        if (other == null || other.isEmpty()) {
            return this;
        }
        return new TextLines(lines.appendAll(other.asList()));
    }

    /**
     * 
     * @param items
     * @return 
     */
    public TextLines addConcat(final CharSequence... items) {
        return add(concat((Object[])items));
    }
    
    /**
     * 
     * @param template
     * @param args
     * @return 
     */
    public TextLines addFormat(final CharSequence template, final Object... args) {
        return add(String.format(StringUtils.toString(template), args));
    }

    /**
     * 
     * @param obj
     * @return 
     */
    public TextLines addObject(final Object obj) {
        if (obj == null) {
            return this;
        }
        if (obj instanceof TextLines tl) {
            return add(tl);
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
     * @param template
     * @param args
     * @return 
     */
    public TextLines addPropertyString(
            final CharSequence template, 
            final CharSequence... args) {
        return add(PropertyStrings.of(template, args).resolve());
    }

    /**
     * 
     * @param condition
     * @param ifTrue
     * @return 
     */
    public TextLines addIf(final boolean condition,
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
    public TextLines addIf(final boolean condition,
            final CharSequence ifTrue,
            final CharSequence ifFalse) {
        return add(condition ? ifTrue : ifFalse);
    }
    
    /**
     * 
     * @param function
     * @return 
     */
    public TextLines map(final Function<? super String, String> function) {
        if (lines.isEmpty()) {
            return this;
        }
        return new TextLines(lines.map(function));
    }

    /**
     * 
     * @return 
     */
    public TextLines trim() {
        final var trimmedLines = lines
                .dropRightUntil(Predicates.isNotEmpty())
                .dropUntil(Predicates.isNotEmpty());
        if (lines == trimmedLines) {
            return this;
        }
        return new TextLines(trimmedLines);
    }
    
    @Override
    public List<String> asList() {
        if (list == null) {
            list = lines.asJava();
        }
        return list;
    }
    
    /**
     * 
     * @param predicate
     * @return 
     */
    public TextLines retain(final Predicate<? super String> predicate) {
        final var newLines = lines.filter(predicate);
        return lines == newLines ? this : new TextLines(newLines);
    }

    /**
     * 
     * @param predicate
     * @return 
     */
    public TextLines remove(final Predicate<? super String> predicate) {
        final var newLines = lines.reject(predicate);
        return lines == newLines ? this : new TextLines(newLines);
    }

    @Override
    public Iterator<String> iterator() {
        return lines.iterator();
    }

    @Override
    public <T> T to(final Function<? super Iterable<String>, T> function) {
        return function.apply(lines);
    }

    @Override
    public int size() {
        return lines.size();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null 
                || getClass() != obj.getClass()) {
            return false;
        }
        final TextLines other = TextLines.class.cast(obj);
        return lines.eq(other.lines);
    }
    
    @Override
    public int hashCode() {
        return lines.hashCode();
    }
    
    @Override
    public String toString() {
        if (str == null) {
            str = lines.mkString(System.lineSeparator());
        }
        return str;
    }
    
    private final Vector<String> lines;
    
    private List<String> list;

    private String str;
    
}
