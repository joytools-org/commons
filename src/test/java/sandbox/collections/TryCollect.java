/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import com.google.common.collect.MoreCollectors;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.stream.JsonCollectors;
import org.joytools.commons.collections.IterableAccessors;

/**
 *
 * @author AndreaR
 */
public class TryCollect {
    
    public static void main(final String... args) {
        final String[] data = { "uno", "due", "tre", "quattro", "cinque", "sei" };
        
        final String s1 = IterableAccessors.from(data)
                .collect(Collectors.joining(" ", "{", "}"));
        System.out.println(s1);

        final String s2 = IterableAccessors.from(data)
                .collect(Collectors.joining(" ", "{", "}"));
        System.out.println(s2);

        final List<String> s3 = IterableAccessors.from(data)
                .collect(Collectors.toList());
        System.out.println(s3);

        final List<String> s4 = IterableAccessors.from(data)
                .collect(Collectors.toList());
        System.out.println(s4);
        
        final Optional<String> s5 = IterableAccessors.from(data)
                .top(1)
                .collect(MoreCollectors.toOptional());
        System.out.println(s5);

        final Optional<String> s6 = IterableAccessors.from(data)
                .top(1)
                .collect(MoreCollectors.toOptional());
        System.out.println(s6);

        final JsonArray s7 = IterableAccessors.from(data)
                .map(x -> Json.createValue(x))
                .collect(JsonCollectors.toJsonArray());
        System.out.println(s7);

        final JsonArray s8 = IterableAccessors.from(data)
                .map(x -> Json.createValue(x))
                .collect(JsonCollectors.toJsonArray());
        System.out.println(s8);

    }
    
}
