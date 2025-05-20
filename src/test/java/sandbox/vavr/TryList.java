/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.vavr;

import io.vavr.collection.List;
import java.util.Iterator;

/**
 *
 * @author AndreaR
 */
public class TryList {
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final List<String> l = List.of("Uno", "Due", "Tre", "Quattro", "Cinque");
        final Iterator<String> itr = l.iterator();
        while (itr.hasNext()) {
            final String s = itr.next();
            System.out.println(s);
        }
    }
    
}
