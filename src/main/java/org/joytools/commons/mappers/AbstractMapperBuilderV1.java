/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.mappers;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.Function1;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.joytools.commons.function.Functions;

/**
 *
 * @author AndreaR
 * @param <I>
 * @param <O>
 */
public abstract class AbstractMapperBuilderV1<I, O> {
 
    /**
     * 
     * @param <R>
     * @param mapper 
     */
    protected <R extends O> void add(final Function<I, Option<R>> mapper) {
        Objects.requireNonNull(mapper, "Mapper");
        checkAdd();
        internalImmutableFunctions = null;
        internalFunctions.add((Function<I, Option<O>>)(Function)mapper);
    }    

    /**
     * 
     * @param <R>
     * @param nullToAbsent
     * @param function 
     */
    protected <R extends O> void add(final Function<I, R> function, 
            final boolean nullToAbsent) {
        Objects.requireNonNull(function, "Function");
        final Function<I, Option<R>> mapper = (final I i) -> {
            final R r = function.apply(i);
            return r == null && nullToAbsent ? None() : Some(r);
        };
        add(mapper);
    }
    
    /**
     * 
     * @param <R>
     * @param p
     * @param value 
     */
    protected <R extends O> void add(final Predicate<? super I> p, final R value) {
        Objects.requireNonNull(p, "Predicate");
        add((final I i) -> p.test(i) ? Some(value) : None());        
    }    

    /**
     * 
     * @param <R>
     * @param p
     * @param supplier 
     */
    protected <R extends O> void add(final Predicate<? super I> p, final Supplier<R> supplier) {
        Objects.requireNonNull(p, "Predicate");
        Objects.requireNonNull(supplier, "Supplier");
        add((final I i) -> p.test(i) ? Some(supplier.get()) : None());        
    }    

    /**
     * 
     * @param <R>
     * @param p
     * @param value 
     */
    protected <R extends O> void add(final Predicate<? super I> p, final Option<R> value) {
        Objects.requireNonNull(p, "Predicate");
        Objects.requireNonNull(p, "Option");
        add((final I i) -> p.test(i) ? value : None());        
    }    
    
    /**
     * 
     * @param <R>
     * @param map 
     */
    protected <R extends O> void add(final java.util.Map<I, R> map) {
        Objects.requireNonNull(map, "Map");
        add(Mappers.forMap(map));
    }    

    /**
     * 
     * @param <R>
     * @param map 
     */
    protected <R extends O> void add(final io.vavr.collection.Map<I, R> map) {
        Objects.requireNonNull(map, "Map");
        add(map::get);
    }    

    /**
     * 
     * @param <R>
     * @param nullToAbsent
     * @param map 
     */
    protected <R extends O> void add(final java.util.Map<I, R> map, final boolean nullToAbsent) {
        Objects.requireNonNull(map, "Map");
        add((final I i) -> map.get(i), nullToAbsent);
    }    

    /**
     * 
     * @return 
     */
    protected Seq<Function<I, Option<O>>> immutableFunctions() {
        if (internalImmutableFunctions == null) {
            internalImmutableFunctions = Vector.ofAll(internalFunctions);
        }
        return internalImmutableFunctions;
    }
    
    /**
     * 
     */
    protected void checkAdd() {
        if (!defaultAbsent) {
            throw new IllegalStateException("No additional calls permitted after default value added");
        }
    }

    /**
     * 
     * @return 
     */
    protected Function1<I, Option<O>> buildMapper() {
        final Seq<Function<I, Option<O>>> immutableFunctions = immutableFunctions();
        return Functions.firstDefined(None(), immutableFunctions);
    }
    
    /**
     * 
     * @return 
     */
    protected Function1<I, O> buildFunction() {
        final Function1<I, Option<O>> function = buildMapper();
        final O o = null;
        return (final I i) -> function.apply(i).getOrElse(o);
    }

    /**
     * 
     * @return 
     */
    protected Function1<I, Seq<O>> buildList() {
        final Seq<Function<I, Option<O>>> immutableFunctions = immutableFunctions();
        return Functions.allDefined(immutableFunctions);
    }

    /**
     * 
     * @return 
     */
    protected Predicate<I> buildPredicate() {
        // if (!defaultAbsent) {
        //    throw new IllegalStateException("Predicate not permitted with default value present");
        // }
        final Function<I, Option<O>> function = buildMapper();
        return (final I i) -> {
            final Option o = function.apply(i);
            if (o.isDefined()) {
                final Object v = o.get();
                if (v != null && v instanceof Boolean flag) {
                    return flag;
                }
                return true;
            }
            return false;
        };
    }

    /**
     * 
     * @param <R>
     * @param defValue 
     */
    protected <R extends O> void addDefault(final R defValue) {
        addDefault(Some(defValue));
    }

    /**
     * 
     * @param <R>
     * @param defOption 
     */
    protected <R extends O> void addDefault(final Option<R> defOption) {
        Objects.requireNonNull(defOption, "Default Option");
        add((final I i) -> defOption);
        defaultAbsent = false;
    }
    
    /**
     * 
     * @param <R>
     * @param defSupp 
     */
    protected <R extends O> void addDefault(final Supplier<R> defSupp) {
        Objects.requireNonNull(defSupp, "Default Supplier");
        add((final I i) -> Some(defSupp.get()));
        defaultAbsent = false;
    }

    public boolean hasDefault() {
        return !defaultAbsent;
    }

    private boolean defaultAbsent = true;
    
    private final java.util.List<Function<I, Option<O>>> internalFunctions = new ArrayList();

    private Seq<Function<I, Option<O>>> internalImmutableFunctions = null;
    
}
