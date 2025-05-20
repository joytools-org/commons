/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import io.vavr.Function1;

/**
 *
 * @author AndreaR
 * @param <T>
 */
public interface StringFunction<T> extends Function1<T, String> {
    
    /**
     * 
     * @param <E>
     * @param type
     * @return 
     */
    default <E> StringFunction<E> castAs(final Class<E> type) {
        return (StringFunction<E>)this;
    }
    
    /**
     * 
     * @return 
     */
    default StringFunction<Object> castAsObject() {
        return (StringFunction<Object>)this;
    }

    /**
     * 
     * @return 
     */
    default StringFunction<CharSequence> castAsCharSequence() {
        return (StringFunction<CharSequence>)this;
    }

    /**
     * 
     * @return 
     */ 
    default StringFunction<String> castAsString() {
        return (StringFunction<String>)this;
    } 

    /**
     * 
     * @param input
     * @return 
     */
    public String eval(final String input);
    
    /**
     * 
     * @param input
     * @return 
     */
    @Override
    default String apply(final T input) {
        final String s = input == null ? null : input.toString();
        return eval(s);
    }
    
}
