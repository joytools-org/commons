/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.accessors.record;

import org.joytools.commons.accessors.StringAccessors;

/**
 *
 * @author AndreaR
 */
public class TryRecAccessor {
    
    public static void main(final String... args) {
        final var r1 = new Rec1("Andrea", "Rombaldi", 38);
        final var r2 = new Rec2("Andrea", "ANDREA", "Rombaldi", "ROMBALDI", 38, 83);        
        System.out.println(r1);
        System.out.println(r2);
        final var acc1 = StringAccessors.of(r1);
        final var acc2 = StringAccessors.of(r2);
        System.out.println(acc1);
        System.out.println(acc2);
        final var accIC1 = StringAccessors.ofIgnoreCase(r1);
        final var accIC2 = StringAccessors.ofIgnoreCase(r2);
        System.out.println(accIC1);
        System.out.println(accIC2);
    }
    
}
