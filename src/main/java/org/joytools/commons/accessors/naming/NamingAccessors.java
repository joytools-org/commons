/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.naming;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.naming.NamingUtils;

/**
 *
 * @author AndreaR
 */
public class NamingAccessors {
    
    /**
     * 
     * @param attr
     * @return 
     */
    public static ListAccessor<Object> of(final Attribute attr) {
        return new AttrAccessorImpl(attr);
    }

    /**
     * 
     * @param attr
     * @return 
     */
    public static StringMapAccessor<Object> of(final Attributes attr) {
        return new AttrsAccessorImpl(attr);
    }

    /**
     * 
     * @param attrs
     * @param id
     * @return 
     */
    static Object getObjectOrListAccessor(final Attributes attrs, final String id) {
        return getObjectOrListAccessor(attrs.get(id));
    }

    /**
     * 
     * @param attr
     * @return 
     */
    static Object getObjectOrListAccessor(final Attribute attr) {
        switch (attr.size()) {
            case 0:
                return null;
            case 1:
                return NamingUtils.get(attr, 0);
            default:
                return of(attr);
        }
    }
    
}
