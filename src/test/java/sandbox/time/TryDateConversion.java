/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.time;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.joytools.commons.time.TimeConverters;
import org.joytools.commons.time.TimeValue;
import org.joytools.commons.time.TimeValues;

/**
 *
 * @author AndreaR
 */
public class TryDateConversion {
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        // final Calendar cal = TimeValues.parseOrFail("2023-01-01 10:00:00").toCalendar();
        
        System.out.println(TimeConverters.toInstant().apply("2023-01-01 10:00:00"));
        
        
        final TimeValue<Instant> nowInst = TimeValues.now();
        System.out.println("*** NOW ***");
        System.out.println(nowInst);
        System.out.println(nowInst.timeMillis());
        System.out.println(nowInst.toSQLTimestamp());
        System.out.println(nowInst.toCalendar());
        System.out.println(nowInst.toLocalDateTime());
        System.out.println(nowInst.toInstant().toEpochMilli());
        System.out.println(nowInst.timeMillis());
        System.out.println(nowInst.toSQLTimestamp().getTime());
        System.out.println(nowInst.toCalendar().getTimeInMillis());
        
        System.out.println("*** CONVERSIONS ***");
        final LocalDateTime ldt = LocalDateTime.ofInstant(nowInst.toInstant(), ZoneId.systemDefault());
        System.out.println(ldt);
        
        final TimeValue<LocalDateTime> loc1 = TimeValues.of(ldt);
        final TimeValue<Instant> loc2 = TimeValues.of(loc1.toInstant());
        
        System.out.println(loc1.toLocalDateTime());
        System.out.println(loc2.toLocalDateTime());

        if (Math.random() >= 0) {
            System.exit(0);
        }
        
        final LocalDateTime ldt1 = nowInst.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
        final LocalDateTime ldt2 = nowInst.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        
        final Date d = TimeValues.parseAllOrFail("2020-01-01").toSQLDate();
        final LocalDate d1 = d.toLocalDate();
        System.out.println(d1);
        final LocalDate d2 = null; // TimeConverters.toZonedDateTime(d).toLocalDate();
        System.out.println(d2);
        final Time t = TimeValues.parseAllOrFail("10:10:10").toSQLTime();
        System.out.println(TimeConverters.toInstant().apply(t));
    }
    
}
