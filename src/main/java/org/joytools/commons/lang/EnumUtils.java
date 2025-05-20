/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.lang;

import io.vavr.Function1;

/**
 *
 * @author AndreaR
 */
public class EnumUtils {
    
    /**
     * 
     * @return 
     */
    public final static Function1<Enum, String> toName() {
        return TO_NAME;
    }
    
    /**
     * 
     * @param e
     * @return 
     */
    public final static String toName(final Enum e) {
        return e == null ? null : e.name();
    }
    
    private final static Function1<Enum, String> TO_NAME = EnumUtils::toName;
    
}
