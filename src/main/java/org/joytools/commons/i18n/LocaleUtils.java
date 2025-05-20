/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.i18n;

import static com.google.common.base.Preconditions.checkArgument;
import java.util.Locale;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class LocaleUtils extends org.apache.commons.lang3.LocaleUtils {

    /**
     * 
     * @param locale
     * @return 
     */    
    public static Locale toNonEmptyLocale(final String locale) {
        checkArgument(StringUtils.isNotEmpty(locale), "Locale is empty");
        return toNonEmptyLocale(toLocale(locale.trim().replace("-", "_")));
    }
    
    /**
     * 
     * @param locale
     * @return 
     */
    public static Locale toNonEmptyLocale(final Locale locale) {
        checkArgument(locale != null, "Locale is null");
        checkArgument(StringUtils.isNotEmpty(locale.toString()), "Locale is empty");
        return locale;
    }

    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        IterableAccessors.from(Locale.getISOCountries())
                .sorted()
                .forEach(isocc -> {
                    System.out.println(isocc + "," + IterableAccessors.from(languagesByCountry(isocc)).mkString(" "));
                });
    }
    
}
