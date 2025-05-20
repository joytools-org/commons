/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import io.vavr.Value;
import io.vavr.collection.Traversable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author AndreaR
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {
    
    /**
     * 
     */
    public ArrayUtils() {
    }
    
    /**
     * 
     * @param <T>
     * @param array
     * @return 
     */
    public static <T> int length(T... array) {
        return array == null ? 0 : array.length;
    }
    
    /**
     * 
     * @param array
     * @param start
     * @return 
     */
    public static <T> T[] slice(final T[] array, int start) {
        if (array == null) {
            return null;
        }
        int len = array.length;
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
        return subarray(array, start, len);
    }
    
    /**
     * 
     * @param array
     * @param startIndex
     * @param endIndex
     * @return 
     */
    public static <T> T[] slice(final T[] array, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        final int len = array.length;
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
        return subarray(array, startIndex, endIndex);
    }
    
    /**
     * 
     * @param array
     * @param count
     * @return 
     */
    public static <T> T[] strip(final T[] array, final int count) {
        return strip(array, count, count);
    }

    /**
     * 
     * @param array
     * @param beginCount
     * @param endCount
     * @return 
     */
    public static <T> T[] strip(final T[] array, 
            final int beginCount,
            final int endCount) {
        if (array == null) {
            return null;
        }
        int len = array.length;
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
        return subarray(array, startIndex, endIndex);
    }
    
}
