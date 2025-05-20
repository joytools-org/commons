/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.collections;

import io.vavr.Tuple2;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.collections.Mergers;
import org.joytools.commons.interop.Java;

/**
 *
 * @author AndreaR
 */
public class TryMapMerge {
    
    public static void main(final String... args) {
        final java.util.Map<String, Integer> jm1 = null; // LinkedHashMap.of("Zero", 0, "Uno", 1, "Due", 2, "Tre", 3, "Quattro", 4).to(Java.linkedHashMap());
        // final Map<String, Integer> m2 = LinkedHashMap.of("Zero", 1, "Dieci", 10, "Undici", 11, "Dodici", 12, "Tredici", 13, "Quattordici", 14).to(Java.linkedHashMap());
        final java.util.Map<String, Integer> jm2 = IterableAccessors.tuples("Zero", 11, "Dieci", 10, "Undici", 11, "Dodici", 12, "Tredici", 13, "Quattordici", 14).to(Java.linkedHashMap());
        MapUtils.merge(jm1, jm2, Mergers.mergeLast());
        // Mutator(m1).merge(m2, Mergers.mergeLast());
        jm1.entrySet().forEach(System.out::println);

        final io.vavr.collection.Map<CharSequence, Object> vm1 = MapUtils.insert(io.vavr.collection.LinkedHashMap.<CharSequence, Object>empty(),
                "Zero", 0, "Uno", 1, "Due", 2, "Tre", 3, "Quattro", 4);
        // final Map<String, Integer> m2 = LinkedHashMap.of("Zero", 1, "Dieci", 10, "Undici", 11, "Dodici", 12, "Tredici", 13, "Quattordici", 14).to(Java.linkedHashMap());
        final Iterable<Tuple2<CharSequence, Integer>> vm2 = IterableAccessors.tuples("Zero", 11, "Dieci", 10, "Undici", 11, "Dodici", 12, "Tredici", 13, "Quattordici", 14);
        final io.vavr.collection.Map<CharSequence, Object> vm3 = MapUtils.merge(vm1, vm2, Mergers.mergeLast());
        // Mutator(m1).merge(m2, Mergers.mergeLast());
        vm3.forEach(System.out::println);

    }
    
}
