/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import static io.vavr.API.None;
import io.vavr.Value;

/**
 *
 * @author AndreaR
 */
public class Values {
    
    public static <T> Value<T> empty() {
        return None();
    }
    
    public static <T> Value<T> empty(final Class<T> type) {
        return None();
    }

}
