/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.text;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import java.io.IOException;

import java.io.UncheckedIOException;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.AbstractIterator;

/**
 *
 * @author AndreaR
 */
public class TextTokenizer {
    
    /**
     * 
     * @param delim
     * @param returnTokens 
     */
    protected TextTokenizer(
            final CharSequence delim, 
            final boolean returnTokens) {
        m_delim = delim;
        m_returnTokens = returnTokens;
    }
    
    /**
     * 
     * @return 
     */
    public static TextTokenizer create() {
        return new TextTokenizer(" \t\n\r", false);
    }
    
    /**
     * 
     * @param delimiters
     * @return 
     */
    public static TextTokenizer on(final CharSequence delimiters) {
        return new TextTokenizer(delimiters, false);
    }
    
    /**
     * 
     * @param delimiters
     * @param returnTokens
     * @return 
     */
    public static TextTokenizer on(final CharSequence delimiters,
            final boolean returnTokens) {
        return new TextTokenizer(delimiters, returnTokens);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public Worker tokenize(final CharSequence cs) {
        return new Worker(m_delim, m_returnTokens, cs);
    }
    
    /**
     * 
     * @return 
     */
    protected CharSequence getDelimiters() {
        return m_delim;
    }
    
    /**
     * 
     * @return 
     */
    protected boolean isReturnTokens() {
        return m_returnTokens;
    }

    /**
     * 
     */
    private final CharSequence m_delim;

    /**
     * 
     */
    private final boolean m_returnTokens;    
    
    /**
     * 
     */
    public class Worker implements Iterable<String> {

        /**
         * 
         * @param delim
         * @param returnTokens
         * @param text 
         */
        Worker(final CharSequence delim,
                final boolean returnTokens,
                final CharSequence text) {
            m_delim = delim;
            m_returnTokens = returnTokens;
            m_text = text;
            m_index = -1;
            m_current = 0;
        }

        /**
         * Are there any more delimiters in the string?  This should only be called
         * if hasMoreTokens is false, to determine if the string contains trailing
         * delimiters.
         * @return 
         */
        public boolean hasMoreDelimiters() {
            return (m_current < m_text.length());
        }

        /**
         * 
         * @return 
         */
        public String nextToken() {
            final StringBuilder sb = new StringBuilder();
            if (appendNextToken(sb)) {
                return sb.toString();
            }
            return null;
        }

        /**
         * 
         * @param delim
         * @return 
         */
        public String nextToken(final CharSequence delim) {
            setDelimiters(delim);
            return nextToken();
        }

        /**
         * 
         * @param delim 
         */
        public void setDelimiters(final CharSequence delim) {
            m_delim = delim;
        }

        /**
         * 
         * @return 
         */
        public List<String> toList() {
            assertIterationNotStarted();
            final List<String> result = new ArrayList<>();
            String token;
            while ((token = nextToken()) != null) {
                result.add(token);
            }
            return result;
        }
        
        /**
         * 
         * @return 
         */
        public String[] toArray() {
            assertIterationNotStarted();
            String[] result = new String[32];
            int size = 0;
            String token;
            while ((token = nextToken()) != null) {
                size++;
                if (size > result.length) {
                    result = Arrays.copyOf(result, result.length + 32);
                }
                result[size - 1] = token;
            }
            if (size != result.length) {
                result = Arrays.copyOf(result, size);
            }
            return result;
        }

        /**
         * 
         * @param c 
         * @return  
         */
        public int addAll(final Collection c) {
            assertIterationNotStarted();
            int count = 0;
            String token;
            while ((token = nextToken()) != null) {
                if (c.add(token)) {
                    count++;
                }
            }
            return count;
        }

        /**
         * 
         */
        protected void assertIterationNotStarted() {
            if (m_index >= 0) {
                throw new IllegalStateException();
            }
        }
        
        /**
         * 
         * @param sb
         * @return 
         */
        public boolean appendNextToken(final Appendable sb) {
            try {
                if (appendNextToken0(sb)) {
                    m_index++;
                    return true;
                }
                return false;
            } catch (final IOException ioe) {
                throw new UncheckedIOException(ioe);
            }
        }

        /**
         * 
         * @param sb
         * @return
         * @throws java.io.IOException 
         */
        protected boolean appendNextToken0(final Appendable sb) throws java.io.IOException {
            int start = m_current;
            if (m_current >= m_text.length()) {
                return false;
            }
            if (isDelim(m_current)) {
                /* This is whitespace */
                while (m_current < m_text.length() && isDelim(m_current)) {
                    m_current++;
                }
                if (m_returnTokens) {
                    m_text.subSequence(start, m_current - start);
                }
                else if (m_current >= m_text.length()) {
                    return false;
                }
            }
            boolean quoted = false;
            boolean escaped = false;
            while (true) {
                if (m_current == m_text.length()) {
                  break;
                }
                if (escaped) {
                    if (Character.digit(m_text.charAt(m_current), 10) >= 0) {
                        final String s = m_text.subSequence(m_current, 3).toString();
                        int i = Integer.parseInt(s);
                        sb.append((char)i);
                        m_current += 2;
                    }
                    else {
                        sb.append(m_text.charAt(m_current));
                    }
                    escaped = false;
                } else
                if (quoted) {
                    if (m_text.charAt(m_current) == '"') {
                        m_current++;
                        break;
                    } else {
                        sb.append(m_text.charAt(m_current));
                    }
                } else {
                    if (m_text.charAt(m_current) == '"')
                        quoted = true;
                    else
                    if (m_text.charAt(m_current) == '\\')
                        escaped = true;
                    else
                    if (isDelim(m_current)) {
                        break;
                    }
                    else {
                        sb.append(m_text.charAt(m_current));
                    }
                }
                m_current++;
            }
            return true;
        }

        /**
         *
         * @param i int
         * @return boolean
         */
        private boolean isDelim(final int i) {
            return StringUtils.indexOf(m_delim, m_text.charAt(i)) >= 0;
        }

        /** Returns a concatenation of all remaining tokens
         * @return  **/
        public CharSequence remainingTokens() {
            final StringBuilder sb = new StringBuilder();
            while (appendNextToken(sb)) {
            }
            return sb;
        }

        /**
         * 
         */
        private CharSequence m_text;

        /**
         * 
         */
        private CharSequence m_delim;

        /**
         * 
         */
        private boolean m_returnTokens;

        /**
         * 
         */
        private int m_current;

        /**
         * 
         */
        private int m_index;

        /**
         * 
         * @return 
         */
        @Override
        public Iterator<String> iterator() {
            final Worker worker = new Worker(m_delim, m_returnTokens, m_text);
            return new AbstractIterator<String>() {
                @Override
                protected String computeNext() {
                    final String token = worker.nextToken();
                    if (token != null) {
                        return token;
                    }
                    return endOfData();
                }
            };
        }
        
    }
    
}
