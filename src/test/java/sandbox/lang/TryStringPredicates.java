/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.lang;

import java.util.Set;
import org.apache.commons.text.TextStringBuilder;
import org.joytools.commons.collections.SetUtils;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TryStringPredicates {
    
    public static void main(final String... args) throws Exception {
        final CharSequence csInput = new TextStringBuilder("stai?");
        final Set<CharSequence> csItems = SetUtils.build("Ciao", "come", "stai?");
        // final String s = "Ciao";
        System.out.println(StringUtils.in(csInput, csItems, true));

        final String strInput = new TextStringBuilder("stai?").toString();
        final Set<String> strItems = SetUtils.build("Ciao", "come", "stai?");
        // final String s = "Ciao";
        System.out.println(StringUtils.in(strInput, strItems, true));
    }
    
}
