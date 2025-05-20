/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.naming;

import com.google.common.collect.AbstractIterator;
import io.vavr.collection.Vector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.SearchResult;
import org.apache.commons.collections4.IteratorUtils;
import org.joytools.commons.collections.AbstractFailableIterator;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.function.FailableIterator;
import org.joytools.commons.function.Failables;
import static org.joytools.commons.function.Functions.asString;
import org.joytools.commons.util.Disposer;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class NamingUtils {

    public final static String SR_NAME = "$searchResult.name";
    
    public final static String SR_NAME_IN_NAMESPACE = "$searchResult.nameInNamespace";

    public final static String SR_IS_RELATIVE = "$searchResult.isRelative";

    /**
     * 
     * @param <S>
     * @param sr
     * @return 
     */
    public static <S extends CharSequence> Attributes toAttributes(
            final SearchResult sr) {
        final Attributes attrs = sr.getAttributes();
        attrs.put(SR_NAME, sr.getName());
        attrs.put(SR_NAME_IN_NAMESPACE, sr.getNameInNamespace());
        attrs.put(SR_IS_RELATIVE, String.valueOf(sr.isRelative()));
        return attrs;
    }

    /**
     * 
     * @param <S>
     * @param sr
     * @param returnedAttrs
     * @return 
     */
    public static <S extends CharSequence> Attributes toAttributes(
            final SearchResult sr, 
            final S... returnedAttrs) {
        return withReturnedAttrs(toAttributes(sr), IterableAccessors.from(returnedAttrs));
    }

    /**
     * 
     * @param sr
     * @param returnedAttrs
     * @return 
     */
    public static Attributes toAttributes(
            final SearchResult sr, 
            final Iterable<? extends CharSequence> returnedAttrs) {
        return withReturnedAttrs(toAttributes(sr), returnedAttrs);
    }

    /**
     * 
     * @param <S>
     * @param attrs
     * @param returnedAttrs
     * @return 
     */
    public static <S extends CharSequence> Attributes withReturnedAttrs(
            final Attributes attrs, 
            final S... returnedAttrs) {
        return withReturnedAttrs(attrs, IterableAccessors.from(returnedAttrs));
    }

    /**
     * 
     * @param attrs
     * @param returnedAttrs
     * @return 
     */
    public static Attributes withReturnedAttrs(
            final Attributes attrs, 
            final Iterable<? extends CharSequence> returnedAttrs) {
        final CaseSensitivity cs = CaseSensitivity.forIgnoreCase(attrs.isCaseIgnored());
        final Set<String> idAttrsSet = NamingUtils.ofAll(attrs.getIDs())
                .collect(cs.newSortedSet());
        final Set<String> retAttrsSet = IterableAccessors.ofAll(returnedAttrs)
                .map(asString())
                .collect(cs.newSortedSet());
        retAttrsSet.removeAll(idAttrsSet);
        for (final String id : retAttrsSet) {
            attrs.put(new BasicAttribute(id));
        }
        return attrs;
    }
    
    /**
     * 
     * @param attr
     * @return 
     */
    public static Object get(final Attribute attr) {
        try {
            return attr.get();
        } catch (final NamingException ex) {
            throw new UncheckedNamingException(ex);
        }
    }

    /**
     * 
     * @param <T>
     * @param enumeration
     * @return 
     */
    public static <T> IterableAccessor<T> ofAll(final NamingEnumeration<T> enumeration) {
        // return ofAll(IteratorUtils.asIterator(enumeration));
        if (enumeration == null) {
            return IterableAccessors.ofAll(IteratorUtils.EMPTY_ITERATOR);
        }
        final Iterator<T> itr = toFailableIterator(enumeration)
                .asIterator(Failables.wrapped);
        return IterableAccessors.ofAll(itr);
    }
    
    /**
     * 
     * @param <T>
     * @param enumeration
     * @return 
     */
    public static <T> FailableIterator<T, NamingException> toFailableIterator(final NamingEnumeration<T> enumeration) {
        return new AbstractFailableIterator<T, NamingException>() {
            @Override
            protected T computeNext() throws NamingException {
                if (enumeration == null) {
                    return endOfData();
                }
                try {
                    if (enumeration.hasMore()) {
                       return enumeration.next();
                    }
                    return endOfData();
                } catch (final javax.naming.PartialResultException pre) {
                    return endOfData();
                } 
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param enumeration
     * @return 
     */
    public static <T> Vector<T> toVector(final NamingEnumeration<T> enumeration) {
        if (enumeration == null) {
            return Vector.empty();
        }
        return Vector.ofAll(toArrayListAndClose0(enumeration));
    }

    /**
     * 
     * @param <T>
     * @param enumeration
     * @return 
     */
    static <T> ArrayList<T> toArrayListAndClose0(final NamingEnumeration<T> enumeration) {
        return toArrayListAndClose0(enumeration, -1);
    }

    /**
     * 
     * @param <T>
     * @param enumeration
     * @param expectedSize
     * @return 
     */
    static <T> ArrayList<T> toArrayListAndClose0(final NamingEnumeration<T> enumeration, final int expectedSize) {
        final ArrayList<T> list = new ArrayList(expectedSize > 0 ? expectedSize : 16);
        if (enumeration == null) {
            return list;
        }
        try {
            while (enumeration.hasMore()) {
                list.add(enumeration.next());
            }
            return list;
        } catch (final PartialResultException pre) {
            return list;
        } catch (final NamingException ne) {
            throw new UncheckedNamingException(ne);
        } finally {
            try {
                enumeration.close();
            } catch (final NamingException ne) {
                throw new UncheckedNamingException(ne);
            }
        }
    }

    /**
     * 
     * @param attr
     * @return 
     */
    public static IterableAccessor<Object> toIteration(final Attribute attr) {
        if (attr == null) {
            return IterableAccessors.of();
        }
        try {
            final NamingEnumeration<Object> attrEnum = (NamingEnumeration<Object>)attr.getAll();
            return IterableAccessors.ofAll(toArrayListAndClose0(attrEnum, attr.size()));
        } catch (final NamingException ne) {
            throw new UncheckedNamingException(ne);
        }
    }

    /**
     * 
     * @param attrs
     * @return 
     */
    public static IterableAccessor<Attribute> toIteration(final Attributes attrs) {
        if (attrs == null) {
            return IterableAccessors.of();
        }
        final NamingEnumeration<Attribute> attrEnum = (NamingEnumeration<Attribute>)attrs.getAll();
        return IterableAccessors.ofAll(toArrayListAndClose0(attrEnum, attrs.size()));
    }

    /**
     * 
     * @param attr
     * @return 
     */
    public static Vector<Object> toVector(final Attribute attr) {
        if (attr == null) {
            return Vector.empty();
        }
        try {
            final NamingEnumeration<Object> attrEnum = (NamingEnumeration<Object>)attr.getAll();
            return Vector.ofAll(toArrayListAndClose0(attrEnum, attr.size()));
        } catch (final NamingException ne) {
            throw new UncheckedNamingException(ne);
        }
    }

    /**
     * 
     * @param attrs
     * @return 
     */
    public static Vector<Attribute> toVector(final Attributes attrs) {
        if (attrs == null) {
            return Vector.empty();
        }
        final NamingEnumeration<Attribute> attrEnum = (NamingEnumeration<Attribute>)attrs.getAll();
        return Vector.ofAll(toArrayListAndClose0(attrEnum, attrs.size()));
    }

    /**
     * 
     * @param <T>
     * @param disp
     * @param enumeration
     * @return 
     */
    public static <T> IterableAccessor<T> ofAll(final Disposer<? super NamingException> disp, 
            final NamingEnumeration<T> enumeration) {
        if (enumeration == null) {
            return ofAll(enumeration);
        }
        disp.dispose(enumeration, enumeration::close);
        return ofAll(enumeration);
    }

    /**
     * 
     * @param attr
     * @param index
     * @return 
     */
    public static Object get(final Attribute attr, final int index) {
        try {
            return attr.get(index);
        } catch (final NamingException ex) {
            throw new UncheckedNamingException(ex);
        }
    }

}
