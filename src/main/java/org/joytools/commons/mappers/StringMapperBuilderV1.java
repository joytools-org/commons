/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.mappers;

import static io.vavr.API.None;
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
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 * @author AndreaR
 * @param <O>
 */
@Deprecated
public final class StringMapperBuilderV1<O> extends MapperBuilder<String, O> implements CaseSensitivitySupport {

    /**
     *
     */
    public StringMapperBuilderV1() {
    }
    
    /**
     * 
     * @return 
     */
    static StringMapperBuilderV1<String> of() {
        return new StringMapperBuilderV1<>();
    }
    
    /**
     * 
     * @param cs
     * @return 
     */
    static StringMapperBuilderV1<String> of(final CaseSensitivity cs) {
        return new StringMapperBuilderV1<>(cs);
    }

    /**
     * 
     * @return 
     */
    public static StringMapperBuilderV1<String> ofIgnoreCase() {
        return new StringMapperBuilderV1<>(CaseSensitivity.INSENSITIVE);
    }

    /**
     * 
     * @param <T>
     * @param clazz
     * @return 
     */
    static <T> StringMapperBuilderV1<T> of(final Class<T> clazz) {
        return new StringMapperBuilderV1<>();
    }

    /**
     * 
     * @param <T>
     * @param clazz
     * @return 
     */
    public static <T> StringMapperBuilderV1<T> ofIgnoreCase(final Class<T> clazz) {
        return new StringMapperBuilderV1<>(CaseSensitivity.INSENSITIVE);
    }

    /**
     * 
     * @param <T>
     * @param clazz
     * @param cs
     * @return 
     */
    static <T> StringMapperBuilderV1<T> of(final Class<T> clazz, final CaseSensitivity cs) {
        return new StringMapperBuilderV1<>(cs);
    }

    /**
     *
     * @param cs
     */
    public StringMapperBuilderV1(final CaseSensitivity cs) {
        caseSensitivity = Objects.requireNonNull(cs, "Case Sensitivity");
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
    public StringMapperBuilderV1<O> mapInput(final Function<? super String, ? extends String> mapper) {
        super.mapInput(mapper);
        return this;
    }

    /**
     * 
     * @return 
     */
    public boolean isIgnoreCase() {
        return caseSensitivity.isIgnoreCase();
    }

    /**
     * 
     * @return 
     */
    public StringMapperBuilderV1<O> ignoreCase() {
        caseSensitivity = CaseSensitivity.INSENSITIVE;
        return this;
    }
    
    /**
     * 
     * @param elements
     * @return 
     */
    protected io.vavr.collection.TreeSet<String> toTreeSet(final String... elements) {
        return io.vavr.collection.TreeSet.of(caseSensitivity.comparator(), elements);
    }
       
    /**
     * 
     * @param cs
     * @return 
     */
    public StringMapperBuilderV1<O> caseSensitivity(final CaseSensitivity cs) {
        caseSensitivity = Objects.requireNonNull(cs, "Case Sensitivity");
        return this;
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
    public <R extends O> StringMapperBuilderV1<O> addFunction(final Function<? super String, R> function) {
        super.addFunction(function);
        return this;
    }

    /**
     * 
     * @param p
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilderV1<O> when(final Predicate<? super String> p, final R value) {
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
    public <R extends O> StringMapperBuilderV1<O> when(final Predicate<? super String> p, final Option<R> value) {
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
    public <R extends O> StringMapperBuilderV1<O> when(final Predicate<? super String> p, final Supplier<R> supplier) {
        super.when(p, supplier);
        return this;
    }
    
    /**
     * 
     * @param <R>
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilderV1<O> whenEmpty(final R value) {
        return when(StringPredicates.isEmpty(), value);
    }

    /**
     * 
     * @param <R>
     * @param supplier
     * @return 
     */
    public <R extends O> StringMapperBuilderV1<O> whenEmpty(final Supplier<R> supplier) {
        return when(StringPredicates.isEmpty(), supplier);
    }

    /**
     * 
     * @param <R>
     * @param value
     * @return 
     */
    public <R extends O> StringMapperBuilderV1<O> whenEmpty(final Option<R> value) {
        return when(StringPredicates.isEmpty(), value);
    }

    /**
     * 
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilderV1<O> whenNull(final R value) {
        super.whenNull(value);
        return this;
    }

    /**
     * 
     * @param supplier
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilderV1<O> whenNull(final Supplier<R> supplier) {
        super.whenNull(supplier);
        return this;
    }

    /**
     * 
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilderV1<O> whenNull(final Option<R> value) {
        super.whenNull(value);
        return this;
    }

    /**
     * 
     * @param function
     * @return 
     */
    @Override
    public <R extends O, V extends Value<R>> StringMapperBuilderV1<O> addMapper(final Function<? super String, ? extends V> function) {
        super.addMapper(function);
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
    public <R extends O> StringMapperBuilderV1<O> whenContains(
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
    public <R extends O> StringMapperBuilderV1<O> whenContains(
            final String searchFor,
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
    public <R extends O> StringMapperBuilderV1<O> whenContainsIgnoreCase(
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
    public <R extends O> StringMapperBuilderV1<O> whenEndsWith(
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
    public <R extends O> StringMapperBuilderV1<O> whenEndsWith(
            final String endsWith,
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
    public <R extends O> StringMapperBuilderV1<O> whenEndsWithIgnoreCase(
            final String endsWith,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.endsWith(endsWith, true), value);
        return when(StringPredicates.endsWithIgnoreCase(endsWith), value);
    }    

    /**
     * 
     * @param <R>
     * @param like
     * @param ignoreCase
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilderV1<O> whenLike(
            final String like,
            final boolean ignoreCase,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.like(like, ignoreCase), value);
        return when(StringPredicates.like(like, ignoreCase), value);
    }    
    
    /**
     * 
     * @param <R>
     * @param like
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilderV1<O> whenLike(
            final String like,
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
    public <R extends O> StringMapperBuilderV1<O> whenNotLike(
            final String like,
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
    public <R extends O> StringMapperBuilderV1<O> whenLikeIgnoreCase(
            final String like,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.likeIgnoreCase(like), value);
        return when(StringPredicates.likeIgnoreCase(like), value);
    }    
    
    /**
     * 
     * @param <R>
     * @param value
     * @param equalsTo
     * @param ignoreCase 
     * @return  
     */
    public <R extends O> StringMapperBuilderV1<O> whenEquals(
            final String equalsTo,
            final boolean ignoreCase,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.equals(equalsTo, ignoreCase), value);
        return when(StringPredicates.equals(equalsTo, ignoreCase), value);
    }

    /**
     * 
     * @param <R>
     * @param equalsTo
     * @param value 
     * @return  
     */
    @Override
    public <R extends O> StringMapperBuilderV1<O> whenEquals(
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
    public <R extends O> StringMapperBuilderV1<O> whenEquals(
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
    public <R extends O> StringMapperBuilderV1<O> whenEquals(
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
    public <R extends O> StringMapperBuilderV1<O> whenEqualsIgnoreCase(
            final String equalsTo,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.equals(equalsTo, true), value);
        return when(StringPredicates.equalsIgnoreCase(equalsTo), value);
    }
    
    /**
     * 
     * @param <R>
     * @param value
     * @param pattern
     * @param flags 
     * @return  
     */
    public <R extends O> StringMapperBuilderV1<O> whenMatches(
            final String pattern,
            final int flags,
            final R value) {
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
    public <R extends O> StringMapperBuilderV1<O> whenMatches(
            final Pattern pattern,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.matches(pattern), value);
        return when(StringPredicates.matches(pattern), value);
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
    public <R extends O> StringMapperBuilderV1<O> old_addSet(
            final Object iterator,
            final boolean ignoreCase,
            final R value) {
        throw new UnsupportedOperationException();
        // return when(StringPredicates.in(toSortedSet(iterator, ignoreCase), ignoreCase), value);
    }

    /**
     * 
     * @param <R>
     * @param iterator
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilderV1<O> addSet(
            final Object iterator,
            final R value) {
        return when(StringPredicates.isAnyOf(toSortedSet(iterator, isIgnoreCase()), isIgnoreCase()), value);
    }

    /**
     * 
     * @param <R>
     * @param iterator
     * @param value 
     * @return  
     */
    @Deprecated
    public <R extends O> StringMapperBuilderV1<O> old_addSetIgnoreCase(
            final Object iterator,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.contains(toSortedSet(iterator, true), true), value);
        return when(StringPredicates.isAnyOfIgnoreCase(toSortedSet(iterator, true)), value);
    }

    /**
     * 
     * @param <R>
     * @param iterator
     * @param ignoreCase
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilderV1<O> whenIsAnyOf(
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
    public <R extends O> StringMapperBuilderV1<O> whenIsAnyOf(
            final Iterable<String> iterator,
            final R value) {
        return when(StringPredicates.isAnyOf(iterator, isIgnoreCase()), value);
    }

    /**
     * 
     * @param <R>
     * @param iterator
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilderV1<O> whenIsAnyOfIgnoreCase(
            final Iterable<String> iterator,
            final R value) {
        // return StringMapperBuilder.this.when(StringPredicates.contains(toSortedSet(iterator, true), true), value);
        return when(StringPredicates.isAnyOfIgnoreCase(iterator), value);
    }

    /**
     * 
     * @param <R>
     * @param map
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilderV1<O> addAccessor(final MapAccessor<? super String, R> map) {
        add(map);
        return this;
    }

    /**
     * 
     * @param <R>
     * @param map 
     * @return  
     */
    @Override
    public <R extends O> StringMapperBuilderV1<O> addMap(final java.util.Map<String, R> map) {
        return addMapper(mapValue(toSortedMap(map, isIgnoreCase()), isIgnoreCase()));
    }

    /**
     * 
     * @param <R>
     * @param map
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilderV1<O> addMap(final io.vavr.collection.Map<String, R> map) {
        return addMapper(mapValue(toSortedMap(map, isIgnoreCase()), isIgnoreCase()));
    }

    /**
     * 
     * @param <R>
     * @param map
     * @param ignoreCase 
     * @return  
     */
    @Deprecated
    public <R extends O> StringMapperBuilderV1<O> old_addMap(
            final java.util.Map<String, R> map,
            final boolean ignoreCase) {
        return addMapper(mapValue(toSortedMap(map, ignoreCase), ignoreCase));
    }

    /**
     * 
     * @param <R>
     * @param map
     * @param ignoreCase
     * @return 
     */
    @Deprecated
    public <R extends O> StringMapperBuilderV1<O> old_addMap(
            final io.vavr.collection.Map<String, R> map,
            final boolean ignoreCase) {
        return addMapper(mapValue(toSortedMap(map, ignoreCase), ignoreCase));
    }

    /**
     * 
     * @param <R>
     * @param map
     * @return 
     */
    @Deprecated
    public <R extends O> StringMapperBuilderV1<O> old_addMapIgnoreCase(final java.util.Map<String, R> map) {
        return addMapper(mapValue(toSortedMap(map, true), true));
    }

    /**
     * 
     * @param <R>
     * @param map
     * @return 
     */
    @Deprecated
    public <R extends O> StringMapperBuilderV1<O> old_addMapIgnoreCase(final io.vavr.collection.Map<String, R> map) {
        return addMapper(mapValue(toSortedMap(map, true), true));
    }

    /**
     * 
     * @param <R>
     * @param startsWith
     * @param ignoreCase
     * @param value 
     * @return  
     */
    public <R extends O> StringMapperBuilderV1<O> whenStartsWith(
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
    public <R extends O> StringMapperBuilderV1<O> whenStartsWith(
            final String startsWith,
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
    public <R extends O> StringMapperBuilderV1<O> whenStartsWithIgnoreCase(
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
    public <R extends O> StringMapperBuilderV1<O> orElse(final R value) {
        super.orElse(value);
        return this;
    }

    /**
     * 
     * @param value
     * @return 
     */
    @Override
    public <R extends O> StringMapperBuilderV1<O> orElse(final Option<R> value) {
        super.orElse(value);
        return this;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public StringMapperBuilderV1<O> orElseNull() {
        super.orElseNull();
        return this;
    }

    /**
     * 
     * @return 
     */
    @Override
    public StringMapperBuilderV1<O> orElseThrow() {
        super.orElseThrow();
        return this;
    }

    /**
     *
     * @param iterator Object
     * @param makeLowerCase boolean
     * @return SortedSet
     */
    protected static SortedSet<String> toSortedSet(
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
    protected static <I extends String, O> java.util.SortedMap<String, O> toSortedMap(
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
    protected static <I extends String, O> io.vavr.collection.SortedMap<String, O> toSortedMap(
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
    public static <X extends String, Y, M extends java.util.Map<String, Y>> Function<X, Option<Y>> mapValue(
            final M map,
            final boolean makeLowerCase) {
        return (final X input) -> get(map, input, makeLowerCase);
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
    public static <X extends String, Y, M extends io.vavr.collection.Map<String, Y>> Function<X, Option<Y>> mapValue(
            final M map,
            final boolean makeLowerCase) {
        return (final X input) -> get(map, input, makeLowerCase);
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
    protected static <E, M extends java.util.Map<String, E>> Option<E> get(
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
    protected static <E, M extends io.vavr.collection.Map<String, E>> Option<E> get(
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
     */
    private CaseSensitivity caseSensitivity = CaseSensitivity.SENSITIVE;
    
}
