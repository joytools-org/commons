/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.collections;

import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class TryIterationContains {
    
    public static void main(final String... args) {
        final IterableAccessor<String> items = IterableAccessors.from("a", "b", "c", "d", "e", "f");
        
        final IterableAccessor<String> empty = IterableAccessors.empty();
        final IterableAccessor<String> any = IterableAccessors.from("a", "z");
        final IterableAccessor<String> all = IterableAccessors.from("a", "b");
        final IterableAccessor<String> none = IterableAccessors.from("z");
        
        System.out.println("containsAny(any):" + items.containsAny(any));
        System.out.println("containsAny(all):" + items.containsAny(all));
        System.out.println("containsAll(any):" + items.containsAll(any));
        System.out.println("containsAll(all):" + items.containsAll(all));
        System.out.println("containsAny(none):" + items.containsAny(none));
        System.out.println("containsAll(none):" + items.containsAll(none));
        
        System.out.println("containsAny(empty):" + items.containsAny(empty));
        System.out.println("containsAll(empty):" + items.containsAll(empty));
    }
    
}
