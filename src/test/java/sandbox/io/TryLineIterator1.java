/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.io;

import java.io.File;
import java.nio.charset.StandardCharsets;
import org.joytools.commons.collections.IterableAccessors;

import org.joytools.commons.io.LineIterators;

/**
 *
 * @author AndreaR
 */
public class TryLineIterator1 {
    
    public static void main(final String... args) throws Exception {
        try (final var itr = LineIterators.of(new File("c:\\temp\\hcm\\test.csv"), StandardCharsets.UTF_8.displayName())) {
            IterableAccessors.from(itr).forEach(System.out::println);
        }
    }
}
