/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.time;

import java.sql.Timestamp;
import java.time.Instant;
import org.joytools.commons.time.DateTimeUtils;
import org.joytools.commons.time.TimeValue;
import org.joytools.commons.time.TimeValues;

/**
 *
 * @author AndreaR
 */
public class TryTruncate {
    
    public static void main(final String... args) {
        final long cts = System.currentTimeMillis();
        final TimeValue<?> now = TimeValues.of(cts);
        System.out.println(cts);
        System.out.println(now);
        final Instant inst = DateTimeUtils.truncateToDay(now.toInstant());
        System.out.println("Instant:   " + inst.toEpochMilli());
        System.out.println("           " + inst);
        final TimeValue<Instant> ts1 = DateTimeUtils.truncateToDay(now);
        System.out.println("Timestamp1: " + ts1.timeMillis());
        System.out.println("            " + ts1);
        System.out.println("            " + ts1.toSQLTimestamp());
        final Timestamp ts2 = DateTimeUtils.truncateToDay(now.toSQLTimestamp());
        System.out.println("Timestamp2: " + ts2.getTime());
        System.out.println("            " + ts2);
    }
    
}
