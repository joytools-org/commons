/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.collections;

import org.joytools.commons.collections.MapUtils;

/**
 *
 * @author AndreaR
 */
public class TryMapUtilsBuild {
 
    public static void main(final String... args) {
        final java.util.Map<String, Integer> m1 = MapUtils.insert(new java.util.LinkedHashMap<>(), 
                "Uno", 1,
                "Due", 2,
                "Tre", 3,
                "Quattro", 4,
                "Cinque", 5,
                "Sei", 6,
                "Sette", 7);
        m1.entrySet().forEach(System.out::println);
    }
    
}
