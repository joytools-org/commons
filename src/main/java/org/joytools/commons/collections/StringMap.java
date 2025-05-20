/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import org.joytools.commons.util.CaseSensitivitySupport;

import java.util.Map;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public interface StringMap<V> extends Map<String, V>, CaseSensitivitySupport {
       
}
