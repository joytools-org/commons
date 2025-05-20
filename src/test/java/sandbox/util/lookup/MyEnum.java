/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.util.lookup;

import io.vavr.control.Option;
import java.util.function.Function;
import org.joytools.commons.util.lookup.LookupFunctions;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.interop.Vavr;
import org.joytools.commons.util.lookup.LookupFunction;

/**
 *
 * @author AndreaR
 */
public enum MyEnum {
    
    ONE("uno", "ein"),
    TWO("due"),
    THREE("tre"),
    FOUR("quattro", "quater"),
    FIVE("zinque"),
    SEI("uno");

    MyEnum(final String... names) {
        aliases = IterableAccessors.from(names).to(Vavr.vector());
    }
    
    public Iterable<String> aliases() {
        return aliases;
    }
    
    public static MyEnum lookup(final String name) {
        return LOOKUP.apply(name);
    }
    
    public static MyEnum lookupOrNull(final String name) {
        return LOOKUP.applyOrNull(name);
    }

    public static MyEnum lookupOrElse(final String name, final MyEnum def) {
        return LOOKUP.applyOrElse(name, def);
    }

    private final Iterable<String> aliases;
    
    public static LookupFunction<String, MyEnum> lookup() {
        return LOOKUP;
    }
    
    public static Function<String, Option<MyEnum>> mapper() {
        return LOOKUP.asMapper();
    }

    private final static LookupFunction<String, MyEnum> LOOKUP = LookupFunctions.lazy(() -> LookupFunctions.forEnum(values(), MyEnum::aliases));

}
