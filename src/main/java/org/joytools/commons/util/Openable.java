/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

/**
 *
 * @author AndreaR
 */
public interface Openable extends FailableOpenable<RuntimeException> {
    
    /**
     * 
     */
    @Override
    public void open() throws RuntimeException;
    
    /**
     * 
     * @throws RuntimeException 
     */
    @Override
    public void close() throws RuntimeException;
    
}
