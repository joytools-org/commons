/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.collections;

import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.interop.Java;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class TryIterationToArray {
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final IterableAccessor<String> itr = IterableAccessors.from("Uno", "Due", "Tre", "Quattrro");
        final String[] strArray = itr.to(Java.array(String[]::new));
        // final String[] s = i.to(Java.array(String[]::new));
        int i = 0;
        for (final String item : strArray) {
            System.out.println(i++ + " -> " + item);
        }
        // System.out.println(s);
    }
    
}
