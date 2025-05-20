/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import org.apache.commons.text.WordUtils;

/**
 *
 * @author AndreaR
 */
public abstract class CaseNormalizer<T> implements StringFunction<T> {

    /**
     * ORIGINAL
     */
    public final static CaseNormalizer<Object> ORIGINAL = new CaseNormalizer() {

        @Override
        public String eval(final String input) {
            return input;
        }
        
    };
    
    /**
     * UPPER
     */
    public final static CaseNormalizer<Object> UPPER = new CaseNormalizer() {
        
        @Override
        public String eval(final String input) {
            return input == null ? null : input.toUpperCase();
        }

    };

    /**
     * LOWER
     */
    public final static CaseNormalizer<Object> LOWER = new CaseNormalizer() {
        
        @Override
        public String eval(final String input) {
            return input == null ? null : input.toLowerCase();
        }

    };

    /**
     * CAPITALIZED
     */
    public final static CaseNormalizer<Object> CAPITALIZED = new CaseNormalizer() {
        
        @Override
        public String eval(final String input) {
            return WordUtils.capitalize(input);
        }

    };

    /**
     * FULLY_CAPITALIZED
     */
    public final static CaseNormalizer<Object> FULLY_CAPITALIZED = new CaseNormalizer() {
        
        @Override
        public String eval(final String input) {
            return WordUtils.capitalizeFully(input);
        }

    };

    /**
     * 
     */
    protected CaseNormalizer() {
    }
                        
}
