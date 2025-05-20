/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util.lookup;

import io.vavr.control.Option;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.concurrent.AtomicInitializers;
import org.joytools.commons.concurrent.ConcurrentInitializers;
import org.joytools.commons.lang.CaseSpaceNormalizer;
import org.joytools.commons.lang.StringPredicates;

/**
 *
 * @author AndreaR
 */
public class LookupFunctions {
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param supplier
     * @return 
     */
    public static <N, E> LookupFunction<N, E> lazy(final Supplier<LookupFunction<N, E>> supplier) {
        Objects.requireNonNull(supplier, "Supplier");
        final Supplier<LookupFunction<N, E>> initializer = ConcurrentInitializers.asSupplier(
                AtomicInitializers.of(supplier));
        return new LookupFunction<N, E>() {
            @Override
            public E applyOrNull(final N name) {
                return initializer.get().applyOrNull(name);
            }

            @Override
            public E applyOrElse(N name, E def) {
                return initializer.get().applyOrElse(name, def);
            }

            @Override
            public Function<N, Option<E>> asMapper() {
                return initializer.get().asMapper();
            }

            @Override
            public E apply(final N name) {
                return initializer.get().apply(name);
            }
        };
    }

    /**
     * 
     * @param <E>
     * @param data
     * @return 
     */
    public static <E extends Enum> LookupFunction<String, E> forEnum(final E... data) {
        final EnumLookupBuilder<E> b = new EnumLookupBuilder<>(true);
        for (final E e : data) {
            b.register(e);
        }
        return b.build();
    }
    
    /**
     * 
     * @param <E>
     * @param data
     * @param aliases
     * @return 
     */
    public static <E extends Enum> LookupFunction<String, E> forEnum(final E[] data, final Function<? super E, ? extends Iterable<String>> aliases) {
        final EnumLookupBuilder<E> b = new EnumLookupBuilder<>(true, CaseSpaceNormalizer.TRIM.castAsString());
        for (final E e : data) {
            b.register(e);
            if (aliases != null) {
                final Iterable<String> a = IterableAccessors.from(aliases.apply(e))
                        .filter(StringPredicates.isNotBlank());
                b.addAll(e, a);
            }
        }
        return b.build();
    }

}
