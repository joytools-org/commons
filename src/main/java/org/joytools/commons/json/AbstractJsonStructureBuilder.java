/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.json;

import java.util.Iterator;
import java.util.Map;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;

import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.iterators.ArrayIterator;
import org.apache.commons.collections4.iterators.ObjectArrayIterator;

/**
 *
 * @author AndreaR
 */
public abstract class AbstractJsonStructureBuilder implements JsonStructureBuilderHelper {

    /**
     * 
     */
    protected AbstractJsonStructureBuilder() {
    }
    
    /**
     * 
     * @return 
     */
    abstract protected OrderedMap<Class, JsonStructureBuilderHelper> helpers();

    /**
     *
     * @param builder
     * @param o
     * @param caller
     */
    @Override
    public void add(final JsonArrayBuilder builder, 
            final Object o, 
            final JsonStructureBuilderHelper caller) {
        if (builder == null) {
            throw new IllegalArgumentException("Builder is null");
        }
        final Object obj = preConversion(o);
        if (obj == null) {
            builder.addNull();
            return;
        }
        final Class clazz = obj.getClass();
        final JsonStructureBuilderHelper helper = findHelper(clazz);
        if (helper == null) {
            throw new UnsupportedOperationException(clazz.getName() + ": " + o);
        }
        helper.add(builder, obj, this);
    }

    /**
     *
     * @param builder
     * @param name
     * @param o
     * @param caller
     */
    @Override
    public void add(final JsonObjectBuilder builder, 
            final String name, 
            final Object o, 
            final JsonStructureBuilderHelper caller) {
        if (builder == null) {
            throw new IllegalArgumentException("Builder is null");
        }
        final Object obj = preConversion(o);
        if (obj == null) {
            builder.addNull(name);
            return;
        }
        final Class clazz = obj.getClass();
        final JsonStructureBuilderHelper helper = findHelper(clazz);
        if (helper == null) {
            throw new UnsupportedOperationException(name + " = " + clazz.getName() + ": " + o);
        }
        helper.add(builder, name, obj, this);
    }

    /**
     *
     * @param objClazz
     * @return
     */
    protected JsonStructureBuilderHelper findHelper(final Class objClazz) {
        if (objClazz == null) {
            throw new IllegalArgumentException("Null object class");
        }
        for (final Map.Entry<Class, JsonStructureBuilderHelper> e : helpers().entrySet()) {
            final Class clazz = e.getKey();
            if (clazz.isAssignableFrom(objClazz)) {
                return e.getValue();
            }
        }
        return null;
    }

    /**
     *
     * @param obj
     * @return
     */
    protected JsonStructureBuilderHelper findHelper(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Null object");
        }
        return findHelper(obj.getClass());
    }

    /**
     *
     * @param o
     * @return
     */
    protected Object preConversion(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Object[] value) {
            return new ObjectArrayIterator(value);
        }
        if (o instanceof byte[] ||
                o instanceof int[] ||
                o instanceof long[]) {
            final StringBuilder b = new StringBuilder();
            final Iterator i = new ArrayIterator(o);
            while (i.hasNext()) {
                if (b.length() > 0) {
                    b.append(',');
                }
                b.append(i.next().toString());
            }
            b.insert(0, '[').append(']');
            return b;
        }
        /*
        if (o instanceof Map) {
        final Map map = (Map)o;
        return map.entrySet().iterator();
        } */
        return o;
    }

    /**
     *
     * @param o
     * @return
     */
    public JsonStructure toJsonStructure(final Object o) {
        return toJsonStructure(o, null);
    }

    /**
     *
     * @param o
     * @param caller
     * @return
     */
    @Override
    public JsonStructure toJsonStructure(final Object o, 
            final JsonStructureBuilderHelper caller) {
        Object obj;
        JsonStructure js;
        if ((obj = preConversion(o)) != null) {
            if ((js = toJsonStructure0(obj)) != null) {
                return js;
            }
        }
        if (obj == null) {
            return null;
        }
        throw new UnsupportedOperationException(obj.getClass().getName());
    }

    /**
     *
     * @param o
     * @param caller
     * @return
     */ /*
    public JsonStructure toJsonStructure(final Object o, final JsonStructureBuilderHelper caller) {
        Object obj;
        JsonStructure js;
        if ((obj = preConversion(o)) != null) {
            if ((js = toJsonStructure1(obj)) != null) {
                return js;
            }
        }
        if (obj == null) {
            return null;
        }
        throw new UnsupportedOperationException(obj.getClass().getName());
    } */

    /**
     *
     * @param obj
     * @return
     */
    protected JsonStructure toJsonStructure0(final Object obj) {
        if (obj instanceof JsonStructure js) {
            return js;
        }
        if (obj instanceof JsonArrayBuilder jab) {
            return jab.build();
        }
        if (obj instanceof JsonObjectBuilder job) {
            return job.build();
        }
        final Class clazz = obj.getClass();
        final JsonStructureBuilderHelper helper = findHelper(clazz);
        if (helper == null) {
            return null;
        }
        return helper.toJsonStructure(obj, this);
    }
    
}
