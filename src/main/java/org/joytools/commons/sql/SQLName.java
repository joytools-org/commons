/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

/**
 *
 * @author AndreaR
 */
public interface SQLName {
    
    public SQLNameType type();
    
    public String name();
    
    public String schema();
    
    public String catalog();
    
    public String server();
    
    public String fullName();
    
}
