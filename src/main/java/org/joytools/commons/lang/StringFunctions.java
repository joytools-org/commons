/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

/**
 *
 * @author AndreaR
 */
public class StringFunctions {
    
    /**
     * 
     * @param <S>
     * @param prefix
     * @return 
     */
    public static <S extends CharSequence> StringFunction<S> prepend(
            final S prefix) {
        return wrap(prefix, null);
    }

    /**
     * 
     * @param <S>
     * @param suffix
     * @return 
     */
    public static <S extends CharSequence> StringFunction<S> append(
            final S suffix) {
        return wrap(null, suffix);
    }

    /**
     * 
     * @param <S>
     * @param wrap
     * @return 
     */
    public static <S extends CharSequence> StringFunction<S> wrap(
            final S wrap) {
        return wrap(wrap, wrap);
    }

    /**
     * 
     * @param <S>
     * @param prefix
     * @param postfix
     * @return 
     */
    public static <S extends CharSequence> StringFunction<S> wrap(
            final S prefix,
            final S postfix) {
        return (final String input) -> {
            if (input == null) {
                return null;
            }
            String s = input;
            final String prefixStr = prefix == null ? null : prefix.toString();
            if (prefixStr != null) {
                s = prefixStr + s;
            }
            final String postfixStr = postfix == null ? null : postfix.toString();
            if (postfixStr != null) {
                s = s + postfixStr;
            }
            return s;
        };
    }
    
    /**
     * 
     * @param <S>
     * @param search
     * @param replacement
     * @return 
     */
    public static <S extends CharSequence> StringFunction<S> replace(
            final S search,
            final S replacement) {
        return (final String input) -> StringUtils.replace(input, 
                StringUtils.toString(search), 
                StringUtils.toString(replacement));
    }

    /**
     * 
     * @param <S>
     * @param search
     * @param replacement
     * @return 
     */
    public static <S extends CharSequence> StringFunction<S> replaceIgnoreCase(
            final S search,
            final S replacement) {
        return (final String input) -> StringUtils.replaceIgnoreCase(input, 
                StringUtils.toString(search), 
                StringUtils.toString(replacement));
    }

    /**
     * 
     * @param <S>
     * @param search
     * @param replacement
     * @return 
     */
    public static <S extends CharSequence> StringFunction<S> replaceRepeatedly(
            final S search,
            final S replacement) {
        return (final String input) -> StringUtils.replaceRepeatedly(input, search, replacement);
    }

    /**
     * 
     * @param <S>
     * @param maxWidth
     * @return 
     */
    public static <S extends CharSequence> StringFunction<S> abbreviate(final int maxWidth) {
        return (final String input) -> {
            if (maxWidth < 0) {
                return input;
            }
            return StringUtils.abbreviate(input, maxWidth);
        };
    }

    /**
     * 
     * @param <S>
     * @param maxWidth
     * @return 
     */
    public static <S extends CharSequence> StringFunction<S> truncate(final int maxWidth) {
        return (final String input) -> {
            if (maxWidth < 0) {
                return input;
            }
            return StringUtils.truncate(input, maxWidth);
        };
    }

}
