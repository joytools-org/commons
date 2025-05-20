/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import java.io.UncheckedIOException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.function.Function;
import org.joytools.commons.function.Functions;


/**
 *
 * @author AndreaR
 */
public class Joiner implements Iterable<String> {
    
    /**
     * 
     */
    protected Joiner() {
        this.parameters = DefaultParameters.EMPTY;
    }
    
    /**
     * 
     * @param iterable 
     */
    protected Joiner(final Iterable<?> iterable) {
        this.parameters = new DefaultParameters() {
            @Override
            public Iterable iterable() {
                return iterable;
            }
        };
    }
    
    /**
     * 
     * @param parameters 
     */
    protected Joiner(final Parameters parameters) {
        if (parameters == null) {
            throw new NullPointerException("parameters");
        }
        this.parameters = parameters;
    }

    /**
     * 
     * @return 
     */
    public Parameters parameters() {
        return parameters;
    }
    
    /**
     * 
     * @param delimiter
     * @return 
     */
    public Joiner delimiter(final CharSequence delimiter) {
        final String del = StringUtils.toString(delimiter);
        final Joiner j = new Joiner(new CopyParameters(parameters) {
            @Override
            public String delimiter() {
                return del;
            }
        });
        return j;
    }
    
    /**
     * 
     * @param c
     * @return 
     */
    public Joiner delimiter(final char c) {
        final char del[] = new char[1];
        del[0] = c;
        return delimiter(new String(del));
    }
    
    /**
     * 
     * @param delimiter
     * @return 
     */
    public static Joiner on(final CharSequence delimiter) {
        return new Joiner().delimiter(delimiter);
    }

    /**
     * 
     * @param delimiter
     * @return 
     */
    public static Joiner on(final char delimiter) {
        return new Joiner().delimiter(delimiter);
    }

    /**
     * 
     * @param iterable
     * @return 
     */
    public static Joiner of(final Iterable<?> iterable) {
        return new Joiner(iterable);
    }

    /**
     * 
     * @param elements
     * @return 
     */
    public static Joiner of(final Object[] elements) {
        return new Joiner(Arrays.asList(elements));
    }

    /**
     * 
     * @param element
     * @param elements
     * @return 
     */
    public static Joiner of(final Object element,
            final Object... elements) {
        final List l = new ArrayList();
        l.add(element);
        l.addAll(Arrays.asList(elements));
        return new Joiner(l);
    }

    /**
     * 
     * @return 
     */
    public Joiner singleQuote() {
        return wrap("'");
    }

    /**
     * 
     * @return 
     */
    public Joiner doubleQuote() {
        return wrap("\"");
    }

    /**
     * 
     * @param wrapString
     * @return 
     */
    public Joiner wrap(final CharSequence wrapString) {
        return wrap(wrapString, wrapString);
    }

    /**
     * 
     * @param prefix
     * @param suffix
     * @return 
     */
    public Joiner wrap(final CharSequence prefix,
            final CharSequence suffix) {
        final String pref = StringUtils.toString(prefix);
        final String suff = StringUtils.toString(suffix);
        if (StringUtils.equals(parameters.prefix(), pref) && 
                StringUtils.equals(parameters.suffix(), suff)) {
            return this;
        }
        final Joiner j = new Joiner(new CopyParameters(parameters) {
            @Override
            public String prefix() {
                return pref;
            }
            @Override
            public String suffix() {
                return suff;
            }
        });
        return j;
    }
    
    /**
     * 
     * @return 
     */
    public Joiner omitNull() {
        if (parameters.omitMode() == OmitMode.NULL) {
            return this;
        }
        final Joiner j = new Joiner(new CopyParameters(parameters) {
            @Override
            public OmitMode omitMode() {
                return OmitMode.NULL;
            }
        });
        return j;
    }

    /**
     * 
     * @return 
     */
    public Joiner omitEmpty() {
        if (parameters.omitMode() == OmitMode.EMPTY) {
            return this;
        }
        final Joiner j = new Joiner(new CopyParameters(parameters) {
            @Override
            public OmitMode omitMode() {
                return OmitMode.EMPTY;
            }
        });
        return j;
    }

    /**
     * 
     * @param function
     * @return 
     */
    public Joiner map(final Function<?, ?> function) {
        if (parameters.transformer() == function) {
            return this;
        }
        final Joiner j = new Joiner(new CopyParameters(parameters) {
            @Override
            public Function transformer() {
                return function;
            }
        });
        return j;
    }

    /**
     * 
     * @param function
     * @return 
     */
    @Deprecated
    public Joiner deprecated_map(final com.google.common.base.Function<?, ?> function) {
        if (parameters.transformer() == function) {
            return this;
        }
        final Joiner j = new Joiner(new CopyParameters(parameters) {
            @Override
            public Function transformer() {
                return function;
            }
        });
        return j;
    }

    /**
     * 
     * @param transformer
     * @return 
     */ /*
    public Joiner transform(final org.apache.commons.collections4.Transformer<?, ?> transformer) {
        if (parameters.transformer() == transformer) {
            return this;
        }
        final Joiner j = new Joiner(new CopyParameters(parameters) {
            @Override
            public Function transformer() {
                return FluentFunction.of(transformer);
            }
        });
        return j;
    } */

    /**
     * 
     * @param limit
     * @return 
     */
    public Joiner limit(final int limit) {
        if (parameters.limit() == limit) {
            return this;
        }
        final Joiner j = new Joiner(new CopyParameters(parameters) {
            @Override
            public int limit() {
                return limit;
            }
        });
        return j;
    }

    /**
     * 
     * @param remove
     * @return 
     */
    public Joiner remove(final boolean remove) {
        if (parameters.remove() == remove) {
            return this;
        }
        final Joiner j = new Joiner(new CopyParameters(parameters) {
            @Override
            public boolean remove() {
                return remove;
            }
        });
        return j;
    }

    /**
     * 
     * @param a 
     */
    public void appendTo(final Appendable a) {
        appendTo(a, this);
    }
    
    /**
     * 
     * @param a
     * @param elements 
     */
    public void appendTo(final Appendable a,
            final Object[] elements) {
        if (a == null || elements == null) {
            return;
        }
        appendTo(a, Arrays.asList(elements));
    }

    /**
     * 
     * @param a
     * @param iterable 
     */
    public void appendTo(final Appendable a,
            final Iterable iterable) {
        if (a == null || iterable == null) {
            return;
        }
        appendTo(a, iterable.iterator());
    }
    
    /**
     * 
     * @param a
     * @param itr 
     */
    public void appendTo(final Appendable a,
            final Iterator itr) {
        if (a == null || itr == null) {
            return;
        }       
        final String delimiter = parameters.delimiter();
        final String prefix = parameters.prefix();
        final String suffix = parameters.suffix();
        final int delLen = StringUtils.length(delimiter);
        final int prefLen = StringUtils.length(prefix);
        final int suffLen = StringUtils.length(suffix);
        final int wrapLen = delLen + prefLen + suffLen;
        final OmitMode om = parameters.omitMode() == null ? OmitMode.NONE : parameters.omitMode();
        final Function transformer = parameters.transformer();
        final boolean remove = parameters.remove();
        final int limit = parameters.limit();
        try {
            int readCount = 0;
            int writeCount = 0;
            while (itr.hasNext()) {
                readCount++;
                CharSequence str = StringUtils.toCharSequence(itr.next());
                if (om.accept(str)) {
                    boolean accepted = true;
                    if (transformer != null) {
                        str = StringUtils.toCharSequence(transformer.apply(str));
                        accepted = om.accept(str);
                    }
                    if (accepted) {
                        if (++writeCount > 1 && delLen > 0) a.append(delimiter);
                        if (prefLen > 0) a.append(prefix);
                        a.append(str);
                        if (suffLen > 0) a.append(suffix);
                    }
                }
                if (remove) {
                    itr.remove();
                }
                if (limit > 0 && writeCount >= limit) {
                    break;
                }
            }
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    /**
     * 
     * @param parameters
     * @param dest 
     */    
    public static void appendTo(final Parameters parameters,
            final Appendable dest) {
        if (parameters == null) {
            throw new NullPointerException("strategy");
        }
        final Iterable iterable = parameters.iterable();
        if (iterable == null) {
            throw new NullPointerException("iterable");
        }
        if (dest == null) {
            throw new NullPointerException("appendable");
        }
        final String delimiter = parameters.delimiter();
        final String prefix = parameters.prefix();
        final String suffix = parameters.suffix();
        final int delLen = StringUtils.length(delimiter);
        final int prefLen = StringUtils.length(prefix);
        final int suffLen = StringUtils.length(suffix);
        final int wrapLen = delLen + prefLen + suffLen;
        final OmitMode om = parameters.omitMode() == null ? OmitMode.NONE : parameters.omitMode();
        final Function transformer = parameters.transformer();
        final boolean remove = parameters.remove();
        final int limit = parameters.limit();
        try {
            int readCount = 0;
            int writeCount = 0;
            final Iterator itr = iterable.iterator();
            while (itr.hasNext()) {
                readCount++;
                Object obj = itr.next();
                CharSequence str = StringUtils.toCharSequence(obj);
                if (om.accept(str)) {
                    boolean accepted = true;
                    if (transformer != null) {
                        str = StringUtils.toCharSequence(transformer.apply(str));
                        accepted = om.accept(str);
                    }
                    if (accepted) {
                        if (++writeCount > 1 && delLen > 0) dest.append(delimiter);
                        if (prefLen > 0) dest.append(prefix);
                        dest.append(str);
                        if (suffLen > 0) dest.append(suffix);
                    }
                }
                if (remove) {
                    itr.remove();
                }
                if (limit > 0 && writeCount >= limit) {
                    break;
                }
            }
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    /**
     * 
     * @return 
     */
    public String join() {
        final StringBuilder sb = new StringBuilder();
        appendTo(sb, this);
        return sb.toString();
    }
    
    /**
     * 
     * @param itr
     * @return 
     */
    public String join(final Iterator itr) {
        final StringBuilder sb = new StringBuilder();
        appendTo(sb, itr);
        return sb.toString();
    }

    /**
     * 
     * @param itr
     * @return 
     */
    public String join(final Iterable itr) {
        final StringBuilder sb = new StringBuilder();
        appendTo(sb, itr);
        return sb.toString();
    }

    /**
     * 
     * @param itr
     * @return 
     */
    public String join(final Object[] itr) {
        final StringBuilder sb = new StringBuilder();
        appendTo(sb, Arrays.asList(itr));
        return sb.toString();
    }
    
    /**
     * 
     * @param element
     * @param elements
     * @return 
     */
    public String join(final Object element, final Object... elements) {
        final List l = new ArrayList();
        l.add(element);
        l.addAll(Arrays.asList(elements));
        final StringBuilder sb = new StringBuilder();
        appendTo(sb, l);
        return sb.toString();
    }

    /**
     * 
     * @return 
     */
    @Override
    public Iterator<String> iterator() {
        final Iterable iterable = parameters.iterable();
        if (iterable == null) {
            throw new IllegalStateException("No iterator assigned to this " + getClass().getSimpleName());
        }
        return iterable.iterator();
    }

    /**
     * 
     */
    private Parameters parameters;

    /**
     * 
     */
    public static interface Parameters {
    
        public Iterable iterable();
        
        public String delimiter();
    
        public String prefix();
    
        public String suffix();
    
        public int limit();
    
        public OmitMode omitMode();
    
        public boolean remove();
    
        public Function transformer();

    }
   
    /**
     * 
     */
    public abstract static class DefaultParameters implements Parameters {

        final public static Parameters EMPTY = new DefaultParameters() {      
        };
        
        protected DefaultParameters() {
        }
        
        @Override
        public Iterable iterable() {
            return null;
        }

        @Override
        public String delimiter() {
            return null;
        }

        @Override
        public String prefix() {
            return null;
        }

        @Override
        public String suffix() {
            return null;
        }

        @Override
        public int limit() {
            return 0;
        }

        @Override
        public OmitMode omitMode() {
            return OmitMode.NONE;
        }

        @Override
        public boolean remove() {
            return false;
        }

        @Override
        public Function transformer() {
            return null;
        }
    
    }

    /**
     * 
     */
    public static class CopyParameters implements Parameters {

        protected CopyParameters(final Parameters other) {
            if (other == null) {
                throw new NullPointerException("other");
            }
            this.iterable = other.iterable();
            this.delimiter = other.delimiter();
            this.prefix = other.prefix();
            this.suffix = other.suffix();
            this.limit = other.limit();
            this.omitMode = other.omitMode();
            this.transformer = other.transformer();
            this.remove = other.remove();
        }
        
        private Iterable iterable;
        
        private String delimiter;
        
        private String prefix;

        private String suffix;
        
        private boolean remove;
        
        private int limit;
        
        private OmitMode omitMode;
        
        private Function transformer;

        @Override
        public Iterable iterable() {
            return iterable;
        }

        @Override
        public String delimiter() {
            return delimiter;
        }

        @Override
        public String prefix() {
            return prefix;
        }

        @Override
        public String suffix() {
            return suffix;
        }

        @Override
        public int limit() {
            return limit;
        }

        @Override
        public OmitMode omitMode() {
            return omitMode;
        }

        @Override
        public boolean remove() {
            return remove;
        }

        @Override
        public Function transformer() {
            return transformer;
        }
        
    }
    
    /**
     * 
     */
    public enum OmitMode {
        
        NONE() {
            @Override
            boolean accept(final CharSequence s) {
                return true;
            }
        },
        
        NULL() {
            @Override
            boolean accept(final CharSequence s) {
                return s != null;
            }
        },

        EMPTY() {
            @Override
            boolean accept(final CharSequence s) {
                return s != null && s.length() > 0;
            }
        };
        
        abstract boolean accept(final CharSequence s);
    }
        
}
