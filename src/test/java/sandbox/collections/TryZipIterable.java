/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import java.util.Arrays;
import java.util.List;
import org.joytools.commons.collections.IterableAccessors;

/**
 *
 * @author AndreaR
 */
public class TryZipIterable {
    
    public static void main(final String... args) {
        System.out.println("*** Union ***");
        /*
        StreamIterables.union(
                Arrays.asList("Uno", "Due", "Tre"),
                Arrays.asList("Due", "Tre", "Quattro")
            ).forEach(System.out::println); */

        final List<String> a1 = Arrays.asList("Uno", "Due", "Tre");
        final List<String> b1 = Arrays.asList("One", "Two", "Three");
        final List<String> c1 = Arrays.asList("1", "2", "3", "4");
        System.out.println("*** Zip ***");
        IterableAccessors.zip(a1, b1, c1, null).forEach(System.out::println);

        final String[] a2 = { "Uno", "Due", "Tre" };
        final String[] b2 = { "One", "Two", "Three" };
        final String[] c2 = { "1", "2", "3", "4" };
        IterableAccessors.zip(a1, b1, c1, null).forEach(System.out::println);
    }
    
}
