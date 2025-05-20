/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import java.util.Map;
import java.util.TreeMap;
import org.joytools.commons.collections.IndexedMap;
import org.joytools.commons.collections.IndexedMapBuilder;

/**
 *
 * @author AndreaR
 */
public class TryIndexedMapBuilder {
    
    public static void main(final String... args) {
        final Map<String, String> m = new TreeMap();
        m.put("id", "1948324");
        m.put("givenName", "Andrea");
        m.put("sn", "Rombaldi");
        final IndexedMap<String, String> im1 = IndexedMapBuilder
                .copyOf(m)
                .ignoreCase()
                .create();
        System.out.println(im1);
        final IndexedMap<String, Integer> im2 = IndexedMapBuilder
                .withData(m.keySet(), -1)
                .ignoreCase()
                .create();
        System.out.println(im2);
    }
}
