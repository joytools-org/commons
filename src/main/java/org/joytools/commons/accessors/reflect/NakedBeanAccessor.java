/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.reflect;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.joytools.commons.accessors.MapAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.newUnsupportedOperationException;
import org.joytools.commons.accessors.Mutators;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessors;
import static org.joytools.commons.lang.ObjectUtils.coalesce;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
class NakedBeanAccessor extends NakedMultiValueAccessor<String, Object> {

    protected NakedBeanAccessor(final Object bean) throws IntrospectionException {
        this(bean, null);
    }

    protected NakedBeanAccessor(final Object bean,
            final CaseSensitivity cs) throws IntrospectionException {
        this.bean = Objects.requireNonNull(bean, "Bean");
        this.cs = coalesce(cs, CaseSensitivity.SENSITIVE);
        this.methods = this.cs.newLinkedMap();
        final var mut = Mutators.of(methods);
        for (final var pd : Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors()) {
            if (!pd.getName().equalsIgnoreCase("class")) {
                final Method read0 = pd.getReadMethod();
                final Method read = read0 != null && read0.canAccess(bean) ? read0 : null;
                final Method write0 = pd.getWriteMethod();
                final Method write = write0 != null && write0.canAccess(bean) ? write0 : null;
                if (read != null || write != null) {
                    mut.insert(pd.getName(), Tuple(read, write));
                }
            }
        }
    }

    protected Tuple2<Method, Method> getMethods0(final String key) {
        return methods.get(key);
    }
    
    protected Object get0(final String key) {
        final Tuple2<Method, Method> t = getMethods0(key);
        if (t == null || t._1 == null) {
            throw newItemNotFoundException("get", key);
        }
        try {
            return t._1.invoke(bean);
        } catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw newUnsupportedOperationException("get", key, ex);
        }
    }
    
    protected void set0(final String key, final Object value) {
        final Tuple2<Method, Method> t = getMethods0(key);
        if (t == null || t._2 == null) {
            throw newItemNotFoundException("set", key);
        }
        try {
            t._2.invoke(bean, value);
        } catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw newUnsupportedOperationException("get", key, ex);
        }
    }
    
    public int size() {
        return methods.size();
    }
    
    public CaseSensitivity caseSensitivity() {
        return cs;
    }
    
    public boolean contains(final String key) {
        return methods.containsKey(key);
    }
    
    public Option<Object> get(final String key) {
        if (contains(key)) {
            return Some(get0(key));
        }
        return None();
    }
    
    public Object getOrElse(final String key, final Object defValue) {
        if (contains(key)) {
            return get0(key);
        }
        return defValue;
    }

    public Object getOrNull(final String key) {
        if (contains(key)) {
            return get0(key);
        }
        return null;
    }

    public Object getOrFail(final String key) {
        if (contains(key)) {
            return get0(key);
        }
        throw newItemNotFoundException("get", key);
    }

    public IterableAccessor<String> keys() {
        return IterableAccessors.ofAll(methods.keySet());
    }
    
    public IterableAccessor<Object> values() {
        return IterableAccessors.ofAll(methods.keySet())
                .map(k -> get0(k));
    }

    public IterableAccessor<Tuple2<String, Object>> entriesByKey() {
        return IterableAccessors.ofAll(methods.keySet())
                .map(k -> Tuple(k, get0(k)));
    }
    
    public <U> boolean update(final String key, final U value) {
        if (!contains(key)) {
            return false;
        }
        set0(key, value);
        return true;
    }

    public <U> U set(final String key, final U value) {
        set0(key, value);
        return value;
    }

    public MapAccessor.Reader<String, Object> read() {
        return read0();
    }
    
    protected final Object bean;
    
    private final CaseSensitivity cs;

    private final java.util.Map<String, Tuple2<Method, Method>> methods;
    
}
