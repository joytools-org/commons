/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util.lookup;

import java.util.Objects;
import java.util.function.Function;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public class EnumLookupBuilder<E extends Enum> extends StringLookupBuilder<E> {
    
    /**
     * 
     * @param cs 
     */
    public EnumLookupBuilder(final CaseSensitivity cs) {
        super(cs);
    }
    
    /**
     * 
     * @param ignoreCase 
     */
    public EnumLookupBuilder(final boolean ignoreCase) {
        super(ignoreCase);
    }
    
    /**
     * 
     * @param cs
     * @param normalizer 
     */
    public EnumLookupBuilder(final CaseSensitivity cs,
            final Function<String, String> normalizer) {
        super(cs, normalizer);
    }
    
    /**
     * 
     * @param ignoreCase 
     * @param normalizer 
     */
    public EnumLookupBuilder(final boolean ignoreCase,
            final Function<String, String> normalizer) {
        super(ignoreCase, normalizer);
    }

    /**
     * 
     * @param item
     * @return 
     */
    public EnumLookupBuilder<E> register(final E item) {
        Objects.requireNonNull(item, "Item");
        add0(item, item.name());
        return this;
    }

    /**
     * 
     * @param item
     * @param names
     * @return 
     */
    public EnumLookupBuilder<E> registerAll(final E item, final String... names) {
        return register(item).addAll(item, names);
    }

    /**
     * 
     * @param item
     * @param names
     * @return 
     */
    public EnumLookupBuilder<E> registerAll(final E item, final Iterable<String> names) {
        return register(item).addAll(item, names);
    }

    /**
     * 
     * @param item
     * @param names
     * @return 
     */
    @Override
    public EnumLookupBuilder<E> addAll(final E item, final String... names) {
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
    public EnumLookupBuilder<E> addAll(final E item, final Iterable<String> names) {
        super.addAll(item, names);
        return this;
    }

    /**
     * 
     * @param item
     * @param name
     * @return 
     */
    @Override
    public EnumLookupBuilder<E> add(final E item, final String name) {
        super.add(item, name);
        return this;
    }
    
}
