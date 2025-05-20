/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.time;

import java.time.Clock;
import java.time.ZoneId;

/**
 *
 * @author AndreaR
 */
public class TryClock {
    
    public static void main(final String... args) throws Exception {
        final Clock cl1 = Clock.tickMinutes(ZoneId.systemDefault());
        final Clock cl2 = Clock.tickMinutes(ZoneId.systemDefault());
        for (int i = 0; i < 16; i++) {
            System.out.println(cl1.instant());
            System.out.println(cl1.millis());
            Thread.sleep(200);
        }
    }
    
}
