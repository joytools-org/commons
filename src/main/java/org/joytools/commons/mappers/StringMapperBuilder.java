/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.mappers;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.Value;
import io.vavr.control.Option;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.apache.commons.collections4.IteratorUtils;

import java.util.function.Function;
import java.util.function.Predicate;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.accessors.MapMutator;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 * @author AndreaR
 * @param <O>
 */
public final class StringMapperBuilder<O> extends MapperBuilder<String, O> implements CaseSensitivitySupport {

    /**
     * 
     */
    public StringMapperBuilder() {
        this(CaseSensitivity.SENSITIVE, null);
    }

    /**
     * 
     * @param ignoreCase 
     */
    public StringMapperBuilder(final boolean ignoreCase) {
        this(CaseSensitivity.forIgnoreCase(ignoreCase), null);
    }

    /**
     * 
     * @param cs 
     */
    public StringMapperBuilder(final CaseSensitivity cs) {
        this(cs, null);
    }

    /**
     *
     * @param valType 
     * @param cs 
     */
    public StringMapperBuilder(final CaseSensitivity cs,
            final Class<O> valType) {
        this.caseSensitivity = Objects.requireNonNull(cs, "Case Sensitivity");
        if (cs.isUnknown()) {
            throw new IllegalArgumentException("Case Sentivitity: " + cs);
        }
        this.valueType = valType;
    }
    
    /**
     * 
     * @return 
     */
    public static StringMapperBuilder<String> of() {
        return of(CaseSensitivity.SENSITIVE, String.class);
    }
    
    /**
     * 
     * @param <T>
     * @param valueType
     * @return 
     */
    public static <T> StringMapperBuilder<T> of(final Class<T> valueType) {
        return of(CaseSensitivity.SENSITIVE, valueType);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static StringMapperBuilder<String> of(final CaseSensitivity cs) {
        return of(cs, String.class);
    }

    /**
     * 
     * @param <T>
     * @param valueType
     * @param cs
     * @return 
     */
    public static <T> StringMapperBuilder<T> of(final CaseSensitivity cs, 
            final Class<T> valueType) {
        return new StringMapperBuilder<>(cs, valueType);
    }

    /**
     * 
     * @return 
     */
    public static StringMapperBuilder<String> ofIgnoreCase() {
        return of(CaseSensitivity.INSENSITIVE, String.class);
    }

    /**
     * 
     * @param <T>
     * @param valueType
     * @return 
     */
    public static <T> StringMapperBuilder<T> ofIgnoreCase(final Class<T> valueType) {
        return of(CaseSensitivity.INSENSITIVE, valueType);
    }

    /*
    public Function<String, Value<O>> buildStringMapper() {
        final Function<String, Value<O>> mapper = buildMapper();
        return s -> {
            return mapper.apply(s);
        };
    }

    public Function<String, O> buildStringFunction() {
        final Function<String, O> mapper = buildFunction();
        return s -> {
            return mapper.apply(s);
        };
    }

    public Predicate<String> buildStringPredicate() {
        final Predicate<String> predicate = buildPredicate();
        return s -> {
            return predicate.test(s);
        };
    }
    
    public Function<String, List<O>> buildStringList() {
        final Function<String, List<O>> function = buildList();
        return s -> {
            return function.apply(s);
        };
    }
    */

    /**
     * 
     * @param mapper
     * @return 
     */
    @Override
    public StringMapperBuilder<O> mapInput(final Function<? super String, ? extends String> mapper) {
        super.mapInput(mapper);
        return this;
    }

    /**
     * 
     * @param mapper
     * @return 
     */
    @Override
    public StringMapperBuilder<O> mapOutput(final Function<? super O, ? extends O> mapper) {
        super.mapOutput(mapper);
        return this;
    }

    /**
     * 
     * @return 
     */
    public boolean isIgnoreCase() {
        return caseSensitivity().isIgnoreCase();
    }

    /**
     * 
     * @param elements
     * @return 
     */
    @Deprecated
    protected io.vavr.collection.TreeSet<String> old_toTreeSet(final String... elements) {
        return io.vavr.collection.TreeSet.of(caseSensitivity().comparator(), elements);
    }
       
    /**
     * 
     * @return 
     */
    @Override
    public CaseSensitivity caseSensitivity() {
        return caseSensitivity;
    }
    
    /**
     * 
     * @param <R>
     * @param function
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> addFunction(final Function<? super String, R> function) {
        super.addFunction(function);
        return this;
    }
    
    /**
     * 
     * @param <R>
     * @param function
     * @param noneTest
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> addFunction(final Function<? super String, R> function,
            final Predicate<? super R> noneTest) {
        super.addFunction(function, noneTest);
        return this;
    }
    

    /**
     * 
     * @param p
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> when(final Predicate<? super String> p, final R value) {
        super.when(p, value);
        return this;
    }

    /**
     * 
     * @param p
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> when(final Predicate<? super String> p, final Option<R> value) {
        super.when(p, value);
        return this;
    }

    /**
     * 
     * @param p
     * @param supplier
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> when(final Predicate<? super String> p, final Supplier<R> supplier) {
        super.when(p, supplier);
        return this;
    }
    
    /**
     * 
     * @param p
     * @param mapper
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> when(final Predicate<? super String> p, final Function<? super String, Option<R>> mapper) {
        super.when(p, mapper);
        return this;
    }

    /**
     * 
     * @param <R>
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenEmpty(final R value) {
        return when(StringPredicates.isEmpty(), value);
    }

    /**
     * 
     * @param <R>
     * @param supplier
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenEmpty(final Supplier<R> supplier) {
        return when(StringPredicates.isEmpty(), supplier);
    }

    /**
     * 
     * @param <R>
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenEmpty(final Option<R> value) {
        return when(StringPredicates.isEmpty(), value);
    }

    /**
     * 
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> whenNull(final R value) {
        super.whenNull(value);
        return this;
    }

    /**
     * 
     * @param supplier
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> whenNull(final Supplier<R> supplier) {
        super.whenNull(supplier);
        return this;
    }

    /**
     * 
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> whenNull(final Option<R> value) {
        super.whenNull(value);
        return this;
    }

    /**
     * 
     * @param mapper
     * @return 
     */
    @Override
    public <R extends O, V extends Value<R>> StringMapperBuilder<O> addMapper(final Function<? super String, ? extends V> mapper) {
        super.addMapper(mapper);
        return this;
    }
    
    /**
     * 
     * @param <R>
     * @param searchFor
     * @param retValue
     * @param ignoreCase 
     * @return  
     */
    @Deprecated
    public <R extends O> StringMapperBuilder<O> old_whenContains(
            final String searchFor,
            final boolean ignoreCase,
            final R retValue) {
        // return StringMapperBuilder.this.when(StringPredicates.contains(searchFor, ignoreCase), retValue);
        return when(StringPredicates.contains(searchFor, ignoreCase), retValue);
    }       
    
    /**
     * 
     * @param <R>
     * @param searchFor
     * @param retValue 
     * @return  
     */
    public <R extends O> StringMapperBuilder<O> whenContains(
            final CharSequence searchFor,
            final R retValue) {
        // return StringMapperBuilder.this.when(StringPredicates.contains(searchFor, isIgnoreCase()), retValue);
        return when(StringPredicates.contains(searchFor, isIgnoreCase()), retValue);
    }       

    /**
     * 
     * @param <R>
     * @param searchFor
     * @param retValue
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenContains(
            final CharSequence searchFor,
            final Option<R> retValue) {
        // return StringMapperBuilder.this.when(StringPredicates.contains(searchFor, isIgnoreCase()), retValue);
        return when(StringPredicates.contains(searchFor, isIgnoreCase()), retValue);
    }       

    /**
     * 
     * @param <R>
     * @param searchFor
     * @param retValue
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenContains(
            final CharSequence searchFor,
            final Supplier<R> retValue) {
        // return StringMapperBuilder.this.when(StringPredicates.contains(searchFor, isIgnoreCase()), retValue);
        return when(StringPredicates.contains(searchFor, isIgnoreCase()), retValue);
    }       

    /**
     * 
     * @param <R>
     * @param searchFor
     * @param retValue
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenContains(
            final CharSequence searchFor,
            final Function<String, Option<R>> retValue) {
        // return StringMapperBuilder.this.when(StringPredicates.contains(searchFor, isIgnoreCase()), retValue);
        return when(StringPredicates.contains(searchFor, isIgnoreCase()), retValue);
    }       

    /**
     * 
     * @param <R>
     * @param searchFor
     * @param retValue 
     * @return  
     */
    @Deprecated
    public <R extends O> StringMapperBuilder<O> old_whenContainsIgnoreCase(
            final String searchFor,
            final R retValue) {
        // return StringMapperBuilder.this.when(StringPredicates.contains(searchFor, true), retValue);
        return when(StringPredicates.containsIgnoreCase(searchFor), retValue);
    }       

    /**
     * 
     * @param <R>
     * @param value
     * @param endsWith
     * @param ignoreCase 
     * @return  
     */
    @Deprecated
    public <R extends O> StringMapperBuilder<O> old_whenEndsWith(
            final String endsWith,
            final boolean ignoreCase,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.endsWith(endsWith, ignoreCase), value);
        return when(StringPredicates.endsWith(endsWith, ignoreCase), value);
    }    

    /**
     * 
     * @param <R>
     * @param endsWith
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilder<O> whenEndsWith(
            final CharSequence endsWith,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.endsWith(endsWith, isIgnoreCase()), value);
        return when(StringPredicates.endsWith(endsWith, isIgnoreCase()), value);
    }    
    
    /**
     * 
     * @param <R>
     * @param endsWith
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenEndsWith(
            final CharSequence endsWith,
            final Option<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.endsWith(endsWith, isIgnoreCase()), value);
        return when(StringPredicates.endsWith(endsWith, isIgnoreCase()), value);
    }    

    /**
     * 
     * @param <R>
     * @param endsWith
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenEndsWith(
            final CharSequence endsWith,
            final Supplier<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.endsWith(endsWith, isIgnoreCase()), value);
        return when(StringPredicates.endsWith(endsWith, isIgnoreCase()), value);
    }    

    /**
     * 
     * @param <R>
     * @param endsWith
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenEndsWith(
            final CharSequence endsWith,
            final Function<? super String, Option<R>> value) {
        // return StringMapperBuilder.this.when(StringPredicates.endsWith(endsWith, isIgnoreCase()), value);
        return when(StringPredicates.endsWith(endsWith, isIgnoreCase()), value);
    }    

    /**
     * 
     * @param <R>
     * @param endsWith
     * @param value 
     * @return  
     */
    @Deprecated
    public <R extends O> StringMapperBuilder<O> old_whenEndsWithIgnoreCase(
            final String endsWith,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.endsWith(endsWith, true), value);
        return when(StringPredicates.endsWithIgnoreCase(endsWith), value);
    }    

    /**
     * 
     * @param <R>
     * @param like
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilder<O> whenLike(
            final CharSequence like,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.like(like, isIgnoreCase()), value);
        return when(StringPredicates.like(like, isIgnoreCase()), value);
    }    

    /**
     * 
     * @param <R>
     * @param like
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenLike(
            final CharSequence like,
            final Option<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.like(like, isIgnoreCase()), value);
        return when(StringPredicates.like(like, isIgnoreCase()), value);
    }    

    /**
     * 
     * @param <R>
     * @param like
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenLike(
            final CharSequence like,
            final Supplier<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.like(like, isIgnoreCase()), value);
        return when(StringPredicates.like(like, isIgnoreCase()), value);
    }    

    /**
     * 
     * @param <R>
     * @param like
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenLike(
            final CharSequence like,
            final Function<? super String, Option<R>> value) {
        // return StringMapperBuilder.this.when(StringPredicates.like(like, isIgnoreCase()), value);
        return when(StringPredicates.like(like, isIgnoreCase()), value);
    }    

    /**
     * 
     * @param <R>
     * @param like
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenNotLike(
            final CharSequence like,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.like(like, isIgnoreCase()), value);
        return when(StringPredicates.like(like, isIgnoreCase()).negate(), value);
    }    

    /**
     * 
     * @param <R>
     * @param like
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenNotLike(
            final CharSequence like,
            final Option<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.like(like, isIgnoreCase()), value);
        return when(StringPredicates.like(like, isIgnoreCase()).negate(), value);
    }    

    /**
     * 
     * @param <R>
     * @param like
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenNotLike(
            final CharSequence like,
            final Supplier<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.like(like, isIgnoreCase()), value);
        return when(StringPredicates.like(like, isIgnoreCase()).negate(), value);
    }    

    /**
     * 
     * @param <R>
     * @param like
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenNotLike(
            final CharSequence like,
            final Function<? super String, Option<R>> value) {
        // return StringMapperBuilder.this.when(StringPredicates.like(like, isIgnoreCase()), value);
        return when(StringPredicates.like(like, isIgnoreCase()).negate(), value);
    }    

    /**
     * 
     * @param <R>
     * @param equalsTo
     * @param value 
     * @return  
     */
    @Override
    public <R extends O> StringMapperBuilder<O> whenEquals(
            final String equalsTo,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.equals(equalsTo, isIgnoreCase()), value);
        return when(StringPredicates.equals(equalsTo, isIgnoreCase()), value);
    }

    /**
     * 
     * @param <R>
     * @param equalsTo
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> whenEquals(
            final String equalsTo,
            final Option<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.equals(equalsTo, isIgnoreCase()), value);
        return when(StringPredicates.equals(equalsTo, isIgnoreCase()), value);
    }

    /**
     * 
     * @param <R>
     * @param equalsTo
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> whenEquals(
            final String equalsTo,
            final Supplier<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.equals(equalsTo, isIgnoreCase()), value);
        return when(StringPredicates.equals(equalsTo, isIgnoreCase()), value);
    }

    /**
     * 
     * @param <R>
     * @param equalsTo
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> whenEquals(
            final String equalsTo,
            final Function<? super String, Option<R>> value) {
        // return StringMapperBuilder.this.when(StringPredicates.equals(equalsTo, isIgnoreCase()), value);
        return when(StringPredicates.equals(equalsTo, isIgnoreCase()), value);
    }

    /**
     * 
     * @param <R>
     * @param value
     * @param pattern
     * @param flags 
     * @return  
     */
    public <R extends O> StringMapperBuilder<O> whenMatches(
            final CharSequence pattern,
            final int flags,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.matches(newPattern(pattern, flags)), value);
        return when(StringPredicates.matches(pattern, flags), value);
    }

    /**
     * 
     * @param <R>
     * @param pattern
     * @param flags
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenMatches(
            final CharSequence pattern,
            final int flags,
            final Option<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.matches(newPattern(pattern, flags)), value);
        return when(StringPredicates.matches(pattern, flags), value);
    }

    /**
     * 
     * @param <R>
     * @param pattern
     * @param flags
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenMatches(
            final CharSequence pattern,
            final int flags,
            final Supplier<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.matches(newPattern(pattern, flags)), value);
        return when(StringPredicates.matches(pattern, flags), value);
    }

    /**
     * 
     * @param <R>
     * @param pattern
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilder<O> whenMatches(
            final Pattern pattern,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.matches(pattern), value);
        return when(StringPredicates.matches(pattern), value);
    }

    /**
     * 
     * @param <R>
     * @param pattern
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenMatches(
            final Pattern pattern,
            final Option<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.matches(pattern), value);
        return when(StringPredicates.matches(pattern), value);
    }

    /**
     * 
     * @param <R>
     * @param pattern
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenMatches(
            final Pattern pattern,
            final Supplier<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.matches(pattern), value);
        return when(StringPredicates.matches(pattern), value);
    }

    /**
     * 
     * @param <R>
     * @param pattern
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenMatches(
            final Pattern pattern,
            final Function<? super String, Option<R>> value) {
        // return StringMapperBuilder.this.when(StringPredicates.matches(pattern), value);
        return when(StringPredicates.matches(pattern), value);
    }

    /**
     * 
     */
    private final static Function1<Object, Tuple2<String, String>> TO_TUPLE2 = o -> {
        final String s = StringUtils.toString(o);
        return Tuple(s, s);
    };
    
    /**
     * 
     * @param <R>
     * @param elements
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilder<O> addSet(
            final Iterable<? super String> elements,
            final R value) {
        final java.util.Map<String, String> map = toSearchableMap(elements);
        return when((final String k) -> map.containsKey(k), value);
    }

    /**
     * 
     * @param <R>
     * @param elements
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> addSet(
            final Iterable<? super String> elements,
            final Option<R> value) {
        final java.util.Map<String, String> map = toSearchableMap(elements);
        return when((final String k) -> map.containsKey(k), value);
    }

    /**
     * 
     * @param <R>
     * @param elements
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> addSet(
            final Iterable<? super String> elements,
            final Supplier<R> value) {
        final java.util.Map<String, String> map = toSearchableMap(elements);
        return when((final String k) -> map.containsKey(k), value);
    }

    /**
     * 
     * @param <R>
     * @param elements
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> addSet(
            final Iterable<? super String> elements,
            final Function<? super String, Option<R>> value) {
        final java.util.Map<String, String> map = toSearchableMap(elements);
        return when((final String k) -> map.containsKey(k), value);
    }

    /**
     * 
     * @param <R>
     * @param iterator
     * @param ignoreCase
     * @param value 
     * @return  
     */
    @Deprecated
    public <R extends O> StringMapperBuilder<O> old_whenIsAnyOf(
            final Iterable<String> iterator,
            final boolean ignoreCase,
            final R value) {
        return when(StringPredicates.isAnyOf(iterator, ignoreCase), value);
    }

    /**
     * 
     * @param <R>
     * @param iterator
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilder<O> whenEqualsAny(
            final Iterable<String> iterator,
            final R value) {
        return addSet(iterator, value);
    }

    /**
     * 
     * @param <R>
     * @param iterator
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenEqualsAny(
            final Iterable<String> iterator,
            final Option<R> value) {
        return addSet(iterator, value);
    }

    /**
     * 
     * @param <R>
     * @param iterator
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenEqualsAny(
            final Iterable<String> iterator,
            final Supplier<R> value) {
        return addSet(iterator, value);
    }

    /**
     * 
     * @param <R>
     * @param iterator
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenEqualsAny(
            final Iterable<String> iterator,
            final Function<? super String, Option<R>> value) {
        return addSet(iterator, value);
    }

    /**
     * 
     * @param <R>
     * @param iterator
     * @param value 
     * @return  
     */
    @Deprecated
    public <R extends O> StringMapperBuilder<O> old_whenIsAnyOfIgnoreCase(
            final Iterable<String> iterator,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.contains(toSortedSet(iterator, true), true), value);
        return when(StringPredicates.isAnyOfIgnoreCase(iterator), value);
    }

    /**
     * 
     * @param <R>
     * @param acc
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> addAccessor(final MapAccessor<? super String, R> acc) {
        super.addAccessor(acc);
        return this;
    }

    /**
     * 
     * @param elements
     * @return 
     */
    protected java.util.Map<String, String> toSearchableMap(final Iterable<? super String> elements) {
        final Iterable<Tuple2<String, String>> data = IterableAccessors.from(elements)
                .map(TO_TUPLE2);
        return caseSensitivity().newLinkedMap(data);
    }

    /**
     * 
     * @param <R>
     * @param map
     * @return 
     */
    protected <R extends O> java.util.Map<String, R> toSearchableMap(final java.util.Map<String, R> map) {
        final CaseSensitivity cs = caseSensitivity();
        if (CaseSensitivity.fromMap(map) == cs) {
            return map;
        }
        return cs.newLinkedMap(map);
    }
            
    /**
     * 
     * @param <R>
     * @param map
     * @return 
     */
    protected <R extends O> io.vavr.collection.Map<String, R> toSearchableMap(final io.vavr.collection.Map<String, R> map) {
        final CaseSensitivity cs = caseSensitivity();
        if (CaseSensitivity.fromMap(map) == cs) {
            return map;
        }
        return io.vavr.collection.TreeMap.ofEntries(cs.comparator(), map);
    }

    /**
     * 
     * @param <R>
     * @param map 
     * @return  
     */
    @Override
    public <R extends O> StringMapperBuilder<O> addMap(final java.util.Map<String, R> map) {
        super.addMap(toSearchableMap(map));
        return this;
    }

    /**
     * 
     * @param <R>
     * @param map
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> addMap(final io.vavr.collection.Map<String, R> map) {
        super.addMap(toSearchableMap(map));
        return this;
    }

    /**
     * 
     * @param <R>
     * @param startsWith
     * @param ignoreCase
     * @param value 
     * @return  
     */
    @Deprecated
    public <R extends O> StringMapperBuilder<O> old_whenStartsWith(
            final String startsWith,
            final boolean ignoreCase,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.startsWith(startsWith, ignoreCase), value);
        return when(StringPredicates.startsWith(startsWith, ignoreCase), value);
    }

    /**
     * 
     * @param <R>
     * @param startsWith
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilder<O> whenStartsWith(
            final CharSequence startsWith,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.startsWith(startsWith, isIgnoreCase()), value);
        return when(StringPredicates.startsWith(startsWith, isIgnoreCase()), value);
    }

    /**
     * 
     * @param <R>
     * @param startsWith
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenStartsWith(
            final CharSequence startsWith,
            final Option<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.startsWith(startsWith, isIgnoreCase()), value);
        return when(StringPredicates.startsWith(startsWith, isIgnoreCase()), value);
    }

    /**
     * 
     * @param <R>
     * @param startsWith
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenStartsWith(
            final CharSequence startsWith,
            final Supplier<R> value) {
        // return StringMapperBuilder.this.when(StringPredicates.startsWith(startsWith, isIgnoreCase()), value);
        return when(StringPredicates.startsWith(startsWith, isIgnoreCase()), value);
    }

    /**
     * 
     * @param <R>
     * @param startsWith
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilder<O> whenStartsWith(
            final CharSequence startsWith,
            final Function<? super String, Option<R>> value) {
        // return StringMapperBuilder.this.when(StringPredicates.startsWith(startsWith, isIgnoreCase()), value);
        return when(StringPredicates.startsWith(startsWith, isIgnoreCase()), value);
    }

    /**
     * 
     * @param <R>
     * @param startsWith
     * @param value 
     * @return  
     */
    @Deprecated
    public <R extends O> StringMapperBuilder<O> old_whenStartsWithIgnoreCase(
            final String startsWith,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.startsWith(startsWith, true), value);
        return when(StringPredicates.startsWithIgnoreCase(startsWith), value);
    }

    /**
     * 
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> orElse(final R value) {
        super.orElse(value);
        return this;
    }

    /**
     * 
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> orElse(final Option<R> value) {
        super.orElse(value);
        return this;
    }
    
    /**
     * 
     * @param <R>
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilder<O> orElse(final Supplier<R> value) {
        super.orElse(value);
        return this;
    }

    /**
     * 
     * @return 
     */
    @Override
    public StringMapperBuilder<O> orElseNull() {
        super.orElseNull();
        return this;
    }

    /**
     * 
     * @return 
     */
    @Override
    public StringMapperBuilder<O> orElseThrow() {
        super.orElseThrow();
        return this;
    }

    /**
     *
     * @param iterator Object
     * @param makeLowerCase boolean
     * @return SortedSet
     */
    @Deprecated
    protected static SortedSet<String> old_toSortedSet(
            final Object iterator,
            final boolean makeLowerCase) {
        final SortedSet<String> set = new TreeSet<>();
        final Iterator itr = IteratorUtils.getIterator(iterator);
        if (itr != null) {
            String s;
            while (itr.hasNext()) {
                final Object o = itr.next();
                if (o != null && (s = o.toString()) != null) {
                    if (makeLowerCase) {
                        s = s.toLowerCase();
                    }
                    set.add(s);
                }
            }
        }
        return set;
    }

    /**
     * 
     * @param <I>
     * @param <O>
     * @param map
     * @param makeLowerCase
     * @return 
     */
    @Deprecated
    protected static <I extends String, O> java.util.SortedMap<String, O> old_toSortedMap(
            final java.util.Map<I, O> map,
            final boolean makeLowerCase) {
        final java.util.SortedMap<String, O> ret = new java.util.TreeMap<>();
        if (map != null) {
            for (final java.util.Map.Entry<I, O> e : map.entrySet()) {
                final I key = e.getKey();
                String s = key == null ? null : key.toString();
                if (s != null && makeLowerCase) {
                    s = s.toLowerCase();
                }
                ret.put(s, e.getValue());
            }
        }
        return ret;
    }

    /**
     * 
     * @param <I>
     * @param <O>
     * @param map
     * @param makeLowerCase
     * @return 
     */
    @Deprecated
    protected static <I extends String, O> io.vavr.collection.SortedMap<String, O> old_toSortedMap(
            final io.vavr.collection.Map<I, O> map,
            final boolean makeLowerCase) {
        io.vavr.collection.SortedMap<String, O> ret = io.vavr.collection.TreeMap.empty();
        if (map != null) {
            for (final Tuple2<I, O> e : map) {
                final I key = e._1();
                String s = key == null ? null : key.toString();
                if (s != null && makeLowerCase) {
                    s = s.toLowerCase();
                }
                ret = ret.put(s, e._2());
            }
        }
        return ret;
    }

    /**
     * 
     * @param <X>
     * @param <Y>
     * @param <M>
     * @param map
     * @param makeLowerCase
     * @return 
     */
    @Deprecated
    public static <X extends String, Y, M extends java.util.Map<String, Y>> Function<X, Option<Y>> old_mapValue(
            final M map,
            final boolean makeLowerCase) {
        return (final X input) -> old_get(map, input, makeLowerCase);
    }

    /**
     * 
     * @param <X>
     * @param <Y>
     * @param <M>
     * @param map
     * @param makeLowerCase
     * @return 
     */
    @Deprecated
    public static <X extends String, Y, M extends io.vavr.collection.Map<String, Y>> Function<X, Option<Y>> old_mapValue(
            final M map,
            final boolean makeLowerCase) {
        return (final X input) -> old_get(map, input, makeLowerCase);
    }

    /**
     * 
     * @param <E>
     * @param <M>
     * @param map
     * @param input
     * @param makeLowerCase
     * @return 
     */
    @Deprecated
    protected static <E, M extends java.util.Map<String, E>> Option<E> old_get(
            final M map,
            final String input,
            final boolean makeLowerCase) {
        String s = input;
        if (map == null || map.isEmpty() || s == null) {
            return None();
        }
        if (makeLowerCase) {
            s = s.toLowerCase();
        }
        return AccessorMethods.get(map, s);
    }
    
    /**
     * 
     * @param <E>
     * @param <M>
     * @param map
     * @param input
     * @param makeLowerCase
     * @return 
     */
    @Deprecated
    protected static <E, M extends io.vavr.collection.Map<String, E>> Option<E> old_get(
            final M map,
            final String input,
            final boolean makeLowerCase) {
        String s = input;
        if (map == null || map.isEmpty() || s == null) {
            return None();
        }
        if (makeLowerCase) {
            s = s.toLowerCase();
        }
        return map.get(s);
    }

    /**
     * 
     * @return 
     */
    public Option<Class<O>> valueType() {
        return valueType == null ? None() : Some(valueType);
    }

    /**
     * 
     */
    private final Class<O> valueType;

    /**
     * 
     */
    private final CaseSensitivity caseSensitivity;
    
    
}
