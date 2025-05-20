/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import java.util.Arrays;
import java.util.List;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.MoreCollectors;
import org.joytools.commons.interop.Vavr;

/**
 *
 * @author AndreaR
 */
public class TryIterableCardinality {
 
    public static void main(final String... args) {
        final List<String> a = Arrays.asList("a", "a", "b", "b", "c", "c", "c", "d", "d");
        System.out.println(IterableAccessors.from(a).collect(MoreCollectors.cardinality()));
        System.out.println(IterableAccessors.from(a).stream().collect(MoreCollectors.cardinality()));
        System.out.println(IterableAccessors.from(a).parallelStream().collect(MoreCollectors.cardinality()));

        final List<String> b = Arrays.asList("c", "d", "d", "e", "f", "g");
        System.out.println(IterableAccessors.from(b).collect(MoreCollectors.cardinality()));
        System.out.println(IterableAccessors.from(b).stream().collect(MoreCollectors.cardinality()));
        System.out.println(IterableAccessors.from(b).parallelStream().collect(MoreCollectors.cardinality()));
        
        // System.out.println(a.stream().parallel().collect(MoreCollectors.cardinality()));
        // System.out.println(b.stream().parallel().collect(MoreCollectors.cardinality()));
        System.out.println("intersection: " + IterableAccessors.intersection(a, b).to(Vavr.vector()));
        System.out.println("disjunction:  " + IterableAccessors.disjunction(a, b).to(Vavr.vector()));
        System.out.println("subtraction:  " + IterableAccessors.subtraction(a, b).to(Vavr.vector()));
        System.out.println("union:        " + IterableAccessors.union(a, b).to(Vavr.vector()));
        System.out.println("concat:       " + IterableAccessors.concat(a, b).sorted().to(Vavr.vector()));
    }
    
}
