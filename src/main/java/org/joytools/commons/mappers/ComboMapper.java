/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.mappers;

import io.vavr.Function1;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 * @param <I>
 * @param <O>
 */
public class ComboMapper<I, O> extends AbstractComboMapper<I, O> {
    
    @Override
    public Function1<I, O> function() {
        return super.function();
    }
    
    @Override
    public Function1<I, Option<O>> mapper() {
        return super.mapper();
    }

    @Override
    public Function1<I, Seq<O>> list() {
        return super.list();
    }

    @Override
    public Predicate<I> predicate() {
        return super.predicate();
    }

}
