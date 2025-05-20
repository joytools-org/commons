/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.interop;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import java.util.stream.Collectors;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.interop.Guava;
import org.joytools.commons.interop.Vavr;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class Interops {
 
    public static void main(final String... args) {
        final IterableAccessor<Tuple2<String, Integer>> i = IterableAccessors.of(
                Tuple("Uno", 1),
                Tuple("Due", 2),
                Tuple("Tre", 3),
                Tuple("Uno", 11));
        final java.util.Map<String, Integer> jm0 = i.collect(Collectors.toMap(t -> t._1, t -> t._2));
        final java.util.Map<String, Integer> jm1 = null; // Java.toIgnoreCaseTreeMap(i);
        final io.vavr.collection.Map<String, Integer> vm = Vavr.toTreeMapIgnoreCase(i);
        System.out.println(jm0);
        System.out.println(jm1);
        System.out.println(vm); 
    }
    
    static void testBiMap() {
        final Iterable<Tuple2<String, Integer>> i = IterableAccessors.of(
                Tuple("Uno", 1),
                Tuple("Due", 2),
                Tuple("Tre", 3),
                Tuple("Uno", 11));
        System.out.println(Guava.toImmutableBiMap(i));
    }
    
}
