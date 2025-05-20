/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.reflect;

import org.joytools.commons.accessors.StringMapAccessor;

/**
 *
 * @author AndreaR
 * @param <R>
 */
public interface RecordAccessor<R extends Record> extends StringMapAccessor<Object> {
    
    public R toRecord();
    
}
