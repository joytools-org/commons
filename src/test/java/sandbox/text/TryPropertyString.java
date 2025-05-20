/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.text;

import io.vavr.control.Option;
import org.joytools.commons.collections.MapUtils;
import java.util.function.Function;
import org.joytools.commons.mappers.StringMapperBuilder;
import org.joytools.commons.text.PropertyString;
import org.joytools.commons.text.PropertyStringBuilder;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryPropertyString {
    
    public static void main(final String... args) {
        final StringMapperBuilder<String> builder = StringMapperBuilder.ofIgnoreCase()
                .addMap(MapUtils.insert(CaseSensitivity.INSENSITIVE.newLinkedMap(), 
                        "firstName", " andrea ", 
                        "lastName", " rombaldi ",
                        "name", "Name",
                        "prova", "prova"));
        final Function<String, Option<String>> mapper = builder.build();
        
        /* final Option<String> a = mapper.apply("firstName");
        System.out.println(a); */
        
        final PropertyString ps = PropertyStringBuilder.of(
                    "Ciao ${first${name}|upper|trim} ${last${name}|upper|trim} questa é una ${prova}!" + System.lineSeparator() +
                    "Ciao ${firstName|capitalize|trim} ${lastName|capitalize|trim} questa é la seconda prova Capitalizzata")
                .resolver(mapper)
                .build();
        System.out.println(ps.resolve());
        System.out.println(ps.configuration().escapeMode());
    }
    
}
