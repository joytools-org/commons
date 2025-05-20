/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import java.util.Map;
import org.apache.commons.collections4.Equator;
import org.joytools.commons.collections.EquatorLinkedHashMap;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.MoreCollectors;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryHashedMap {
    
    public static void main(final String... args) {
        test(CaseSensitivity.SENSITIVE.equator());
        test(CaseSensitivity.INSENSITIVE.equator());
    }
    
    static void test(final Equator<String> eq) {
        final Map<String, String> m1 = new EquatorLinkedHashMap(eq, CaseSensitivity.INSENSITIVE.equator());
        m1.put("UNO", "ONE");
        m1.put("uno", "one");
        m1.put("DUE", "TWO");
        m1.put("due", "two");
        m1.put("TRE", "THREE");
        m1.put("tre", "three");
        System.out.println(m1);
        final Map<String, Integer> m2 = IterableAccessors.from(m1.values()).collect(MoreCollectors.cardinality(CaseSensitivity.INSENSITIVE.equator()));
        System.out.println(m2);
    }
    
}
