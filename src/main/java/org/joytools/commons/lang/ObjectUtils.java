/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import io.vavr.Value;
import io.vavr.control.Option;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 *
 * @author AndreaR
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils { 

    /**
     * 
     */
    public ObjectUtils() {
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(final Object a, final Object b) {
        return Objects.equals(a, b);
    }

    /**
     * 
     * @param test
     * @param others
     * @return 
     */
    public static boolean equalsAny(final Object test, final Object... others) {
        for (final Object other : others) {
            if (Objects.equals(test, other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param <T>
     * @param first
     * @param second
     * @return 
     */
    public static <T> T firstNonNull(final T first, final T second) {
        return first != null ? first : second;
    }

    /**
     * 
     * @param <T>
     * @param first
     * @param second
     * @param others
     * @return 
     */
    public static <T> T firstNonNull(final T first, final T second, final T... others) {
        if (first != null) {
            return first;
        }
        if (second != null) {
            return second;
        }
        if (others == null || others.length == 0) {
            return null;
        }
        for (final T item : others) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    /**
     * 
     * @param <T>
     * @param values
     * @return 
     */
    @SafeVarargs
    public static <T> T firstNonEmpty(final T... values) {
        if (values != null) {
            for (final T val : values) {
                if (!isEmpty(val)) {
                    return val;
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static String toString(final Object o) {
        return o == null ? null : o.toString();
    }
    
    /**
     *
     * @param o Object
     * @return String
     */
    public static String toNullableString(final Object o) {
        if (o == null) {
            return null;
        }
        final String s = o.toString();
        if (s == null || s.length() == 0) {
            return null;
        }
        return s;
    }

    /**
     *
     * @param o Object
     * @return String
     */
    public static String toEmptyString(final Object o) {
        if (o == null) {
            return StringUtils.EMPTY;
        }
        final String s = o.toString();
        if (s == null || s.length() == 0) {
            return StringUtils.EMPTY;
        }
        return s;
    }

    /**
     * 
     * @param <T>
     * @param first
     * @param second
     * @return 
     */
    public static <T> T coalesce(final T first, final T second) {
        return first != null ? first : second;
    }

    /**
     * 
     * @param <T>
     * @param first
     * @param second
     * @param others
     * @return 
     */
    public static <T> T coalesce(final T first, final T second, final T... others) {
        if (first != null) {
            return first;
        }
        if (second != null) {
            return second;
        }
        if (others == null || others.length == 0) {
            return null;
        }
        for (final T item : others) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    /**
     * 
     * @param <T>
     * @param iterable
     * @return 
     */
    public static <T> T coalesce(final Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        for (final T item : iterable) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public static <T> T coalesce(final Iterator<T> iterator) {
        if (iterator == null) {
            return null;
        }
        while (iterator.hasNext()) {
            final T obj = iterator.next();
            if (obj != null) {
                return obj;
            }
        }
        return null;
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public static <T> T coalesce(final Enumeration<T> iterator) {
        if (iterator == null) {
            return null;
        }
        while (iterator.hasMoreElements()) {
            final T obj = iterator.nextElement();
            if (obj != null) {
                return obj;
            }
        }
        return null;
    }
    
    /**
     * 
     */
    private final static FailableConsumer<Object, RuntimeException> CLEARER = o -> clear(o);
    
    /**
     * 
     * @return 
     */
    public static FailableConsumer<Object, RuntimeException> clearer() {
        return CLEARER;
    }
    
    /**
     * 
     * @param items
     * @return 
     */
    public static String concat(final Object... items) {
        if (items == null || items.length == 0) {
            return null;
        }
        StringBuilder sb = null;
        for (final Object i : items) {
            if (i != null) {
                final String s = i.toString();
                if (s != null && s.length() > 0) {
                    if (sb == null) {
                        sb = new StringBuilder(s);
                    } else {
                        sb.append(s);
                    }
                }
            }
        }
        return sb == null ? null : sb.toString();
    }
    
    /**
     * 
     * @param items
     * @return 
     */
    public static String concatToNull(final Object... items) {
        final String s = concat(items);
        if (s == null || s.length() == 0) {
            return null;
        }
        return s;
    }

    /**
     * 
     * @param items
     * @return 
     */
    public static String concatToEmpty(final Object... items) {
        final String s = concat(items);
        if (s == null || s.length() == 0) {
            return StringUtils.EMPTY;
        }
        return s;
    }

    /**
     * 
     * @param o 
     * @return  
     */
    public static boolean clear(final Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof StringBuffer sb) {
            return clear(sb);
        }
        if (o instanceof StringBuilder sb) {
            return clear(sb);
        }
        return clearReflection(o);
    }
    
    /**
     * 
     * @param sb
     * @return 
     */
    private static boolean clear(final StringBuffer sb) {
        try {
            sb.delete(0, sb.length());
            sb.trimToSize();
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * 
     * @param sb
     * @return 
     */
    private static boolean clear(final StringBuilder sb) {
        try {
            sb.delete(0, sb.length());
            sb.trimToSize();
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * 
     * @param o
     * @return 
     */
    private static boolean clearReflection(final Object o) {
        try {
            MethodUtils.invokeExactMethod(o, "clear");
            minimizeCapacity(o);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
    
    /**
     * 
     * @param o
     * @return 
     */
    private static boolean minimizeCapacity(final Object o) {
        try {
            MethodUtils.invokeExactMethod(o, "minimizeCapacity");
            return true;
        } catch (final Exception e) { }
        return false;
    }
    
    /**
     * 
     * @param input
     * @return 
     */
    public static boolean isNull(final Object input) {
        return input == null;
    }
    
    /**
     * 
     * @param input
     * @return 
     */
    public static boolean isNotNull(final Object input) {
        return input != null;
    }

    /**
     * 
     * @param input
     * @return 
     */
    public static boolean isAbsent(final Object input) {
        Objects.requireNonNull(input, "Input");
        if (input instanceof Optional &&
                !Optional.class.cast(input).isPresent()) {
            return true;
        } /*
        if (input instanceof 0ption &&
                Option.class.cast(input).isEmpty()) {
            return true;
        } */
        if (input instanceof Value val && val.isEmpty()) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @param input
     * @return 
     */
    public static boolean isEmpty(final Object input) {
        if (org.apache.commons.lang3.ObjectUtils.isEmpty(input)) {
            return true;
        }
        /* if (input == null) {
            return true;
        }
        if (input instanceof CharSequence) {
            return CharSequence.class.cast(input).length() == 0;
        }
        if (input instanceof Collection) {
            return Collection.class.cast(input).isEmpty();
        }
        if (input instanceof Map) {
            return Map.class.cast(input).isEmpty();
        } */
        if (input instanceof Object[] arr) {
            return arr.length == 0;
        }
        if (input instanceof io.vavr.Value value) {
            return value.isEmpty();
        }
        if (input instanceof Optional opt) {
            return !opt.isPresent();
        }
        if (input instanceof com.google.common.base.Optional opt) {
            return !opt.isPresent();
        }
        if (input instanceof Void) {
            return true;
        }
        /*
        if (input instanceof Iterable) {
            final Iterator itr = Iterable.class.cast(input).iterator();
            if (itr instanceof AutoCloseable) {
                try {
                    return !itr.hasNext();
                } finally {
                    try {
                        AutoCloseable.class.cast(itr).close();
                    } catch (final Exception e) {}
                }
            }
            return !itr.hasNext();
        } */
        return false;        
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static boolean isNotEmpty(final Object o) {
        if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(o)) {
            return true;
        }
        return !isEmpty(o);
    }
    
    /**
     * 
     * @param <T>
     * @param first
     * @param others
     * @return 
     */ 
    public static <T> List<T> varArgsToList(final T first, final T... others) {
        final int size = 1 + (others == null ? 0 : others.length);
        return new AbstractList<T>() {
            @Override
            public T get(final int index) {
                if (index < 0 || index >= size) {
                    throw new IndexOutOfBoundsException("Invalid index: " + index);
                }
                return index == 0 ? first : others[index - 1];
            }

            @Override
            public int size() {
                return size;
            }
        };
    } 

}
