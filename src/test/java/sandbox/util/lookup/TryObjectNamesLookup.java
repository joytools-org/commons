/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.util.lookup;

import io.vavr.control.Option;
import java.util.function.Function;
import org.joytools.commons.lang.CaseSpaceNormalizer;
import org.joytools.commons.util.lookup.LookupFunction;
import org.joytools.commons.util.lookup.StringLookupBuilder;

/**
 *
 * @author AndreaR
 */
public class TryObjectNamesLookup {
    
    public static void main(final String... args) {
        final StringLookupBuilder<Integer> lookup = new StringLookupBuilder<Integer>(true, CaseSpaceNormalizer.SINGLE_SPACE_TO_NULL.castAsString())
                .addAll(0, " zero ")
                .addAll(1, " uno", "one", " EIN")
                .addAll(2, "due", " two" , "zwei");
        
        final LookupFunction<String, Integer> f = lookup.build();
        final Function<String, Option<Integer>> m = f.asMapper();
        
        System.out.println(f.applyOrElse("ZERO", -1));
        System.out.println(f.applyOrElse("ein", -1));
        System.out.println(f.applyOrElse("two", -1));
        System.out.println(f.applyOrElse("quattro", -1));

        System.out.println(m.apply("ZERO"));
        System.out.println(m.apply("ein"));
        System.out.println(m.apply("two"));
        System.out.println(m.apply("quattro"));
        
        final MyEnum e = MyEnum.lookup("uno");
        
    }
    
}
