/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.control.Either;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public abstract interface ComposedResult<K, V> {
 
    /**
     * 
     * @return 
     */
    public Seq<Either<Exception, V>> entriesSeq();  
    
    /**
     * 
     * @return 
     */
    public Map<K, Either<Exception, V>> entriesMap();
    
    /**
     * 
     * @return 
     */
    public SeqComposedResult<V> asSeq();

    /**
     * 
     * @return 
     */
    public MapComposedResult<K, V> asMap();

    /**
     * 
     * @return 
     */
    public int size();
    
    /**
     * 
     * @return 
     */
    public boolean isEmpty();
    
    /**
     * 
     * @return 
     */
    public boolean isNotEmpty();

    /**
     * 
     * @return 
     */
    public Seq<K> keysSeq();

    /**
     * 
     * @return 
     */
    public Seq<V> valuesSeq();
    
    /**
     * 
     * @return 
     */
    public Map<K, V> valuesMap();

    /**
     * 
     * @return 
     */
    public Seq<V> succeededSeq();
    
    /**
     * 
     * @return 
     */
    public Map<K, V> succeededMap();

    /**
     * 
     * @return 
     */
    public int succeededCount();
    
    /**
     * 
     * @return 
     */
    public double succeededRatio();

    /**
     * 
     * @return 
     */
    public Seq<Exception> failedSeq();

    /**
     * 
     * @return 
     */
    public Map<K, Exception> failedMap();

    /**
     * 
     * @return 
     */
    public int failedCount();
    
    /**
     * 
     * @return 
     */
    public double failedRatio();
    
    /**
     * 
     * @return 
     */
    public boolean isAnySucceeded();

    /**
     * 
     * @return 
     */
    public boolean isAnySucceededOrEmpty();

    /**
     * 
     * @return 
     */
    public boolean isAllSucceeded();

    /**
     * 
     * @return 
     */
    public boolean isAllSucceededOrEmpty();
    
    /**
     * 
     * @return 
     */
    public boolean isAllFailed();
    
    /**
     * 
     * @return 
     */
    public boolean isAnyFailed();
    
    /**
     * 
     * @param <T> 
     * @return  
     * @throws java.lang.Exception 
     */
    public <T> T throwExceptions() throws Exception;
            
}
