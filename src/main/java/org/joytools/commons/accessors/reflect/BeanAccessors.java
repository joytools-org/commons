/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.reflect;

import java.beans.IntrospectionException;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class BeanAccessors {
    
    public static StringMapAccessor<Object> of(final Object bean) throws IntrospectionException {
        return new BeanAccessor(bean, null);
    }

    public static StringMapAccessor<Object> of(final Object bean,
            final CaseSensitivity cs) throws IntrospectionException {
        return new BeanAccessor(bean, cs);
    }
    
}
