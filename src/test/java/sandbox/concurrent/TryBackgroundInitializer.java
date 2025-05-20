/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.concurrent;

import org.apache.commons.lang3.concurrent.BackgroundInitializer;
import org.joytools.commons.concurrent.AtomicUtils;

/**
 *
 * @author AndreaR
 */
public class TryBackgroundInitializer {
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(final String... args) throws Exception {
        System.out.println("*** FIRST PASS ***");
        final BackgroundInitializer<String> init = BackgroundInitializer.<String>builder()
                .setInitializer(Commons.failingInitializer())
                .setCloser(AtomicUtils.defaultCloser())
                .get();
        init.start();
        for (var i = 0; i < 3; i++) {
            try {
                System.out.println(init.get());
            } catch (final Exception ex) {
                System.out.println(ex);
            }
        }
    }
    
}
