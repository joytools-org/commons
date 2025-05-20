/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.naming;

import static com.google.common.base.Preconditions.checkArgument;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import java.util.AbstractList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import org.joytools.commons.accessors.ListAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemAlreadyExistsException;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.newUnsupportedOperationException;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
class NakedAttributeMutator extends NakedMultiValueAccessor<Void, Object> {
    
    protected NakedAttributeMutator(final Attribute attr) {
        this.attr = Objects.requireNonNull(attr, "Attribute");
        this.list = new AbstractList<Object>() {
            @Override
            public Object get(int index) {
                return get0Unchecked(index);
            }

            @Override
            public int size() {
                return attr.size();
            }
        };
        this.values = IterableAccessors.ofAll(list);
    }
    
    public boolean isUpdatable() {
        return true;
    }
    
    public int size() {
        return attr.size();
    }
    
    public boolean contains(final int index) {
        return index >= 0 && index < attr.size();
    }
    
    protected Object get0Unchecked(final int index) {
        try {
            return attr.get(index);
        } catch (final NamingException ex) {
            throw newUnsupportedOperationException("get", index, ex);
        }
    }
    
    public Option<Object> get(final int index) {
        if (!contains(index)) {
            return None();
        }
        try {
            return Some(attr.get(index));
        } catch (final NamingException ex) {
            throw newUnsupportedOperationException("get", index, ex);
        }
    }
    
    public Object getOrFail(final int index) {
        if (!contains(index)) {
            throw newItemNotFoundException("getOrFail", index);
        }
        try {
            return attr.get(index);
        } catch (final NamingException ex) {
            throw newUnsupportedOperationException("get", index, ex);
        }
    }

    public Object getOrElse(final int index, final Object defValue) {
        if (!contains(index)) {
            return defValue;
        }
        try {
            return attr.get(index);
        } catch (final NamingException ex) {
            throw newUnsupportedOperationException("get", index, ex);
        }
    }

    public IterableAccessor<Integer> indexes() {
        return IterableAccessors.ofAll(Vector.range(0, attr.size()));
    }
    
    public IterableAccessor<Object> values() {
        return values;
    }

    public IterableAccessor<Tuple2<Integer, Object>> entriesByIndex() {
        return indexes().map(i -> Tuple(i, get0Unchecked(i)));
    }
    
    public ListAccessor.Reader<Object> read() {
        return read0();
    }
    
    public <V extends Object> V add(final V value) {
        attr.add(attr);
        return value;
    }

    public <V extends Object> V set(final int index, final V value) {
        attr.set(index, attr);
        return value;
    }
    
    public <V extends Object> V put(final int index, final V value) {
        checkArgument(index >= 0, "Illegal index: %s", index);
        while (attr.size() <= index) {
            attr.add(null);
        }
        attr.set(index, value);
        return value;
    }
    
    public <V extends Object> V insert(final int index, final V value) {
        attr.add(index, value);
        return put(index, value);
    }

    public <V extends Object> boolean update(final int index, final V value) {
        if (index < 0 || index >= attr.size()) {
            return false;
        }
        attr.set(index, attr);
        return true;
    }

    public boolean delete(final int index) {
        if (index < 0 || index >= attr.size()) {
            return false;
        }
        attr.remove(index);
        return true;
    }

    public void remove(final int index) {
        if (index < 0 || index >= attr.size()) {
            throw newItemNotFoundException("remove", index);
        }
        attr.remove(index);
    }

    protected final Attribute attr;
    
    protected final java.util.List<Object> list;
    
    protected final IterableAccessor<Object> values;
            
}
