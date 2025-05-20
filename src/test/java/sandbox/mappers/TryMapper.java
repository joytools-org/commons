/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.mappers;

import org.joytools.commons.mappers.Mappers;
import java.util.function.Function;
import io.vavr.control.Option;

/**
 *
 * @author AndreaR
 */
public class TryMapper {
    
    public static void main(final String... mapper) {
        final Function<String, Option<String>> m = Mappers.forMapIgnoreCase(
                "Uno", "1",
                "Due", "2",
                "Tre", "3",
                "Quattro", "4",
                "Cinque", "5",
                "Sei");
        System.out.println(m.apply("Uno"));
        System.out.println(m.apply("Dieci"));
    }
    
}
