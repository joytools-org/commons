/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.apache.commons.lang3.exception.UncheckedException;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.time.TimeConverters;
import org.joytools.commons.util.Variants;

/**
 *
 * @author AndreaR
 */
public class RecordUtils {
 
    /**
     * 
     * @param <R>
     * @param <V>
     * @param clazz
     * @param payload
     * @return 
     */
    public static <R extends Record, V> R newRecord(final Class<R> clazz, final MapAccessor<String, V> payload) {
        Objects.requireNonNull(clazz, "Class");
        Objects.requireNonNull(payload, "Payload");
        if (!clazz.isRecord()) {
            throw new IllegalArgumentException(String.format("%s is not a java record", clazz));
        }
        if (!Modifier.isPublic(clazz.getModifiers())) {
            throw new UnsupportedOperationException(String.format("Record %s is not public", clazz.getName()));
        }
        final Constructor[] cc = clazz.getConstructors();
        if (cc.length != 1) {
            throw new IllegalArgumentException(String.format("Invalid number of constructors for %s: %s", clazz, cc.length));
        }
        final Constructor c = cc[0];
        final List<Object> args = new ArrayList();
        for (final var p : c.getParameters()) {
            Object val = payload.getOrFail(p.getName());
            if (val != null && p.getType() != val.getClass()) {
                final var converter = DEFAULT_CONVERTERS.get(p.getType());
                if (converter != null) {
                    try {
                        val = converter.apply(val);
                    } catch (final RuntimeException ex) {
                        if (Objects.equals(val, StringUtils.EMPTY)) {
                            try {
                                val = converter.apply(null);
                            } catch (final RuntimeException ignore) {
                                throw ex;
                            }
                        } else {
                            throw ex;
                        }
                    }
                } else  {
                    throw new IllegalArgumentException(String.format("Argument type mismatch for %s: %s, %s", p.getName(), p.getType(), val.getClass()));
                }
            }
            args.add(val);
        }
        try {
            return (R)c.newInstance(args.toArray());
        } catch (final InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new UncheckedException(ex);
        }
    }

    /**
     * 
     */
    private final static IdentityHashMap<Class, Function<Object, ?>> DEFAULT_CONVERTERS = new IdentityHashMap<>();
    static {
        DEFAULT_CONVERTERS.put(CharSequence.class, x -> (x instanceof CharSequence ? x : StringUtils.toString(x)));
        DEFAULT_CONVERTERS.put(String.class, Objects::toString);
        DEFAULT_CONVERTERS.put(int.class, Variants.toInteger());
        DEFAULT_CONVERTERS.put(Integer.class, Variants.toInteger());
        DEFAULT_CONVERTERS.put(long.class, Variants.toLong());
        DEFAULT_CONVERTERS.put(Long.class, Variants.toLong());
        DEFAULT_CONVERTERS.put(boolean.class, Variants.toBoolean());
        DEFAULT_CONVERTERS.put(Boolean.class, Variants.toBoolean());
        DEFAULT_CONVERTERS.put(double.class, Variants.toDouble());
        DEFAULT_CONVERTERS.put(Double.class, Variants.toDouble());
        DEFAULT_CONVERTERS.put(float.class, Variants.toFloat());
        DEFAULT_CONVERTERS.put(Float.class, Variants.toFloat());
        DEFAULT_CONVERTERS.put(Calendar.class, TimeConverters.toCalendar());
        DEFAULT_CONVERTERS.put(java.util.Date.class, TimeConverters.toDate());
        DEFAULT_CONVERTERS.put(java.sql.Date.class, TimeConverters.toSQLDate());
        DEFAULT_CONVERTERS.put(java.sql.Time.class, TimeConverters.toSQLTime());
        DEFAULT_CONVERTERS.put(java.sql.Timestamp.class, TimeConverters.toSQLTimestamp());
        DEFAULT_CONVERTERS.put(Instant.class, TimeConverters.toInstant());
    }         
    
}
