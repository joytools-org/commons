/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.time;

import org.joytools.commons.time.StopWatch;

/**
 *
 * @author AndreaR
 */
public class TryStopWatch {
    
    public static void main(final String... args) throws InterruptedException {
        final StopWatch sw = new StopWatch("Ciao");
        sw.start();
        Thread.sleep(500);
        System.out.println("formatTime: " + sw.formatTimeAndRestart());
        sw.split();
        System.out.println("formatSplitTime: " + sw.formatSplitTime());
        System.out.println("toString: " + sw.toString());
        System.out.println("toSplitString: " + sw.toSplitString());
    }
    
}
