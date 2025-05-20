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
class BeanAccessor extends NakedBeanAccessor implements StringMapAccessor<Object> {
    
    protected BeanAccessor(final Object bean) throws IntrospectionException {
        this(bean, null);
    }

    protected BeanAccessor(final Object bean,
            final CaseSensitivity cs) throws IntrospectionException {
        super(bean, cs);
    }
        
}
