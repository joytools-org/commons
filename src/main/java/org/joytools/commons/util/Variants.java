/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import io.vavr.Function1;
import java.util.Objects;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.time.TimeValue;
import org.joytools.commons.time.TimeValues;

/**
 *
 * @author AndreaR
 */
public class Variants {
    
    /**
     * 
     * @param value
     * @return 
     */
    public static String toString(final Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    public static byte[] toBytes(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof byte[] val) {
            return val;
        }
        throw new IllegalArgumentException("Could not convert to byte[]: " + value);
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static Boolean toBoolean(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean bool) {
            return bool;
        }
        try {
            return toInteger(value) != 0;
        } catch (final Exception e) {
            // Ignore
        }
        final String str = value.toString().trim().toLowerCase();
        if (str.isEmpty()) {
            return null;
        }
        if (StringUtils.equalsAny(str, "on", "true", "yes")) {
            return true;
        }
        if (StringUtils.equalsAny(str, "off", "false", "no")) {
            return false;
        }
        throw new IllegalArgumentException("Could not convert to Boolean: " + value);
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    public static int toBooleanInt(final Object value) {
        return Objects.requireNonNull(toBoolean(value), "Value") ? 1 : 0;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static Integer toBooleanInteger(final Object value) {
        final Boolean val = toBoolean(value);
        return val == null ? null : val ? 1 : 0;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static int toInt(final Object value) {
        Objects.requireNonNull(value, "Value");
        if (value instanceof Number num) {
            return num.intValue();
        }
        return Integer.parseInt(value.toString());
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static Integer toInteger(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number num) {
            return num.intValue();
        }
        return Integer.parseInt(value.toString());
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    public static Long toLong(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long num) {
            return num;
        }
        if (value instanceof Number num) {
            return num.longValue();
        }
        return Long.parseLong(value.toString());
    }

    /**
     * 
     * @param <T>
     * @param value
     * @return 
     */
    public static <T> Object toObject(final T value) {
        return value;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static Float toFloat(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Float num) {
            return num;
        }
        if (value instanceof Number num) {
            return num.floatValue();
        }
        return Float.parseFloat(value.toString());
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static Double toDouble(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Double num) {
            return num;
        }
        if (value instanceof Number num) {
            return num.doubleValue();
        }
        return Double.parseDouble(value.toString());
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Boolean> toBoolean() {
        return (Function1<T, Boolean>)TO_BOOLEAN;
    }
    
    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, byte[]> toBytes() {
        return (Function1<T, byte[]>)TO_BYTES;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Double> toDouble() {
        return (Function1<T, Double>)TO_DOUBLE;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Float> toFloat() {
        return (Function1<T, Float>)TO_FLOAT;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Integer> toInteger() {
        return (Function1<T, Integer>)TO_INTEGER;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Long> toLong() {
        return (Function1<T, Long>)TO_LONG;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Object> toObject() {
        return (Function1<T, Object>)TO_OBJECT;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Variant<T>> toVariant() {
        return (Function1<T, Variant<T>>)TO_COMBO_VARIANT;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, TimeValue<?>> toTimeValue() {
        return (Function1<T, TimeValue<?>>)TO_TIMEVALUE;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, ComboVariant<T>> toComboVariant() {
        return (Function1<T, ComboVariant<T>>)TO_COMBO_VARIANT;
    }

    /**
     * 
     */
    private final static Function1<Object, Boolean> TO_BOOLEAN = x -> toBoolean(x);
    
    /**
     * 
     */
    private final static Function1<Object, byte[]> TO_BYTES = x -> toBytes(x);

    /**
     * 
     */
    private final static Function1<Object, Double> TO_DOUBLE = x -> toDouble(x);

    /**
     * 
     */
    private final static Function1<Object, Float> TO_FLOAT = x -> toFloat(x);

    /**
     * 
     */
    private final static Function1<Object, Integer> TO_INTEGER = x -> toInteger(x);

    /**
     * 
     */
    private final static Function1<Object, Long> TO_LONG = x -> toLong(x);
    
    /**
     * 
     */
    private final static Function1<Object, Object> TO_OBJECT = x -> x;

    /**
     * 
     */
    private final static Function1 TO_COMBO_VARIANT = x -> ComboVariants.of(x);

    /**
     * 
     */
    private final static Function1 TO_TIMEVALUE = x -> TimeValues.findOrFailWithParseAll(x);

}
