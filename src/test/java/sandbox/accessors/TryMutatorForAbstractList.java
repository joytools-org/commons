/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.accessors;

import com.google.common.collect.ImmutableList;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import java.util.AbstractList;
import java.util.List;
import org.joytools.commons.accessors.FixedSizeListMutator;
import org.joytools.commons.accessors.FixedSizeMutators;

/**
 *
 * @author AndreaR
 */
public class TryMutatorForAbstractList {
    
    public static void main(final String... args) {
        final Seq<String> seq = Vector.of(
            "Andrea",
            "Marco",
            "Micol",
            "Corrado");
        final List<String> list = new AbstractList<String>(){
            @Override
            public String get(final int index) {
                return seq.get(index);
            }
            @Override
            public int size() {
                return seq.size();
            }
        };
        final List<String> list1 = seq.asJava();
        final List<String> list2 = seq.asJavaMutable();
        System.out.println(list1.getClass().getName());
        System.out.println(list2.getClass().getName());
        final FixedSizeListMutator<String> mut = FixedSizeMutators.of(list2);
        mut.set(0, "Andrea 2");
        System.err.println(list2);
    }
    
}
