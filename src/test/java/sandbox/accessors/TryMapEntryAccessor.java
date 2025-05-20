/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.accessors;

import io.vavr.collection.Vector;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.joytools.commons.accessors.FixedSizeIndexedMapMutator;
import org.joytools.commons.accessors.FixedSizeMutators;
import static org.joytools.commons.lang.ObjectUtils.concat;

/**
 *
 * @author AndreaR
 */
public class TryMapEntryAccessor {
    
    public static void main(final String... args) {
        final Vector<String> v = Vector.of("uno", "due", "tre");
        System.out.println(v);
        final Map<String, String> m = new LinkedHashMap<>();
        m.put("nome", "Andrea");
        m.put("cognome", "Rombaldi");
        m.put("location", "Trichiana");
        for (final Map.Entry<String, String> e : m.entrySet()) {
            System.out.println("**************************");
            final FixedSizeIndexedMapMutator<String, String> acc = FixedSizeMutators.of(e);
            System.out.println(concat("          keys: ", acc.keys()));
            System.out.println(concat("       indexes: ", acc.indexes()));
            System.out.println(concat("        values: ", acc.values()));
            System.out.println(concat("  entriesbyKey: ", acc.entriesByKey()));
            System.out.println(concat("entriesByIndex: ", acc.entriesByIndex()));
            System.out.println(acc);
            acc.set(e.getKey(), e.getValue() + "1");
            System.out.println(acc);
            acc.set(0, e.getValue() + "2");
            System.out.println(acc);
        }
    }
    
}
