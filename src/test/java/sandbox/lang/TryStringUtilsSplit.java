/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.lang;

import org.joytools.commons.lang.CharFilter;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TryStringUtilsSplit {
    
    public static void main(final String... args) {
        final String[] str = StringUtils.split("SMTP:andrea.rombaldi@luxottica.com smtp:andrea.rombaldi@luxottica.com", CharFilter.WhiteSpaceCharFilter);
        System.out.println(str);
    }
    
}
