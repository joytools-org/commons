/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util.lookup;

import java.util.function.Function;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public class StringLookupBuilder<E> extends LookupBuilder<String, E> {
    
    /**
     * 
     * @param cs 
     * @param norm 
     */
    public StringLookupBuilder(final CaseSensitivity cs,
            final Function<String, String> norm) {
        super(io.vavr.collection.TreeMap.empty(cs.comparator()), norm);
    }
    
    /**
     * 
     * @param ignoreCase 
     * @param norm 
     */
    public StringLookupBuilder(final boolean ignoreCase,
            final Function<String, String> norm) {
        this(CaseSensitivity.forIgnoreCase(ignoreCase), norm);
    }
    
    /**
     * 
     * @param cs 
     */
    public StringLookupBuilder(final CaseSensitivity cs) {
        this(cs, Function.identity());
    }
    
    /**
     * 
     * @param ignoreCase 
     */
    public StringLookupBuilder(final boolean ignoreCase) {
        this(CaseSensitivity.forIgnoreCase(ignoreCase), Function.identity());
    }

    /**
     * 
     * @param item
     * @param name
     * @return 
     */
    @Override
    public StringLookupBuilder<E> add(final E item, final String name) {
        super.add(item, name);
        return this;
    }

    /**
     * 
     * @param item
     * @param names
     * @return 
     */
    @Override
    public StringLookupBuilder<E> addAll(final E item, final String... names) {
        super.addAll(item, names);
        return this;
    }

    /**
     * 
     * @param item
     * @param names
     * @return 
     */
    @Override
    public StringLookupBuilder<E> addAll(final E item, final Iterable<String> names) {
        super.addAll(item, names);
        return this;
    }

}
