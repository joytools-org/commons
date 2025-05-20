/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import io.vavr.Value;
import org.joytools.commons.util.Delegated;

/**
 *
 * @author AndreaR
 */
public interface DelegatedValue<T> extends Value<T>, Delegated<T> {
    
}
