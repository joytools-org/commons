/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joytools.commons.time.TimeConverters;
import org.joytools.commons.util.ComboVariant;
import org.joytools.commons.util.ComboVariants;

/**
 *
 * @author AndreaR
 */
public class TryVariant {
 
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final Object in = new Date(System.currentTimeMillis());
        final List<Object> outputs = new ArrayList();
        final Timestamp ts = TimeConverters.toSQLTimestamp().apply(in); outputs.add(ts);
        final Instant inst = TimeConverters.toInstant().apply(in); outputs.add(inst);
        final LocalDate locDate = inst.atZone(ZoneId.systemDefault()).toLocalDate(); outputs.add(locDate);
        final LocalTime locTime = inst.atZone(ZoneId.systemDefault()).toLocalTime(); outputs.add(locTime);
        final LocalDateTime locDateTime = inst.atZone(ZoneId.systemDefault()).toLocalDateTime(); outputs.add(locDateTime);
        outputs.forEach((x) -> {
            System.out.println("" + x.getClass() + ": " + x);
        });
        
        final Map<String, Object> m = new HashMap();
        m.put("firstName", "Andrea");
        m.put("lastName", "Rombaldi");
        m.put("birthDate", TimeConverters.toSQLTimestamp().apply("1974-04-08 00:00:00"));
        m.put("age", 45);
        
        m.entrySet().forEach((e) -> {
            System.out.println(e.getKey());
            final ComboVariant v = ComboVariants.of(e.getValue());
            System.out.println("  Object: " + v.getObject());
            System.out.println("  Type: " + v.type().get());
        });
        
        final int age = ComboVariants.read(m, "age").getInteger();
        System.out.println(age);
    }
    
}
