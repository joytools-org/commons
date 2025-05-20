/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.List;

/**
 *
 * @author AndreaR
 */
public class ListUtils {
    
    /**
     * 
     * @param <T>
     * @param list
     * @param start
     * @return 
     */
    public static <T> List<T> slice(final List<T> list, int start) {
        if (list == null) {
            return null;
        }
        int len = list.size();
        if (len == 0) {
            start = 0;
        } else {
            if (start < 0) {
                start += len;
            }
            if (start < 0) {
                start = 0;
            }
            if (start >= len) {
                start = 0;
                len = 0;
            }
        }
        return list.subList(start, len);
    }

    /**
     * 
     * @param list
     * @param startIndex
     * @param endIndex
     * @return 
     */
    public static <T> List<T> slice(final List<T> list, int startIndex, int endIndex) {
        if (list == null) {
            return null;
        }
        final int len = list.size();
        if (len == 0) {
            startIndex = 0;
            endIndex = 0;
        } else {
            if (startIndex < 0) {
                startIndex += len;
            }
            if (endIndex < 0) {
                endIndex += len;
            }
            if (startIndex < 0) {
                startIndex = 0;
            }
            if (endIndex >= len) {
                endIndex = len;
            }
            if (startIndex >= endIndex) {
                startIndex = 0;
                endIndex = 0;
            }
        }
        return list.subList(startIndex, endIndex);
    }
    
    /**
     * 
     * @param <T>
     * @param list
     * @param count
     * @return 
     */
    public static <T> List<T> strip(final List<T> list, final int count) {
        return strip(list, count, count);
    }

    /**
     * 
     * @param <T>
     * @param list
     * @param beginCount
     * @param endCount
     * @return 
     */
    public static <T> List<T> strip(final List<T> list, final int beginCount, final int endCount) {
        if (list == null) {
            return null;
        }
        int len = list.size();
        int startIndex = 0;
        int endIndex = 0;
        if (len != 0) {
            startIndex = Math.max(beginCount, 0);
            endIndex = len - Math.max(endCount, 0);
            if (startIndex < 0) {
                startIndex = 0;
            }
            if (startIndex >= endIndex) {
                startIndex = 0;
                endIndex = 0;
            }
        }
        return list.subList(startIndex, endIndex);
    }    
        
}
