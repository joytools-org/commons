/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.text;

import static io.vavr.API.Left;
import static io.vavr.API.None;
import static io.vavr.API.Right;
import io.vavr.Function0;
import io.vavr.Value;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.set.ListOrderedSet;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookup;
import org.joytools.commons.collections.IterableAccessors;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.lang.CaseSpaceNormalizer;
import org.joytools.commons.lang.EnumLookup;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class PropertyString implements Supplier<String> { 

// implements CharSequence {

    /**
     * 
     */
    public interface Configuration {
        
        public EscapeMode escapeMode();
        
        public String text();
        
        public String variablePrefix();
        
        public String variableSuffix();
        
        public String valueDelimiter();

        public String formatDelimiter();
        
        public char escapeChar();
        
        public boolean memoized();
        
        public Function<String, Value<Object>> resolver();
        
        public Function<String, Value<Object>> rawResolver();

        public Formatter formatter();

    }
    
    
    
    /**
     * 
     */
    public enum EscapeMode {

        NONE(0, StringUtils::toString, "DEFAULT", "NOESCAPE"),
        HTML(1, StringEscapeUtils.ESCAPE_HTML4_FUNCTION),
        XML(2, StringEscapeUtils.ESCAPE_XML_FUNCTION),
        SQL(3, StringEscapeUtils.ESCAPE_SQL_FUNCTION),
        URL(4, StringEscapeUtils.ESCAPE_URL_FUNCTION),
        JSON(5, StringEscapeUtils.ESCAPE_JSON_FUNCTION),
        CSV(6, StringEscapeUtils.ESCAPE_CSV_FUNCTION);

        /**
         * 
         * @param value
         * @param names 
         */
        EscapeMode(final int value,
                final Function<? super String, String> escaper,
                final String... names) {
            this.escaper = Objects.requireNonNull(escaper, "Escape Function");
            this.value = value;
            this.names = names;
        }

        /**
         * 
         * @param name
         * @return 
         */
        public static EscapeMode of(final String name) {
            return LOOKUP.getExisting(name);
        }

        /**
         * 
         * @param value
         * @return 
         */
        public static EscapeMode of(final int value) {
            for (final EscapeMode t : values()) {
                if (t.value == value) {
                    return t;
                }
            }
            throw new IllegalArgumentException("No escape mode found with value: " + value);
        }

        /**
         * 
         * @return 
         */
        public int value() {
            return value;
        }

        /**
         * 
         */
        private final static EnumLookup<String, EscapeMode> LOOKUP = new EnumLookup<String, EscapeMode>(
            values(),
            CaseSpaceNormalizer.UPPERCASE_TRIM.castAsString()) {

            @Override
            protected Iterable<String> keysOf(final EscapeMode item) {
                final Set<String> keys = new HashSet();
                keys.add("." + item.name()); // .sql, .html, .json, etc
                for (final String n : IterableAccessors.from(item.name()).append(item.names)) {
                    keys.addAll(expand(n));
                }
                return keys;
            }
        };

        /**
         * 
         * @return 
         */
        public Function<? super String, String> escaper() {
            return escaper;
        }

        private final Function<? super String, String> escaper;

        /**
         * 
         */
        private final int value;

        /**
         * 
         */
        private final String[] names;

    }

    /**
     * 
     * @param conf 
     */
    PropertyString(final Configuration conf) {
        this.conf = Objects.requireNonNull(conf, "Property String Configuration");
        this.textCache = conf.text();
        final Function0<Either<PropertyString, String>> reducer0 = this::reduce0;
        this.reducer = conf.memoized() ? reducer0.memoized() : reducer0;
    }
    
    /**
     * 
     * @return 
     */
    public String resolve() {
        final Either<PropertyString, String> result = reducer.get();
        if (result.isLeft()) {
            throw new IllegalArgumentException("Could not resolve the following properties: " + result.getLeft().propertySet());
        }
        return result.get();
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String get() {
        return resolve();
    }

    /**
     * 
     * @param text
     * @return 
     */
    public String resolve(final CharSequence text) {
        return buildPropertyString(text).resolve();
    }

    /**
     * 
     * @return 
     */
    public PropertyString reduce() {
        final Either<PropertyString, String> reduced = reducer.get();
        if (reduced.isLeft()) {
            return reduced.getLeft();
        }
        return buildPropertyString(reduced.get());
    }

    /**
     * 
     * @param text
     * @return 
     */
    public PropertyString reduce(final CharSequence text) {
        return buildPropertyString(text).reduce();
    }

    /**
     * 
     * @param unresolved
     * @return 
     */
    protected StringLookup buildStringLookup(final List<String> unresolved) {
        final Formatter fmt = Objects.requireNonNull(conf.formatter(), "Formatter");
        return input -> {
            final String fullPropName = StringUtils.toString(input);
            
            // Try to resolve the full property name, no formatter will be involved
            String resolved = StringLookups.toLookupString(fullPropName, conf.rawResolver());
            if (resolved != null) {
                return resolved;
            }
            
            // Try to resolve the formattable property name
            final Map.Entry<String, List<String>> propEntry = propertyEntry(fullPropName);
            final String propName = propEntry.getKey();
            final List<String> fmtOpts = propEntry.getValue();
            resolved = StringLookups.toLookupString(propName, conf.resolver());
            String formatted = null;
            if (resolved == null || 
                    (formatted = fmt.format(resolved, conf.escapeMode(), fmtOpts)) == null) {
                unresolved.add(input);
                return null;
            }
            return formatted;
        };
    }

    /**
     * 
     * @param propName
     * @param resolver
     * @return 
     */
    @Deprecated
    static String old_resolve0(final String propName, final Function<String, ?> resolver) {
        return StringLookups.toLookupString(propName, resolver);
        /* if (resolver == null) {
            return null;
        }
        final Object resolved = resolver.apply(propName);
        return StringLookups.toLookupString(resolved); */
    }

    /**
     * 
     * @param propName
     * @param resolver
     * @return 
     */
    @Deprecated
    static <T> Option<String> old_resolve0AsOptionString(final String propName, final Function<String, Option<T>> resolver) {
        if (resolver == null) {
            return None();
        }
        final Option<T> resolved = resolver.apply(propName);
        // resolved.isEmpty() ? None() : Some(StringUtils.toString(resolved.get()));
        return Resolvers.toResolvedString(resolved); 
    }

    /**
     * 
     * @return 
     */
    public ListOrderedSet<String> rawPropertySet() {
        final ListOrderedSet<String> props = new ListOrderedSet();
        props.addAll(buildRawPropertyList(textCache));
        return props;
    }

    /**
     * 
     * @param ignoreCase
     * @return 
     */
    public ListOrderedSet<String> propertySet(final boolean ignoreCase) {
        return ignoreCase ? propertySetIgnoreCase() : propertySet();
    }

    /**
     * 
     * @return 
     */
    public ListOrderedSet<String> propertySet() {
        final ListOrderedSet<String> props = new ListOrderedSet();
        buildRawPropertyList(textCache)
                .stream()
                .map((s) -> propertyEntry(s).getKey())
                .forEach(props::add);
        return props;
    }

    /**
     * 
     * @return 
     */
    public ListOrderedSet<String> propertySetIgnoreCase() {
        final Set<String> caseInsensitive = CaseSensitivity.INSENSITIVE.newSortedSet();
        final ListOrderedSet<String> result = new ListOrderedSet();
        propertySet().forEach((p) -> {
            if (caseInsensitive.add(p)) {
                result.add(p);
            }
        });
        return result;
    }

    /**
     * 
     * @param rawPropName
     * @return 
     */
    protected Map.Entry<String, List<String>> propertyEntry(final String rawPropName) {
        final Iterator<String> itr = IterableAccessors.from(
                StringUtils.splitPreserveAllTokens(rawPropName, conf.formatDelimiter())).iterator();
        final String propName = itr.next();
        final List<String> fmtOpts = new ArrayList();
        while (itr.hasNext()) {
            fmtOpts.add(itr.next().toLowerCase());
        }
        return new Map.Entry<String, List<String>>() {
            @Override
            public String getKey() {
                return propName;
            }

            @Override
            public List<String> getValue() {
                return fmtOpts;
            }

            @Override
            public List<String> setValue(final List<String> value) {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    /**
     * 
     * @return 
     */
    protected Either<PropertyString, String> reduce0() {
        final List<String> currUnresolved = new ArrayList();
        final List<String> prevUnresolved = new ArrayList();
        final StringLookup resolver = buildStringLookup(currUnresolved);
        final StringSubstitutor ss = buildStringSubstitutor();
        ss.setVariableResolver(resolver);
        String text = textCache;
        while (true) {
            text = ss.replace(text);
            if (currUnresolved.isEmpty()) {
                return Right(text);
            }
            if (ListUtils.isEqualList(currUnresolved, prevUnresolved)) {
                return Left(buildPropertyString(text));
            } 
            prevUnresolved.clear();
            prevUnresolved.addAll(currUnresolved);
            currUnresolved.clear();
        } 
    }

    /**
     * 
     * @return 
     */
    protected StringSubstitutor buildStringSubstitutor() {
        final StringSubstitutor ss = new StringSubstitutor();
        ss.setVariablePrefix(conf.variablePrefix());
        ss.setVariableSuffix(conf.variableSuffix());
        ss.setEscapeChar(conf.escapeChar());
        ss.setValueDelimiter(conf.valueDelimiter());
        ss.setEnableSubstitutionInVariables(true);
        return ss;
    }
    
    /**
     * 
     * @param text
     * @return 
     */
    protected PropertyString buildPropertyString(final CharSequence text) {
        final String newText = StringUtils.toEmptyString(text);
        final Configuration newConf = new Configuration() {
            @Override
            public EscapeMode escapeMode() {
                return conf.escapeMode();
            }

            @Override
            public String text() {
                return newText;
            }

            @Override
            public String variablePrefix() {
                return conf.variablePrefix();
            }

            @Override
            public String variableSuffix() {
                return conf.variableSuffix();
            }

            @Override
            public String valueDelimiter() {
                return conf.valueDelimiter();
            }

            @Override
            public String formatDelimiter() {
                return conf.formatDelimiter();
            }

            @Override
            public char escapeChar() {
                return conf.escapeChar();
            }

            @Override
            public boolean memoized() {
                return conf.memoized();
            }

            @Override
            public Function<String, Value<Object>> resolver() {
                return conf.resolver();
            }
            
            @Override
            public Function<String, Value<Object>> rawResolver() {
                return conf.rawResolver();
            }

            @Override
            public Formatter formatter() {
                return conf.formatter();
            }
        };
        return new PropertyString(newConf);
    }
    
    /**
     * 
     * @param text
     * @return 
     */
    protected List<String> buildRawPropertyList(final CharSequence text) {
        final List<String> list = new ArrayList();
        final StringLookup resolver = s -> { list.add(s); return StringUtils.EMPTY; };
        final StringSubstitutor ss = buildStringSubstitutor();
        ss.setVariableResolver(resolver);
        ss.replace(text);
        return list;
    }
    
    /**
     * 
     */
    public interface Formatter {
    
        String format(final CharSequence value, 
                final EscapeMode escapeMode,
                final List<String> fmtOpts);
        
    }
    

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return conf.text();
    }
    
    /**
     * 
     * @return 
     */
    public Configuration configuration() {
        return conf;
    }
    
    private final Function0<Either<PropertyString, String>> reducer;

    private final String textCache;

    private final PropertyString.Configuration conf;
    
}
