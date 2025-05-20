/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.collections;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import java.util.Objects;
import org.joytools.commons.lang.StringUtils;
import org.joytools.vavr.VavrFunctions;

/**
 *
 * @author AndreaR
 */
public class TryVavrCollectors {
    
    public static void main(final String... args) {
        final Vector<String> data = Vector.rangeClosed('A', 'Z').map(c -> StringUtils.repeat(c, 5));
        // System.out.println(data.mkString(", "));
        final Vector<Tuple2<String, Integer>> d2 = data.zipWithIndex();
        final Vector<Tuple2<String, Integer>> d3 = d2
                .appendAll(d2.map(t -> t.update2(t._2() * 10)))
                .appendAll(d2.map(t -> t.update2(t._2() * 100)));
        System.out.println(d3.mkString(", "));
        Map<String, Integer> m = HashMap.empty();
        for (final Tuple2<String, Integer> e : d3) {
            m = m.put(e, VavrFunctions.mergeNoDuplicates());
        }
        System.out.println(m);
        /*
        final Map<String, Integer> m2 = HashMap.ofEntries(d3);
        System.out.println(m2);*/
    }
    
}
