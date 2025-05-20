/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.text;

import io.vavr.control.Option;
import java.util.List;
import java.util.function.Function;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.text.PropertyString.Formatter;

/**
 *
 * @author AndreaR
 */
public class PropertyStrings {
    
    /**
     * 
     */
    private final static Formatter DEFAULT_FORMATTER = new Formatter() {
        @Override
        public String format(final CharSequence value, 
                final PropertyString.EscapeMode escapeMode, 
                final List<String> fmtOpts) {
            return format0(value, escapeMode, fmtOpts);
        }
    };
    
    /**
     * 
     * @return 
     */
    public static Formatter defaultFormatter() {
        return DEFAULT_FORMATTER;
    }
    
    /**
     * 
     * @param text
     * @return 
     */
    public static PropertyString of(final CharSequence text) {
        return PropertyStringBuilder.of(text).memoized().build();
    }
            
    /**
     * 
     * @param text
     * @param items
     * @return 
     */
    /* public static PropertyString of(final CharSequence text, final CharSequence k1, final CharSequence v1, final CharSequence... others) {
        return PropertyStringBuilder.of(text, join(k1, v1, others)).memoized().build();
    } */
    public static PropertyString of(final CharSequence text, final CharSequence... items) {
        return PropertyStringBuilder.of(text, items).memoized().build();
    }

    /**
     * 
     * @param text
     * @param acc
     * @return 
     */
    public static PropertyString of(final CharSequence text, final MapAccessor<String, ?> acc) {
        return PropertyStringBuilder.of(text).resolver(acc).build();
    }

    /**
     * 
     * @param <T>
     * @param text
     * @param acc
     * @return 
     */
    public static <T> PropertyString of(final CharSequence text, final Function<String, Option<T>> acc) {
        return PropertyStringBuilder.of(text).resolver(acc).build();
    }

    /**
     * 
     * @param text
     * @return 
     */
    public static PropertyString ofIgnoreCase(final CharSequence text) {
        return PropertyStringBuilder.ofIgnoreCase(text).memoized().build();
    }

    /**
     * 
     * @param text
     * @param k1
     * @param v1
     * @param items
     * @return 
     */
    /* public static PropertyString ofIgnoreCase(final CharSequence text, final CharSequence k1, final CharSequence v1, final CharSequence... others) {
        return PropertyStringBuilder.ofIgnoreCase(text, join(k1, v1, others)).memoized().build();
    } */
    public static PropertyString ofIgnoreCase(final CharSequence text, final CharSequence... items) {
        return PropertyStringBuilder.ofIgnoreCase(text, items).memoized().build();
    } 
    
    /**
     * 
     * @return 
     */
    public static PropertyStringBuilder builder() {
        return PropertyStringBuilder.of();
    }

    /**
     * 
     * @param text
     * @return 
     */
    public static PropertyStringBuilder builder(final CharSequence text) {
        return PropertyStringBuilder.of(text);
    }

    /**
     * 
     * @param text
     * @param elements
     * @return 
     */
    public static PropertyStringBuilder builder(final CharSequence text, final CharSequence... elements) {
        return PropertyStringBuilder.of(text, elements);
    }

    /**
     * 
     * @param k1
     * @param v1
     * @param others
     * @return 
     */
    static String[] join(final CharSequence k1, final CharSequence v1, final CharSequence... others) {
        if (others == null || others.length == 0) {
            return StringUtils.toArray(k1, v1);
        }
        final String[] elements = new String[2 + others.length];
        int index = 0;
        elements[index++] = StringUtils.toString(k1);
        elements[index++] = StringUtils.toString(v1);
        for (final CharSequence s : others) {
            elements[index++] = StringUtils.toString(s);
        }
        return elements;
    }
    
    /**
     * 
     * @param value
     * @param escapeMode
     * @param fmtOpts
     * @return 
     */
    static String format0(final CharSequence value,
            final PropertyString.EscapeMode escapeMode,
            final List<String> fmtOpts) {
        String propValue = value.toString();
        if (fmtOpts.isEmpty()) {
            return escapeMode.escaper().apply(propValue);
        }
        
        if (fmtOpts.contains("isots") || fmtOpts.contains("isotimestamp")) {
            propValue = StringUtils.substringBefore(propValue, ".");
        } else if (fmtOpts.contains("isodate")) {
            propValue = StringUtils.substringBefore(propValue, " ");
        } else if (fmtOpts.contains("timehhmm")) {
            propValue = StringUtils.substringBeforeLast(
                    StringUtils.substringAfter(propValue, " "), ":");
        } else if (fmtOpts.contains("timehhmmss")) {
            propValue = StringUtils.substringBeforeLast(
                    StringUtils.substringAfter(propValue, " "), ".");
        }

        if (fmtOpts.contains("ucase") || fmtOpts.contains("upper")) {
            propValue = StringUtils.upperCase(propValue);
        } else if (fmtOpts.contains("lcase") || fmtOpts.contains("lower")) {
            propValue = StringUtils.lowerCase(propValue);
        } else if (fmtOpts.contains("capital") || fmtOpts.contains("capitalize")) {
            propValue = StringUtils.capitalize(propValue);
        }

        if (fmtOpts.contains("nospace")) {
            propValue = StringUtils.replace(propValue, " ", "");
        } else if (fmtOpts.contains("trim")) {
            propValue = StringUtils.trim(propValue);
        }

        for (final PropertyString.EscapeMode mode : PropertyString.EscapeMode.values()) {
            // Check for "escapehtml", "escapexml", etc formmaters
            final String s = mode.name().toLowerCase();
            if (fmtOpts.contains("escape" + s) ||
                    fmtOpts.contains("encode" + s)) {
                return mode.escaper().apply(propValue);
            }
        }

        final String suffix = escapeMode.name().toLowerCase();
        if (fmtOpts.contains("noescape") || 
                fmtOpts.contains("noencode") || 
                fmtOpts.contains("noescape" + suffix) ||
                fmtOpts.contains("noencode" + suffix)) {
            return propValue;
        }
        
        return escapeMode.escaper().apply(propValue);
    }
    
}
