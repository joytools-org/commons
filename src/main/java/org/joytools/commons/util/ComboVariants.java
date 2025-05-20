/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.joytools.commons.lang.ObjectUtils;

/**
 *
 * @author AndreaR
 */
public class ComboVariants {
    
    /**
     * 
     * @param value
     * @return 
     */
    public static ComboVariant of(final Object value) {
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return value;
            }
        };
    }
    
    /**
     * 
     * @param <E>
     * @param getter
     * @param setter
     * @return 
     */
    public static <E> ComboVariant<E> of(
            final Supplier getter,
            final Consumer setter) {
        Objects.requireNonNull(getter, "Getter");
        Objects.requireNonNull(setter, "Setter");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return getter.get();
            }
            protected void putInner(final Object value) {
                setter.accept(value);
            }
        };
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    public static ComboVariant get(final Map m, 
            final Object key) {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(key, "Key");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return m.get(key);
            }
            
            @Override
            protected void putInner(final Object value) {
                m.put(key, value);
            }
        };
    }

    /**
     * 
     * @param l
     * @param key
     * @return 
     */
    public static ComboVariant get(final List l, 
            final int key) {
        Objects.requireNonNull(l, "List");
        Objects.requireNonNull(key, "Key");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return l.get(key);
            }
            
            @Override
            protected void putInner(final Object value) {
                l.set(key, value);
            }
        };
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    public static ComboVariant read(final Map m, 
            final Object key) {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(key, "Key");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return read0(m, key);
            }

            @Override
            protected void putInner(final Object value) {
                write0(m, key, value);
            }
        };
    }
    
    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    public static ComboVariant read(final List m, 
            final int key) {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(key, "Key");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return read0(m, key);
            }

            @Override
            protected void putInner(final Object value) {
                write0(m, key, value);
            }
        };
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    public static ComboVariant nonNull(final Map m, 
            final Object key) {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(key, "Key");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return readNonNull0(m, key);
            }

            @Override
            protected void putInner(final Object value) {
                writeNonNull0(m, key, value);
            }
        };
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    public static ComboVariant nonNull(final List m, 
            final int key) {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(key, "Key");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return readNonNull0(m, key);
            }

            @Override
            protected void putInner(final Object value) {
                writeNonNull0(m, key, value);
            }
        };
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    public static ComboVariant nonEmpty(final Map m, 
            final Object key) {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(key, "Key");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return readNonEmpty0(m, key);
            }

            @Override
            protected void putInner(final Object value) {
                writeNonEmpty0(m, key, value);
            }
        };
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    public static ComboVariant nonEmpty(final List m, 
            final int key) {
        Objects.requireNonNull(m, "List");
        Objects.requireNonNull(key, "Key");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return readNonEmpty0(m, key);
            }

            @Override
            protected void putInner(final Object value) {
                writeNonEmpty0(m, key, value);
            }
        };
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    public static ComboVariant trimNonEmpty(final Map m, 
            final Object key) {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(key, "Key");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return readTrimNonEmpty0(m, key);
            }

            @Override
            protected void putInner(final Object value) {
                writeTrimNonEmpty0(m, key, value);
            }
        };
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    public static ComboVariant trimNonEmpty(final List m, 
            final int key) {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(key, "Key");
        return new AbstractComboVariant() {
            @Override
            protected Object doGetInner() {
                return readTrimNonEmpty0(m, key);
            }

            @Override
            protected void putInner(final Object value) {
                writeTrimNonEmpty0(m, key, value);
            }
        };
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    protected static boolean checkContainsKey(final Map m, final Object key) {
        if (!m.containsKey(key)) {
            throw new IllegalArgumentException("Item " + key + " not found");
        }
        return true;
    }
    
    /**
     * 
     * @param key
     * @param value
     * @return 
     */
    protected static boolean checkNotNull(final Object key, final Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Item " + key + " is null");
        }
        return true;
    }

    /**
     * 
     * @param key
     * @param value
     * @return 
     */
    protected static boolean checkNotEmpty(final Object key, final Object value) {
        if (ObjectUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Item " + key + " is empty");
        }
        return true;
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    private static Object read0(final Map m, final Object key) {
        final Object val = m.get(key);
        if (val == null) { 
            checkContainsKey(m, key);
        }
        return val;
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    private static Object read0(final List m, final int key) {
        final Object val = m.get(key);
        // if (val == null) { 
        //    checkContainsKey(m, key);
        // }
        return val;
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    private static Object readNonNull0(final Map m, final Object key) {
        final Object val = read0(m, key);
        checkNotNull(key, val);
        return val;
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    private static Object readNonNull0(final List m, final int key) {
        final Object val = read0(m, key);
        checkNotNull(key, val);
        return val;
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    private static Object readNonEmpty0(final Map m, final Object key) {
        final Object val = read0(m, key);
        checkNotEmpty(key, val);
        return val;
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    private static Object readNonEmpty0(final List m, final int key) {
        final Object val = read0(m, key);
        checkNotEmpty(key, val);
        return val;
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    private static Object readTrimNonEmpty0(final Map m, final Object key) {
        final Object val = trim(read0(m, key));
        checkNotEmpty(key, val);
        return val;
    }

    /**
     * 
     * @param m
     * @param key
     * @return 
     */
    private static Object readTrimNonEmpty0(final List m, final int key) {
        final Object val = trim(read0(m, key));
        checkNotEmpty(key, val);
        return val;
    }

    /**
     * 
     * @param m
     * @param key
     * @param value 
     */
    private static void write0(final Map m, final Object key, Object value) {
        checkContainsKey(m, key);
        m.put(key, value);
    }

    /**
     * 
     * @param m
     * @param key
     * @param value 
     */
    private static void write0(final List m, final int key, Object value) {
        // checkContainsKey(m, key);
        m.set(key, value);
    }

    /**
     * 
     * @param m
     * @param key
     * @param value 
     */
    private static void writeNonNull0(final Map m, final Object key, Object value) {
        checkContainsKey(m, key);
        checkNotNull(key, value);
        m.put(key, value);
    }

    /**
     * 
     * @param m
     * @param key
     * @param value 
     */
    private static void writeNonNull0(final List m, final int key, Object value) {
        // checkContainsKey(m, key);
        checkNotNull(key, value);
        m.set(key, value);
    }

    /**
     * 
     * @param m
     * @param key
     * @param value 
     */
    private static void writeNonEmpty0(final Map m, final Object key, Object value) {
        checkContainsKey(m, key);
        checkNotEmpty(key, value);
        m.put(key, value);
    }

    /**
     * 
     * @param m
     * @param key
     * @param value 
     */
    private static void writeNonEmpty0(final List m, final int key, Object value) {
        // checkContainsKey(m, key);
        checkNotEmpty(key, value);
        m.set(key, value);
    }

    /**
     * 
     * @param m
     * @param key
     * @param value 
     */
    private static void writeTrimNonEmpty0(final Map m, final Object key, Object value) {
        checkContainsKey(m, key);
        final Object trimVal = trim(value);
        checkNotEmpty(key, trimVal);
        m.put(key, trimVal);
    }
    
    /**
     * 
     * @param m
     * @param key
     * @param value 
     */
    private static void writeTrimNonEmpty0(final List m, final int key, Object value) {
        // checkContainsKey(m, key);
        final Object trimVal = trim(value);
        checkNotEmpty(key, trimVal);
        m.set(key, trimVal);
    }

    /**
     * 
     * @param value
     * @return 
     */
    private static Object trim(final Object value) {
        if (value == null || !(value instanceof CharSequence)) {
            return value;
        }
        return value.toString().trim();
    }
    
}
