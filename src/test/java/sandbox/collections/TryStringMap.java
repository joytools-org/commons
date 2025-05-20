/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.collections.StringMap;
import org.joytools.commons.collections.StringMapBuilder;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryStringMap {
    
    public static void main(final String... args) {
        final Map<String, String> m = MapUtils.insert(CaseSensitivity.INSENSITIVE.newLinkedMap(),
                "uno", "one",
                "due", "two",
                "tre", "three",
                "DUE", "due",
                "TRE", "three");
        System.out.println(m);
        
        final StringMap<String> s = StringMapBuilder
                .of(String.class)
                .caseSensitive()
                .createLinked(m);
        System.out.println(s);
    }
    
}
