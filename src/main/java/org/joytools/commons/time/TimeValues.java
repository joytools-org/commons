/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import com.google.common.cache.CacheBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import org.joytools.commons.accessors.MapMutator;
import org.joytools.commons.accessors.Mutators;
import org.joytools.commons.accessors.StringAccessors;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.cache.CachedSuppliers;
import org.joytools.commons.concurrent.AtomicSuppliers;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TimeValues {

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValue<?> findOrNull(final Object value) {
        if (value == null) {
            return empty();
        }
        if (value instanceof TimeValue tv) {
            return tv;
        }
        if (value instanceof Instant inst) {
            return of(inst);
        }
        if (value instanceof Clock clock) {
            return of(clock);
        }
        if (value instanceof java.sql.Timestamp ts) {
            return of(ts);
        }
        if (value instanceof java.sql.Date dt) {
            return of(dt);
        }
        if (value instanceof java.sql.Time time) {
            return of(time);
        }
        if (value instanceof java.util.Date dt) {
            return of(dt);
        }
        if (value instanceof Calendar cal) {
            return of(cal);
        }
        if (value instanceof TimeMillis tm) {
            return of(tm);
        }
        if (value instanceof LocalDateTime ldt) {
            return of(ldt);
        }
        if (value instanceof OffsetDateTime odt) {
            return of(odt);
        }
        if (value instanceof ZonedDateTime zdt) {
            return of(zdt);
        }
        /* if (value instanceof Long) {
            return of(Long.class.cast(value));
        } */
        return null;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValue<?> findOrFail(final Object value) {
        final TimeValue<?> ret = findOrNull(value);
        if (ret == null) {
            throw new DateTimeException(String.format("%s does not contain a known TimeValue", value.toString()));
        }
        return ret;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValue<?> findOrEmpty(final Object value) {
        final TimeValue<?> ret = findOrNull(value);
        if (ret == null) {
            return TimeValues.empty();
        }
        return ret;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValue<?> findOrFailWithParseAll(final Object value) {
        final TimeValue<?> ret = findOrNullWithParseAll(value);
        if (ret == null) {
            throw new DateTimeException(value.toString());
        }
        return ret;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValue<?> findOrEmptyWithParseAll(final Object value) {
        final TimeValue<?> ret = findOrNullWithParseAll(value);
        if (ret == null) {
            return TimeValues.empty();
        }
        return ret;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValue<?> findOrNullWithParseAll(final Object value) {
        if (value != null && value instanceof CharSequence cs) {
            return parseAllOrNull(cs);
        }
        return findOrNull(value);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static TimeValue<?> parseAllOrNull(final CharSequence cs) {
        final String s = StringUtils.trimToEmpty(cs);
        
        if (s.isEmpty()) {
            return null;
        }
        
        try {
            return TimeValues.of(Instant.parse(s));
        } catch (final Exception ex) { }
        
        try {
            return TimeValues.of(java.sql.Timestamp.valueOf(s));
        } catch (final Exception ex) { }

        try {
            return TimeValues.of(java.sql.Date.valueOf(s));
        } catch (final Exception ex) { }
        
        try {
            return TimeValues.of(java.sql.Time.valueOf(s));
        } catch (final Exception ex) { }

        try {
            return TimeValues.of(LocalDateTime.parse(s));
        } catch (final Exception ex) { }
        
        try {
            return TimeValues.of(OffsetDateTime.parse(s));
        } catch (final Exception ex) { }

        try {
            return TimeValues.of(ZonedDateTime.parse(s));
        } catch (final Exception ex) { }

        for (final var e : dateTimeFormatters().values()) {
            try {
                final var ta = e.parse(s);
                
                try {
                    return TimeValues.of(Instant.from(ta));
                } catch (final Exception ex) { }
                
                try {
                    return TimeValues.of(LocalDateTime.from(ta));
                } catch (final Exception ex) { }
                
                try {
                    return TimeValues.of(OffsetDateTime.from(ta));
                } catch (final Exception ex) { }

                try {
                    return TimeValues.of(ZonedDateTime.from(ta));
                } catch (final Exception ex) { }
            } catch (final Exception ex) { }
        }
        
        for (final var e : dateFormats().values()) {
            try {
                return TimeValues.of(e.parse(s));
            } catch (final Exception ex) { }
        }
        
        try {
            return TimeValues.of(TimeParsers.parseCalendar(s, false));
        } catch (final Exception ex) { }
        
        return null;
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static TimeValue<?> parseAllOrFail(final CharSequence cs) {
        final String s = StringUtils.trimToEmpty(cs);

        if (s.isEmpty()) {
            return empty();
        }
        
        final TimeValue<?> tv = parseAllOrNull(cs);
        if (tv == null) {
            throw new DateTimeException("Value could not be parsed to a TimeValue: " + s);
        }
        
        return tv;
    }

    /**
     * 
     * @param start
     * @param stop
     * @return 
     */
    public static Duration duration(final TimeValue<?> start, final TimeValue<?> stop) {
        Objects.requireNonNull(start, "Start");
        Objects.requireNonNull(stop, "Stop");
        return Duration.ofMillis(stop.timeMillis() - start.timeMillis());
    }
    
    /**
     * 
     * @param cs
     * @return 
     */
    public static TimeValue<?> parseAllOrEmpty(final CharSequence cs) {
        final TimeValue<?> tv = parseAllOrNull(cs);
        return tv == null ? TimeValues.empty() : tv;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> TimeValue<T> empty() {
        return (TimeValue<T>)TimeValueEmpty.INSTANCE;
    }
    
    /**
     * 
     * @return 
     */
    public static TimeValue<Instant> now() {
        return of(Instant.now());
    }

    /**
     * 
     * @param cached
     * @return 
     */
    public static TimeValue<Instant> now(final boolean cached) {
        return cached ? cachedNow() : of(Instant.now());
    }

    /**
     * 
     */
    private final static Supplier<TimeValue<Instant>> CACHED_NOW = 
            CachedSuppliers.of(() -> now(), CacheBuilder.newBuilder().expireAfterWrite(Duration.ofMillis(900)));
            
    /**
     * 
     * @return 
     */
    public static TimeValue<Instant> cachedNow() {
        return CACHED_NOW.get();
    }

    /**
     * 
     * @param timeInMillis
     * @return 
     */
    public static TimeValue<Long> of(final Long timeInMillis) {
        if (timeInMillis == null) {
            return empty();
        }
        return TimeValueLong.of(timeInMillis);
    }

    /**
     * 
     * @param year
     * @param month
     * @param dayOfMonth
     * @param hour
     * @param minute
     * @param second
     * @return 
     */
    public static TimeValue<Instant> of(final int year, final int month, final int dayOfMonth,
            final int hour, final int minute, final int second) {
        final Instant instant = LocalDateTime.of(year, Month.MARCH, dayOfMonth, hour, minute, second)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return of(instant);
    }

    /**
     * 
     * @param year
     * @param month
     * @param dayOfMonth
     * @param hour
     * @param minute
     * @param second
     * @param nanoOfSeconds
     * @return 
     */
    public static TimeValue<Instant> of(final int year, final int month, final int dayOfMonth,
            final int hour, final int minute, final int second, final int nanoOfSeconds) {
        final Instant instant = LocalDateTime.of(year, Month.MARCH, dayOfMonth, hour, minute, second, nanoOfSeconds)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return of(instant);
    }

    /**
     * 
     * @param timeInMillis
     * @return 
     */
    public static TimeValue<Long> of(final TimeMillis timeInMillis) {
        if (timeInMillis == null) {
            return empty();
        }
        return new AbstractNonEmptyTimeValue<Long>() {
            @Override
            public long timeMillis() {
                return timeInMillis.timeMillis();
            }

            @Override
            public Long get() {
                return timeInMillis.timeMillis();
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param ta
     * @return 
     */
    public static <T extends TemporalAccessor> TimeValue<T> ofTemporalAccessor(final T ta) {
        if (ta == null) {
            return empty();
        }
        return TimeValueTemporalAccessor.of(ta);
    }

    /**
     * 
     * @param inst
     * @return 
     */
    public static TimeValue<Instant> of(final Instant inst) {
        if (inst == null) {
            return empty();
        }
        return TimeValueInstant.of(inst);
    }

    /**
     * 
     * @param <D>
     * @param date
     * @return 
     */ /*
    public static <D extends java.util.Date> TimeValue<D> of(final D date) {
        if (date == null) {
            return empty();
        }
        final long timeMillis = date.getTime();
        return new AbstractTimeValue<D>() {
            @Override
            public long timeMillis() {
                return timeMillis;
            }

            @Override
            public D get() {
                return date;
            }
        };
    } */

    /**
     * 
     * @param date
     * @return 
     */
    public static TimeValue<java.util.Date> of(final java.util.Date date) {
        if (date == null) {
            return empty();
        }
        return TimeValueDate.of(date);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public static TimeValue<java.sql.Date> of(final java.sql.Date date) {
        if (date == null) {
            return empty();
        }
        return TimeValueSQLDate.of(date);
    }

    /**
     * 
     * @param time
     * @return 
     */
    public static TimeValue<java.sql.Time> of(final java.sql.Time time) {
        if (time == null) {
            return empty();
        }
        return TimeValueSQLTime.of(time);
    }

    /**
     * 
     * @param ts
     * @return 
     */
    public static TimeValue<java.sql.Timestamp> of(final java.sql.Timestamp ts) {
        if (ts == null) {
            return empty();
        }
        return TimeValueSQLTimestamp.of(ts);
    }

    /**
     * 
     * @param cal
     * @return 
     */
    public static TimeValue<Calendar> of(final Calendar cal) {
        if (cal == null) {
            return empty();
        }
        return TimeValueCalendar.of(cal);
    }

    /**
     * 
     * @param clock
     * @return 
     */
    public static TimeValue<Instant> of(final Clock clock) {
        if (clock == null) {
            return empty();
        }
        return TimeValueInstant.of(clock.instant());
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValue<OffsetDateTime> of(
            final OffsetDateTime value) {
        if (value == null) {
            return empty();
        }
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValue<LocalDateTime> of(
            final LocalDateTime value) {
        if (value == null) {
            return empty();
        }
        return TimeValueLocalDateTime.of(value);
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValue<ZonedDateTime> of(
            final ZonedDateTime value) {
        if (value == null) {
            return empty();
        }
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @return
     */
    public static StringMapAccessor<DateFormat> dateFormats() {
        return DATE_FORMATS_ACC.get();
    }

    /**
     *
     * @return
     */
    public static StringMapAccessor<DateTimeFormatter> dateTimeFormatters() {
        return DATE_TIME_FORMATTERS_ACC.get();
    }
 
    /**
     *
     */
    private static final Supplier<StringMapAccessor<DateFormat>> DATE_FORMATS_ACC = AtomicSuppliers.of(() -> {
        try {
            final Map<String, DateFormat> map = CaseSensitivity.INSENSITIVE.newLinkedMap();
            final MapMutator<String, DateFormat> mut = Mutators.of(map);
            final DateFormat fmt = DateFormat.getDateInstance();
            final Class<DateFormat> clazz = DateFormat.class;
            for (final Method m : clazz.getMethods()) {
                final int mod = m.getModifiers();
                if (Modifier.isStatic(mod) && Modifier.isPublic(mod) && m.getReturnType() == clazz && m.getParameterCount() == 0) {
                    mut.insert(m.getName(), clazz.cast(m.invoke(fmt)));
                }
            }
            return StringAccessors.of(map);
        } catch (final IllegalAccessException | InvocationTargetException ex) {
            throw new UnsupportedOperationException(ex);
        }
    });
    /**
     *
     */
    private static final Supplier<StringMapAccessor<DateTimeFormatter>> DATE_TIME_FORMATTERS_ACC = AtomicSuppliers.of(() -> {
        try {
            final Map<String, DateTimeFormatter> map = CaseSensitivity.INSENSITIVE.newLinkedMap();
            final MapMutator<String, DateTimeFormatter> mut = Mutators.of(map);
            final DateTimeFormatter fmt = DateTimeFormatter.BASIC_ISO_DATE;
            final Class<DateTimeFormatter> clazz = DateTimeFormatter.class;
            for (final Field f : clazz.getFields()) {
                final int mod = f.getModifiers();
                if (Modifier.isStatic(mod) && Modifier.isPublic(mod) && f.getType() == clazz) {
                    mut.insert(f.getName(), clazz.cast(f.get(fmt)));
                }
            }
            return StringAccessors.of(map);
        } catch (final IllegalAccessException ex) {
            throw new UnsupportedOperationException(ex);
        }
    });
    
}
