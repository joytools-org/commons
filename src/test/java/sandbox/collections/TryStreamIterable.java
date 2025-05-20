/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.joytools.commons.collections.IterableAccessors;

import org.joytools.commons.lang.StringPredicates;

/**
 *
 * @author AndreaR
 */
public class TryStreamIterable {
    
    public static void main(final String... args) {
        IterableAccessors.from("uno", "due", "tre", "quattro", "cinque", "sei")
                .append("sette", "otto", "nove")
                .stream()
                .filter(StringPredicates.containsIgnoreCase("e"))
                .forEach(System.out::println);
        
        final List<String> aa = new ArrayList(Arrays.asList("uno", "due", "tre", "quattro", "cinque", "sei"));
        System.out.println(aa);
        // RichIterable.of(aa).retainAll(Arrays.asList("UNO", "DUE", "TRE"));
        // RichIterable.of(aa).retainAll(Arrays.asList("UNO", "DUE", "TRE"), CaseSensitivity.INSENSITIVE.comparator());
        // Glitterable.of(aa).retainAll(Arrays.asList("UNO", "DUE", "TRE"));
        // Flyterable.of(aa).retainAll(Arrays.asList("UNO", "DUE", "TRE"));
        // Greaterable.of(aa).retainAll(Arrays.asList("UNO", "DUE", "TRE"));
        // Joyterable.of(aa).retainAll(Arrays.asList("UNO", "DUE", "TRE"));
        // Winterable.of(aa).retainAll(Arrays.asList("UNO", "DUE", "TRE"));
        // Iterable.of(aa).retainAll(Arrays.asList("UNO", "DUE", "TRE"));
        /*
        System.out.println(Iterations.from(aa).containsAny(Arrays.asList("A", "B", "C", "QUATTRO")));
        System.out.println(Iterations.from(aa).containsAny(Arrays.asList("A", "B", "C", "QUATTRO"), CaseSensitivity.INSENSITIVE.comparator()));
        System.out.println(Iterations.from(aa).containsAll(Arrays.asList("UNO", "DUE", "TRE", "4")));
        System.out.println(Iterations.from(aa).containsAll(Arrays.asList("UNO", "DUE", "TRE", "4"), CaseSensitivity.INSENSITIVE.comparator()));
        */
        IterableAccessors.disjunction(aa, aa);
    }
    
}
