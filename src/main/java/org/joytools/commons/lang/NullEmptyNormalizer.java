/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author AndreaR
 */
public abstract class NullEmptyNormalizer<T> implements StringFunction<T> {

    /**
     * ORIGINAL
     */
    public final static NullEmptyNormalizer<Object> ORIGINAL = new NullEmptyNormalizer() {

        @Override
        public String eval(final String input) {
            return input;
        }

    };
    
    /**
     * NULL
     */
    public final static NullEmptyNormalizer<Object> NULL = new NullEmptyNormalizer() {
        
        @Override
        public String eval(final String input) {
            return input == null || input.length() == 0 ? null : input;
        }

    };

    /**
     * EMPTY
     */
    public final static NullEmptyNormalizer<Object> EMPTY = new NullEmptyNormalizer() {
        
        @Override
        public String eval(final String input) {
            return input == null ? StringUtils.EMPTY : input;
        }

    };

    /**
     * 
     */
    protected NullEmptyNormalizer() {
    }
    
}
