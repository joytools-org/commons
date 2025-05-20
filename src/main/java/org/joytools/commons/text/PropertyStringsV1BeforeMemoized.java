/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.text;

import java.util.List;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.text.PropertyString.Formatter;

/**
 *
 * @author AndreaR
 */
public class PropertyStringsV1BeforeMemoized {
    
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
     * @param elements
     * @return 
     */
    public static PropertyString of(final CharSequence text, final CharSequence... elements) {
        return PropertyStringBuilder.of(text, elements).build();
    }
            
    /**
     * 
     * @param text
     * @param elements
     * @return 
     */
    public static PropertyString ofIgnoreCase(final CharSequence text, final CharSequence... elements) {
        return PropertyStringBuilder.ofIgnoreCase(text, elements).build();
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

            /*
        if (!fmtOpts.contains("noescape")) {
            // Check for "noescapehtml", "noescapexml", etc formmaters
            if (!fmtOpts.contains("noescape" + escapeMode.name().toLowerCase())) {
                return escapeMode.escaper().apply(propValue);
            }
        } 

        if (!fmtOpts.contains("noencode")) {
            // Check for "noescapehtml", "noescapexml", etc formmaters
            if (!fmtOpts.contains("noencode" + escapeMode.name().toLowerCase())) {
                return escapeMode.escaper().apply(propValue);
            }
        } 
        
        return propValue;
        */

    }
    
}
