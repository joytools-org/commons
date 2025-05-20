/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.collections;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.interop.Java;
import org.joytools.commons.interop.Vavr;
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class TryVavrIteration {
    
    public static void main(final String... args) {
        final Vector<String> v1 = Vector.of(
                "a", "b", "c", "d", "e",
                "1", "2", "3", "4", "5");
        final IterableAccessor<String> i1 = IterableAccessors.ofAll(v1);
        final IterableAccessor<String> i2 = IterableAccessors.ofAll(Some("Ciao"));
        final IterableAccessor<String> i3 = IterableAccessors.ofAll(None());
        final Vector<String> v2 = i1.to(Vavr.vector());
        System.out.println(v1 == v2);
        System.out.println(i1.first());
        final Option<String> s = i1.findFirst(StringPredicates.isNumeric());
        System.out.println(s);
        System.out.println(i1.getOrFail(2));
        /* final String[] arr2 = v1.to(Java.array(String.class));
        final String[] arr3 = v1.to(Java.array(String[]::new));
        System.out.println(arr2);
        System.out.println(arr3); */
    }
    
}
