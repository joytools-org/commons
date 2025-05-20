/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.naming;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.Objects;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import org.joytools.commons.accessors.MapAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemAlreadyExistsException;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
abstract class NakedAttributesMutator extends NakedMultiValueAccessor<String, Object>{

    protected NakedAttributesMutator(final Attributes attrs) {
        this.attrs = Objects.requireNonNull(attrs, "Attributes");
    }
    
    public CaseSensitivity caseSensitivity() {
        return CaseSensitivity.forIgnoreCase(attrs.isCaseIgnored());
    }
    
    public boolean isUpdatable() {
        return true;
    }
    
    public int size() {
        return attrs.size();
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(final String key) {
        return attrs.get(key) != null;
    }
    
    abstract protected Object get0(final Attribute attr);

    public Option<Object> get(final String key) {
        final Attribute attr = attrs.get(key);
        if (attr == null) {
            return None();
        }
        return Some(get0(attr));
    }
    
    public Object getOrFail(final String key) {
        final Attribute attr = attrs.get(key);
        if (attr == null) {
            throw newItemNotFoundException("getOrFail", key);
        }
        return get0(attr);
    }

    public IterableAccessor<String> keys() {
        return IterableAccessors.ofAll(attrs.getIDs());
    }
    
    public IterableAccessor<Tuple2<String, Object>> entriesByKey() {
        return IterableAccessors.ofAll(attrs.getAll())
                .map(a -> Tuple(a.getID(), get0(a)));
    }

    public IterableAccessor<Object> values() {
        return IterableAccessors.ofAll(attrs.getAll())
                .map(a -> get0(a));
    }

    public Object getOrElse(final String key, final Object defValue) {
        final Attribute attr = attrs.get(key);
        if (attr == null) {
            return defValue;
        }
        return get0(attr);
    }
    
    public <U> U set(final String key, final U value) {
        final Attribute attr = attrs.get(key);
        if (attr == null) {
            throw newItemNotFoundException("set", key);
        }
        attrs.put(key, value);
        return value;
    }

    public <U> U put(final String key, final U value) {
        attrs.put(key, value);
        return value;
    }

    public <U> U insert(final String key, final U value) {
        final Attribute attr = attrs.get(key);
        if (attr != null) {
            throw newItemAlreadyExistsException("insert", key);
        }
        attrs.put(key, value);
        return value;
    }

    public boolean delete(final String key) {
        final Attribute attr = attrs.get(key);
        if (attr == null) {
            return false;
        }
        attrs.remove(key);
        return true;
    }

    public void remove(final String key) {
        final Attribute attr = attrs.get(key);
        if (attr == null) {
            throw newItemNotFoundException("remove", key);
        }
        attrs.remove(key);
    }

    public <U> boolean update(final String key, final U value) {
        final Attribute attr = attrs.get(key);
        if (attr == null) {
            return false;
        }
        attrs.put(key, value);
        return true;
    }

    public MapAccessor.Reader<String, Object> read() {
        return read0();
    }
    
    private final Attributes attrs;
    
}
