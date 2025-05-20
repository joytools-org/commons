/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.json;

import java.util.Iterator;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.JsonStructure;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.joytools.commons.accessors.reflect.BeanAccessors;
import org.joytools.commons.lang.ExceptionUtils;


/**
 *
 * @author AndreaR
 */
public class DefaultJsonStructureBuilder extends AbstractJsonStructureBuilder {
 
    /**
     * 
     */
    public DefaultJsonStructureBuilder() {
    }

    /**
     * 
     * @return 
     */
    protected synchronized OrderedMap<Class, JsonStructureBuilderHelper> helpers() {
        if (this.helpers == null) {
            final OrderedMap<Class, JsonStructureBuilderHelper> ordMap = new ListOrderedMap<>();
            addHelpers(ordMap);
            this.helpers = ordMap;
        }
        return this.helpers;
    }

    /**
     * 
     * @param ordMap
     */
    protected void addHelpers(final OrderedMap<Class, JsonStructureBuilderHelper> ordMap) {
        addHelper(ordMap, CharSequence.class, new CharSequenceBuilderHelper());
        addHelper(ordMap, JsonArrayBuilder.class, new JsonArrayBuilderBuilderHelper());
        addHelper(ordMap, JsonObjectBuilder.class, new JsonObjectBuilderBuilderHelper());
        addHelper(ordMap, JsonValue.class, new JsonValueBuilderHelper());
        addHelper(ordMap, Boolean.class, new BooleanBuilderHelper());
        addCustomHelpers(ordMap);
        addHelper(ordMap, Number.class, new NumberBuilderHelper());
        addHelper(ordMap, Iterable.class, new ToIteratorBuilderHelper());
        addHelper(ordMap, Iterator.class, new ToIteratorBuilderHelper());
        addHelper(ordMap, Map.class, new MapBuilderHelper());
        addHelper(ordMap, Map.Entry.class, new MapEntryBuilderHelper());
        addHelper(ordMap, Date.class, new ToStringBuilderHelper());
        addHelper(ordMap, Calendar.class, new CalendarBuilderHelper());
        addDefaultHelpers(ordMap);
    }
    
    /**
     * 
     * @param ordMap
     * @param clazz
     * @param converter 
     */
    protected void addHelper(final OrderedMap<Class, JsonStructureBuilderHelper> ordMap,
            final Class clazz, final JsonStructureBuilderHelper converter) {
        if (ordMap.containsKey(clazz)) {
            throw new IllegalArgumentException("Helper already present for " + clazz);
        }
        ordMap.put(clazz, converter);
    }

    /**
     * 
     */
    private OrderedMap<Class, JsonStructureBuilderHelper> helpers;
    
    /**
     * 
     * @param ordMap
     */
    protected void addCustomHelpers(final OrderedMap<Class, JsonStructureBuilderHelper> ordMap) {
    }
    
    /**
     * 
     * @param hs 
     */
    protected void addDefaultHelpers(final OrderedMap<Class, JsonStructureBuilderHelper> hs) {
        // addHelper(hs, Object.class, new ObjectBuilderHelper());
        addHelper(hs, Object.class, new ToStringBuilderHelper());
    }

    /**
     * 
     */
    class ToStringBuilderHelper implements JsonStructureBuilderHelper<Object> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final Object obj,
                final JsonStructureBuilderHelper caller) {
            final String value = obj.toString();
            builder.add(value);
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final Object obj,
                final JsonStructureBuilderHelper caller) {
            final String value = obj.toString();
            builder.add(name, value);
        }
        
        @Override
        public JsonStructure toJsonStructure(final Object obj,
                final JsonStructureBuilderHelper caller) {
            throw new UnsupportedOperationException("Object can't be converted to JsonStructure");
        }
        
    }

    /**
     * 
     */
    class CharSequenceBuilderHelper implements JsonStructureBuilderHelper<CharSequence> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final CharSequence cs,
                final JsonStructureBuilderHelper caller) {
            final String value = cs.toString();
            builder.add(value);
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final CharSequence cs,
                final JsonStructureBuilderHelper caller) {
            final String value = cs.toString();
            builder.add(name, value);
        }

        @Override
        public JsonStructure toJsonStructure(final CharSequence cs,
                final JsonStructureBuilderHelper caller) {
            throw new UnsupportedOperationException("CharSequence can't be converted to JsonStructure");
        }
        
    }
    
    /**
     * 
     */
    class BooleanBuilderHelper implements JsonStructureBuilderHelper<Boolean> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final Boolean value,
                final JsonStructureBuilderHelper caller) {
            builder.add(value);
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final Boolean value,
                final JsonStructureBuilderHelper caller) {
            builder.add(name, value);
        }

        @Override
        public JsonStructure toJsonStructure(final Boolean obj,
                final JsonStructureBuilderHelper caller) {
            throw new UnsupportedOperationException("Boolean can't be converted to JsonStructure");
        }
        
    }

    /**
     * 
     */
    class JsonArrayBuilderBuilderHelper implements JsonStructureBuilderHelper<JsonArrayBuilder> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final JsonArrayBuilder value,
                final JsonStructureBuilderHelper caller) {
            builder.add(value);
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final JsonArrayBuilder value,
                final JsonStructureBuilderHelper caller) {
            builder.add(name, value);
        }

        @Override
        public JsonStructure toJsonStructure(final JsonArrayBuilder value,
                final JsonStructureBuilderHelper caller) {
            return value.build();
        }
        
    }

    /**
     * 
     */
    class JsonValueBuilderHelper implements JsonStructureBuilderHelper<JsonValue> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final JsonValue value,
                final JsonStructureBuilderHelper caller) {
            builder.add(value);
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final JsonValue value,
                final JsonStructureBuilderHelper caller) {
            builder.add(name, value);
        }

        @Override
        public JsonStructure toJsonStructure(final JsonValue value,
                final JsonStructureBuilderHelper caller) {
            if (value instanceof JsonArray ja) {
                return ja;
            }
            if (value instanceof JsonObject jo) {
                return jo;
            }
            throw new UnsupportedOperationException("JsonValue can't be converted to JsonStructure");
        }
        
    }

    /**
     * 
     */
    class JsonObjectBuilderBuilderHelper implements JsonStructureBuilderHelper<JsonObjectBuilder> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final JsonObjectBuilder value,
                final JsonStructureBuilderHelper caller) {
            builder.add(value);
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final JsonObjectBuilder value,
                final JsonStructureBuilderHelper caller) {
            builder.add(name, value);
        }

        @Override
        public JsonStructure toJsonStructure(final JsonObjectBuilder builder,
                final JsonStructureBuilderHelper caller) {
            return builder.build();
        }
        
    }

    /**
     * 
     */
    class NumberBuilderHelper implements JsonStructureBuilderHelper<Number> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final Number value,
                final JsonStructureBuilderHelper caller) {
            if (value instanceof Long l) {
                builder.add(l);
            } else
            if (value instanceof Integer ||
                    value instanceof Short ||
                    value instanceof Byte) {
                builder.add(value.intValue());
            } else
            if (value instanceof Double ||
                    value instanceof Float) {
                builder.add(value.doubleValue());
            } else
            if (value instanceof BigInteger bi) {
                builder.add(bi);
            } else
            if (value instanceof BigDecimal bd) {
                builder.add(bd);
            } else {
                builder.add(value.doubleValue());
            }
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final Number value,
                final JsonStructureBuilderHelper caller) {
            if (value instanceof Long) {
                builder.add(name, value.longValue());
            } else
            if (value instanceof Integer ||
                    value instanceof Short ||
                    value instanceof Byte) {
                builder.add(name, value.intValue());
            } else
            if (value instanceof Double ||
                    value instanceof Float) {
                builder.add(name, value.doubleValue());
            } else
            if (value instanceof BigInteger) {
                builder.add(name, (BigInteger)value);
            } else
            if (value instanceof BigDecimal) {
                builder.add(name, (BigDecimal)value);
            } else {
                builder.add(name, value.doubleValue());
            }
        }

        @Override
        public JsonStructure toJsonStructure(final Number value,
                final JsonStructureBuilderHelper caller) {
            throw new UnsupportedOperationException("Number can't be converted to JsonStructure");
        }
        
    }
    
    /**
     * 
     */
    class CalendarBuilderHelper implements JsonStructureBuilderHelper<Calendar> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final Calendar cal,
                final JsonStructureBuilderHelper caller) {
            final java.sql.Timestamp ts = new java.sql.Timestamp(cal.getTimeInMillis());
            builder.add(ts.toString());
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final Calendar cal,
                final JsonStructureBuilderHelper caller) {
            final java.sql.Timestamp ts = new java.sql.Timestamp(cal.getTimeInMillis());
            builder.add(name, ts.toString());
        }

        @Override
        public JsonStructure toJsonStructure(final Calendar value,
                final JsonStructureBuilderHelper caller) {
            throw new UnsupportedOperationException("Calendar can't be converted to JsonStructure");
        }
        
    }

    /**
     * 
     */ 
    class MapBuilderHelper implements JsonStructureBuilderHelper<Map> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final Map value,
                final JsonStructureBuilderHelper caller) {
            final JsonObjectBuilder obj = Json.createObjectBuilder();
            for (final Object o : value.entrySet()) {
                final Map.Entry e = (Map.Entry)o;
                caller.add(obj, e.getKey().toString(), e.getValue(), null);
            }
            builder.add(obj);
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final Map value,
                final JsonStructureBuilderHelper caller) {
            builder.add(name, toJsonStructure(value, caller));
        }

        @Override
        public JsonStructure toJsonStructure(final Map val, 
                final JsonStructureBuilderHelper caller) {
            final JsonObjectBuilder builder = Json.createObjectBuilder();
            for (final Object o : val.entrySet()) {
                final Map.Entry e = (Map.Entry)o;
                caller.add(builder, e.getKey().toString(), e.getValue(), null);
            }
            return builder.build();
        }
        
    } 

    /**
     * 
     * @param o
     * @return 
     */
    protected static Map toMap(final Object o) {
        try {
            final Map m = BeanAccessors.of(o).toJava().linkedMap();
            return m;
        } catch (final Exception e) {
            return ExceptionUtils.wrapAndThrow(e);
        }
    }
    
    class ObjectBuilderHelper implements JsonStructureBuilderHelper<Object> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final Object value,
                final JsonStructureBuilderHelper caller) {
            final Map val = toMap(value);
            final JsonObjectBuilder obj = Json.createObjectBuilder();
            for (final Object o : val.entrySet()) {
                final Map.Entry e = (Map.Entry)o;
                caller.add(obj, e.getKey().toString(), e.getValue(), null);
            }
            builder.add(obj);
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final Object value,
                final JsonStructureBuilderHelper caller) {
            builder.add(name, toJsonStructure(value, caller));
        }

        @Override
        public JsonStructure toJsonStructure(final Object obj, 
                final JsonStructureBuilderHelper caller) {
            final Map val = toMap(obj);
            final JsonObjectBuilder builder = Json.createObjectBuilder();
            for (final Object o : val.entrySet()) {
                final Map.Entry e = (Map.Entry)o;
                caller.add(builder, e.getKey().toString(), e.getValue(), null);
            }
            return builder.build();
        }
        
    } 

    /**
     * 
     */
    class MapEntryBuilderHelper implements JsonStructureBuilderHelper<Map.Entry> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final Map.Entry entry,
                final JsonStructureBuilderHelper caller) {
            builder.add(toJsonStructure(entry, caller));
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final Map.Entry entry,
                final JsonStructureBuilderHelper caller) {
            builder.add(name, toJsonStructure(entry, caller));
        }

        @Override
        public JsonStructure toJsonStructure(final Map.Entry entry, 
                final JsonStructureBuilderHelper caller) {
            final JsonObjectBuilder builder = Json.createObjectBuilder();
            caller.add(builder, entry.getKey().toString(), entry.getValue(), null);
            return builder.build();
        }
        
    }

    /**
     * 
     */
    class IterableBuilderHelper implements JsonStructureBuilderHelper<Iterable> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final Iterable value,
                final JsonStructureBuilderHelper caller) {
            final Iterator itr = value.iterator();
            while (itr.hasNext()) {
                final Object val = itr.next();
                caller.add(builder, val, null);
            }
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final Iterable value,
                final JsonStructureBuilderHelper caller) {
            final Iterator itr = value.iterator();
            final JsonArrayBuilder obj = Json.createArrayBuilder();
            while (itr.hasNext()) {
                final Object val = itr.next();
                caller.add(obj, val, null);
            }
            builder.add(name, obj);
        }

        @Override
        public JsonStructure toJsonStructure(final Iterable obj, 
                final JsonStructureBuilderHelper caller) {
            final JsonArrayBuilder builder = Json.createArrayBuilder();
            add(builder, obj, caller);
            return builder.build();
        }
        
    }

    /**
     * 
     */
    class ToIteratorBuilderHelper implements JsonStructureBuilderHelper<Object> {

        @Override
        public void add(final JsonArrayBuilder builder, 
                final Object value,
                final JsonStructureBuilderHelper caller) {
            final Iterator itr = IteratorUtils.getIterator(value);
            while (itr.hasNext()) {
                final Object val = itr.next();
                caller.add(builder, val, null);
            }
        }

        @Override
        public void add(final JsonObjectBuilder builder, 
                final String name, 
                final Object value,
                final JsonStructureBuilderHelper caller) {
            final Iterator itr = IteratorUtils.getIterator(value);
            final JsonArrayBuilder obj = Json.createArrayBuilder();
            while (itr.hasNext()) {
                final Object val = itr.next();
                caller.add(obj, val, null);
            }
            builder.add(name, obj);
        }

        @Override
        public JsonStructure toJsonStructure(final Object obj, 
                final JsonStructureBuilderHelper caller) {
            final JsonArrayBuilder builder = Json.createArrayBuilder();
            add(builder, obj, caller);
            return builder.build();
        }
        
    }
    
}
