/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import static io.vavr.API.Some;
import io.vavr.Value;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.lang.CaseSpaceNormalizer;
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class TryIteration {
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        findIterator();
        
        IterableAccessors.from("uno", "due", "tre", "quattro", "cinque", "sei")
                .append("sette", "otto", "nove")
                .accept(x -> System.out.println("*******************************"))
                .accept(System.out::println)
                .map(CaseSpaceNormalizer.UPPERCASE)
                .accept(System.out::println)
                .filter(StringPredicates.containsIgnoreCase("e"))
                .forEach(System.out::println);
        
        final Vector<Object> v = Vector.ofAll(IterableAccessors.from("1", "2"));
        
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
        System.out.println(Iterations.ofAll(aa).containsAny(Arrays.asList("A", "B", "C", "QUATTRO")));
        System.out.println(Iterations.ofAll(aa).containsAny(Arrays.asList("A", "B", "C", "QUATTRO"), CaseSensitivity.INSENSITIVE.comparator()));
        System.out.println(Iterations.ofAll(aa).containsAll(Arrays.asList("UNO", "DUE", "TRE", "4")));
        System.out.println(Iterations.ofAll(aa).containsAll(Arrays.asList("UNO", "DUE", "TRE", "4"), CaseSensitivity.INSENSITIVE.comparator()));
        StreamIterables.disjunction(aa, aa);
        */
    }
    
    static void findOption() {
        final Option<String> opt = Some("ciao");
        final IterableAccessor<String> i = IterableAccessors.from(opt);
        final Option<String> a = i.first();
        System.out.println(a);
        final Option<String> b = i.findFirst(x -> false);
        System.out.println(b);
    }
    
    static void findVector() {
        final Vector<String> vec = Vector.of("ciao", "come", "stai", "?");
        final IterableAccessor<String> i = IterableAccessors.ofAll(vec);
        final Option<String> a = i.first();
        System.out.println(a);
        final Option<String> b = i.findFirst(x -> false);
        System.out.println(b);
    }

    static void findIterator() {
        final Iterator<String> itr = Arrays.asList("ciao", "come", "stai", "?").iterator();
        final IterableAccessor<String> i = IterableAccessors.from(itr);
        final Option<String> a = i.findFirst(x -> false);
        System.out.println(a);
        final Option<String> b = i.findFirst(x -> false);
        System.out.println(b);
    }

}
