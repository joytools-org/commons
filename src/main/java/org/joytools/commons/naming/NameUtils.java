/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.naming;

import java.util.Objects;
import javax.naming.Name;

/**
 *
 * @author AndreaR
 */
public class NameUtils {
    
    /**
     * 
     * @param name
     * @return 
     */
    public static String getMostSignificantComponent(final Name name) {
        return Objects.requireNonNull(name, "Name").get(0);
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    public static String getLeastSignificantComponent(final Name name) {
        return Objects.requireNonNull(name, "Name").get(name.size() - 1);
    }

}
