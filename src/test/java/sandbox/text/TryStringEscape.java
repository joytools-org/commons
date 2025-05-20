/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.text;

import org.apache.commons.text.StringEscapeUtils;

/**
 *
 * @author AndreaR
 */
public class TryStringEscape {
    
    public static void main(final String... args) {
        final String escaped = StringEscapeUtils.ESCAPE_JSON.translate("\n\t");
        System.out.println(escaped);
    }
}
