/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.accessors;

import java.util.Map;
import org.joytools.commons.accessors.MapMutator;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.accessors.StringMutators;

/**
 *
 * @author AndreaR
 */
public class MapAccessorMeth {
    
    public static void main(final String... args) throws Exception {
        final Map<String, Integer> m = CaseSensitivity.INSENSITIVE.newLinkedMap();
        final MapMutator<String, Integer> acc = StringMutators.of(m);
        acc.insert(
                "uno", 1,
                "due", 2,
                "tre", 3,
                "quattro", 4,
                "cinque", 5,
                "sei", 6,
                "sette", 7,
                "otto", 8);
        acc.entriesByKey().forEach(System.out::println);
    }
    
}
