/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.json;

import io.vavr.Tuple2;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;

import javax.json.*;
import javax.json.JsonValue.ValueType;
import static javax.json.JsonValue.ValueType.FALSE;
import static javax.json.JsonValue.ValueType.STRING;
import static javax.json.JsonValue.ValueType.TRUE;
import javax.json.stream.JsonGenerator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.collections4.KeyValue;
import org.joytools.commons.concurrent.AtomicSuppliers;
import org.joytools.commons.interop.Vavr;

import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.IgnoreCaseAccessorMethods;
import org.joytools.commons.text.TextLinesBuffer;

/**
 *
 * @author AndreaR
 */
public class JsonUtils {
 
    /**
     * 
     */
    public JsonUtils() {
    }

    private final static String DEFAULT_CHARSET_NAME = StandardCharsets.UTF_8.displayName();
    
    /*
    private static JsonWriterFactory PRETTY_JSON_WRITER_FACTORY = null;
            
    static {
        final Map<String, Boolean> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, Boolean.TRUE);
        PRETTY_JSON_WRITER_FACTORY = Json.createWriterFactory(config);
    } */
    
    private final static Supplier<JsonWriterFactory> PRETTY_JSON_WRITER_FACTORY = AtomicSuppliers.of(
            () -> {
                final Map<String, Boolean> config = new HashMap<>();
                config.put(JsonGenerator.PRETTY_PRINTING, Boolean.TRUE);
                return Json.createWriterFactory(config);
            });
    
    /**
     * 
     * @param json
     * @return 
     */
    public static String toPrettyString(final JsonStructure json) {
        return toPrettyStringBuffer(json).toString();
    }

    /**
     * 
     * @param json
     * @return 
     */
    public static StringBuffer toPrettyStringBuffer(final JsonStructure json) {
        if (json == null) {
            return new StringBuffer();
        }
        final StringWriter sw = new StringWriter();
        try (final JsonWriter jsonWriter = PRETTY_JSON_WRITER_FACTORY.get().createWriter(sw)) {
            jsonWriter.write(json);
        }
        return sw.getBuffer();
    }

    /**
     * 
     * @param json
     * @return 
     */
    public static io.vavr.collection.Vector<String> toPrettyStringLines(final JsonStructure json) {
        final StringBuffer buf = toPrettyStringBuffer(json);
        return TextLinesBuffer.of(buf).to(Vavr.vector());
    }
    
    /**
     * 
     * @param s
     * @return 
     */
    public static JsonStructure read(final CharSequence s) {
        return read(s, null);
    }

    /**
     * 
     * @param s
     * @param encoding
     * @return 
     */
    public static JsonStructure read(final CharSequence s, final CharSequence encoding) {
        try (final InputStream in = IOUtils.toInputStream(s, 
                encoding == null ? DEFAULT_CHARSET_NAME : encoding.toString());
                final JsonReader reader = Json.createReader(in)) {
            return reader.read();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /**
     * 
     * @param s
     * @return 
     */
    public static JsonArray readArray(final CharSequence s) {
        return readArray(s, null);
    }
    
    /**
     * 
     * @param s
     * @param encoding
     * @return 
     */
    public static JsonArray readArray(final CharSequence s, final CharSequence encoding) {
        try (final InputStream in = IOUtils.toInputStream(s, 
                encoding == null ? DEFAULT_CHARSET_NAME : encoding.toString());
                final JsonReader reader = Json.createReader(in)) {
            return reader.readArray();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        } 
    }

    /**
     * 
     * @param s
     * @return 
     */
    public static JsonObject readObject(final CharSequence s) {
        return readObject(s, null);
    }
    
    /**
     * 
     * @param s
     * @param encoding
     * @return 
     */
    public static JsonObject readObject(final CharSequence s, final CharSequence encoding) {
        try (final InputStream in = IOUtils.toInputStream(s,
                encoding == null ? DEFAULT_CHARSET_NAME : encoding.toString());
                final JsonReader reader = Json.createReader(in)) {
            return reader.readObject();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        } 
    }
    
    /**
     * 
     * @param obj
     * @param name
     * @return 
     */
    public static JsonObject getMandatoryJsonObject(final JsonObject obj, final String name) {
        final JsonObject arr = nonNull(obj).getJsonObject(name);
        if (arr == null) {
            throw new IllegalArgumentException("Mandatory JsonObject " + name + " not found");
        }
        return arr;
    }

    /**
     * 
     * @param obj
     * @param name
     * @return 
     */
    public static JsonArray getMandatoryJsonArray(final JsonObject obj, final String name) {
        final JsonArray arr = nonNull(obj).getJsonArray(name);
        if (arr == null) {
            throw new IllegalArgumentException("Mandatory JsonArray " + name + " not found");
        }
        return arr;
    }

    /**
     * 
     * @param obj
     * @param name
     * @return 
     */
    public static JsonValue getMandatoryJsonValue(final JsonObject obj, final String name) {
        final JsonValue arr = nonNull(obj).get(name);
        if (arr == null) {
            throw new IllegalArgumentException("Mandatory JsonValue " + name + " not found");
        }
        return arr;
    }

    /**
     * 
     * @param obj
     * @param name
     * @return 
     */
    public static JsonArray getNonEmptyJsonArray(final JsonObject obj, final String name) {
        final JsonArray arr = getMandatoryJsonArray(obj, name);
        if (arr.isEmpty()) {
            throw new IllegalArgumentException("JsonArray " + name + " is empty");
        }
        return arr;
    }

    /**
     * 
     * @param obj
     * @param name
     * @return 
     */
    public static JsonValue getJsonValue(final JsonObject obj, final String name) {
        final JsonValue v = AccessorMethods.getOrNull(obj, name);
        return v == null ? JsonValue.NULL : v;
    }

    /**
     * 
     * @param obj
     * @param name
     * @return 
     */
    public static JsonValue getJsonValueIgnoreCase(final JsonObject obj, final String name) {
        final JsonValue v = IgnoreCaseAccessorMethods.getOrNull(obj, name);
        return v == null ? JsonValue.NULL : v;
    }

    /**
     * 
     * @param obj
     * @param name
     * @return 
     */
    public static String getString(final JsonObject obj, final String name) {
        return getString(getJsonValue(obj, name));
    }
    
    /**
     * 
     * @param v
     * @return 
     */
    public static String getString(final JsonValue v) {
        if (v == JsonValue.NULL) {
            return null;
        }
        final ValueType type = v.getValueType();
        switch (type) {
            case NULL:
                return null;
            case STRING:
                return JsonString.class.cast(v).getChars().toString();
            case NUMBER:
                final JsonNumber n = JsonNumber.class.cast(v);
                if (n.isIntegral()) {
                    return String.valueOf(n.longValue());
                } else {
                    return String.valueOf(n.doubleValue());
                }
            case TRUE:
                return Boolean.TRUE.toString();
            case FALSE:
                return Boolean.FALSE.toString();
        }
        throw new UnsupportedOperationException("Could not convert to a string: " + type);
    }

    /**
     * 
     * @param obj
     * @param name
     * @return 
     */
    public static String getStringIgnoreCase(final JsonObject obj, final String name) {
        final JsonValue v = getJsonValueIgnoreCase(obj, name);
        if (v == JsonValue.NULL) {
            return null;
        }
        return JsonString.class.cast(v).getChars().toString();
    }
    
    /**
     * 
     * @param <T>
     * @param obj
     * @return 
     */
    protected static <T extends JsonStructure> T nonNull(final T obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Json structure is null");
        }
        return obj;
    }

    /**
     * 
     * @param m
     * @return 
     */
    public static JsonObject toJsonObject(final Map m) {
        if (m == null) {
            return null;
        }
        final JsonObjectBuilder builder = toJsonObjectBuilder(m);
        return builder.build();
    }
    
    /**
     * 
     * @param m
     * @return 
     */
    public static JsonObject toJsonObject(final io.vavr.collection.Map m) {
        if (m == null) {
            return null;
        }
        final JsonObjectBuilder builder = toJsonObjectBuilder(m);
        return builder.build();
    }

    /**
     * 
     * @param e
     * @return 
     */
    public static JsonObject toJsonObject(final Map.Entry e) {
        if (e == null) {
            return null;
        }
        final JsonObjectBuilder builder = toJsonObjectBuilder(e);
        return builder.build();
    }

    /**
     * 
     * @param t
     * @return 
     */
    public static JsonObject toJsonObject(final Tuple2 t) {
        if (t == null) {
            return null;
        }
        final JsonObjectBuilder builder = toJsonObjectBuilder(t);
        return builder.build();
    }

    /**
     * 
     * @param dest
     * @param value 
     */
    public static void add(final JsonObjectBuilder dest, 
            final Object value) {
        if (dest == null ||
                value == null) {
            return;
        }
        if (value instanceof Tuple2 val) {
            add(dest, val._1().toString(), val._2());
            return;
        }
        if (value instanceof Map.Entry val) {
            add(dest, val.getKey().toString(), val.getValue());
            return;
        }
        if (value instanceof KeyValue val) {
            add(dest, val.getKey().toString(), val.getValue());
            return;
        }
        throw new UnsupportedOperationException(value.getClass().getName());
    }
    
    /**
     * 
     * @param dest
     * @param name
     * @param value 
     */
    public static void add(final JsonObjectBuilder dest, 
            final String name,
            final Object value) {
        if (dest == null) {
            return;
        }
        if (value == null) {
            dest.addNull(name);
            return;
        }
        if (value instanceof CharSequence) {
            final String val = value.toString();
            dest.add(name, val);
            return;
        }
        if (value instanceof Iterable itr) {
            dest.add(name, toJsonArrayBuilder(itr.iterator()));
            return;
        }
        if (value instanceof Map map) {
            dest.add(name, toJsonObjectBuilder(map));
            return;
        }
        if (value instanceof io.vavr.collection.Map map) {
            dest.add(name, toJsonObjectBuilder(map));
            return;
        }
        if (value instanceof Boolean val) {
            dest.add(name, val);
            return;
        }
        if (value instanceof Date) {
            dest.add(name, value.toString());
            return;
        }
        if (value instanceof Number val) {
            if (value instanceof Long) {
                dest.add(name, val.longValue());
            } else
            if (value instanceof Integer ||
                    value instanceof Short ||
                    value instanceof Byte) {
                dest.add(name, val.intValue());
            } else
            if (value instanceof Double ||
                    value instanceof Float) {
                dest.add(name, val.doubleValue());
            } else
            if (value instanceof BigInteger bi) {
                dest.add(name, bi);
            } else
            if (value instanceof BigDecimal bd) {
                dest.add(name, bd);
            } else {
                dest.add(name, val.doubleValue());
            }
            return;
        }
        if (value instanceof Date) {
            dest.add(name, value.toString());
            return;
        }
        if (value instanceof Object[] val) {
            dest.add(name, toJsonArrayBuilder(val));
            return;
        }
        if (value instanceof Iterator itr) {
            dest.add(name, toJsonArrayBuilder(itr));
            return;
        }
        if (value instanceof JsonObjectBuilder job) {
            dest.add(name, job);
            return;
        }
        if (value instanceof JsonArrayBuilder jab) {
            dest.add(name, jab);
            return;
        }
        if (value instanceof JsonValue val) {
            dest.add(name, val);
            return;
        }
        throw new UnsupportedOperationException(value.getClass().getName());
    }

    /**
     * 
     * @param dest
     * @param value 
     */
    public static void add(final JsonArrayBuilder dest,
            final Object value) {
        if (dest == null) {
            return;
        }
        if (value == null) {
            dest.addNull();
            return;
        }
        if (value instanceof CharSequence) {
            final String val = value.toString();
            dest.add(val);
            return;
        }
        if (value instanceof Iterable itr) {
            dest.add(toJsonArrayBuilder(itr.iterator()));
            return;
        }
        if (value instanceof Map map) {
            dest.add(toJsonObjectBuilder(map));
            return;
        }
        if (value instanceof io.vavr.collection.Map map) {
            dest.add(toJsonObjectBuilder(map));
            return;
        }
        if (value instanceof Tuple2 val) {
            dest.add(toJsonObjectBuilder(val));
            return;
        }
        if (value instanceof Map.Entry val) {
            dest.add(toJsonObjectBuilder(val));
            return;
        }
        if (value instanceof Boolean val) {
            dest.add(val);
            return;
        }
        if (value instanceof Number val) {
            if (value instanceof Long) {
                dest.add(val.longValue());
            } else
            if (value instanceof Integer ||
                    value instanceof Short ||
                    value instanceof Byte) {
                dest.add(val.intValue());
            } else
            if (value instanceof Double ||
                    value instanceof Float) {
                dest.add(val.doubleValue());
            } else
            if (value instanceof BigInteger bi) {
                dest.add(bi);
            } else
            if (value instanceof BigDecimal bd) {
                dest.add(bd);
            } else {
                dest.add(val.doubleValue());
            }
            return;
        }
        if (value instanceof Object[] val) {
            dest.add(toJsonArrayBuilder(val));
            return;
        }
        if (value instanceof Iterator val) {
            dest.add(toJsonArrayBuilder(val));
            return;
        }
        if (value instanceof JsonObjectBuilder val) {
            dest.add(val);
            return;
        }
        if (value instanceof JsonArrayBuilder val) {
            dest.add(val);
            return;
        }
        if (value instanceof JsonValue val) {
            dest.add(val);
            return;
        }
        throw new UnsupportedOperationException(value.getClass().getName());
    }

    /**
     * 
     * @param m
     * @return 
     */
    public static JsonObjectBuilder toJsonObjectBuilder(final Map m) {
        if (m == null) {
            throw new IllegalArgumentException("Map is null");
        }
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        for (final Object o : m.entrySet()) {
            final Map.Entry e = (Map.Entry)o;
            add(builder, e);
        }
        return builder;
    }

    /**
     * 
     * @param m
     * @return 
     */
    public static JsonObjectBuilder toJsonObjectBuilder(final io.vavr.collection.Map m) {
        if (m == null) {
            throw new IllegalArgumentException("Map is null");
        }
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        for (final Object o : m) {
            final Tuple2 e = (Tuple2)o;
            add(builder, e);
        }
        return builder;
    }

    /**
     * 
     * @param e
     * @return 
     */
    public static JsonObjectBuilder toJsonObjectBuilder(final Map.Entry e) {
        if (e == null) {
            throw new IllegalArgumentException("Map entry is null");
        }
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        add(builder, e);
        return builder;
    }

    /**
     * 
     * @param t
     * @return 
     */
    public static JsonObjectBuilder toJsonObjectBuilder(final Tuple2 t) {
        if (t == null) {
            throw new IllegalArgumentException("Tuple is null");
        }
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        add(builder, t);
        return builder;
    }

    /**
     * 
     * @param itr
     * @return 
     */
    public static JsonArrayBuilder toJsonArrayBuilder(final Iterable itr) {
        if (itr == null) {
            throw new IllegalArgumentException("Iterable is null");
        }
        return toJsonArrayBuilder(itr.iterator());
    }

    /**
     * 
     * @param itr
     * @return 
     */
    public static JsonArrayBuilder toJsonArrayBuilder(final Iterator itr) {
        if (itr == null) {
            throw new IllegalArgumentException("Iterator is null");
        }
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        while (itr.hasNext()) {
            final Object value = itr.next();
            add(builder, value);
        }
        return builder;
    }
    
    /**
     * 
     * @param arr
     * @return 
     */
    public static JsonArrayBuilder toJsonArrayBuilder(final Object[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final Object item : arr) {
            add(builder, item);
        }
        return builder;
    }

    /**
     * 
     * @param itr
     * @return 
     */
    public static JsonArray toJsonArray(final Iterable itr) {
        if (itr == null) {
            return null;
        }
        return toJsonArray(itr.iterator());
    }
    
    /**
     * 
     * @param itr
     * @return 
     */
    public static JsonArray toJsonArray(final Iterator itr) {
        if (itr == null) {
            return null;
        }
        final JsonArrayBuilder ab = toJsonArrayBuilder(itr);
        return ab.build();
    }

    /**
     * 
     * @param arr
     * @return 
     */
    public static JsonArray toJsonArray(final Object[] arr) {
        if (arr == null) {
            return null;
        }
        final JsonArrayBuilder ab = toJsonArrayBuilder(arr);
        return ab.build();
    }
    
}
