/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.lang;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.joytools.commons.lang.MultipleException;

/**
 *
 * @author AndreaR
 */
class Commons {
 
    static MultipleException buildMultipleException(final int level) {
        final List<Throwable> t = new ArrayList();
        final int count = (int)(Math.random() * 4 + 1);
        for (int i = 0; i < count; i++) {
            if (level > 0 && Math.random() > 0.5) {
                t.add(buildMultipleException(level - 1));
            } else {
                t.add(new RuntimeException("Level" + level + ",Index" + i));
            }
        }
        return new MultipleException(t);
    }
    
    static void dump(final MultipleException e) {
        dump0(e, 0);
    }
    
    private static void dump0(final MultipleException e, final int level) {
        final String prefix = StringUtils.repeat(" ", level * 2);
        System.out.println(prefix + "MultipleException[" + e.count() + "]");
        int i = 0;
        for (final Throwable t : e) {
            if (t instanceof MultipleException me) {
                dump0(me, level + 1);
            } else {
                System.out.println(prefix + "  " + t.getMessage());
            }
        }
    }
    
}
