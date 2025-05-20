/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.util;

import java.util.ArrayList;
import java.util.List;
import org.joytools.commons.util.Variable;
import org.joytools.commons.util.ResettableVariable;
import org.joytools.commons.util.Variables;

/**
 *
 * @author AndreaR
 */
public class TryVariable {
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final Variable<Boolean> flag1 = Variables.withDefault(true);
        System.out.println(flag1 + " " + flag1.isSet());
        final Variable<Boolean> flag2 = Variables.of((Boolean)null);
        System.out.println(flag2 + " " + flag2.isSet());
        final ResettableVariable<String> identity = Variables.named("identity")
                .withDefault("<Unknown>");
        System.out.println(identity + " " + identity.isSet());
        identity.set("123456");
        System.out.println(identity + " " + identity.isSet());
        identity.reset();
        System.out.println(identity + " " + identity.isSet());
        final ResettableVariable<List<String>> memberOf = Variables.named("memberOf")
                .withDefault(ArrayList::new);
        System.out.println(memberOf + " " + identity.isSet());
        memberOf.get().add("Ciao");
        System.out.println(memberOf + " " + identity.isSet());
        memberOf.reset();
        System.out.println(memberOf + " " + identity.isSet());
    }
    
}
