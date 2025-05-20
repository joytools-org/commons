/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.reflect;

import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class RecordAccessors {

    /**
     * 
     * @param <R>
     * @param record
     * @return 
     */
    public static <R extends Record> RecordAccessor<R> of(final R record) {
        return new RecordAccessorImpl<>(record, CaseSensitivity.SENSITIVE);
    }
    
    /**
     * 
     * @param <R>
     * @param record
     * @param cs
     * @return 
     */
    public static <R extends Record> RecordAccessor<R> of(final R record,
            final CaseSensitivity cs) {
        return new RecordAccessorImpl<>(record, cs);
    }

    /**
     * 
     * @param <R>
     * @param record
     * @return 
     */
    public static <R extends Record> RecordAccessor<R> ofIgnoreCase(final R record) {
        return new RecordAccessorImpl<>(record, CaseSensitivity.INSENSITIVE);
    }

}
