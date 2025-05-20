/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryCaseSensitivity {
    
    public static void main(final String... args) throws Exception {
        final Map<String, Integer> m1 = new HashMap<>();
        m1.put("uno", 1);
        m1.put("due", 2);
        m1.put("tre", 3);
        m1.put("quattro", 4);
        final Map<String, Integer> m2 = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        m2.put("uno", 1);
        m2.put("due", 2);
        m2.put("tre", 3);
        m2.put("quattro", 4);
        final Map<String, Integer> m3 = new TreeMap<>();
        m3.put("uno", 1);
        m3.put("due", 2);
        m3.put("tre", 3);
        m3.put("quattro", 4);
        final CaseSensitivity cs1 = CaseSensitivity.fromMapKeys(m1.keySet(), m1::containsKey);
        final CaseSensitivity cs2 = CaseSensitivity.fromMapKeys(m2.keySet(), m2::containsKey);
        final CaseSensitivity cs3 = CaseSensitivity.fromMapKeys(m3.keySet(), m3::containsKey);
        System.out.println(cs1);
        System.out.println(cs2);
        System.out.println(cs3);
    }
    
}
