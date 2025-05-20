/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util.lookup;

import io.vavr.control.Option;
import java.util.function.Function;

/**
 *
 * @author AndreaR
 * @param <N>
 * @param <O>
 */
public interface LookupFunction<N, O> extends Function<N, O> {
    
    /**
     * 
     * @param name
     * @return 
     */
    public O applyOrNull(final N name);

    /**
     * 
     * @param name
     * @param def
     * @return 
     */
    public O applyOrElse(final N name, final O def);
    
    /**
     * 
     * @return 
     */
    public Function<N, Option<O>> asMapper();

}
