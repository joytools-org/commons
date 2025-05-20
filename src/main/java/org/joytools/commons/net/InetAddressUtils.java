/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.net;

/**
 *
 * @author AndreaR
 */
public class InetAddressUtils {
 
    /**
     * 
     */
    public InetAddressUtils() {
    }
    
    /**
     * 
     * @return 
     */
    public static InetAddressComparator comparator() {
        return InetAddressComparator.instance();
    }
    
}
