/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import java.util.Map;
import java.util.Properties;
import org.joytools.commons.collections.StringHashMap;
import org.joytools.commons.collections.StringLinkedHashMap;
import org.joytools.commons.collections.StringMaps;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryCaseSensitivity {
    
    public static void main(final String... args) {
        test(CaseSensitivity.SENSITIVE);
        test(CaseSensitivity.INSENSITIVE);
        testOther();
    }

    static void test(final CaseSensitivity cs) {
        System.out.println("*************************");
        System.out.println(cs);
        dump(cs.newLinkedMap());
        dump(cs.newIndexedMap());
        dump(cs.newSortedMap());
        dump(new StringHashMap(cs));
        dump(new StringLinkedHashMap(cs));
    }
    
    static void testOther() {
        System.out.println("*************************");
        System.out.println("Other");
        dump(new Properties());
    }
    
    static void dump(final Map m) {
        final CaseSensitivity cs = StringMaps.caseSensitivityOf(m);
        System.out.println(m.getClass().getName() + ": " + cs);
    }
    
}
