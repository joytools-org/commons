/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this text file, choose Tools | Templates
 * and open the text in the editor.
 */
package org.joytools.commons.text;

import io.vavr.Value;
import java.util.Objects;
import java.util.function.Function;
import org.apache.commons.text.StringSubstitutor;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.mappers.Mappers;
import org.joytools.commons.text.PropertyString.Formatter;

/**
 *
 * @author AndreaR
 */
public class PropertyStringBuilder { // implements Supplier<PropertyString> {
    
    private final static String FMT_OPTS_SEP = "|";

    /**
     * 
     * @param start
     * @param end
     * @return 
     */
    public PropertyStringBuilder delimiters(final CharSequence start, final CharSequence end) {
        final String varStart = StringUtils.trimToNull(StringUtils.toString(start));
        final String varEnd = StringUtils.trimToNull(StringUtils.toString(end));
        if (StringUtils.isEmpty(varStart)) {
            throw new IllegalArgumentException("Variable prefix must not be empty");
        }
        if (StringUtils.isEmpty(varEnd)) {
            throw new IllegalArgumentException("Variable suffix must not be empty");
        }
        this.varStart = varStart;
        this.varEnd = varEnd;
        return this;
    }
    
    /**
     * 
     * @param <T>
     * @param <V>
     * @param resolver
     * @return 
     */
    public <T, V extends Value<T>> PropertyStringBuilder resolver(final Function<String, V> resolver) {
        this.resolver = Resolvers.toObjectResolver(resolver);
        return this;
    }

    /**
     * 
     * @param <T>
     * @param acc
     * @return 
     */
    public <T> PropertyStringBuilder resolver(final MapAccessor<String, T> acc) {
        this.resolver = Resolvers.toObjectResolver(Mappers.forAccessor(acc));
        return this;
    }

    /**
     * 
     * @param map
     * @return 
     */
    public <T> PropertyStringBuilder resolver(final java.util.Map<String, T> map) {
        this.resolver = Resolvers.toObjectResolver(Mappers.forMap(map));
        return this;
    }

    /**
     * 
     * @param map
     * @return 
     */
    public <T> PropertyStringBuilder resolver(final io.vavr.collection.Map<String, T> map) {
        this.resolver = Resolvers.toObjectResolver(Mappers.forMap(map));
        return this;
    }

    /**
     * 
     * @param <T>
     * @param <V>
     * @param rawResolver
     * @return 
     */
    public <T, V extends Value<T>> PropertyStringBuilder rawResolver(final Function<String, V> rawResolver) {
        this.rawResolver = Resolvers.toObjectResolver(rawResolver);
        return this;
    }

    /**
     * 
     * @param formatter
     * @return 
     */
    public PropertyStringBuilder formatter(final Formatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "Formatter");
        return this;
    }

    /**
     * 
     * @param escapeMode
     * @return 
     */
    public PropertyStringBuilder escape(final PropertyString.EscapeMode escapeMode) {
        this.escapeMode = Objects.requireNonNull(escapeMode, "Escape Mode");
        return this;
    }

    /**
     * 
     * @param text
     * @return 
     */
    public PropertyStringBuilder text(final CharSequence text) {
        this.text = StringUtils.toEmptyString(text);
        return this;
    }
    
    /**
     * 
     * @return 
     */
    public PropertyStringBuilder memoized() {
        this.memoized = true;
        return this;
    }

    /**
     * 
     * @return 
     */
    public static PropertyStringBuilder of() {
        return new PropertyStringBuilder();
    }
    
    /**
     * 
     * @param text
     * @return 
     */
    public static PropertyStringBuilder of(final CharSequence text) {
        return new PropertyStringBuilder().text(text);
    }

    /**
     * 
     * @param elements
     * @return 
     */
    private static String[] toNormalizedElements(final CharSequence... elements) {
        final String[] arr = new String[elements.length];
        for (int i = 0; i < elements.length; i++) {
            String s = StringUtils.toString(elements[i]);
            if (i % 2 == 0) {
                if (StringUtils.startsWith(s, StringSubstitutor.DEFAULT_VAR_START)
                        && StringUtils.endsWith(s, StringSubstitutor.DEFAULT_VAR_END)) {
                    s = StringUtils.slice(s, 2, -1);
                }
            }
            arr[i] = s;
        }
        return arr;
    }

    /**
     * 
     * @param elements
     * @return 
     */
    public PropertyStringBuilder resolver(final CharSequence... elements) {
        this.resolver = Resolvers.toObjectResolver(Mappers.forMap(toNormalizedElements(elements)));
        return this;
    }

    /**
     * 
     * @param elements
     * @return 
     */
    public PropertyStringBuilder resolverIgnoreCase(final CharSequence... elements) {
        this.resolver = Resolvers.toObjectResolver(Mappers.forMapIgnoreCase(toNormalizedElements(elements)));
        return this;
    }

    
    /**
     * 
     * @param text
     * @param elements
     * @return 
     */
    public static PropertyStringBuilder of(final CharSequence text, final CharSequence... elements) {
        return PropertyStringBuilder.of(text).resolver(elements);
    }

    /**
     * 
     * @param text
     * @param elements
     * @return 
     */
    public static PropertyStringBuilder ofIgnoreCase(final CharSequence text, final CharSequence... elements) {
        return PropertyStringBuilder.of(text).resolverIgnoreCase(elements);
    }

    /**
     * 
     * @return 
     */
    public PropertyString build() {
        if (conf == null) {
            conf = new PropertyString.Configuration() {
                @Override
                public PropertyString.EscapeMode escapeMode() {
                    return escapeMode;
                }

                @Override
                public String text() {
                    return StringUtils.toEmptyString(text);
                }

                @Override
                public String variablePrefix() {
                    return varStart;
                }

                @Override
                public String variableSuffix() {
                    return varEnd;
                }

                @Override
                public String valueDelimiter() {
                    return varDefault;
                }

                @Override
                public String formatDelimiter() {
                    return fmtOptsSep;
                }

                @Override
                public char escapeChar() {
                    return escapeChar;
                }

                @Override
                public boolean memoized() {
                    return memoized;
                }

                @Override
                public Function<String, Value<Object>> resolver() {
                    return resolver;
                }
                
                @Override
                public Function<String, Value<Object>> rawResolver() {
                    return rawResolver;
                }

                @Override
                public Formatter formatter() {
                    return formatter;
                }
            };
        }
        return new PropertyString(conf);
    }
    
    private String fmtOptsSep = FMT_OPTS_SEP;
    
    private String varStart = StringSubstitutor.DEFAULT_VAR_START;
    
    private String varEnd = StringSubstitutor.DEFAULT_VAR_END;
    
    private String varDefault = StringSubstitutor.DEFAULT_VAR_DEFAULT;

    private char escapeChar = StringSubstitutor.DEFAULT_ESCAPE;
            
    private boolean memoized = false;

    private String text = StringUtils.EMPTY;
    
    private Function<String, Value<Object>> resolver = null;
    
    private Function<String, Value<Object>> rawResolver = null;

    private Formatter formatter = PropertyStrings.defaultFormatter();

    private PropertyString.EscapeMode escapeMode = PropertyString.EscapeMode.NONE;
    
    private PropertyString.Configuration conf = null;

}
