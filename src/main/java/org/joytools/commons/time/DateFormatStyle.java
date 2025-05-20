/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.text.DateFormat;

/**
 *
 * @author AndreaR
 */
public enum DateFormatStyle {
    
    FULL(DateFormat.FULL),
    LONG(DateFormat.LONG),
    MEDIUM(DateFormat.MEDIUM),
    SHORT(DateFormat.SHORT),
    DEFAULT(DateFormat.DEFAULT);
    
    DateFormatStyle(final int style) {
        this.style = style;
    }
    
    public int style() {
        return style;
    }
    
    private final int style;
}
