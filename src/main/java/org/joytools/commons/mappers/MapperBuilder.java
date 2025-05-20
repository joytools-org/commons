/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.mappers;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.Function1;
import io.vavr.Value;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.accessors.MapMutator;

/**
 *
 * @author AndreaR
 * @param <I>
 * @param <O>
 */
public class MapperBuilder<I, O> extends AbstractMapperBuilder<I, O> {
    
    /**
     * 
     */
    protected MapperBuilder() {
    }
    
    /**
     * 
     * @param mapper
     * @return 
     */
    @Override
    public MapperBuilder<I, O> mapInput(final Function<? super I, ? extends I> mapper) {
        super.mapInput(mapper);
        return this;
    }

    /**
     * 
     * @param mapper
     * @return 
     */
    @Override
    public MapperBuilder<I, O> mapOutput(final Function<? super O, ? extends O> mapper) {
        super.mapOutput(mapper);
        return this;
    }

    /**
     * 
     * @param <T>
     * @param t
     * @return 
     */
    @Deprecated
    public static <T> MapperBuilder<T, T> old_of(
            final Class<T> t) {
        return new MapperBuilder();
    }

    /**
     * 
     * @param <X>
     * @param <Y>
     * @param x
     * @param y
     * @return 
     */
    @Deprecated
    public static <X, Y> MapperBuilder<X, Y> old_of(
            final Class<X> x, 
            final Class<Y> y) {
        return new MapperBuilder();
    }
    
    /**
     * 
     * @param <R>
     * @param <V>
     * @param mapper
     * @return 
     */
    public <R extends O, V extends Value<R>> MapperBuilder<I, O> addMapper(final Function<? super I, ? extends V> mapper) {
        add(mapper);
        return this;
    }

    /**
     * 
     * @param <R>
     * @param function
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> addFunction(final Function<? super I, R> function) {
        add(Mappers.forFunction(function));
        return this;
    }

    /**
     * 
     * @param <R>
     * @param function
     * @param noneTest
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> addFunction(final Function<? super I, R> function,
            final Predicate<? super R> noneTest) {
        add(Mappers.forFunction(function, noneTest));
        return this;
    }

    /**
     * 
     * @param <R>
     * @param p
     * @param value
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> when(final Predicate<? super I> p, final R value) {
        // Objects.requireNonNull(p, "Predicate");
        add(p, value); // (final I i) -> p.test(i) ? Some(value) : None());
        return this;
    }

    /**
     * 
     * @param <R>
     * @param p
     * @param value
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> when(final Predicate<? super I> p, final Option<R> value) {
        add(p, value);
        return this;
    }

    /**
     * 
     * @param <R>
     * @param p
     * @param supplier
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> when(final Predicate<? super I> p, final Supplier<R> supplier) {
        add(p, supplier);
        return this;
    }

    /**
     * 
     * @param <R>
     * @param p
     * @param mapper
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> when(final Predicate<? super I> p, final Function<? super I, Option<R>> mapper) {
        add(p, mapper);
        return this;
    }

    /**
     * 
     * @param <R>
     * @param m
     * @param value
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> whenEquals(final I m, final R value) {
        add(Mappers.equals(m, value));
        // add((final I i) -> Objects.equals(i, m) ? Some(value) : None());
        return this;
    }

    /**
     * 
     * @param <R>
     * @param m
     * @param value
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> whenEquals(final I m, final Supplier<R> value) {
        add(Mappers.equals(m, value));
        /* Objects.requireNonNull(value, "Supplier"); 
        add((final I i) -> Objects.equals(i, m) ? Some(value.get()) : None()); */
        return this;
    }

    /**
     * 
     * @param <R>
     * @param m
     * @param value
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> whenEquals(final I m, final Function<? super I, Option<R>> value) {
        add(Mappers.equals(m, value));
        /* Objects.requireNonNull(value, "Supplier"); 
        add((final I i) -> Objects.equals(i, m) ? Some(value.get()) : None()); */
        return this;
    }
    
    /**
     * 
     * @param <R>
     * @param m
     * @param value
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> whenEquals(final I m, final Option<R> value) {
        add(Mappers.equals(m, value));
        /* Objects.requireNonNull(value, "Option");
        add((final I i) -> Objects.equals(i, m) ? value : None()); */
        return this;
    }

    /**
     * 
     * @param <R>
     * @param value
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> whenNull(final R value) {
        add(Mappers.forNull(value));
        // add((final I i) -> i == null ? Some(value) : None());
        return this;
    }

    /**
     * 
     * @param <R>
     * @param value
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> whenNull(final Supplier<R> value) {
        add(Mappers.forNull(value));
         /*Objects.requireNonNull(value, "Supplier");
        add((final I i) -> i == null ? Some(value.get()) : None()); */
        return this;
    }

    /**
     * 
     * @param <R>
     * @param value
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> whenNull(final Option<R> value) {
        add(Mappers.forNull(value));
        /* Objects.requireNonNull(value, "Option");
        add((final I i) -> i == null ? value : None()); */
        return this;
    }

    /**
     * 
     * @param <R>
     * @param map
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> addMap(final java.util.Map<I, R> map) {
        add(map);
        return this;
    }
    
    /**
     * 
     * @param <R>
     * @param acc
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> addAccessor(final MapAccessor<? super I, R> acc) {
        add(acc);
        return this;
    }

    /**
     * 
     * @param <R>
     * @param map
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> addMap(final io.vavr.collection.Map<I, R> map) {
        add(map);
        return this;
    }

    /**
     * 
     * @param <R>
     * @param defOption
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> orElse(final R defOption) {
        addDefault(defOption);
        return this;
    }

    /**
     * 
     * @param <R>
     * @param def
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> orElse(final Supplier<R> def) {
        addDefault(def);
        return this;
    }

    /**
     * 
     * @param <R>
     * @param defValue
     * @return 
     */
    public <R extends O> MapperBuilder<I, O> orElse(final Option<R> defValue) {
        addDefault(defValue);
        return this;
    }
    
    /**
     * 
     * @return 
     */
    public MapperBuilder<I, O> orElseNull() {
        addDefault(Some(null));
        return this;
    }

    /**
     * 
     * @return 
     */
    public MapperBuilder<I, O> orElseThrow() {
        add((final I i) -> { 
            throw new IllegalArgumentException(Objects.toString(i)); 
        });
        return this;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Function1<I, Option<O>> buildMapper() {
        return super.buildMapper();
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Function1<I, O> buildFunction() {
        return super.buildFunction();
    }

    /**
     * 
     * @return 
     */
    @Override
    public Function1<I, Seq<O>> buildList() {
        return super.buildList();
    }

    /**
     * 
     * @return 
     */
    @Override
    public Predicate<I> buildPredicate() {
        return super.buildPredicate();
    }    
    
}
