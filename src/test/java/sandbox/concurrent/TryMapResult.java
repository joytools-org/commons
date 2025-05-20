/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.concurrent;

import static io.vavr.API.Right;
import io.vavr.collection.LinkedHashMap;
import io.vavr.control.Either;
import org.joytools.commons.concurrent.ComposedResults;
import org.joytools.commons.concurrent.MapComposedResult;

/**
 *
 * @author AndreaR
 */
public class TryMapResult {
    
    public static void main(final String... args) {
        final LinkedHashMap<String, Either<Exception, Integer>> m1 = LinkedHashMap.of(
                "Uno", Right(1),
                "Due", Right(2),
                "Tre", Right(3));
        final LinkedHashMap<String, Either<Exception, Integer>> m2 = LinkedHashMap.of(
                "Tre", Right(3),
                "Quattro", Right(4),
                "Cinque", Right(5));
        final MapComposedResult<String, Integer> a = ComposedResults.ofMap(m1)
                .appendAll(m2);
    }
    
}
