/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

/**
 *
 * @author AndreaR
 */
public interface SQLColumnName extends SQLName {
    
    @Override
    default SQLNameType type() {
        return SQLNameType.COLUMN;
    }
    
    public String column();
    
}
