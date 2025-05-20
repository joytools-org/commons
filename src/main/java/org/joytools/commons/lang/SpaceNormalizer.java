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
public enum SpaceNormalizer implements StringFunction<Object> {
    
    ORIGINAL() {
        @Override
        public String eval(final String input) {
            return input;
        }
    },
    
    TRIM() {
        @Override
        public String eval(final String input) {
            return input == null ? null : input.trim();
        }
    },
    
    NONE() {
        @Override
        public String eval(final String input) {
            return StringUtils.deleteWhitespace(input);
        }
    },
    
    SINGLE() {
        @Override
        public String eval(final String input) {
            return StringUtils.normalizeSpace(input);
        }
    };
 
}
