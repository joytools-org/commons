/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import org.joytools.commons.accessors.impl.AccessorImplUtils;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.Tuple2;
import io.vavr.collection.Iterator;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;
import static org.joytools.commons.accessors.MutatorImplUtils.newInvalidValueException;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.time.TimeValue;
import org.joytools.commons.util.ComboVariant;
import org.joytools.commons.util.Variant;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public interface ListAccessor<V> extends MultiValueAccessor<V> {
    
    /**
     * 
     * @param <Z>
     * @param type
     * @return 
     */
    default <Z> ListAccessor<Z> castAs(final Class<Z> type) {
        return (ListAccessor<Z>)this;
    }
    
    /**
     *
     * @author AndreaR
     * @param <V>
     */
    public interface Reader<V> {

        /**
         * 
         * @param index
         * @return 
         */
        public AccessorSelector<Object, Object> asAccessor(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public V nonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public V nonEmpty(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public Object asObject(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public Object asObjectNonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public Object asObjectNonEmpty(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public TimeValue<?> asTimeValue(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public TimeValue<?> asTimeValueNonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public Variant<V> asVariant(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public ComboVariant<V> asComboVariant(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public Variant<V> asVariantNonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public Variant<V> asVariantNonEmpty(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public ComboVariant<V> asComboVariantNonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public ComboVariant<V> asComboVariantNonEmpty(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public String asString(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public String asStringNonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public String asStringNonEmpty(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public Boolean asBoolean(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public Boolean asBooleanNonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public byte[] asBytes(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public byte[] asBytesNonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public Integer asInteger(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public Integer asIntegerNonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public IterableAccessor<Object> asSequence(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public IterableAccessor<String> asStringSequence(final int index);

        /**
         * 
         * @param <Z>
         * @param index
         * @param type
         * @return 
         */
        public <Z> IterableAccessor<Z> asSequence(final int index, final Class<Z> type);

        /**
         * 
         * @param index
         * @return 
         */
        public IterableAccessor<Object> asSequenceNonNull(final int index);

        /**
         * 
         * @param <Z>
         * @param index
         * @param type
         * @return 
         */
        public <Z> IterableAccessor<Z> asSequenceNonNull(final int index, final Class<Z> type);

        /**
         * 
         * @param index
         * @return 
         */
        public IterableAccessor<Object> asSequenceNonEmpty(final int index);

        /**
         * 
         * @param <Z>
         * @param index
         * @param type
         * @return 
         */
        public <Z> IterableAccessor<Z> asSequenceNonEmpty(final int index, final Class<Z> type);

        /**
         * 
         * @param index
         * @return 
         */
        public int asInt(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public java.sql.Time asSQLTime(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public java.sql.Time asSQLTimeNonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public java.sql.Date asSQLDate(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public java.sql.Date asSQLDateNonNull(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public java.sql.Timestamp asSQLTimestamp(final int index);

        /**
         * 
         * @param index
         * @return 
         */
        public java.sql.Timestamp asSQLTimestampNonNull(final int index);

    }
    
    /**
     * 
     * @return 
     */
    public Reader<V> read();
    
    /**
     * 
     * @param index
     * @return 
     */
    public Option<V> get(final int index);

    /**
     * 
     * @param index
     * @param validator
     * @return 
     */
    default Option<V> get(final int index, 
            final Predicate<? super V> validator) {
        // return MutatorUtils.defaultGet(this, index, validator);
        final Option<V> val = get(index);
        if (val.isEmpty()) {
            return val;
        }
        if (!AccessorImplUtils.test(index, val.get(), validator)) {
            throw newInvalidValueException(index, val);
        }
        return val;
    }
    
    /**
     * 
     * @param <Z>
     * @param index
     * @param preValidator
     * @param function
     * @return 
     */
    default <Z> Option<Z> get(final int index, 
            final Predicate<? super V> preValidator,
            final Function<? super V, ? extends Z> function) {
        // return MutatorUtils.defaultGet(this, index, preValidator, function);
        final Option<V> val = get(index, preValidator);
        if (val.isEmpty()) {
            return None();
        }
        return Some(AccessorImplUtils.apply(index, val.get(), function));
    }

    /**
     * 
     * @param <Z>
     * @param index
     * @param preValidator
     * @param function
     * @param postValidator
     * @return 
     */
    default <Z> Option<Z> get(final int index, 
            final Predicate<? super V> preValidator,
            final Function<? super V, ? extends Z> function,
            final Predicate<? super Z> postValidator) {
        // return MutatorUtils.defaultGet(this, index, preValidator, function);
        final Option<Z> val = get(index, preValidator, function);
        if (val.isEmpty()) {
            return None();
        }
        if (!AccessorImplUtils.test(index, val.get(), postValidator)) {
            throw newInvalidValueException(index, val);
        }
        return val;
    }

    /**
     * 
     * @param <Z>
     * @param index
     * @param function
     * @return 
     */
    default <Z> Option<Z> get(final int index, 
            final Function<? super V, ? extends Z> function) {
        // return MutatorUtils.defaultGet(this, index, function);
        final Option<V> val = get(index);
        if (val.isEmpty()) {
            return None();
        }
        return Some(AccessorImplUtils.apply(index, val.get(), function));
    }
    
    /**
     * 
     * @param <Z>
     * @param index
     * @param function
     * @param postValidator
     * @return 
     */
    default <Z> Option<Z> get(final int index, 
            final Function<? super V, ? extends Z> function,
            final Predicate<? super Z> postValidator) {
        final Option<Z> val = get(index, function);
        if (val.isEmpty()) {
            return None();
        }
        if (!AccessorImplUtils.test(index, val.get(), postValidator)) {
            throw newInvalidValueException(index, val);
        }
        return val;
    }

    /**
     * 
     * @param index
     * @return 
     */
    default V getOrNull(final int index) {
        return getOrElse(index, null);
    }
    
    /**
     * 
     * @param <Z>
     * @param index
     * @param function
     * @return 
     */
    default <Z> Z getOrNull(final int index, 
            final Function<? super V, ? extends Z> function) {
        final V v = getOrNull(index);
        return AccessorImplUtils.apply(index, v, function);
    }    
    

    /**
     * 
     * @param index
     * @param defValue
     * @return 
     */ /*
    default V getOrElse(final int index, final V defValue) {
        return get(index).getOrElse(defValue);
    } */
    public V getOrElse(final int index, final V defValue);

    /**
     * 
     * @param index
     * @param supplier
     * @return 
     */ /*
    default V getOrElse(final int index, final Supplier<V> supplier) {
        return get(index).getOrElse(supplier);
    } */
    
    /**
     * 
     * @param <Z>
     * @param index
     * @param supplier
     * @param function
     * @return 
     */ /*
    default <Z> Z getOrElse(final int index, final Supplier<V> supplier,
            final Function<? super V, ? extends Z> function) {
        final V v = getOrElse(index, supplier);
        return apply(index, v, function);
    }    */
    
    
    /**
     * 
     * @param <Z>
     * @param index
     * @param defValue
     * @param function
     * @return 
     */
    default <Z> Z getOrElse(final int index, final V defValue,
            final Function<? super V, ? extends Z> function) {
        final V v = getOrElse(index, defValue);
        return AccessorImplUtils.apply(index, v, function);
    }    

    /**
     * 
     * @param index
     * @return 
     */
    public V getOrFail(final int index);
    
    /**
     * 
     * @param index
     * @param validator
     * @return 
     */
    default V getOrFail(final int index, 
            final Predicate<? super V> validator) {
        // return MutatorUtils.defaultGetOrFail(this, index, validator);
        final V val = getOrFail(index);
        if (!AccessorImplUtils.test(index, val, validator)) {
            throw newInvalidValueException(index, val);
        }
        return val;
    }

    /**
     * 
     * @param <Z>
     * @param index
     * @param preValidator
     * @param function
     * @return 
     */
    default <Z> Z getOrFail(final int index, 
            final Predicate<? super V> preValidator,
            final Function<? super V, ? extends Z> function) {
        // return MutatorUtils.defaultGetOrFail(this, index, preValidator, function);
        final V v = getOrFail(index, preValidator);
        return AccessorImplUtils.apply(index, v, function);
    }

    /**
     * 
     * @param <Z>
     * @param index
     * @param function
     * @return 
     */
    default <Z> Z getOrFail(final int index, 
            final Function<? super V, ? extends Z> function) {
        // return MutatorUtils.defaultGetOrFail(this, index, function);
        final V v = getOrFail(index);
        return AccessorImplUtils.apply(index, v, function);
    }

    /**
     * 
     * @param <Z>
     * @param index
     * @param function
     * @param postValidator
     * @return 
     */
    default <Z> Z getOrFail(final int index, 
            final Function<? super V, ? extends Z> function,
            final Predicate<? super Z> postValidator) {
        // return MutatorUtils.defaultGetOrFail(this, index, function, postValidator);
        final Z z = getOrFail(index, function);
        if (!AccessorImplUtils.test(index, z, postValidator)) {
            throw newInvalidValueException(index, z);
        }
        return z;
    }

    /**
     * 
     * @param <Z>
     * @param index
     * @param preValidator
     * @param function
     * @param postValidator
     * @return 
     */
    default <Z> Z getOrFail(final int index, 
            final Predicate<? super V> preValidator,
            final Function<? super V, ? extends Z> function,
            final Predicate<? super Z> postValidator) {
        // return MutatorUtils.defaultGetOrFail(this, index, preValidator, function, postValidator);
        final Z z = getOrFail(index, preValidator, function);
        if (!AccessorImplUtils.test(index, z, postValidator)) {
            throw newInvalidValueException(index, z);
        }
        return z;
    }

    /**
     * 
     * @param index
     * @return 
     */
    default boolean contains(final int index) {
        return index >= 0 && index < size();
    }
    
    /**
     * 
     * @return 
     */
    default IterableAccessor<Integer> indexes() {
        return IterableAccessors.ofAll(Iterator.range(0, size()));
    }
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<Tuple2<Integer, V>> entriesByIndex();
    
}
