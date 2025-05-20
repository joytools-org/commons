/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.mappers;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 * @param <I>
 * @param <O>
 */
public interface ComboMapperBuilder<I, O> {
    
    public Function<I, Option<O>> buildMapper();
    
    public Function<I, O> buildFunction();

    public Function<I, Seq<O>> buildList();

    public Predicate<I> buildPredicate();
    
}
