/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-public abstract.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

/**
 *
 * @author AndreaR
 */
abstract class AbstractTimeValue<T> implements TimeValue<T> {
    
    /**
     * 
     * @return 
     */
    @Override
    public final int hashCode() {
        if (isEmpty()) {
            return 0;
        }
        return Long.hashCode(timeMillis());
    }
    
    /**
     * 
     * @param o
     * @return 
     */
    @Override
    public final boolean equals(final Object o) {
        if (o == null || !(o instanceof TimeValue)) {
            return false;
        }
        final TimeValue other = TimeValue.class.cast(o);
        final boolean b1 = isEmpty();
        final boolean b2 = other.isEmpty();
        if (b1 && b2) {
            return true;
        }
        if (b1 || b2) {
            return false;
        }
        return timeMillis() == other.timeMillis();
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    @Override
    public final int compareTo(final TimeValue<T> other) {
        final boolean b1 = isEmpty();
        final boolean b2 = other.isEmpty();
        if (b1 && b2) {
            return 0;
        }
        if (b1) {
            return -1;
        }
        if (b2) {
            return 1;
        }
        return Long.compare(timeMillis(), other.timeMillis());
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public final String toString() {
        if (isEmpty()) {
            return "Empty";
        }
        return get().toString();
    }
       
}
