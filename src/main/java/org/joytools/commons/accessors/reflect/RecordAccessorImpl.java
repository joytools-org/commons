/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.reflect;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.collection.SortedMap;
import io.vavr.collection.TreeMap;
import io.vavr.control.Option;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.Objects;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.accessors.MutatorImplUtils;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import org.joytools.commons.accessors.MutatorMethods;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.interop.Java;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
class RecordAccessorImpl<R extends Record> extends NakedMultiValueAccessor<String, Object> implements RecordAccessor<R> {

    /**
     * 
     * @param record 
     */
    protected RecordAccessorImpl(final R record,
            final CaseSensitivity cs) {
        this.record = Objects.requireNonNull(record, "Record");
        this.cs = Objects.requireNonNull(cs, "CaseSensitivity");
        Objects.requireNonNull(record, "Record");
        final Class<?> clazz = record.getClass();
        if (!Modifier.isPublic(clazz.getModifiers())) {
            throw new UnsupportedOperationException(String.format("Record %s is not public", clazz.getName()));
        }
        final java.util.HashMap<String, RecordComponent> tempMap = GET_METHODS.apply(clazz);
        final String duplicates = cs.findDuplicates(tempMap.keySet()).mkString(", ");
        if (!duplicates.isEmpty()) {
            throw new IllegalArgumentException(String.format("Duplicated key(s): %s", duplicates));
        }            
        this.recComponents = cs.newLinkedMap();
        this.recComponents.putAll(tempMap);
    }
    
    @Override
    public CaseSensitivity caseSensitivity() {
        return cs;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public R toRecord() {
        return record;
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public boolean contains(final String key) {
        return recComponents.containsKey(key);
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Option<Object> get(final String key) {
        final Option<RecordComponent> m = AccessorMethods.get(recComponents, key);
        if (m.isEmpty()) {
            return None();
        }
        try {
            return Some(m.get().getAccessor().invoke(record));
        } catch (final IllegalAccessException ex) {
            throw MutatorImplUtils.newUnsupportedOperationException("get", key, ex);
        } catch (InvocationTargetException ex) {
            throw MutatorImplUtils.newUnsupportedOperationException("get", key, ex);
        }
    }

    /**
     * 
     * @param key
     * @param defValue
     * @return 
     */
    @Override
    public Object getOrElse(final String key, final Object defValue) {
        return get(key).getOrElse(defValue);
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Object getOrFail(final String key) {
        final var opt = get(key);
        if (opt.isEmpty()) {
            throw newItemNotFoundException("getOrFail", key);
        }
        return opt.get();
    }

    /**
     * 
     * @param key
     * @return 
     */
    protected Object get0(final String key) {
        final Method m = recComponents.get(key).getAccessor();
        try {
            return m.invoke(record);
        } catch (final IllegalAccessException ex) {
            throw MutatorImplUtils.newUnsupportedOperationException("get", key, ex);
        } catch (InvocationTargetException ex) {
            throw MutatorImplUtils.newUnsupportedOperationException("get", key, ex);
        }
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<String> keys() {
        return IterableAccessors.ofAll(recComponents.keySet());
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<Tuple2<String, Object>> entriesByKey() {
        return keys().map(k -> Tuple(k, get0(k)));
    }

    /**
     * 
     * @return 
     */
    @Override
    public int size() {
        return recComponents.size();
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<Object> values() {
        return IterableAccessors.ofAll(recComponents.keySet()).map(k -> get0(k));
    }

    /**
     * 
     * @return 
     */
    @Override
    public MapAccessor.Reader<String, Object> read() {
        return read0();
    }
    
    /**
     * 
     * @param clazz
     * @return 
     */
    static java.util.LinkedHashMap<String, RecordComponent> getRecordComponents(final Class<?> clazz) {
        return IterableAccessors.of(clazz.getRecordComponents())
                .map(m -> Tuple(m.getName(), m))
                .to(Java.linkedHashMap());
    }

    private final java.util.Map<String, RecordComponent> recComponents;
        
    private final static Function1<Class, java.util.LinkedHashMap<String, RecordComponent>> GET_METHODS = 
            Function1.<Class, java.util.LinkedHashMap<String, RecordComponent>>of(c -> getRecordComponents(c)).memoized();    
    
    private final R record;

    private final CaseSensitivity cs;
    
}
