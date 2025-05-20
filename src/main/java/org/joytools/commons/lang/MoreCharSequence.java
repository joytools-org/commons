/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

/**
 *
 * @author AndreaR
 */
public interface MoreCharSequence extends Appendable {
    
    /**
     * 
     * @return 
     */
    public int capacity();

    /**
     * 
     * @param capacity 
     * @return  
     */
    public MoreCharSequence ensureCapacity(final int capacity);

    /**
     * 
     * @param start
     * @param end 
     * @return  
     */
    public MoreCharSequence delete(final int start, final int end);

    /**
     * 
     * @param index 
     * @return  
     */
    public MoreCharSequence deleteCharAt(final int index);

    /**
     * 
     * @param index
     * @param cs 
     * @return  
     */
    public MoreCharSequence insert(final int index,
            final CharSequence cs);

    /**
     * 
     * @param index
     * @param c 
     * @return  
     */
    public MoreCharSequence setCharAt(final int index,
            final char c);

    /**
     * 
     * @param len 
     * @return  
     */
    public MoreCharSequence setLength(final int len);

    /**
     * 
     * @param cs
     * @return 
     */
    public int indexOf(final CharSequence cs);

    /**
     * 
     * @param cs
     * @param fromIndex
     * @return 
     */
    public int indexOf(final CharSequence cs,
            final int fromIndex);

    /**
     * 
     * @param cs
     * @return 
     */
    public int indexOfIgnoreCase(final CharSequence cs);

    /**
     * 
     * @param cs
     * @param fromIndex
     * @return 
     */
    public int indexOfIgnoreCase(final CharSequence cs,
            final int fromIndex);
    
}
