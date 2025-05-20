/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.lang;

import org.joytools.commons.util.DefaultDisposer;

/**
 *
 * @author AndreaR
 */
public class TryDisposer {
    
    public static void main(final String... args) throws Exception {
        try (final DefaultDisposer disp = DefaultDisposer.create()) {
            final AutoCloseable a = () -> {
                System.out.println("a.close()");
            };
            final AutoCloseable b = () -> {
                System.out.println("b.close()");
            };
            final AutoCloseable c = () -> {
                System.out.println("c.close()");
            };
            final AutoCloseable d = () -> {
                System.out.println("d.close()");
            };
            final AutoCloseable e = () -> {
                System.out.println("e.close()");
            };
            final AutoCloseable f = () -> {
                System.out.println("f.close()");
            };
            final AutoCloseable g = () -> {
                System.out.println("g.close()");
            };
            disp.dispose(a);
            disp.dispose(b);
            disp.dispose(c);
            disp.dispose(d);
            disp.dispose(e);
            disp.dispose(f);
            disp.dispose(g);
            
            System.out.println("=== closeAll ===");
        }
    }
    
}
