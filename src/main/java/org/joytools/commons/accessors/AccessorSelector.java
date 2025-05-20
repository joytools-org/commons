/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

/**
 * 
 * @param <K>
 * @param <V> 
 */
public interface AccessorSelector<K, V> {

    /**
     * 
     * @return 
     */
    public ListAccessor<V> list();

    /**
     * 
     * @return 
     */
    public ListAccessor<V> singleList();

    /**
     * 
     * @return 
     */
    public ListAccessor<V> nonEmptyList();

    /**
     * 
     * @param minSize
     * @param maxSize
     * @return 
     */
    public ListAccessor<V> sizedList(final int minSize, final int maxSize);

    /**
     * 
     * @return 
     */
    public ListAccessor<V> nonMultipleList();

    /**
     * 
     * @return 
     */
    public MapAccessor<K, V> map();

    /**
     * 
     * @return 
     */
    public MapAccessor<K, V> nonEmptyMap();

}
