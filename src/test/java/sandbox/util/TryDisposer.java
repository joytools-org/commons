/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.util;

import org.joytools.commons.util.DefaultDisposer;

/**
 *
 * @author AndreaR
 */
public class TryDisposer {
    
    public static class C {
        public void close() {
            System.out.println("close()!");
        }
    }
    
    public static void main(final String... args) throws Exception {
        try (final DefaultDisposer disp = DefaultDisposer.create()) {
            // disp.dispose(new C(), "close");
        }
    }
    
}
