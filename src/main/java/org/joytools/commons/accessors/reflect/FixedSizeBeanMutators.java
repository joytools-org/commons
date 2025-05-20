/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.reflect;

import java.beans.IntrospectionException;
import org.joytools.commons.accessors.FixedSizeStringMapMutator;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class FixedSizeBeanMutators {
    
    public static FixedSizeStringMapMutator<Object> of(final Object bean) throws IntrospectionException {
        return new FixedSizeBeanMutator(bean, null);
    }

    public static FixedSizeStringMapMutator<Object> of(final Object bean,
            final CaseSensitivity cs) throws IntrospectionException {
        return new FixedSizeBeanMutator(bean, cs);
    }
    
}
