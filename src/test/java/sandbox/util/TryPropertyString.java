/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.util;

import java.util.function.Function;
import org.joytools.commons.mappers.StringMapperBuilder;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TryPropertyString {
    
    public static void main(final String... args) throws Exception {
        final Function<String, String> f = StringMapperBuilder.ofIgnoreCase()
                .when(StringUtils::isEmpty, "nullOrEmpty")
                .orElse("Nullable found! ;-)")
                .buildFunction();
        System.out.println(f.apply(""));
    }
    
}
