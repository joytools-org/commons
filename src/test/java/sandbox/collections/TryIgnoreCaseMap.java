/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.collections;

import java.util.Map;
import java.util.Set;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryIgnoreCaseMap {
    
    public static void main(final String... args) {
        final Map<String, Object> m = CaseSensitivity.INSENSITIVE.newLinkedMap();
        m.put("id", 10);
        m.put("firstName", "Andrea");
        m.put("lastName", "Rombaldi");
        m.put("displayName", "Andrea Rombaldi");
        System.out.println(m);
        System.out.println(m.get("DISPLAYname"));
        
    }
    
}
