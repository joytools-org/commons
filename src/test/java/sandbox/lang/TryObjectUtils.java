/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.lang;

import org.joytools.commons.lang.ObjectUtils;

/**
 *
 * @author AndreaR
 */
public class TryObjectUtils {
    
    public boolean clear() throws Exception {
        System.out.println("Clear invoked");
        return true;
    }
    
    public static void main(final String... args) {
        final TryObjectUtils app = new TryObjectUtils();
        System.out.println(ObjectUtils.clear(app));
    }
    
}
