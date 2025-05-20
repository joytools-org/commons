/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.collections;

import org.joytools.commons.collections.IterableAccessors;

/**
 *
 * @author AndreaR
 */
public class TryIterationCast {
    
    public static void main(final String... args) {
        final Object[] a = new Object[] { "Andrea", "Rombaldi", "Paolino", "Paperino" };
        IterableAccessors.findOrSingleton(a, String.class).map(s -> "[" + s + "]").forEach(System.out::println);
    }
    
}
