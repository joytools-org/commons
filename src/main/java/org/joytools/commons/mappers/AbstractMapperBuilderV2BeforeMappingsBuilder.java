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
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.accessors.MapMutator;
import org.joytools.commons.function.Functions;
import static org.joytools.vavr.APIX.SingleValue;

/**
 *
 * @author AndreaR
 * @param <I>
 * @param <O>
 */
public abstract class AbstractMapperBuilderV2BeforeMappingsBuilder<I, O> implements ComboMapperBuilder<I, O> {
 
    /**
     * 
     * @param mapper
     * @return 
     */
    protected AbstractMapperBuilderV2BeforeMappingsBuilder<I, O> mapInput(final Function<? super I, ? extends I> mapper) {
        Objects.requireNonNull(mapper, "Input Mapper");
        checkModifiable();
        if (mapInput != null) {
            throw new IllegalStateException("Input mapper already defined");
        }
        mapInput = (Function<I, I>)mapper;
        return this;
    }
    
    /**
     * 
     * @param mapper
     * @return 
     */
    protected AbstractMapperBuilderV2BeforeMappingsBuilder<I, O> mapOutput(final Function<? super O, ? extends O> mapper) {
        Objects.requireNonNull(mapper, "Output Mapper");
        checkModifiable();
        if (mapOutput != null) {
            throw new IllegalStateException("Output mapper already defined");
        }
        mapOutput = (Function<O, O>)mapper;
        return this;
    }

    /**
     * 
     * @param <R>
     * @param <V>
     * @param mapper 
     */ /*
    final protected <R extends O> void add(final Function<? super I, Option<R>> mapper) {
        Objects.requireNonNull(mapper, "Mapper");
        checkModifiable();
        checkAdd();
        mappersList.add((Function<I, Option<O>>)((Function)mapper));
    }     */

    final protected <R extends O, V extends Value<R>> void add(final Function<? super I, ? extends V> mapper) {
        Objects.requireNonNull(mapper, "Mapper");
        checkModifiable();
        checkAdd();
        mappersList.add((Function<I, Value<O>>)((Function)mapper));
        // mappersList1.add((Function<I, Option<O>>)((Function)mapper));
    }    

    /**
     * 
     * @param <R>
     * @param p
     * @param value 
     */
    protected <R extends O> void add(final Predicate<? super I> p, final R value) {
        add(Mappers.of(p, value));
        /* Objects.requireNonNull(p, "Predicate");
        add((final I i) -> p.test(i) ? Some(value) : None()); */
    }    

    /**
     * 
     * @param <R>
     * @param p
     * @param supplier 
     */
    protected <R extends O> void add(final Predicate<? super I> p, final Supplier<R> supplier) {
        add(Mappers.of(p, supplier));
        /* Objects.requireNonNull(p, "Predicate");
        Objects.requireNonNull(supplier, "Supplier");
        add((final I i) -> p.test(i) ? Some(supplier.get()) : None());        */
    }    

    /**
     * 
     * @param <R>
     * @param p
     * @param mapper 
     */
    protected <R extends O> void add(final Predicate<? super I> p, final Function<? super I, Option<R>> mapper) {
        add(Mappers.of(p, mapper));
        /* Objects.requireNonNull(p, "Predicate");
        Objects.requireNonNull(supplier, "Supplier");
        add((final I i) -> p.test(i) ? Some(supplier.get()) : None());        */
    }    

    /**
     * 
     * @param <R>
     * @param p
     * @param value 
     */
    protected <R extends O> void add(final Predicate<? super I> p, final Option<R> value) {
        add(Mappers.of(p, value));
        /* Objects.requireNonNull(p, "Predicate");
        Objects.requireNonNull(p, "Option");
        add((final I i) -> p.test(i) ? value : None()); */
    }    
    
    /**
     * 
     * @param <R>
     * @param acc 
     */
    protected <R extends O> void add(final MapAccessor<? super I, R> acc) {
        add(Mappers.forAccessor(acc));
    }    

    /**
     * 
     * @param <R>
     * @param map 
     */
    protected <R extends O> void add(final java.util.Map<? super I, R> map) {
        add(Mappers.forMap(map));
    }    

    /**
     * 
     * @param <R>
     * @param map 
     */
    protected <R extends O> void add(final io.vavr.collection.Map<? super I, R> map) {
        add(Mappers.forMap(map));
    }    

    /**
     * 
     * @return 
     */
    protected Seq<Function<I, Value<O>>> mappers() {
        if (mappersSeq == null) {
            mappersSeq = Vector.ofAll(mappersList);
        }
        return mappersSeq;
    }

    /**
     * 
     * @return 
     */
    @Deprecated
    protected Seq<Function<I, Option<O>>> mappersV1() {
        if (mappersSeqV1 == null) {
            mappersSeqV1 = Vector.ofAll(mappersList1);
            if (mappersSeqV1 != null) {
                throw new UnsupportedOperationException("mappers1()");
            }
        }
        return mappersSeqV1;
    }
    
    /**
     * 
     */
    protected void checkModifiable() {
        if (mappersSeqV1 != null) {
            throw new IllegalStateException("No longer modifiable: mapper has been already built");
        }
    }

    /**
     * 
     */
    protected void checkAdd() {
        if (hasDefault) {
            throw new IllegalStateException("No longer modifiable: default rule is present");
        }
    }

    /**
     * 
     * @return 
     */
    public final Function1<I, Option<O>> build() {
        return buildMapper();
    }

    /**
     * 
     * @param <U>
     * @param finisher
     * @return 
     */
    public final <U> Function1<I, Option<U>> build(final Function<? super O, U> finisher) {
        Objects.requireNonNull(finisher, "Finisher");
        final Function<I, Option<O>> mapper = buildMapper();
        return k -> mapper.apply(k).map(finisher);
    }

    /**
     * 
     * @return 
     */
    @Override
    public Function1<I, Option<O>> buildMapper() {
        final Seq<Function<I, Value<O>>> mappers = mappers();
        if (mapOutput != null) {
            throw new UnsupportedOperationException("mapOutput()");
        }
        return i -> {
            final I input = mapInput == null ? i : mapInput.apply(i);
            for (final Function<I, Value<O>> m : mappers) {
                final Value<O> v = SingleValue(m.apply(input));
                if (!v.isEmpty()) {
                    return v.toOption();
                }
            }
            return None();
        };
    }
    
    /**
     * 
     * @return 
     */
    @Deprecated
    public Function1<I, Option<O>> buildMapperV1() {
        if (mapOutput != null) {
            throw new UnsupportedOperationException("mapOutput()");
        }
        final Seq<Function<I, Option<O>>> mappers = mappersV1();
        final Function1<I, Option<O>> mapper = Functions.firstDefined(None(), mappers);
        if (mapInput == null) {
            return mapper;
        }
        return Functions.mapInput(mapInput, mapper);
    }

    /**
     * 
     * @return 
     */
    @Override
    public Function1<I, O> buildFunction() {
        final Function1<I, Option<O>> mapper = buildMapper();
        final O o = null;
        if (mapInput == null) {
            return (final I i) -> mapper.apply(i).getOrElse(o);
        }
        return (final I i) -> mapper.apply(mapInput.apply(i)).getOrElse(o);
    }

    @Deprecated
    public Function1<I, O> buildFunctionV1() {
        if (mapInput != null) {
            throw new UnsupportedOperationException("mapInput()");
        }
        final Function1<I, Option<O>> function = buildMapper();
        final O o = null;
        return (final I i) -> function.apply(i).getOrElse(o);
    }

    /**
     * 
     * @return 
     */
    @Override
    public Function1<I, Seq<O>> buildList() {
        buildMapper();
        final Seq<Function<I, Value<O>>> mappers = mappers();
        final Function1<I, Seq<O>> lister = key -> {
            final java.util.List<O> defined = new ArrayList();
            for (final Function<I, Value<O>> mapper : mappers) {
                final Value<O> v = SingleValue(mapper.apply(key));
                if (!v.isEmpty()) {
                    defined.add(v.get());
                }
            }
            return Vector.ofAll(defined);
        };
        if (mapInput == null) {
            return lister;
        }
        return Functions.mapInput(mapInput, lister);
    }

    @Deprecated
    public Function1<I, Seq<O>> buildListV1() {
        buildMapper();
        final Seq<Function<I, Option<O>>> mappers = mappersV1();
        final Function1<I, Seq<O>> lister = Functions.allDefined(mappers);
        if (mapInput == null) {
            return lister;
        }
        return Functions.mapInput(mapInput, lister);
    }

    /**
     * 
     * @return 
     */
    @Override
    public Predicate<I> buildPredicate() {
        final Function<I, Option<O>> mapper = buildMapper();
        return (final I i) -> {
            final Option o = mapper.apply(i);
            if (o.isDefined()) {
                final Object v = o.get();
                if (v != null && v instanceof Boolean) {
                    return Boolean.class.cast(v);
                }
                return true;
            }
            return false;
        };
    }

    @Deprecated
    public Predicate<I> buildPredicateV1() {
        if (mapInput != null) {
            throw new UnsupportedOperationException("mapInput()");
        }
        // if (!defaultAbsent) {
        //    throw new IllegalStateException("Predicate not permitted with default value present");
        // }
        final Function<I, Option<O>> function = buildMapper();
        return (final I i) -> {
            final Option o = function.apply(i);
            if (o.isDefined()) {
                final Object v = o.get();
                if (v != null && v instanceof Boolean) {
                    return Boolean.class.cast(v);
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
        hasDefault = true;
    }
    
    /**
     * 
     * @param <R>
     * @param defSupp 
     */
    protected <R extends O> void addDefault(final Supplier<R> defSupp) {
        Objects.requireNonNull(defSupp, "Default Supplier");
        add((final I i) -> Some(defSupp.get()));
        hasDefault = true;
    }

    /**
     * 
     * @return 
     */
    public boolean hasDefault() {
        return hasDefault;
    }

    private Function<I, I> mapInput;
    
    private Function<O, O> mapOutput;

    private boolean hasDefault = false;
    
    private final java.util.List<Function<I, Option<O>>> mappersList1 = new ArrayList();

    private final java.util.List<Function<I, Value<O>>> mappersList = new ArrayList();

    private Seq<Function<I, Option<O>>> mappersSeqV1 = null;
    
    private Seq<Function<I, Value<O>>> mappersSeq = null;

}
