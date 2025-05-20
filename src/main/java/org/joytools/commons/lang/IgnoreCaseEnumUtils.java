/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.lang;

import org.apache.commons.lang3.EnumUtils;

/**
 *
 * @author AndreaR
 */
public class IgnoreCaseEnumUtils {
    
    /**
     * 
     * @param <E>
     * @param enumClass
     * @param enumName
     * @return 
     */
    public static <E extends Enum<E>> E getOrNull(final Class<E> enumClass, final CharSequence enumName) {
        return EnumUtils.getEnumIgnoreCase(enumClass, StringUtils.trimToEmpty(enumName));
    }
 
    /**
     * 
     * @param <E>
     * @param enumClass
     * @param enumName
     * @param defaultEnum
     * @return 
     */
    public static <E extends Enum<E>> E getOrElse(final Class<E> enumClass, final CharSequence enumName,
        final E defaultEnum) {
        return EnumUtils.getEnumIgnoreCase(enumClass, StringUtils.trimToEmpty(enumName), defaultEnum);
    }
        
    /**
     * 
     * @param <E>
     * @param enumClass
     * @param enumName
     * @return 
     */
    public static <E extends Enum<E>> E valueOf(final Class<E> enumClass, final CharSequence enumName) {
        final String str = StringUtils.trimToEmpty(enumName);
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Empty enum name for " + enumClass.getSimpleName());
        }
        final E ret = EnumUtils.getEnumIgnoreCase(enumClass, str);
        if (ret != null) {
            return ret;
        }
        throw new IllegalArgumentException("Invalid enum name for " + enumClass.getSimpleName() + ": " + str);
    }

    /**
     * 
     * @param <E>
     * @param enumClass
     * @param enumName
     * @return 
     */
    @Deprecated
    public static <E extends Enum<E>> E old_valueOf(final Class<E> enumClass, final CharSequence enumName) {
        E ret = null;
        final String str = StringUtils.trimToEmpty(enumName);
        if (!str.isEmpty()) {
            ret = EnumUtils.getEnumIgnoreCase(enumClass, str);
        }
        if (ret == null) {
            throw new IllegalArgumentException("Invalid enum name for " + enumClass.getSimpleName() + ": " + str);
        }
        return ret;
    }

}
