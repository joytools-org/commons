/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.net;

import java.net.InetAddress;
import java.util.Comparator;

/**
 *
 * @author AndreaR
 */
public class InetAddressComparator implements Comparator<InetAddress> {

    /**
     * 
     */
    private InetAddressComparator() {
    }
    
    /**
     * 
     * @param inetA
     * @param inetB
     * @return 
     */
    @Override
    public int compare(
            final InetAddress inetA, 
            final InetAddress inetB) {
        checkSameType(inetA, inetB);
        byte[] a = toEmpty(inetA.getAddress()); int al = a.length;
        byte[] b = toEmpty(inetB.getAddress()); int bl = b.length;
        int i = 0;
        int c;
        do {
            if (i < al && i < bl) {
                if ((c = Byte.toUnsignedInt(a[i]) - Byte.toUnsignedInt(b[i])) != 0) {
                    return c;
                }
            } else {
                return al - bl;
            }
            ++i;
        } while (true);
    }
    
    /**
     * 
     * @param a
     * @param b 
     */
    static void checkSameType(final InetAddress a,
            final InetAddress b) {
        final Class ca = a.getClass();
        final Class cb = b.getClass();
        if (ca != cb) {
            throw new ClassCastException(ca.getName() + " could not be compared to " + cb.getName());
        }
    }
    
    /**
     * 
     * @return 
     */
    public static InetAddressComparator instance() {
        return INSTANCE;
    }
    
    /**
     * 
     */
    private final static InetAddressComparator INSTANCE = new InetAddressComparator();
    
    /**
     * 
     * @param b
     * @return 
     */
    static byte[] toEmpty(final byte[] b) {
        return b == null ? EMPTY : b;
    }
    
    /**
     * 
     */
    private final static byte[] EMPTY = new byte[0];
    
}
