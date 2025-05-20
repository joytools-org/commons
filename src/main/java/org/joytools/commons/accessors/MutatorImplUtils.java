/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import com.google.common.collect.Multimap;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Function1;
import io.vavr.Function3;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.text.TextStringBuilder;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.collections.Mergers;
import org.joytools.commons.collections.StringMaps;
import org.joytools.commons.lang.ObjectUtils;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class MutatorImplUtils {

    /**
     * 
     * @param <T>
     * @param obj
     * @return 
     */
    public static <T> T checkNotImmutable(final T obj) {
        if (isImmutable(obj)) {
            throw new UnsupportedOperationException(String.format("%s is immutable", obj.getClass().getName()));
        }
        return obj;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <F>
     * @param fa 
     * @return  
     */
    public static <K, V, F extends Mutator<V>> F checkUpdatable(final F fa) {
        checkUpdatable(fa.isUpdatable());
        return fa;
    }

    /**
     * 
     * @param <T>
     * @param updatable 
     * @return  
     */
    public static <T> T checkUpdatable(final boolean updatable) {
        if (!updatable) {
            throw new UnsupportedOperationException("Read Only");
        }
        return null;
    }
    
    /**
     *
     * @param key
     * @return
     */
    public static <K> RuntimeException newEmptyValueException(final K key) {
        return new IllegalArgumentException("Value is empty for item: " + key);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return 
     */
    public static <K, V> RuntimeException newInvalidValueException(final K key, final K value) {
        String s = value == null ? "null" : value.toString();
        if (StringUtils.isBlank(s)) {
            s = "'" + s + "'";
        }
        return new IllegalArgumentException("Invalid value for item " + key + ": " + s);
    }

    /**
     *
     * @param method
     * @param key
     * @return
     */
    public static <K> RuntimeException newItemAlreadyExistsException(final String method, final K key) {
        return new IllegalArgumentException("Error with " + method + ": item " + key.toString() + " already exists");
    }

    /**
     *
     * @param method
     * @param key
     * @return
     */
    public static <K> RuntimeException newItemNotFoundException(final String method, final K key) {
        return newItemNotFoundException(method, key, null);
    }

    /**
     *
     * @param method
     * @param key
     * @param cause
     * @return
     */
    public static <K> RuntimeException newItemNotFoundException(final String method, final K key, final Exception cause) {
        return new IllegalArgumentException("Error with " + method + ": item " + key.toString() + " not found", cause);
    }

    /**
     *
     * @param key
     * @return
     */
    public static <K> RuntimeException newNullValueException(final K key) {
        return new NullPointerException("Value is null for item: " + key);
    }

    /**
     *
     * @param method
     * @param key
     * @return
     */
    public static <K> RuntimeException newSourceItemNotFoundException(final String method, final K key) {
        return new IllegalArgumentException("Error with " + method + ": source item " + key.toString() + " not found");
    }

    /**
     *
     * @param method
     * @param key
     * @return
     */
    public static <K> RuntimeException newTargetItemAlreadyExistsException(final String method, final K key) {
        return new IllegalArgumentException("Error with " + method + ": target item " + key.toString() + " already exists");
    }

    /**
     *
     * @param method
     * @param key
     * @return
     */
    public static <K> RuntimeException newTargetItemNotFoundException(final String method, final K key) {
        return new IllegalArgumentException("Error with " + method + ": target item " + key.toString() + " not found");
    }

    /**
     * 
     * @param <K>
     * @param method
     * @return 
     */
    public static <K> RuntimeException newUnsupportedOperationException(final String method) {
        return newUnsupportedOperationException(method, null, null);
    }

    /**
     *
     * @param method
     * @param key
     * @return
     */ /*
    public static <K> RuntimeException newUnsupportedOperationException(final String method, final K key) {
        return newUnsupportedOperationException(method, key, null);
    } */

    /**
     *
     * @param <K>
     * @param method
     * @param key
     * @param cause
     * @return
     */
    public static <K> RuntimeException newUnsupportedOperationException(final String method, final K key, final Exception cause) {
        String msg;
        final String meth = method.endsWith("()") ? method : method + "()";
        if (key == null) {
            msg = String.format("Unsupported operation %s", meth);
        } else {
            msg = String.format("Unsupported operation %s for item: %s", meth, key);
        }
        return new UnsupportedOperationException(msg, cause);
    }
    
    /**
     *
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return
     */
    public static <K, V> V nonEmptyValue(final K key, final V value) {
        final V s = nonNullValue(key, value);
        if (ObjectUtils.isEmpty(s)) {
            throw newEmptyValueException(key);
        }
        return value;
    }

    /**
     *
     * @param <V>
     * @param supplier
     * @return
     */
    public static <V> Supplier<V> nonNullSupplier(final Supplier<V> supplier) {
        return Objects.requireNonNull(supplier, "Supplier");
    }

    /**
     * 
     * @param <V>
     * @param predicate
     * @return 
     */
    public static <V> Predicate<V> nonNullPredicate(final Predicate<V> predicate) {
        return Objects.requireNonNull(predicate, "Predicate");
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <F>
     * @param function
     * @return 
     */
    public static <K, V, F extends Function<K, V>> F nonNullFunction(final F function) {
        return Objects.requireNonNull(function, "Function");
    }

    /**
     *
     * @param <K>
     * @param key
     * @return
     */
    public static <K> K nonNullKey(final K key) {
        return Objects.requireNonNull(key, "Key");
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return
     */
    public static <K, V> V nonNullValue(final K key, final V value) {
        return Objects.requireNonNull(value, "Null value for key: " + Objects.toString(key));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @return
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> V old_get(final M map, final K key) {
        return map.get(key);
    }

    /**
     *
     * @param <V>
     * @param <L>
     * @param list
     * @param key
     * @return
     */
    @Deprecated
    public static <V, L extends List<V>> V old_get(final L list, final int key) {
        try {
            return list.get(key);
        } catch (final IndexOutOfBoundsException ex) {
            throw newItemNotFoundException("get", key, ex);
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param defValue
     * @return
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> V old_get(final M map, final K key, final V defValue) {
        final V ret = map.get(key);
        if (ret != null || map.containsKey(key)) {
            return ret;
        }
        return defValue;
    }

    /**
     *
     * @param <V>
     * @param list
     * @param key
     * @return
     */
    @Deprecated
    public static <V> V old_get(final V[] list, final int key) {
        try {
            return list[key];
        } catch (final IndexOutOfBoundsException ex) {
            throw newItemNotFoundException("get", key, ex);
        }
    }

    /**
     *
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @return
     */
    public static <V, M extends Map<String, V>> V getIgnoreCase(final M map, final String key) {
        final Option<V> value = getOptionIgnoreCase(map, key);
        return value.getOrNull();
    }

    /**
     *
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @return
     */
    public static <V, M extends Multimap<String, V>> Collection<V> getIgnoreCase(final M map, final String key) {
        final Set<String> foundKeys = new LinkedHashSet();
        for (final String e : map.keySet()) {
            if (StringUtils.equalsIgnoreCase(key, e)) {
                foundKeys.add(e);
            }
        }
        final Iterator<String> i = foundKeys.iterator();
        if (!i.hasNext()) {
            return Collections.EMPTY_LIST;
        }
        final Collection<V> value = map.get(i.next());
        if (i.hasNext()) {
            throw new IllegalArgumentException("Multiple keys found with different case: " + foundKeys);
        }
        return value;
    }

    /**
     *
     * @param <V>
     * @param list
     * @param key
     * @return
     */
    @Deprecated
    public static <V> Option<V> old_getOption(final V[] list, final int key) {
        try {
            return Some(list[key]);
        } catch (final IndexOutOfBoundsException ex) {
            return None();
        }
    }
    /**
     *
     * @param <K>
     * @param key
     * @return
     */
    /**
     *
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return
     */
    /**
     *
     * @param <V>
     * @param supplier
     * @return
     */
    /**
     *
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return
     */
    /**
     *
     * @param method
     * @param key
     * @return
     */
    /**
     *
     * @param method
     * @param key
     * @return
     */
    /**
     *
     * @param key
     * @return
     */
    /**
     *
     * @param key
     * @return
     */
    /**
     *
     * @param method
     * @param key
     * @return
     */
    /**
     *
     * @param <K>
     * @param method
     * @param key
     * @param cause
     * @return
     */

    /**
     *
     * @param <V>
     * @param <L>
     * @param list
     * @param key
     * @return
     */
    @Deprecated
    public static <V, L extends List<V>> Option<V> old_getOption(final L list, final int key) {
        try {
            return Some(list.get(key));
        } catch (final IndexOutOfBoundsException ex) {
            return None();
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @return
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> Option<V> old_getOption(final M map, final K key) {
        return AccessorMethods.get(map, key);
    }

    /**
     *
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @return
     */
    public static <V, M extends Map<String, V>> Option<V> getOptionIgnoreCase(final M map, final String key) {
        Option<V> value = AccessorMethods.get(map, key);
        if (value.isDefined()) {
            return value;
        }
        // Check if nothing more to do
        if (map.isEmpty() || StringMaps.caseSensitivityOf(map) == CaseSensitivity.INSENSITIVE) {
            return None();
        }
        if (!StringUtils.isAllLowerCase(key) && (value = AccessorMethods.get(map, key.toLowerCase())).isDefined()) {
            return value;
        }
        if (!StringUtils.isAllUpperCase(key) && (value = AccessorMethods.get(map, key.toUpperCase())).isDefined()) {
            return value;
        }
        for (final Map.Entry<String, V> e : map.entrySet()) {
            if (StringUtils.equalsIgnoreCase(key, e.getKey())) {
                return Some(e.getValue());
            }
        }
        return None();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param supplier
     * @return
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> V old_getOrNew(final M map, final K key, final Supplier<V> supplier) {
        V ret = map.get(key);
        if (ret != null || map.containsKey(key)) {
            return ret;
        }
        final V defValue = supplier.get();
        map.put(key, defValue);
        return defValue;
    }

    /**
     *
     * @param <V>
     * @param <L>
     * @param list
     * @param key
     * @param supplier
     * @return
     */
    @Deprecated
    public static <V, L extends List<V>> V old_getOrNew(final L list, final int key, final Supplier<V> supplier) {
        int size = list.size();
        if (key >= 0 && key < size) {
            return list.get(key);
        }
        if (key == size) {
            try {
                final V defValue = supplier.get();
                list.add(defValue);
                return defValue;
            } catch (final UnsupportedOperationException ex) {
                throw newUnsupportedOperationException("add", key, ex);
            }
        }
        throw newUnsupportedOperationException("getOrNew", key, null);
    }

    /**
     *
     * @param <V>
     * @param <L>
     * @param list
     * @param key
     * @param defValue
     * @return
     */
    @Deprecated
    public static <V, L extends List<V>> V old_getOrNew(final L list, final int key, final V defValue) {
        int size = list.size();
        if (key >= 0 && key < size) {
            return list.get(key);
        }
        if (key == size) {
            try {
                list.add(defValue);
                return defValue;
            } catch (final UnsupportedOperationException ex) {
                throw newUnsupportedOperationException("add", key, ex);
            }
        }
        throw newUnsupportedOperationException("getOrNew", key, null);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param defValue
     * @return 
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> V old_getOrNew(final M map, final K key, final V defValue) {
        V ret = map.get(key);
        if (ret != null || map.containsKey(key)) {
            return ret;
        }
        map.put(key, defValue);
        return defValue;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key must not exist
     * @param value
     * @return
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> V old_insert(final M map, final K key, final V value) {
        if (map.containsKey(key)) {
            throw newItemAlreadyExistsException("insert", key);
        }
        map.put(key, value);
        return value;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param from
     * @param to
     * @return
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> M old_insertAll(final Map<K, V> from, final M to) {
        for (final Map.Entry<K, V> e : from.entrySet()) {
            old_insert(to, e.getKey(), e.getValue());
        }
        return to;
    }


    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param to
     * @param keys
     * @return
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> M putAll_old(final M to, final Iterable<K> keys) {
        if (to != null && keys != null) {
            for (final K k : keys) {
                to.put(k, null);
            }
        }
        return to;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param from
     * @param to
     * @return
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> M putAllUnique_old(final Map<K, V> from, final M to) {
        if (from == null || to == null) {
            return to;
        }
        final Comparator<K> compFrom = MapUtils.comparatorOrNaturalOrder(from);
        final Comparator<K> compTo = MapUtils.comparatorOrNaturalOrder(to);
        final SortedSet<K> toKeySet = compFrom == compTo ? null : new TreeSet<>(compTo);
        List<K> duplicated = null;
        for (final Map.Entry<K, V> e : from.entrySet()) {
            final K key = e.getKey();
            if (toKeySet != null && !toKeySet.add(key)) {
                if (duplicated == null) {
                    duplicated = new ArrayList<>();
                }
                duplicated.add(key);
            } else {
                to.put(key, from.get(key));
            }
        }
        if (duplicated != null) {
            throw new IllegalArgumentException("Duplicated keys on destination map: " + StringUtils.join(duplicated, ", ") + ". Case sensitivity on source and destination may be different.");
        }
        return to;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key must exist
     * @return
     */
    public static <K, V, M extends Map<K, V>> V read(final M map, final K key) {
        final Option<V> value = AccessorMethods.get(map, key);
        if (value.isEmpty()) {
            throw newItemNotFoundException("getOrFail", key);
        }
        return value.get();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @return
     */
    public static <K, V, M extends Map<K, V>> V readNonEmpty(final M map, final K key) {
        final V value = read(map, key);
        if (ObjectUtils.isEmpty(value)) {
            throw newEmptyValueException(key);
        }
        return value;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @return
     */
    public static <K, V, M extends Map<K, V>> V readNonNull(final M map, final K key) {
        final V value = read(map, key);
        if (value == null) {
            throw newNullValueException(key);
        }
        return value;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key must exist
     * @return
     */
    public static <K, V, M extends Map<K, V>> V remove(final M map, final K key) {
        if (!map.containsKey(key)) {
            throw newItemNotFoundException("delete", key);
        }
        return map.remove(key);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param m
     * @param fromKey
     * @param toKey
     * @return
     */
    public static <K, V, M extends Map<K, V>> V rename(final M m, final K fromKey, final K toKey) {
        final Option<V> fromValue = AccessorMethods.get(m, fromKey);
        if (fromValue.isEmpty()) {
            throw newSourceItemNotFoundException("rename ", fromKey);
        }
        final Option<V> toValue = AccessorMethods.get(m, toKey);
        if (toValue.isDefined()) {
            throw newTargetItemAlreadyExistsException("rename", toKey);
        }
        m.put(toKey, m.remove(fromKey));
        return fromValue.get();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param from
     * @param to
     * @return
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> M old_setAllIfPresent(final Map<K, V> from, final M to) {
        for (final Map.Entry<K, V> e : from.entrySet()) {
            old_setIfPresent(to, e.getKey(), e.getValue());
        }
        return to;
    }

    /**
     * See also update
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param value
     * @return
     */
    @Deprecated
    public static <K, V, M extends Map<K, V>> boolean old_setIfPresent(final M map, final K key, final V value) {
        if (map.containsKey(key)) {
            map.put(key, value);
            return true;
        }
        return false;
    }

    /**
     * See also setIfPresent
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param value
     * @return
     */
    public static <K, V, M extends Map<K, V>> boolean update(final M map, final K key, final V value) {
        if (map.containsKey(key)) {
            map.put(key, value);
            return true;
        }
        return false;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param key
     * @param function
     * @return
     */
    @Deprecated
    public static <K, V, Z> Z old_defaultGetOrFail1(final MapMutator<K, V> fa, final K key, final Function<? super V, ? extends Z> function) {
        final V v = fa.getOrFail(key);
        return nonNullFunction(function).apply(v);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param key
     * @return
     */
    @Deprecated
    public static <K, V> V old_defaultGetOrFail1(final MapMutator<K, V> fa, final K key) {
        final Option<V> val = fa.get(key);
        if (val.isDefined()) {
            return val.get();
        }
        throw newItemNotFoundException("getOrFail()", key);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param key
     * @param validator
     * @return
     */
    @Deprecated
    public static <K, V> V old_defaultGetOrFail1(final MapMutator<K, V> fa, final K key, final Predicate<? super V> validator) {
        final V val = fa.getOrFail(key);
        if (!nonNullPredicate(validator).test(val)) {
            throw newInvalidValueException(key, val);
        }
        return val;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param index
     * @param validator
     * @return
     */
    @Deprecated
    public static <K, V> V old_defaultGetOrFail1(final FixedSizeListMutator<V> fa, final int index, final Predicate<? super V> validator) {
        final V val = fa.getOrFail(index);
        if (!nonNullPredicate(validator).test(val)) {
            throw newInvalidValueException(index, val);
        }
        return val;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param index
     * @param function
     * @return
     */
    @Deprecated
    public static <K, V, Z> Z old_defaultGetOrFail1(final FixedSizeListMutator<V> fa, final int index, final Function<? super V, ? extends Z> function) {
        final V v = fa.getOrFail(index);
        return nonNullFunction(function).apply(v);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param key
     * @param function
     * @param postValidator
     * @return
     */
    @Deprecated
    public static <K, V, Z> Z old_defaultGetOrFail1(final MapMutator<K, V> fa, final K key, final Function<? super V, ? extends Z> function, final Predicate<? super Z> postValidator) {
        final V v = fa.getOrFail(key);
        final Z z = nonNullFunction(function).apply(v);
        if (nonNullPredicate(postValidator).test(z)) {
            throw newInvalidValueException(key, z);
        }
        return z;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param index
     * @param function
     * @param postValidator
     * @return
     */
    @Deprecated
    public static <K, V, Z> Z old_defaultGetOrFail1(final FixedSizeListMutator<V> fa, final int index, final Function<? super V, ? extends Z> function, final Predicate<? super Z> postValidator) {
        final V v = fa.getOrFail(index);
        final Z z = nonNullFunction(function).apply(v);
        if (nonNullPredicate(postValidator).test(z)) {
            throw newInvalidValueException(index, z);
        }
        return z;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param index
     * @param preValidator
     * @param function
     * @param postValidator
     * @return
     */
    @Deprecated
    public static <K, V, Z> Z old_defaultGetOrFail1(final FixedSizeListMutator<V> fa, final int index, final Predicate<? super V> preValidator, final Function<? super V, ? extends Z> function, final Predicate<? super Z> postValidator) {
        final Z z = old_defaultGetOrFail1(fa, index, preValidator, function);
        if (!nonNullPredicate(postValidator).test(z)) {
            throw newInvalidValueException(index, z);
        }
        return z;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param key
     * @param preValidator
     * @param function
     * @return
     */
    @Deprecated
    public static <K, V, Z> Z old_defaultGetOrFail1(final MapMutator<K, V> fa, final K key, final Predicate<? super V> preValidator, final Function<? super V, ? extends Z> function) {
        final V v = old_defaultGetOrFail1(fa, key, preValidator);
        return nonNullFunction(function).apply(v);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param index
     * @param preValidator
     * @param function
     * @return
     */
    @Deprecated
    public static <K, V, Z> Z old_defaultGetOrFail1(final FixedSizeListMutator<V> fa, final int index, final Predicate<? super V> preValidator, final Function<? super V, ? extends Z> function) {
        final V v = old_defaultGetOrFail1(fa, index, preValidator);
        return nonNullFunction(function).apply(v);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param key
     * @param preValidator
     * @param function
     * @param postValidator
     * @return
     */
    @Deprecated
    public static <K, V, Z> Z old_defaultGetOrFail1(final MapMutator<K, V> fa, final K key, final Predicate<? super V> preValidator, final Function<? super V, ? extends Z> function, final Predicate<? super Z> postValidator) {
        final Z z = old_defaultGetOrFail1(fa, key, preValidator, function);
        if (!nonNullPredicate(postValidator).test(z)) {
            throw newInvalidValueException(key, z);
        }
        return z;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param key
     * @param validator
     * @return
     */
    @Deprecated
    public static <K, V> Option<V> old_defaultGet1(final MapMutator<K, V> fa, final K key, final Predicate<? super V> validator) {
        final Option<V> val = fa.get(key);
        if (val.isEmpty()) {
            return val;
        }
        if (!nonNullPredicate(validator).test(val.get())) {
            throw newInvalidValueException(key, val);
        }
        return val;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param key
     * @param function
     * @return
     */
    @Deprecated
    public static <K, V, Z> Option<Z> old_defaultGet1(final MapMutator<K, V> fa, final K key, final Function<? super V, ? extends Z> function) {
        final Option<V> val = fa.get(key);
        if (val.isEmpty()) {
            return None();
        }
        return Some(nonNullFunction(function).apply(val.get()));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param index
     * @param function
     * @return
     */
    @Deprecated
    public static <K, V, Z> Option<Z> old_defaultGet1(final FixedSizeListMutator<V> fa, final int index, final Function<? super V, ? extends Z> function) {
        final Option<V> val = fa.get(index);
        if (val.isEmpty()) {
            return None();
        }
        return Some(nonNullFunction(function).apply(val.get()));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param key
     * @param preValidator
     * @param function
     * @return
     */
    @Deprecated
    public static <K, V, Z> Option<Z> old_defaultGet1(final MapMutator<K, V> fa, final K key, final Predicate<? super V> preValidator, final Function<? super V, ? extends Z> function) {
        final Option<V> val = fa.get(key);
        if (val.isEmpty()) {
            return None();
        }
        if (!nonNullPredicate(preValidator).test(val.get())) {
            throw newInvalidValueException(key, val);
        }
        return Some(nonNullFunction(function).apply(val.get()));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param fa
     * @param index
     * @param preValidator
     * @param function
     * @return
     */
    @Deprecated
    public static <K, V, Z> Option<Z> old_defaultGet1(final FixedSizeListMutator<V> fa, final int index, final Predicate<? super V> preValidator, final Function<? super V, ? extends Z> function) {
        final Option<V> val = fa.get(index);
        if (val.isEmpty()) {
            return None();
        }
        if (!nonNullPredicate(preValidator).test(val.get())) {
            throw newInvalidValueException(index, val);
        }
        return Some(nonNullFunction(function).apply(val.get()));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param index
     * @param validator
     * @return
     */
    @Deprecated
    public static <K, V> Option<V> old_defaultGet1(final FixedSizeListMutator<V> fa, final int index, final Predicate<? super V> validator) {
        final Option<V> val = fa.get(index);
        if (val.isEmpty()) {
            return val;
        }
        if (!nonNullPredicate(validator).test(val.get())) {
            throw newInvalidValueException(index, val);
        }
        return val;
    }


    /**
     *
     * @param <K>
     * @param <V>
     * @param <U>
     * @param fa
     * @param key
     * @param value
     * @return
     */
    public static <K, V, U extends V> U defaultInsert(final MapMutator<K, V> fa, final K key, final U value) {
        if (fa.contains(key)) {
            throw newItemAlreadyExistsException("insert", key);
        }
        checkUpdatable(fa);
        fa.put(key, value);
        return value;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param from
     * @param to
     * @return
     */
    public static <K, V> V defaultCopy(final MapMutator<K, V> fa, final K from, final K to) {
        final Option<V> value = fa.get(from);
        if (value.isEmpty()) {
            throw newSourceItemNotFoundException("copy", from);
        }
        checkUpdatable(fa);
        fa.put(to, value.get());
        return value.get();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param key
     * @param defValue
     * @return
     */
    public static <K, V> V defaultGetOrNew(final MapMutator<K, V> fa, final K key, final V defValue) {
        final Option<V> v = fa.get(key);
        if (v.isDefined()) {
            return v.get();
        }
        checkUpdatable(fa);
        fa.put(key, defValue);
        return defValue;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param key
     * @param supplier
     * @return
     */
    public static <K, V> V defaultGetOrNew(final MapMutator<K, V> fa, final K key, final Supplier<V> supplier) {
        nonNullSupplier(supplier);
        final Option<V> v = fa.get(key);
        if (v.isDefined()) {
            return v.get();
        }
        checkUpdatable(fa);
        final V value = supplier.get();
        fa.put(key, value);
        return value;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param key must exist
     */
    public static <K, V> void defaultRemove(final MapMutator<K, V> fa, final K key) {
        if (!fa.contains(key)) {
            throw newItemNotFoundException("remove", key);
        }
        checkUpdatable(fa);
        fa.delete(key);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param from
     * @param to
     * @return
     */
    public static <K, V> V defaultOverwrite(final FixedSizeMapMutator<K, V> fa, final K from, final K to) {
        final Option<V> value = fa.get(from);
        if (value.isEmpty()) {
            throw newSourceItemNotFoundException("overwrite", from);
        }
        if (!fa.contains(to)) {
            throw newTargetItemNotFoundException("overwrite", to);
        }
        checkUpdatable(fa);
        fa.set(to, value.get());
        return value.get();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param key
     * @param value
     * @return
     */
    public static <K, V> boolean defaultUpdate(final FixedSizeMapMutator<K, V> fa, final K key, final V value) {
        if (!fa.contains(key)) {
            return false;
        }
        checkUpdatable(fa);
        fa.set(key, value);
        return true;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param fa
     * @param index
     * @param value
     * @return 
     */
    public static <K, V> boolean defaultUpdate(final FixedSizeListMutator<V> fa, final int index, final V value) {
        if (!fa.contains(index)) {
            return false;
        }
        checkUpdatable(fa);
        fa.set(index, value);
        return true;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param fa
     * @param from must exist
     * @param to must not exist
     * @return
     */
    public static <K, V> V defaultRename(final MapMutator<K, V> fa, final K from, final K to) {
        final Option<V> value = fa.get(from);
        if (value.isEmpty()) {
            throw newSourceItemNotFoundException("rename", from);
        }
        if (fa.contains(to)) {
            throw newItemAlreadyExistsException("rename", to);
        }
        checkUpdatable(fa);
        fa.put(to, value.get());
        fa.delete(from);
        return value.get();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param target
     * @param data
     * @param merger 
     */
    public static <K, V, U extends V> void defaultMerge(final MapMutator<K, V> target, 
            final Iterable<Tuple2<K, U>> data, 
            final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        for (final Tuple2<K, U> e : data) {
            final Option<V> existing = target.get(e._1);
            if (existing.isDefined()) {
                if (merger == null) {
                    Mergers.throwDuplicates(e._1, existing.get(), e._2); // Will throw Exception
                } else {
                    final V val = merger.apply(e._1, existing.get(), e._2);
                    target.set(e._1, val);
                }
            } else {
                target.insert(e._1, e._2);
            }
        }
    }

    /**
     *
     * @param o
     * @return
     */
    public static boolean isImmutable(final Object o) {
        if (o == null) {
            return false;
        }
        final String name = StringUtils.substringAfterLast(o.getClass().getName(), '.');
        return name.contains("Immutable") 
                || name.contains("ReadOnly") 
                || name.contains("Unmodifiable");
    }
    
}
