/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.time;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Supplier;
import org.joytools.commons.accessors.Mutators;
import org.joytools.commons.accessors.StringAccessors;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.concurrent.AtomicSuppliers;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.time.TimeValues;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryReflectDateTimeFormatter {
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(final String... args) throws Exception {
        // System.out.println(DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis())));
        parseAll("2025-05-07");
        parseAll("10:10:10.500");
        parseAll("2025-05-07 10:10:10.500");
        parseAll("2012-W48-6");
        parseAll("7 mag 2025, 19:39:07");
        parseAll("7 mag 2025");
    }
    
    /**
     * 
     * @param cs 
     */
    static void parseAll(final CharSequence cs) {
        System.out.println("*****************************************");
        System.out.println("*** parseAll: " + cs);
        System.out.println("*****************************************");
        System.out.println("--- TIME_VALUES --");
        var tv = TimeValues.parseAllOrNull(cs);
        System.out.println("TimeValue: " + tv);
        boolean df = true;
        final String s = StringUtils.toString(cs);
        for (final var dtf : TimeValues.dateFormats().entriesByKey()) {
            try {
                final var date = dtf._2.parse(s);
                if (df) {
                    System.out.println("--- DATE_FORMATS --");
                    df = false;
                }
                System.out.println(dtf._1 + ": " + date);
                final var inst = date.toInstant();
                tv = TimeValues.of(inst);
                System.out.println("  " + inst);
                System.out.println("  " + tv.toSQLDate());
                System.out.println("  " + tv.toSQLTime());
                System.out.println("  " + tv.toSQLTimestamp());
            } catch (final Exception ex) {
                // Ignore
            }
        }
        df = true;
        for (final var dtf : TimeValues.dateTimeFormatters().entriesByKey()) {
            try {
                final var ta = dtf._2.parse(s);
                if (df) {
                    System.out.println("--- DATE_TIME_FORMATTERS --");
                    df = false;
                }
                System.out.println(dtf._1 + ": " + ta);
                final var inst = Instant.from(ta);
                tv = TimeValues.of(inst);
                System.out.println("  " + inst);
                System.out.println("  " + tv.toSQLDate());
                System.out.println("  " + tv.toSQLTime());
                System.out.println("  " + tv.toSQLTimestamp());
            } catch (final Exception ex) {
                // Ignore
            }
        }
    }
    
}
