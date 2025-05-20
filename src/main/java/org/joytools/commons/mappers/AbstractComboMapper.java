/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.mappers;

import io.vavr.Function1;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;
import static org.joytools.vavr.APIX.Function1;

/**
 *
 * @author AndreaR
 * @param <I>
 * @param <O>
 */
public abstract class AbstractComboMapper<I, O> {
    
    /**
     * 
     * @param builder 
     */
    protected final void buildAll(final ComboMapperBuilder<I, O> builder) {
        function(builder.buildFunction());
        mapper(builder.buildMapper());
        predicate(builder.buildPredicate());
        list(builder.buildList());
    }

    /**
     * 
     * @param function
     * @return 
     */
    protected final Function1<I, O> function(final Function<I, O> function) {
        this.function = Function1(function);
        return this.function;
    }
    
    /**
     * 
     * @return 
     */
    protected Function1<I, O> function() {
        if (function == null) {
            throw new UnsupportedOperationException("Function is not supported");
        }
        return function;
    }
    
    /**
     * 
     * @param mapper
     * @return 
     */
    protected final Function1<I, Option<O>> mapper(final Function<I, Option<O>> mapper) {
        this.mapper = Function1(mapper);
        return this.mapper;
    }

    /**
     * 
     * @return 
     */
    protected Function1<I, Option<O>> mapper() {
        if (mapper == null) {
            throw new UnsupportedOperationException("Mapper is not supported");
        }
        return mapper;
    }

    /**
     * 
     * @param list
     * @return 
     */
    protected final Function1<I, Seq<O>> list(final Function<I, Seq<O>> list) {
        this.list = Function1(list);
        return this.list;
    }

    /**
     * 
     * @return 
     */
    protected Function1<I, Seq<O>> list() {
        if (list == null) {
            throw new UnsupportedOperationException("List is not supported");
        }
        return list;
    }

    /**
     * 
     * @param predicate
     * @return 
     */
    protected final Predicate<I> predicate(final Predicate<I> predicate) {
        this.predicate = predicate;
        return this.predicate;
    }

    /**
     * 
     * @return 
     */
    protected Predicate<I> predicate() {
        if (predicate == null) {
            throw new UnsupportedOperationException("Predicate is not supported");
        }
        return predicate;
    }

    private Function1<I, O> function = null;
    
    private Function1<I, Option<O>> mapper = null;

    private Function1<I, Seq<O>> list = null;

    private Predicate<I> predicate = null;
    
}
