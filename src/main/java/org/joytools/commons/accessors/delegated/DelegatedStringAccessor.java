/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.Accessor;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 * @author AndreaR
 */
public interface DelegatedStringAccessor<V> extends DelegatedAccessor<V>, CaseSensitivitySupport {
    
    @Override
    default CaseSensitivity caseSensitivity() {
        final Accessor<V> acc = delegated();
        if (acc instanceof CaseSensitivitySupport css) {
            return css.caseSensitivity();
        }
        return CaseSensitivity.SENSITIVE;
    }
    
}
