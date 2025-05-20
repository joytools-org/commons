/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import io.vavr.Value;
import io.vavr.collection.Iterator;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author AndreaR
 * @param <T>
 */
public interface TimeValue<T> extends Value<T>, TimeMillis, Comparable<TimeValue<T>> {
    
    @SuppressWarnings("unchecked")
    static <T> TimeValue<T> narrow(TimeValue<? extends T> value) {
        return (TimeValue<T>) value;
    }

    @Override
    default Iterator<T> iterator() {
        return isEmpty() ? Iterator.empty() : Iterator.of(get());
    }
    
    @Override
    default String stringPrefix() {
        return "TimeValue";
    }

    @Override
    default TimeValue<T> peek(final Consumer<? super T> action) {
        Objects.requireNonNull(action, "Action");
        if (!isEmpty()) {
            action.accept(get());
        }
        return this;
    }
    
    @Override
    default <U> TimeValue<U> map(final Function<? super T, ? extends U> mapper) {
        throw new UnsupportedOperationException("map()");
    }
    
    @Override
    default boolean isSingleValued() {
        return true;
    }

    @Override
    default boolean isAsync() {
        return false;
    }

    @Override
    default boolean isLazy() {
        return false;
    }

    /**
     * 
     * @return 
     */
    @Override
    public long timeMillis();
    
    /**
     * 
     * @return 
     */
    public Instant toInstant();

    /**
     * 
     * @return 
     */
    public Calendar toCalendar();

    /**
     * 
     * @return 
     */
    public java.util.Date toDate();

    /**
     * 
     * @return 
     */
    public java.sql.Date toSQLDate();
    
    /**
     * 
     * @return 
     */
    public java.sql.Time toSQLTime();

    /**
     * 
     * @return 
     */
    public java.sql.Timestamp toSQLTimestamp();
    
    /**
     * 
     * @return 
     */
    public LocalDateTime toLocalDateTime();

    /**
     * 
     * @param zoneId
     * @return 
     */
    public LocalDateTime toLocalDateTime(final ZoneId zoneId);

    /**
     * 
     * @param zoneId
     * @return 
     */
    public LocalDateTime toLocalDateTime(final ZoneOffset zoneId);
    
    /**
     * 
     * @return 
     */ /*
    default OffsetDateTime toOffsetDateTime() {
        return OffsetDateTime.ofInstant(toInstant(), ZoneId.systemDefault());
    } */

    /**
     * 
     * @param zoneId
     * @return 
     */ /*
    default OffsetDateTime toOffsetDateTime(final ZoneId zoneId) {
        return OffsetDateTime.ofInstant(toInstant(), zoneId);
    } */

    /**
     * 
     * @return 
     */ /*
    default ZonedDateTime toZonedDateTime() {
        return ZonedDateTime.ofInstant(toInstant(), ZoneId.systemDefault());
    } */

    /**
     * 
     * @param zoneId
     * @return 
     */ /*
    default ZonedDateTime toZonedDateTime(final ZoneId zoneId) {
        return ZonedDateTime.ofInstant(toInstant(), zoneId);
    } */

}
