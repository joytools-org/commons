/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.impl;

import java.util.Objects;
import org.joytools.commons.accessors.Accessor;
import org.joytools.commons.accessors.AccessorSelector;
import org.joytools.commons.accessors.DefaultAccessorSelector;
import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.collections.IterableAccessors;
import static org.joytools.commons.function.Functions.toObjectString;
import static org.joytools.commons.function.Predicates.isNotEmpty;
import static org.joytools.commons.function.Predicates.isNotNull;
import static org.joytools.commons.time.TimeConverters.toSQLDate;
import static org.joytools.commons.time.TimeConverters.toSQLTime;
import static org.joytools.commons.time.TimeConverters.toSQLTimestamp;
import org.joytools.commons.time.TimeValue;
import org.joytools.commons.util.ComboVariant;
import org.joytools.commons.util.Variant;
import org.joytools.commons.util.Variants;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
class IndexedMapAccessorReaderImpl<K, V> implements IndexedMapAccessor.Reader<K, V> {

    IndexedMapAccessorReaderImpl(final Accessor<V> acc) {
        Objects.requireNonNull(acc, "Accessor");
        this.list = acc instanceof ListAccessor ? ListAccessor.class.cast(acc) : null;
        this.map = acc instanceof MapAccessor ? MapAccessor.class.cast(acc) : null;
    }
    
    private final ListAccessor<V> list;
    
    private final MapAccessor<K, V> map;
    
    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public AccessorSelector<K, Object> asAccessor(final K key) {
        return new DefaultAccessorSelector<K, V, K, Object>() {
            @Override
            protected K key() {
                return key;
            }

            @Override
            protected V value() {
                return map.getOrFail(key);
            }
        };
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public AccessorSelector<Object, Object> asAccessor(final int index) {
        return new DefaultAccessorSelector<K, V, Object, Object>() {
            @Override
            protected K key() {
                throw new UnsupportedOperationException("key");
            }

            @Override
            protected V value() {
                return list.getOrFail(index);
            }
        };
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public V nonNull(final K key) {
        return map.getOrFail(key, isNotNull());
        // return nonNullValue(key, this.getOrFail(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public V nonEmpty(final K key) {
        return map.getOrFail(key, isNotEmpty());
        // return nonEmptyValue(key, this.getOrFail(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Object asObject(final K key) {
        return map.getOrFail(key);
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Object asObjectNonNull(final K key) {
        return map.getOrFail(key, isNotNull());
        // return nonNullValue(key, getOrFail(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Object asObjectNonEmpty(final K key) {
        return map.getOrFail(key, isNotEmpty());
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param clazz
     * @return 
     */
    @Override
    public <Z> Z asType(final K key, final Class<Z> clazz) {
        final Object obj = map.getOrFail(key);
        Objects.requireNonNull(clazz, "Type");
        return obj == null ? null : clazz.cast(obj);
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param clazz
     * @return 
     */
    @Override
    public <Z> Z asTypeNonNull(final K key, final Class<Z> clazz) {
        final Object obj = map.getOrFail(key, isNotNull());
        Objects.requireNonNull(clazz, "Type");
        return obj == null ? null : clazz.cast(obj);
        // return nonNullValue(key, getOrFail(key));
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param clazz
     * @return 
     */
    @Override
    public <Z> Z asTypeNonEmpty(final K key, final Class<Z> clazz) {
        final Object obj = map.getOrFail(key, isNotEmpty());
        Objects.requireNonNull(clazz, "Type");
        return obj == null ? null : clazz.cast(obj);
        // return nonNullValue(key, getOrFail(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public TimeValue<?> asTimeValue(final K key) {
        return map.getOrFail(key, Variants.toTimeValue());
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public TimeValue<?> asTimeValueNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), Variants.toTimeValue());
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Variant<V> asVariant(final K key) {
        return map.getOrFail(key, Variants.toVariant());
        /*
        return ComboVariants.of(readObject(key));
        */
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public ComboVariant<V> asComboVariant(final K key) {
        return map.getOrFail(key, Variants.toComboVariant());
        /*
        return ComboVariants.of(readObject(key));
        */
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Variant<V> asVariantNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), Variants.toVariant());
        /*
        return ComboVariants.of(readObjectNonNull(key));
        */
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Variant<V> asVariantNonEmpty(final K key) {
        return map.getOrFail(key, isNotEmpty(), Variants.toVariant());
        /*
        return ComboVariants.of(readObjectNonNull(key));
        */
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public ComboVariant<V> asComboVariantNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), Variants.toComboVariant());
        /*
        return ComboVariants.of(readObjectNonNull(key));
        */
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public ComboVariant<V> asComboVariantNonEmpty(final K key) {
        return map.getOrFail(key, isNotEmpty(), Variants.toComboVariant());
        /*
        return ComboVariants.of(readObjectNonNull(key));
        */
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public String asString(final K key) {
        return map.getOrFail(key, toObjectString());
        /* 
        return Objects.toString(getOrFail(key), null);
        */
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public String asStringNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), toObjectString());
        // return nonNull(key).toString();
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public String asStringNonEmpty(final K key) {
        return map.getOrFail(key, isNotEmpty(), toObjectString());
        /*
        final String s = asStringNonNull(key);
        return nonEmptyValue(key, s);
        */
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Boolean asBoolean(final K key) {
        return map.getOrFail(key, Variants.toBoolean());
        // return Variants.toBoolean(readObject(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Boolean asBooleanNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), Variants.toBoolean());
        // return Variants.toBoolean(readObjectNonNull(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public byte[] asBytes(final K key) {
        return map.getOrFail(key, Variants.toBytes());
        // return Variants.toBytes(readObject(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public byte[] asBytesNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), Variants.toBytes());
        // return Variants.toBytes(readObjectNonNull(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Integer asInteger(final K key) {
        return map.getOrFail(key, Variants.toInteger());
        // return Variants.toInteger(readObject(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Integer asIntegerNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), Variants.toInteger());
        // return Variants.toInteger(readObjectNonNull(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public IterableAccessor<V> asSequence(final K key) {
        return (IterableAccessor<V>)map.getOrFail(key, IterableAccessors.toIteration());
        // return Variants.toInteger(readObject(index));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public IterableAccessor<String> asStringSequence(final K key) {
        return asSequence(key).map(toObjectString());
        // return Variants.toInteger(readObject(index));
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param type
     * @return 
     */
    @Override
    public <Z> IterableAccessor<Z> asSequence(final K key, final Class<Z> type) {
        return map.getOrFail(key, IterableAccessors.toIteration(type));
        // return Variants.toInteger(readObject(index));
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public IterableAccessor<Object> asSequenceNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), IterableAccessors.toIteration());
        // return Variants.toInteger(readObjectNonNull(index));
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param type
     * @return 
     */
    @Override
    public <Z> IterableAccessor<Z> asSequenceNonNull(final K key, final Class<Z> type) {
        return map.getOrFail(key, isNotNull(), IterableAccessors.toIteration(type));
        // return Variants.toInteger(readObjectNonNull(index));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public IterableAccessor<Object> asSequenceNonEmpty(final K key) {
        return map.getOrFail(key, isNotEmpty(), IterableAccessors.toIteration());
        // return Variants.toInteger(readObjectNonNull(index));
    }

    /**
     * 
     * @param <Z>
     * @param key
     * @param type
     * @return 
     */
    @Override
    public <Z> IterableAccessor<Z> asSequenceNonEmpty(final K key, final Class<Z> type) {
        return map.getOrFail(key, isNotEmpty(), IterableAccessors.toIteration(type));
        // return Variants.toInteger(readObjectNonNull(index));
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public int asInt(final K key) {
        return asIntegerNonNull(key);
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public java.sql.Time asSQLTime(final K key) {
        return map.getOrFail(key, toSQLTime());
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public java.sql.Time asSQLTimeNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), toSQLTime());
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public java.sql.Date asSQLDate(final K key) {
        return map.getOrFail(key, toSQLDate());
        /*
        final Object o = asObject(key);
        if (o == null) {
            return null;
        }
        if (o instanceof java.sql.Date) {
            return java.sql.Date.class.cast(o);
        }
        if (o instanceof java.util.Date) {
            return new java.sql.Date(java.util.Date.class.cast(o).getTime());
        }
        return java.sql.Date.valueOf(o.toString());
        */
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public java.sql.Date asSQLDateNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), toSQLDate());
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public java.sql.Timestamp asSQLTimestamp(final K key) {
        return map.getOrFail(key, toSQLTimestamp());
        /*
        final Object o = asObject(key);
        if (o == null) {
            return null;
        }
        return java.sql.Timestamp.class.cast(o);
        */
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public java.sql.Timestamp asSQLTimestampNonNull(final K key) {
        return map.getOrFail(key, isNotNull(), toSQLTimestamp());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public V nonNull(final int index) {
        return list.getOrFail(index, isNotNull());
        // return nonNullValue(index, this.getOrFail(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public V nonEmpty(final int index) {
        return list.getOrFail(index, isNotEmpty());
        // return nonEmptyValue(index, this.getOrFail(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public Object asObject(final int index) {
        return list.getOrFail(index);
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public Object asObjectNonNull(final int index) {
        return list.getOrFail(index, isNotNull());
        // return nonNullValue(index, getOrFail(index));
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public Object asObjectNonEmpty(final int index) {
        return list.getOrFail(index, isNotEmpty());
        // return nonNullValue(index, getOrFail(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public TimeValue<?> asTimeValue(final int index) {
        return list.getOrFail(index, Variants.toTimeValue());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public TimeValue<?> asTimeValueNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), Variants.toTimeValue());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public Variant<V> asVariant(final int index) {
        return list.getOrFail(index, Variants.toVariant());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public ComboVariant<V> asComboVariant(final int index) {
        return list.getOrFail(index, Variants.toComboVariant());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public Variant<V> asVariantNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), Variants.toVariant());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public Variant<V> asVariantNonEmpty(final int index) {
        return list.getOrFail(index, isNotEmpty(), Variants.toVariant());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public ComboVariant<V> asComboVariantNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), Variants.toComboVariant());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public ComboVariant<V> asComboVariantNonEmpty(final int index) {
        return list.getOrFail(index, isNotEmpty(), Variants.toComboVariant());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public String asString(final int index) {
        return list.getOrFail(index, toObjectString());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public String asStringNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), toObjectString());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public String asStringNonEmpty(final int index) {
        return list.getOrFail(index, isNotEmpty(), toObjectString());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public Boolean asBoolean(final int index) {
        return list.getOrFail(index, Variants.toBoolean());
        // return Variants.toBoolean(readObject(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public Boolean asBooleanNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), Variants.toBoolean());
        // return Variants.toBoolean(readObjectNonNull(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public byte[] asBytes(final int index) {
        return list.getOrFail(index, Variants.toBytes());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public byte[] asBytesNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), Variants.toBytes());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public Integer asInteger(final int index) {
        return list.getOrFail(index, Variants.toInteger());
        // return Variants.toInteger(readObject(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public Integer asIntegerNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), Variants.toInteger());
        // return Variants.toInteger(readObjectNonNull(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public IterableAccessor<Object> asSequence(final int index) {
        return list.getOrFail(index, IterableAccessors.toIteration());
        // return Variants.toInteger(readObject(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public IterableAccessor<String> asStringSequence(final int index) {
        return asSequence(index).map(toObjectString());
        // return Variants.toInteger(readObject(index));
    }

    /**
     * 
     * @param <Z>
     * @param index
     * @param type
     * @return 
     */
    @Override 
    public <Z> IterableAccessor<Z> asSequence(final int index, final Class<Z> type) {
        return list.getOrFail(index, IterableAccessors.toIteration(type));
        // return Variants.toInteger(readObjectNonNull(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public IterableAccessor<Object> asSequenceNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), IterableAccessors.toIteration());
        // return Variants.toInteger(readObjectNonNull(index));
    }

    /**
     * 
     * @param <Z>
     * @param index
     * @param type
     * @return 
     */
    @Override 
    public <Z> IterableAccessor<Z> asSequenceNonNull(final int index, final Class<Z> type) {
        return (IterableAccessor<Z>)list.getOrFail(index, isNotNull(), IterableAccessors.toIteration(type));
        // return Variants.toInteger(readObjectNonNull(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public IterableAccessor<Object> asSequenceNonEmpty(final int index) {
        return list.getOrFail(index, isNotEmpty(), IterableAccessors.toIteration());
        // return Variants.toInteger(readObjectNonNull(index));
    }

    /**
     * 
     * @param <Z>
     * @param index
     * @param type
     * @return 
     */
    @Override 
    public <Z> IterableAccessor<Z> asSequenceNonEmpty(final int index, final Class<Z> type) {
        return list.getOrFail(index, isNotEmpty(), IterableAccessors.toIteration(type));
        // return Variants.toInteger(readObjectNonNull(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public int asInt(final int index) {
        return asIntegerNonNull(index);
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public java.sql.Time asSQLTime(final int index) {
        return list.getOrFail(index, toSQLTime());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public java.sql.Time asSQLTimeNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), toSQLTime());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public java.sql.Date asSQLDate(final int index) {
        return list.getOrFail(index, toSQLDate());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public java.sql.Date asSQLDateNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), toSQLDate());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public java.sql.Timestamp asSQLTimestamp(final int index) {
        return list.getOrFail(index, toSQLTimestamp());
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override 
    public java.sql.Timestamp asSQLTimestampNonNull(final int index) {
        return list.getOrFail(index, isNotNull(), toSQLTimestamp());
    }
    
}
