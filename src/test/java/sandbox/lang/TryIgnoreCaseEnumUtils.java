/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.lang;

import org.joytools.commons.lang.IgnoreCaseEnumUtils;

/**
 *
 * @author AndreaR
 */
public class TryIgnoreCaseEnumUtils {
    
    static enum MyEnum {
        A, B, C, D, E;
    }
    
    public static void main(final String... args) {
        final MyEnum one = IgnoreCaseEnumUtils.getOrNull(MyEnum.class, null);
        final MyEnum two = IgnoreCaseEnumUtils.getOrElse(MyEnum.class, null, MyEnum.B);
        final MyEnum three = IgnoreCaseEnumUtils.valueOf(MyEnum.class, " ");
    }
    
}
