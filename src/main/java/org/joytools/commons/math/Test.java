/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.math;

/**
 *
 * @author AndreaR
 */
public class Test {
    
    public static void main(final String... args) {
        final IntScaling s = IntScaling.of(10000, 23534, 0, 100);
        System.out.println(s.scale(12354));
        System.out.println(s.unscale(17));
    }
    
}
