/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author AndreaR
 */
public record TimeValueUnitImpl(long value, TimeUnit unit) implements TimeValueUnit {
    
}
