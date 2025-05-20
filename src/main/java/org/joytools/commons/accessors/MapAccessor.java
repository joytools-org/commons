/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import org.joytools.commons.accessors.impl.AccessorImplUtils;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;
import static org.joytools.commons.accessors.MutatorImplUtils.newInvalidValueException;
import org.joytools.commons.time.TimeValue;
import org.joytools.commons.util.ComboVariant;
import org.joytools.commons.util.Variant;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public interface MapAccessor<K, V> extends MultiValueAccessor<V> {
 
    /**
     * 
     * @param <Z>
     * @param clazz
     * @return 
     */
    default <Z> MapAccessor<K, Z> castAs(final Class<Z> clazz) {
        return (MapAccessor<K, Z>)this;
    }
    
    /**
     * 
     * @param key
     * @return 
     */ /*
    default boolean contains(final K key) {
        return get(key).isDefined();
    } */
    public boolean contains(final K key);
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<K> keys();
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<Tuple2<K, V>> entriesByKey();

    /**
     * 
     * @param key
     * @return 
     */
    public Option<V> get(final K key);
    
    /**
     * 
     * @param key
     * @param validator
     * @return 
     */
    default Option<V> get(final K key, 
            final Predicate<? super V> validator) {
        // return MutatorUtils.defaultGet(this, key, validator);
        final Option<V> val = get(key);
        if (val.isEmpty()) {
            return val;
        }
        if (!AccessorImplUtils.test(key, val.get(), validator)) {
            throw newInvalidValueException(key, val);
        }
        return val;
    }
    
    /**
     * 
     * @param <Z>
     * @param key
     * @param function
     * @return 
     */
    default <Z> Option<Z> get(final K key, 
            final Function<? super V, ? extends Z> function) {
        // return MutatorUtils.defaultGet(this, key, function);
        final Option<V> val = get(key);
        if (val.isEmpty()) {
            return None();
        }
        return Some(AccessorImplUtils.apply(key, val.get(), function));
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param function
     * @param postValidator
     * @return 
     */
    default <Z> Option<Z> get(final K key, 
            final Function<? super V, ? extends Z> function,
            final Predicate<? super Z> postValidator) {
        // return MutatorUtils.defaultGet(this, key, function);
        final Option<Z> val = get(key, function);
        if (val.isEmpty()) {
            return None();
        }
        if (!AccessorImplUtils.test(key, val.get(), postValidator)) {
            throw newInvalidValueException(key, val);
        }
        return val;
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param preValidator
     * @param function
     * @return 
     */
    default <Z> Option<Z> get(final K key, 
            final Predicate<? super V> preValidator, 
            final Function<? super V, ? extends Z> function) {
        // return MutatorUtils.defaultGet(this, key, validator, function);
        final Option<V> val = get(key, preValidator);
        if (val.isEmpty()) {
            return None();
        }
        return Some(AccessorImplUtils.apply(key, val.get(), function));
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param preValidator
     * @param function
     * @param postValidator
     * @return 
     */
    default <Z> Option<Z> get(final K key, 
            final Predicate<? super V> preValidator, 
            final Function<? super V, ? extends Z> function,
            final Predicate<? super Z> postValidator) {
        // return MutatorUtils.defaultGet(this, key, validator, function);
        final Option<Z> val = get(key, preValidator, function);
        if (val.isEmpty()) {
            return None();
        }
        if (!AccessorImplUtils.test(key, val.get(), postValidator)) {
            throw newInvalidValueException(key, val);
        }
        return val;
    }

    /**
     * 
     * @param key
     * @return 
     */
    default public V getOrNull(final K key) {
        return getOrElse(key, (V)null);
    }
    
    /**
     * 
     * @param <Z>
     * @param key
     * @param function
     * @return 
     */
    default <Z> Z getOrNull(final K key, 
            final Function<? super V, ? extends Z> function) {
        final V v = getOrNull(key);
        return AccessorImplUtils.apply(key, v, function);
    }

    /**
     * 
     * @param key
     * @param defVal
     * @return 
     */
    /* default public V getOrElse(final K key, final V defVal) {
        return get(key).getOrElse(defVal);
    } */
    public V getOrElse(final K key, final V defVal);
    
    /**
     * 
     * @param <Z>
     * @param key
     * @param defVal
     * @param function
     * @return 
     */
    default <Z> Z getOrElse(final K key, final V defVal,
            final Function<? super V, ? extends Z> function) {
        final V v = getOrElse(key, defVal);
        return AccessorImplUtils.apply(key, v, function);
    }
    
    /**
     * 
     * @param key
     * @param supplier
     * @return 
     */
    /* default public V getOrElse(final K key, final Supplier<V> supplier) {
        return get(key).getOrElse(supplier);
    } */ /*
    public V getOrElse(final K key, final Supplier<V> supplier); */
    
    /**
     * 
     * @param <Z>
     * @param key
     * @param supplier
     * @param function
     * @return 
     */ /*
    default <Z> Z getOrElse(final K key, final Supplier<V> supplier,
            final Function<? super V, ? extends Z> function) {
        final V v = getOrElse(key, supplier);
        return apply(key, v, function);
    } */

    /**
     * Should return 
     * @param key must exist
     * @return 
     * @throws
     */
    /* default V getOrFail(final K key) {
        // return MutatorUtils.defaultGetOrFail(this, key);
        final Option<V> val = get(key);
        if (val.isDefined()) {
            return val.get();
        }
        throw newItemNotFoundException("getOrFail()", key);

    } */
    public V getOrFail(final K key);
    
    /**
     * 
     * @param key
     * @param validator
     * @return 
     */
    default V getOrFail(final K key, 
            final Predicate<? super V> validator) {
        // return MutatorUtils.defaultGetOrFail(this, key, validator);
        final V val = getOrFail(key);
        if (!AccessorImplUtils.test(key, val, validator)) {
            throw newInvalidValueException(key, val);
        }
        return val;
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param function
     * @return 
     */
    default <Z> Z getOrFail(final K key, 
            final Function<? super V, ? extends Z> function) {
        // return MutatorUtils.defaultGetOrFail(this, key, function);
        final V v = getOrFail(key);
        return AccessorImplUtils.apply(key, v, function);
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param preValidator
     * @param function
     * @return 
     */
    default <Z> Z getOrFail(final K key, 
            final Predicate<? super V> preValidator, 
            final Function<? super V, ? extends Z> function) {
        // return MutatorUtils.defaultGetOrFail(this, key, preValidator, function);
        final V v = getOrFail(key, preValidator);
        return AccessorImplUtils.apply(key, v, function);
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param function
     * @param postValidator
     * @return 
     */
    default <Z> Z getOrFail(final K key, 
            final Function<? super V, ? extends Z> function, 
            final Predicate<? super Z> postValidator) {
        // return MutatorUtils.defaultGetOrFail(this, key, function, postValidator);
        final Z z = getOrFail(key, function);
        if (!AccessorImplUtils.test(key, z, postValidator)) {
            throw newInvalidValueException(key, z);
        }
        return z;
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param preValidator
     * @param function
     * @param postValidator
     * @return 
     */
    default <Z> Z getOrFail(final K key, 
            final Predicate<? super V> preValidator, 
            final Function<? super V, ? extends Z> function, 
            final Predicate<? super Z> postValidator) {
        // return MutatorUtils.defaultGetOrFail(this, key, preValidator, function, postValidator);
        final Z z = getOrFail(key, preValidator, function);
        if (!AccessorImplUtils.test(key, z, postValidator)) {
            throw newInvalidValueException(key, z);
        }
        return z;
        
    }

    /**
     *
     * @author AndreaR
     * @param <K>
     * @param <V>
     */
    public interface Reader<K, V> {

        /**
         * 
         * @param key
         * @return 
         */
        public AccessorSelector<K, Object> asAccessor(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public V nonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public V nonEmpty(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public Object asObject(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public Object asObjectNonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public Object asObjectNonEmpty(final K key);

        /**
         * 
         * @param <Z>
         * @param key
         * @param clazz
         * @return 
         */
        public <Z> Z asType(final K key, final Class<Z> clazz);

        /**
         * 
         * @param <Z>
         * @param key
         * @param clazz
         * @return 
         */
        public <Z> Z asTypeNonNull(final K key, final Class<Z> clazz);

        /**
         * 
         * @param <Z>
         * @param key
         * @param clazz
         * @return 
         */
        public <Z> Z asTypeNonEmpty(final K key, final Class<Z> clazz);

        /**
         * 
         * @param key
         * @return 
         */
        public TimeValue<?> asTimeValue(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public TimeValue<?> asTimeValueNonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public Variant<V> asVariant(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public ComboVariant<V> asComboVariant(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public Variant<V> asVariantNonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public Variant<V> asVariantNonEmpty(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public ComboVariant<V> asComboVariantNonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public ComboVariant<V> asComboVariantNonEmpty(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public String asString(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public String asStringNonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public String asStringNonEmpty(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public Boolean asBoolean(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public Boolean asBooleanNonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public byte[] asBytes(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public byte[] asBytesNonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public Integer asInteger(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public Integer asIntegerNonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public IterableAccessor<V> asSequence(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public IterableAccessor<String> asStringSequence(final K key);

        /**
         * 
         * @param <Z>
         * @param key
         * @param type
         * @return 
         */
        public <Z> IterableAccessor<Z> asSequence(final K key, final Class<Z> type);

        /**
         * 
         * @param key
         * @return 
         */
        public IterableAccessor<Object> asSequenceNonNull(final K key);

        /**
         * 
         * @param <Z>
         * @param key
         * @param type
         * @return 
         */
        public <Z> IterableAccessor<Z> asSequenceNonNull(final K key, final Class<Z> type);

        /**
         * 
         * @param key
         * @return 
         */
        public IterableAccessor<Object> asSequenceNonEmpty(final K key);

        /**
         * 
         * @param <Z>
         * @param key
         * @param type
         * @return 
         */
        public <Z> IterableAccessor<Z> asSequenceNonEmpty(final K key, final Class<Z> type);

        /**
         * 
         * @param key
         * @return 
         */
        public int asInt(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public java.sql.Time asSQLTime(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public java.sql.Time asSQLTimeNonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public java.sql.Date asSQLDate(final K key);
        /**
         * 
         * @param key
         * @return 
         */
        public java.sql.Date asSQLDateNonNull(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public java.sql.Timestamp asSQLTimestamp(final K key);

        /**
         * 
         * @param key
         * @return 
         */
        public java.sql.Timestamp asSQLTimestampNonNull(final K key);

    }

    /**
     * 
     * @return 
     */
    public Reader<K, V> read();
    
}
