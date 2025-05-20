/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author AndreaR
 */
public abstract class MoreCharSequenceSupport implements MoreCharSequence {
    
    /**
     * 
     */
    protected MoreCharSequenceSupport() {
    }
    
    /**
     * 
     * @param sb
     * @return 
     */
    public static MoreCharSequenceSupport of(final StringBuilder sb) {
        if (sb == null) {
            return null;
        }
        return new ForStringBuilder(sb);
    }
    
    /**
     * 
     * @param sb
     * @return 
     */
    public static MoreCharSequenceSupport of(final StringBuffer sb) {
        if (sb == null) {
            return null;
        }
        return new ForStringBuffer(sb);
    }

    /**
     * 
     * @param sb
     * @return 
     */
    public static MoreCharSequenceSupport of(final org.apache.commons.text.TextStringBuilder sb) {
        if (sb == null) {
            return null;
        }
        return new ForTextStringBuilder(sb);
    }

    /**
     * 
     * @param sb
     * @return 
     */
    public static MoreCharSequenceSupport of(final org.apache.commons.lang3.text.StrBuilder sb) {
        if (sb == null) {
            return null;
        }
        return new ForOldStrBuilder(sb);
    }

    /**
     * 
     * @param sb
     * @return 
     */
    public static MoreCharSequenceSupport of(final org.apache.commons.text.StrBuilder sb) {
        if (sb == null) {
            return null;
        }
        return new ForStrBuilder(sb);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static MoreCharSequenceSupport of(final Object cs) {
        if (cs == null) {
            return null;
        }
        if (cs instanceof MoreCharSequenceSupport mcs) {
            return mcs;
        }
        if (cs instanceof StringBuilder sb) {
            return of(sb);
        }
        if (cs instanceof StringBuffer sb) {
            return of(sb);
        }
        if (cs instanceof org.apache.commons.text.StrBuilder sb) {
            return of(sb);
        }
        throw new ClassCastException();
    }

    /**
     * 
     */
    static class ForStringBuilder extends MoreCharSequenceSupport {

        ForStringBuilder(final StringBuilder as) {
            Objects.requireNonNull(as);
            s = as;
        }

        private final StringBuilder s;

        @Override
        public int capacity() {
            return s.capacity();
        }

        @Override
        public MoreCharSequence ensureCapacity(final int capacity) {
            s.ensureCapacity(capacity);
            return this;
        }

        @Override
        public MoreCharSequence delete(final int start,
                final int end) {
            s.delete(start, end);
            return this;
        }

        @Override
        public MoreCharSequence deleteCharAt(final int index) {
            s.deleteCharAt(index);
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq) throws IOException {
            s.append(csq);
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq,
                final int start,
                final int end) throws
                IOException {
            s.append(csq, start, end);
            return this;
        }

        @Override
        public Appendable append(final char c) throws IOException {
            s.append(c);
            return this;
        }

        @Override
        public MoreCharSequence insert(final int index,
                final CharSequence cs) {
            s.insert(index, s);
            return this;
        }

        @Override
        public MoreCharSequence setCharAt(final int index,
                final char c) {
            s.setCharAt(index, c);
            return this;
        }

        @Override
        public MoreCharSequence setLength(final int len) {
            s.setLength(len);
            return this;
        }

        @Override
        public int indexOf(final CharSequence cs) {
            if (cs == null) {
                return -1;
            }
            return s.indexOf(cs.toString());
        }

        @Override
        public int indexOf(final CharSequence cs,
                final int fromIndex) {
            if (cs == null) {
                return -1;
            }
            return s.indexOf(cs.toString(), fromIndex);
        }

        @Override
        public int indexOfIgnoreCase(final CharSequence cs) {
            return StringUtils.indexOfIgnoreCase(s, cs);
        }

        @Override
        public int indexOfIgnoreCase(final CharSequence cs,
                final int fromIndex) {
            return StringUtils.indexOfIgnoreCase(s, cs, fromIndex);
        }

    }

    /**
     * 
     */
    static class ForOldStrBuilder extends MoreCharSequenceSupport {

        ForOldStrBuilder(final org.apache.commons.lang3.text.StrBuilder as) {
            Objects.requireNonNull(as);
            s = as;
        }

        private final org.apache.commons.lang3.text.StrBuilder s;

        @Override
        public int capacity() {
            return s.capacity();
        }

        @Override
        public MoreCharSequence ensureCapacity(final int capacity) {
            s.ensureCapacity(capacity);
            return this;
        }

        @Override
        public MoreCharSequence delete(final int start,
                final int end) {
            s.delete(start, end);
            return this;
        }

        @Override
        public MoreCharSequence deleteCharAt(final int index) {
            s.deleteCharAt(index);
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq) throws IOException {
            s.append(csq);
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq,
                final int start,
                final int end) throws IOException {
            s.append(csq, start, end);
            return this;
        }

        @Override
        public Appendable append(final char c) throws IOException {
            s.append(c);
            return this;
        }

        @Override
        public MoreCharSequence insert(final int index,
                final CharSequence cs) {
            s.insert(index, s);
            return this;
        }

        @Override
        public MoreCharSequence setCharAt(final int index,
                final char c) {
            s.setCharAt(index, c);
            return this;
        }

        @Override
        public MoreCharSequence setLength(final int len) {
            s.setLength(len);
            return this;
        }

        @Override
        public int indexOf(final CharSequence cs) {
            if (cs == null) {
                return -1;
            }
            return s.indexOf(cs.toString());
        }

        @Override
        public int indexOf(final CharSequence cs,
                final int fromIndex) {
            if (cs == null) {
                return -1;
            }
            return s.indexOf(cs.toString(), fromIndex);
        }

        @Override
        public int indexOfIgnoreCase(final CharSequence cs) {
            return StringUtils.indexOfIgnoreCase(s, cs);
        }

        @Override
        public int indexOfIgnoreCase(final CharSequence cs,
                final int fromIndex) {
            return StringUtils.indexOfIgnoreCase(s, cs, fromIndex);
        }

    }

    /**
     * 
     */
    static class ForTextStringBuilder extends MoreCharSequenceSupport {

        ForTextStringBuilder(final org.apache.commons.text.TextStringBuilder as) {
            Objects.requireNonNull(as);
            s = as;
        }

        private final org.apache.commons.text.TextStringBuilder s;

        @Override
        public int capacity() {
            return s.capacity();
        }

        @Override
        public MoreCharSequence ensureCapacity(final int capacity) {
            s.ensureCapacity(capacity);
            return this;
        }

        @Override
        public MoreCharSequence delete(final int start,
                final int end) {
            s.delete(start, end);
            return this;
        }

        @Override
        public MoreCharSequence deleteCharAt(final int index) {
            s.deleteCharAt(index);
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq) throws IOException {
            s.append(csq);
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq,
                final int start,
                final int end) throws IOException {
            s.append(csq, start, end);
            return this;
        }

        @Override
        public Appendable append(final char c) throws IOException {
            s.append(c);
            return this;
        }

        @Override
        public MoreCharSequence insert(final int index,
                final CharSequence cs) {
            s.insert(index, s);
            return this;
        }

        @Override
        public MoreCharSequence setCharAt(final int index,
                final char c) {
            s.setCharAt(index, c);
            return this;
        }

        @Override
        public MoreCharSequence setLength(final int len) {
            s.setLength(len);
            return this;
        }

        @Override
        public int indexOf(final CharSequence cs) {
            if (cs == null) {
                return -1;
            }
            return s.indexOf(cs.toString());
        }

        @Override
        public int indexOf(final CharSequence cs,
                final int fromIndex) {
            if (cs == null) {
                return -1;
            }
            return s.indexOf(cs.toString(), fromIndex);
        }

        @Override
        public int indexOfIgnoreCase(final CharSequence cs) {
            return StringUtils.indexOfIgnoreCase(s, cs);
        }

        @Override
        public int indexOfIgnoreCase(final CharSequence cs,
                final int fromIndex) {
            return StringUtils.indexOfIgnoreCase(s, cs, fromIndex);
        }

    }

    /**
     * 
     */
    static class ForStrBuilder extends MoreCharSequenceSupport {

        ForStrBuilder(final org.apache.commons.text.StrBuilder as) {
            Objects.requireNonNull(as);
            s = as;
        }

        private final org.apache.commons.text.StrBuilder s;

        @Override
        public int capacity() {
            return s.capacity();
        }

        @Override
        public MoreCharSequence ensureCapacity(final int capacity) {
            s.ensureCapacity(capacity);
            return this;
        }

        @Override
        public MoreCharSequence delete(final int start,
                final int end) {
            s.delete(start, end);
            return this;
        }

        @Override
        public MoreCharSequence deleteCharAt(final int index) {
            s.deleteCharAt(index);
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq) throws IOException {
            s.append(csq);
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq,
                final int start,
                final int end) throws IOException {
            s.append(csq, start, end);
            return this;
        }

        @Override
        public Appendable append(final char c) throws IOException {
            s.append(c);
            return this;
        }

        @Override
        public MoreCharSequence insert(final int index,
                final CharSequence cs) {
            s.insert(index, s);
            return this;
        }

        @Override
        public MoreCharSequence setCharAt(final int index,
                final char c) {
            s.setCharAt(index, c);
            return this;
        }

        @Override
        public MoreCharSequence setLength(final int len) {
            s.setLength(len);
            return this;
        }

        @Override
        public int indexOf(final CharSequence cs) {
            if (cs == null) {
                return -1;
            }
            return s.indexOf(cs.toString());
        }

        @Override
        public int indexOf(final CharSequence cs,
                final int fromIndex) {
            if (cs == null) {
                return -1;
            }
            return s.indexOf(cs.toString(), fromIndex);
        }

        @Override
        public int indexOfIgnoreCase(final CharSequence cs) {
            return StringUtils.indexOfIgnoreCase(s, cs);
        }

        @Override
        public int indexOfIgnoreCase(final CharSequence cs,
                final int fromIndex) {
            return StringUtils.indexOfIgnoreCase(s, cs, fromIndex);
        }

    }

    /**
     * 
     */
    static class ForStringBuffer extends MoreCharSequenceSupport {

        ForStringBuffer(final StringBuffer as) {
            Objects.requireNonNull(as);
            s = as;
        }

        private final StringBuffer s;

        @Override
        public int capacity() {
            return s.capacity();
        }

        @Override
        public MoreCharSequence ensureCapacity(final int capacity) {
            s.ensureCapacity(capacity);
            return this;
        }

        @Override
        public MoreCharSequence delete(final int start,
                final int end) {
            s.delete(start, end);
            return this;
        }

        @Override
        public MoreCharSequence deleteCharAt(final int index) {
            s.deleteCharAt(index);
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq) throws IOException {
            s.append(csq);
            return this;
        }

        @Override
        public Appendable append(final CharSequence csq,
                final int start,
                final int end) throws
                IOException {
            s.append(csq, start, end);
            return this;
        }

        @Override
        public Appendable append(final char c) throws IOException {
            s.append(c);
            return this;
        }

        @Override
        public MoreCharSequence insert(final int index,
                final CharSequence cs) {
            s.insert(index, s);
            return this;
        }

        @Override
        public MoreCharSequence setCharAt(final int index,
                final char c) {
            s.setCharAt(index, c);
            return this;
        }

        @Override
        public MoreCharSequence setLength(final int len) {
            s.setLength(len);
            return this;
        }

        @Override
        public int indexOf(final CharSequence cs) {
            if (cs == null) {
                return -1;
            }
            return s.indexOf(cs.toString());
        }

        @Override
        public int indexOf(final CharSequence cs,
                final int fromIndex) {
            if (cs == null) {
                return -1;
            }
            return s.indexOf(cs.toString(), fromIndex);
        }

        @Override
        public int indexOfIgnoreCase(final CharSequence cs) {
            return StringUtils.indexOfIgnoreCase(s, cs);
        }

        @Override
        public int indexOfIgnoreCase(final CharSequence cs,
                final int fromIndex) {
            return StringUtils.indexOfIgnoreCase(s, cs, fromIndex);
        }

    }
    
}
