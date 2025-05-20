/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.concurrent;

import org.joytools.commons.concurrent.AtomicSuppliers;

/**
 *
 * @author AndreaR
 */
public class TryAtomicSuppliers {
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(final String... args) throws Exception {
        final var initializer = Commons.failingInitializer();
        final var i = AtomicSuppliers.ofFailable(initializer);

        for (int j = 0; j <= 3; j++) {
            System.out.println("*****************************************");
            try {
                System.out.println(i.get());
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
