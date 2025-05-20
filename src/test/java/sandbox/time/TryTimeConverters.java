/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.time;

import java.time.Instant;
import java.time.ZoneId;
import org.joytools.commons.time.TimeValue;
import org.joytools.commons.time.TimeValues;

/**
 *
 * @author AndreaR
 */
public class TryTimeConverters {
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(final String... args) throws Exception {
        // final TimeValue<?> lt = TimeValues.parseOrFail("08:00");
        // System.out.println(lt);
        System.out.println(TimeValues.parseAllOrFail("2018-01-01 13:43:55"));
        System.out.println(TimeValues.parseAllOrFail("2018-01-01"));
        System.out.println(TimeValues.parseAllOrFail("2018-01-01 13:43:55"));
        System.out.println(TimeValues.parseAllOrFail("2018-01-01"));
    }
    
    public static void main2(final String... args) throws Exception {
        final TimeValue<Instant> now = TimeValues.now();
        System.out.println(now);
        
        /* final LocalTime lt1 = TimeValues.parseOrFail("08:00");
        System.out.println(lt1); */
        
        final TimeValue<?> lt2 = TimeValues.parseAllOrFail("08:00");
        System.out.println(lt2);

        System.out.println(TimeValues.parseAllOrFail("2018-01-01 13:43:55"));
        System.out.println(TimeValues.parseAllOrFail("2018-01-01"));
        System.out.println(TimeValues.parseAllOrFail("2018-01-01 13:43:55"));
        System.out.println(TimeValues.parseAllOrFail("2018-01-01"));
    }

}
