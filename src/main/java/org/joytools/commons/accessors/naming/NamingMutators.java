/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.naming;

import java.util.Objects;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import org.joytools.commons.accessors.ListMutator;
import org.joytools.commons.accessors.StringMapMutator;
import org.joytools.commons.naming.NamingUtils;

/**
 *
 * @author AndreaR
 */
public class NamingMutators {
    
    /**
     * 
     * @param attr
     * @return 
     */
    public static ListMutator<Object> of(final Attribute attr) {
        return new AttrMutatorImpl(attr);
    }
    
    /**
     * 
     * @param attrs
     * @return 
     */
    public static StringMapMutator<Object> of(final Attributes attrs) {
        return new AttrsMutatorImpl(attrs);
        /*
        Objects.requireNonNull(attrs, "Attributes");
        return new AbstractAttributesMapMutator() {
            @Override
            protected Attributes attributes() {
                return attrs;
            }
        }; */
    }

    /**
     * 
     * @param sr
     * @return 
     */
    public static StringMapMutator<Object> of(final SearchResult sr) {
        Objects.requireNonNull(sr, "SearchResult");
        return of(sr.getAttributes());
    }
    
    /**
     * 
     * @param attrs
     * @param id
     * @return 
     */
    static Object getObjectOrListMutator(final Attributes attrs, final String id) {
        return getObjectOrListMutator(attrs.get(id));
    }

    /**
     * 
     * @param attr
     * @return 
     */
    static Object getObjectOrListMutator(final Attribute attr) {
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
