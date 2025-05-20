/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Predicate;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.text.TextStringBuilder;

/**
 *
 */
public final class MultipleException extends Exception implements Iterable<Throwable> {

    /**
     *
     */
    public MultipleException() { }

    /**
     *
     * @param c
     */
    public MultipleException(final Iterable<? extends Throwable> c) {
        addAll(c);
    }

    /**
     *
     * @param exceptions
     */
    public MultipleException(final Throwable... exceptions) {
        addAll(Arrays.asList(exceptions));
    }

    /**
     *
     * @param index
     * @return 
     */
    public Throwable get(final int index) {
        return m_exceptions.get(index);
    }

    private final static int MAX_MESSAGES = 10;
    
    /**
     *
     * @return 
     */
    @Override
    public String getMessage() {
        final TextStringBuilder result = new TextStringBuilder(String.valueOf(count()))
                .append(" exception")
                .append(count() > 1 ? "s" : StringUtils.EMPTY)
                .append(" occurred: ");
        final LongAdder index = new LongAdder();
        m_exceptions
                .stream()
                .limit(MAX_MESSAGES)
                .forEach((t) -> {
                        result.appendNewLine()
                                .append("[").append(String.valueOf(index.intValue() + 1)).append("] ")
                                .append(' ')
                                .append(t.getClass().getName())
                                .append(": ")
                                .appendNewLine()
                                .append('\t')
                                .append(t.getMessage());
                        index.increment();
                });
        int remaining = count() - index.intValue();
        if (remaining > 0) {
                result.appendNewLine()
                        .append("...")
                        .append(String.valueOf(remaining))
                        .append(" exception")
                        .append(remaining > 1 ? "s" : StringUtils.EMPTY)
                        .append(" remaining");
        }
        return result.toString();
    }

    /**
     *
     * @param filter
     * @return 
     */
    public MultipleException subList(final Predicate<Throwable> filter) {
        final MultipleException result = new MultipleException();
        for (final Iterator i = m_exceptions.iterator(); i.hasNext(); ) {
            final Exception e = (Exception)i.next();
            if (filter == null || filter.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     *
     * @param e
     * @return 
     */
    protected final boolean add(final Throwable e) { 
        return e == null ? false : m_exceptions.add(ExceptionUtils.unwrapped(e)); 
    }

    /**
     *
     * @param exception
     * @return 
     */
    protected final boolean addAll(final Iterable<? extends Throwable> exception) {
        boolean changed = false;
        for (final Throwable e : exception) {
            if (add(e)) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     *
     */
    public int count() { 
        return m_exceptions.size(); 
    }

    @Override
    public Iterator<Throwable> iterator() {
        return IteratorUtils.unmodifiableIterator(m_exceptions.iterator());
    }

    /**
     *
     */
    protected List<Throwable> m_exceptions = new ArrayList<>();

    /**
     * 
     * @return 
     */
    public boolean isFlatten() {
        for (final Throwable t : m_exceptions) {
            if (t instanceof MultipleException) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * @return 
     */
    public MultipleException flatten() {
        return flatten(Integer.MAX_VALUE);
    }
    
    /**
     * 
     * @param level
     * @return 
     */
    public MultipleException flatten(final int level) {
        if (isFlatten() || level <= 0) {
            return this;
        }
        final MultipleException flatten = new MultipleException();
        for (final Throwable t : m_exceptions) {
            if (t instanceof MultipleException me) {
                flatten.addAll(me.flatten(level - 1));
            } else {
                flatten.add(t);
            }
        }
        return flatten;
    }

}