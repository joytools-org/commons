/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.logging;

/**
 * 
 * @author AndreaR
 */
public enum TreeLogWriterOperationEnum {

    OPEN("Begin", '>'),         // 0
    UPDATE("Update", '|'),      // 1
    CLOSE("End", '<');          // 2

    /**
     *
     * @param value
     */
    TreeLogWriterOperationEnum(
            final String altName,
            final char symb) {
        this.altName = altName;
        this.symb = symb;
    }

    /**
     *
     * @param i
     * @return
     */
    public static TreeLogWriterOperationEnum fromInteger(final int i) {
        throw new UnsupportedOperationException("fromInteger()");
    }

    /**
     *
     * @param i
     * @return
     */
    public static TreeLogWriterOperationEnum fromInteger(final Integer i) {
        throw new UnsupportedOperationException("fromInteger()");
    }

    /**
     *
     * @return char
     */
    public char getSymbol() {
        return symb;
    }
    
    /**
     * 
     * @return 
     */
    public String getAlternateName() {
        return altName;
    }

    private final String altName;

    private final char symb;
    
}
