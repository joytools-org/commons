/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.collections;

import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.MoreCollectors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class TryPartitioningIndex {
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final IterableAccessor<String> names = IterableAccessors.from(
                "Zia", 
                "Zoe",
                "Andrea",
                "Alice",
                "Diana",
                "Roberto",
                " ",
                "1234",
                "74", "2048",
                "@essilux.com",
                "Ciao!",
                "Diego",
                "Bocia",
                "Piedi",
                "Torino",
                "Dario",
                "Irina", "Irinushka",
                "Zoe");
        names
                .collect(MoreCollectors.partitioningByCount(3))  // (s -> s.charAt(0) - 32))
                // .collect(MoreCollectors.partitioningBy(() -> new TreeMap<>(), s -> StringUtils.length(s)))
                .forEach(System.out::println);
    }
    
}
