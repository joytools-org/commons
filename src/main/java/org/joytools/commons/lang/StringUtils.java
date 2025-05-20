package org.joytools.commons.lang;

import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.collect.AbstractIterator;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.Value;
import io.vavr.control.Option;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

// import org.apache.commons.text.StrBuilder;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.PredicateUtils;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.joytools.commons.collections.IterableAccessors;
import static org.joytools.commons.function.Functions.asString;
import org.joytools.commons.interop.Java;


// import org.joytools.commons.collections.StreamIterable;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.io.CharSequenceReader;
import java.io.UncheckedIOException;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.vavr.Traversables;
import org.joytools.commons.collections.IterableAccessor;

/**
 * <p>
 * Title: </p>
 *
 * <p>
 * Description: </p>
 *
 * <p>
 * Copyright: Copyright (c) 2005</p>
 *
 * <p>
 * Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     *
     */
    public StringUtils() {
    }

    /**
     * 
     */
    public final static String NULL = null;

    /**
     * 
     */
    private final static String[] EMPTY_ARRAY = new String[0];
    
    /**
     * 
     */
    private final static Comparator<CharSequence> LENGTH_COMPARATOR = (a, b) -> {
        final int al = a == null ? -1 : a.length();
        final int bl = b == null ? -1 : b.length();
        return bl - al;
    };    

    /**
     * 
     * @return 
     */
    public static Comparator<CharSequence> lengthComparator() {
        return LENGTH_COMPARATOR;
    }
            
    /**
     * 
     * @param src
     * @param srcBegin
     * @param srcEnd
     * @param dst
     * @param dstBegin 
     */
    public static void getChars(
            final CharSequence src,
            final int srcBegin, 
            final int srcEnd, 
            final char dst[], 
            final int dstBegin) {
        final int srcLen = src.length();
        if (srcBegin < 0) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        }
        if (srcEnd > srcLen) {
            throw new StringIndexOutOfBoundsException(srcEnd);
        }
        if (srcBegin > srcEnd) {
            throw new StringIndexOutOfBoundsException(srcEnd - srcBegin);
        }
        int srcIndex = srcBegin;
        int dstIndex = dstBegin;
        while (srcIndex < srcEnd) {
            dst[dstIndex++] = src.charAt(srcIndex++);
        }
    }
    
    /**
     * 
     * @param coll
     * @return 
     */
    public static <T> String[] toArray(final Collection<T> coll) {
        if (coll == null || coll.isEmpty()) {
            return EMPTY_ARRAY;
        }
        final String[] arr = new String[coll.size()];
        int i = 0;
        for (final Object o : coll) {
            arr[i++] = toString(o);
        }
        return arr;
    }

    /**
     * 
     * @param strings
     * @return 
     */
    public static String[] toArray(final CharSequence... strings) {
        if (strings == null || strings.length == 0) {
            return EMPTY_ARRAY;
        }
        final String[] elements = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            elements[i] = toString(strings[i]);
        }
        return elements;
    }

    /**
     * 
     * @param strings
     * @return 
     */
    public static String[] toArray(final String... strings) {
        if (strings == null || strings.length == 0) {
            return EMPTY_ARRAY;
        }
        return strings;
    }

    /**
     * 
     * @param arr
     * @return 
     */
    public static <T> String[] toArray(final T[] arr) {
        if (arr == null || arr.length == 0) {
            return EMPTY_ARRAY;
        }
        final String[] res = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = toString(arr[i]);
        }
        return res;
    }

    /**
     * 
     * @param <T>
     * @param itr
     * @return 
     */
    public static <T> String[] toArray(final Iterable<T> itr) {
        if (itr == null) {
            return EMPTY_ARRAY;
        }
        if (itr instanceof Collection coll) {
            return toArray(coll);
        }
        return Traversables.ofAll(itr)
                .map(asString())
                .toJavaArray(String[]::new);
    }

    /**
     * 
     * @param itr
     * @return 
     */
    public static <T> String[] toArray(final Iterator<T> itr) {
        if (itr == null) {
            return EMPTY_ARRAY;
        }
        return toArray(Traversables.ofAll(itr));
    }

    /**
     * 
     * @param e
     * @return 
     */
    public static <T> String[] toArray(final Enumeration<T> e) {
        if (e == null) {
            return EMPTY_ARRAY;
        }
        return toArray(Traversables.ofAll(e));
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static boolean isNotNumeric(final CharSequence cs) {
        return !isNumeric(cs);
    }
    
    /**
     * 
     * @return 
     */
    @Deprecated
    public static StringBuffer newStringBuffer_old() {
        return new StringBuffer();
    }
    
    /**
     * 
     * @param <S>
     * @param string
     * @param searchStrings
     * @return 
     */
    public static <S extends CharSequence> boolean equalsAny(
            final CharSequence string, 
            final Iterable<S> searchStrings) {
        if (searchStrings != null) {
            if (searchStrings instanceof Collection coll) {
                if (coll.contains(string)) {
                    return true;
                }
            }
            if (searchStrings instanceof Value val) {
                if (val.contains(string)) {
                    return true;
                }
            }
            for (final CharSequence s : searchStrings) {
                if (equals(string, s)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 
     * @param <S>
     * @param string
     * @param searchStrings
     * @return 
     */
    public static <S extends CharSequence> boolean equalsAnyIgnoreCase(
            final CharSequence string, 
            final Iterable<S> searchStrings) {
        if (searchStrings != null) {
            if (searchStrings instanceof Collection coll) {
                if (coll.contains(string)) {
                    return true;
                }
            }
            if (searchStrings instanceof Value val) {
                if (val.contains(string)) {
                    return true;
                }
            }
            for (final CharSequence s : searchStrings) {
                if (equalsIgnoreCase(string, s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param <S>
     * @param string
     * @param searchStrings
     * @return 
     */
    public static <S extends CharSequence> boolean containsAnyIgnoreCase(
            final CharSequence string, 
            final Iterable<S> searchStrings) {
        if (searchStrings != null) {
            if (searchStrings instanceof Collection coll) {
                if (coll.contains(string)) {
                    return true;
                }
            } 
            if (searchStrings instanceof Value val) {
                if (val.contains(string)) {
                    return true;
                }
            }
            for (final CharSequence s : searchStrings) {
                if (containsIgnoreCase(string, s)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 
     */
    private final static Function<?, CharSequence> CAST_TO_CHARSEQUENCE_FUNCTION = (final Object input) -> (CharSequence.class.cast(input));
   
    /**
     * 
     * @param cs
     * @param matcher
     * @return 
     */
    public static int countMatches(final CharSequence cs, final CharMatcher matcher) {
        if (StringUtils.isEmpty(cs)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < cs.length(); i++) {
            if (matcher.matches(cs.charAt(i))) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * 
     * @param cs
     * @param matcher
     * @return 
     */
    public static int countMatches(final CharSequence cs, final Predicate<Character> matcher) {
        if (StringUtils.isEmpty(cs)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < cs.length(); i++) {
            if (matcher.test(cs.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    /**
     * 
     * @param <S>
     * @param <I>
     * @param input
     * @param items
     * @param ignoreCase
     * @return 
     */
    public static <S extends CharSequence, I extends Iterable<S>> boolean in(
            final CharSequence input, 
            final I items, 
            final boolean ignoreCase) {
        Objects.requireNonNull(input, "Input");
        Objects.requireNonNull(items, "Items");
        if (!ignoreCase && items instanceof Collection coll) {
            return coll.contains(input);
        }
        final Function<S, CharSequence> f = (final S cs) -> (CharSequence.class.cast(cs));
        final Iterable<CharSequence> csItems = (Iterable<CharSequence>)items;
        throw new UnsupportedOperationException();
        /*
        final Equator<CharSequence> eq = ignoreCase ? 
                Equators.caseInsensitive(F) : Equators.caseSensitive(F);
        return StreamIterables.of(csItems).contains(input, eq);
        */
        /*
        final Equator<CharSequence> eq = ignoreCase ? 
                Equators.caseInsensitive((final S cs) -> CharSequence.class.cast(cs)) : 
                Equators.caseSensitive((final S cs) -> CharSequence.class.cast(cs));
        return StreamIterables.of(csItems).contains(input, eq);
        */
    }

    /**
     * 
     * @param capacity
     * @return 
     */
    @Deprecated
    public static StringBuffer newStringBuffer_old(final int capacity) {
        return new StringBuffer(capacity);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    @Deprecated
    public static StringBuffer newStringBuffer_old(final CharSequence cs) {
        if (cs == null) {
            return new StringBuffer();
        }
        return new StringBuffer(cs);
    }

    /**
     * 
     * @return 
     */
    @Deprecated
    public static StringBuilder newStringBuilder_old() {
        return new StringBuilder();
    }
    
    /**
     * 
     * @param capacity
     * @return 
     */
    @Deprecated
    public static StringBuilder newStringBuilder_old(final int capacity) {
        return new StringBuilder(capacity);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    @Deprecated
    public static StringBuilder newStringBuilder_old(final CharSequence cs) {
        if (cs == null) {
            return new StringBuilder();
        }
        return new StringBuilder(cs);
    }

    /**
     * 
     * @return 
     */
    @Deprecated
    public static org.apache.commons.text.TextStringBuilder newTextStringBuilder_old() {
        return new org.apache.commons.text.TextStringBuilder();
    }

    /**
     * 
     * @param capacity
     * @return 
     */
    @Deprecated
    public static org.apache.commons.text.TextStringBuilder newTextStringBuilder_old(final int capacity) {
        return new org.apache.commons.text.TextStringBuilder(capacity);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    @Deprecated
    public static org.apache.commons.text.TextStringBuilder newTextStringBuilder_old(final CharSequence cs) {
        final org.apache.commons.text.TextStringBuilder result = newTextStringBuilder_old();
        if (cs != null) {
            result.append(cs);
        }
        return result;
    }

    /**
     * 
     * @param <T>
     * @param strings
     * @return 
     */
    public static <T extends CharSequence> T firstNonNull(final T... strings) {
        if (strings == null) {
            return null;
        }
        return firstNonNull(Arrays.asList(strings));
    }

    /**
     * 
     * @param <T>
     * @param iterable
     * @return 
     */
    public static <T extends CharSequence> T firstNonNull(final Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        return IterableUtils.find(iterable, PredicateUtils.notNullPredicate());
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    @Deprecated
    public static <T extends CharSequence> T old_firstNonNull(final Iterator<T> iterator) {
        if (iterator == null) {
            return null;
        }
        return IteratorUtils.find(iterator, PredicateUtils.notNullPredicate());
    }

    /**
     * 
     * @param <T>
     * @param iterable
     * @return 
     */
    public static <T extends CharSequence> T firstNonEmpty(final Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        return IterableUtils.find(iterable, StringUtils::isNotEmpty);
    }

    /**
     * 
     * @param <T>
     * @param strings
     * @return 
     */
    public static <T extends CharSequence> T firstNonEmpty(final T... strings) {
        if (strings == null) {
            return null;
        }
        return firstNonEmpty(Arrays.asList(strings));
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    @Deprecated
    public static <T extends CharSequence> T old_firstNonEmpty(final Iterator<T> iterator) {
        if (iterator == null) {
            return null;
        }
        return IteratorUtils.find(iterator, StringUtils::isNotEmpty);
    }

    /**
     * 
     * @param <C>
     * @param cs
     * @return 
     */
    public static <C extends CharSequence> C requireNonEmpty(final C cs) {
        if (isEmpty(cs)) {
            throw new IllegalArgumentException("CharSequence is empty");
        }
        return cs;
    }

    /**
     * 
     * @param <C>
     * @param cs
     * @param message
     * @return 
     */
    public static <C extends CharSequence> C requireNonEmpty(final C cs, final String message) {
        if (isEmpty(cs)) {
            throw new IllegalArgumentException(message);
        }
        return cs;
    }
    
    /**
     * 
     * @param <C>
     * @param cs
     * @param messageSupplier
     * @return 
     */
    public static <C extends CharSequence> C requireNonEmpty(final C cs, final Supplier<String> messageSupplier) {
        if (isEmpty(cs)) {
            throw new IllegalArgumentException(messageSupplier.get());
        }
        return cs;
    }
    
    /**
     * 
     * @param cs
     * @param test
     * @return 
     */
    public static int indexOf(final CharSequence cs, final Predicate<Character> test) {
        return indexOf(cs, test, 0);
    }

    /**
     * 
     * @param cs
     * @param test
     * @param fromIndex
     * @return 
     */
    public static int indexOf(final CharSequence cs, final Predicate<Character> test, final int fromIndex) {
        final int len = cs == null ? 0 : cs.length();
        if (len == 0 || test == null || fromIndex >= len) {
            return INDEX_NOT_FOUND;
        }
        for (int i = fromIndex; i < len; i++) {
            if (test.test(cs.charAt(i))) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 
     * @param cs
     * @param test
     * @return 
     */
    public static int lastIndexOf(final CharSequence cs, final Predicate<Character> test) {
        final int len = cs == null ? 0 : cs.length();
        if (len == 0 || test == null) {
            return INDEX_NOT_FOUND;
        }
        for (int i = len; i > 0; ) {
            if (test.test(cs.charAt(--i))) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 
     * @param s
     * @return 
     */
    public static org.apache.commons.text.TextStringBuilder trim(final org.apache.commons.text.TextStringBuilder s) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.SpaceCharFilter);
        return s;
    }

    /**
     *
     * @param s
     * @return StringBuffer
     */
    public static StringBuffer trim(final StringBuffer s) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.SpaceCharFilter);
        return s;
    }

    /**
     *
     * @param s
     * @return StringBuilder
     */
    public static StringBuilder trim(final StringBuilder s) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.SpaceCharFilter);
        return s;
    }

    /**
     * 
     * @param cs
     * @param search
     * @param replacement
     * @return 
     */
    public static String replaceRepeatedly(final CharSequence cs, final CharSequence search, final CharSequence replacement) {
        return replaceRepeatedly(cs, search, replacement, false);
    }
    
    /**
     * 
     * @param cs
     * @param search
     * @param replacement
     * @return 
     */
    public static String replaceRepeatedlyIgnoreCase(final CharSequence cs, final CharSequence search, final CharSequence replacement) {
        return replaceRepeatedly(cs, search, replacement, true);
    }

    /**
     * 
     * @param str
     * @param search
     * @param replacement
     * @param ignoreCase
     * @return 
     */
    public static String replaceRepeatedly(final CharSequence str, final CharSequence search, final CharSequence replacement, boolean ignoreCase) {
        String oldStr = StringUtils.toString(str);
        if (isEmpty(str) || isEmpty(search)) {
            return oldStr;
        }
        final String searchStr = toString(search);
        final String replStr = toString(replacement);
        final CaseSensitivity cs = CaseSensitivity.forIgnoreCase(ignoreCase);
        while (true) {
            final String newStr = cs.replace(oldStr, searchStr, replStr);
            if (cs.equals(newStr, oldStr)) {
                return newStr;
            }
            oldStr = newStr;
        }
    }

    /**
     * 
     * @param cs
     * @param filter
     * @return 
     */
    public static String stripChars(final CharSequence cs,
            final CharMatcher filter) {
        if (cs == null) {
            return null;
        }
        if (cs.length() == 0) {
            return StringUtils.EMPTY;
        }
        final StringBuilder sb = new StringBuilder(cs);
        return stripChars(sb, filter).toString();
    }

    /**
     *
     * @param s StringBuilder
     * @param filter CharFilter
     * @return StringBuilder
     */
    public static StringBuilder stripChars(final StringBuilder s,
            final CharMatcher filter) {
        stripChars(s, MoreCharSequenceSupport.of(s), filter);
        return s;
    }

    /**
     * 
     * @param s
     * @param filter
     * @return 
     */
    public static org.apache.commons.text.TextStringBuilder stripChars(final org.apache.commons.text.TextStringBuilder s,
            final CharMatcher filter) {
        stripChars(s, MoreCharSequenceSupport.of(s), filter);
        return s;
    }

    /**
     *
     * @param s StringBuffer
     * @param filter CharFilter
     * @return StringBuffer
     */
    public static StringBuffer stripChars(final StringBuffer s,
            final CharMatcher filter) {
        stripChars(s, MoreCharSequenceSupport.of(s), filter);
        return s;
    }

    /**
     *
     * @param t T
     * @param chars CharSequence
     * @return T
     */
    public static StringBuilder stripChars(final StringBuilder t,
            final CharSequence chars) {
        stripChars(t, MoreCharSequenceSupport.of(t), CharFilter.of(chars));
        return t;
    }

    /**
     * 
     * @param t
     * @param chars
     * @return 
     */
    public static org.apache.commons.text.TextStringBuilder stripChars(final org.apache.commons.text.TextStringBuilder t,
            final CharSequence chars) {
        stripChars(t, MoreCharSequenceSupport.of(t), CharFilter.of(chars));
        return t;
    }

    /**
     *
     * @param t StringBuffer
     * @param chars CharSequence
     * @return StringBuffer
     */
    public static StringBuffer stripChars(final StringBuffer t,
            final CharSequence chars) {
        stripChars(t, MoreCharSequenceSupport.of(t), CharFilter.of(chars));
        return t;
    }

    /**
     *
     * @param s T
     * @param chars CharSequence
     * @param ignoreCase boolean
     * @return T
     */
    public static StringBuilder stripChars(final StringBuilder s,
            final CharSequence chars,
            final boolean ignoreCase) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.of(chars, ignoreCase));
        return s;
    }

    /**
     *
     * @param s StringBuffer
     * @param chars CharSequence
     * @param ignoreCase boolean
     * @return StringBuffer
     */
    public static StringBuffer stripChars(final StringBuffer s,
            final CharSequence chars,
            final boolean ignoreCase) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.of(chars, ignoreCase));
        return s;
    }

    /**
     *
     * @param t ModifiableCharSequence
     * @param chars CharSequence
     * @param ignoreCase boolean
     * @return ModifiableCharSequence
     */ /*
    private static <M extends ModifiableCharSequence> M old_stripChars(final M t,
            final CharSequence chars,
            final boolean ignoreCase) {
        stripChars(t, CharFilter.of(chars, ignoreCase));
        return t;
    } */

    /**
     *
     * @param t ModifiableCharSequence
     * @param chars CharSequence
     * @param ignoreCase boolean
     * @return ModifiableCharSequence
     */ /*
    public static <M extends ModifiableCharSequence> M stripChars(final M t,
            final CharSequence chars) {
        stripChars(t, CharFilter.of(chars));
        return t;
    } */

    /**
     *
     * @param t CharSequence
     * @return CharSequence
     */
    public static CharSequence stripWhitespace(final CharSequence t) {
        if (t == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder(t);
        return stripChars(sb, CharFilter.WhiteSpaceCharFilter);
    }

    /**
     * 
     * @param t
     * @return 
     */
    public static String stripWhitespace(final String t) {
        if (t == null) {
            return null;
        }
        final StringBuilder sb = stripChars(new StringBuilder(t), 
                CharFilter.WhiteSpaceCharFilter);
        if (t.length() == sb.length()) {
            return t;
        }
        return sb.toString();
    }

    /**
     *
     * @param t StringBuilder
     * @return StringBuilder
     */ /*
    public static <M extends ModifiableCharSequence> M stripWhitespace(final M t) {
        stripChars(t, CharFilter.WhiteSpaceCharFilter);
        return t;
    } */

    /**
     *
     * @param s StringBuilder
     * @return StringBuilder
     */
    public static StringBuffer stripWhitespace(final StringBuffer s) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.WhiteSpaceCharFilter);
        return s;
    }

    /**
     *
     * @param s StringBuilder
     * @return StringBuilder
     */
    public static StringBuilder stripWhitespace(final StringBuilder s) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.WhiteSpaceCharFilter);
        return s;
    }

    /**
     * 
     * @param s
     * @return 
     */
    public static org.apache.commons.text.TextStringBuilder stripWhitespace(final org.apache.commons.text.TextStringBuilder s) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.WhiteSpaceCharFilter);
        return s;
    }

    /**
     * 
     * @param str
     * @param separator
     * @return 
     */
    public static String substringAfterIgnoreCase(final String str, final String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        final int pos = indexOfIgnoreCase(str, separator);
        if (pos == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 
     * @param str
     * @param separator
     * @return 
     */
    public static String substringBeforeIgnoreCase(final String str, final String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.isEmpty()) {
            return EMPTY;
        }
        final int pos = indexOfIgnoreCase(str, separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 
     * @param str
     * @param open
     * @param close
     * @return 
     */
    public static String substringBetweenIgnoreCase(final String str, final String open, final String close) {
        if (!ObjectUtils.allNotNull(str, open, close)) {
            return null;
        }
        final int start = indexOfIgnoreCase(str, open);
        if (start != INDEX_NOT_FOUND) {
            final int end = indexOfIgnoreCase(str, close, start + open.length());
            if (end != INDEX_NOT_FOUND) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }
    
    /**
     *
     * @param s T
     * @param chars CharSequence
     * @return T
     */
    public static StringBuilder stripCharsIgnoreCase(final StringBuilder s,
            final CharSequence chars) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.of(chars, true));
        return s;
    }

    /**
     * 
     * @param s
     * @param chars
     * @return 
     */
    public static org.apache.commons.text.TextStringBuilder stripCharsIgnoreCase(final org.apache.commons.text.TextStringBuilder s,
            final CharSequence chars) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.of(chars, true));
        return s;
    }

    /**
     *
     * @param s StringBuffer
     * @param chars CharSequence
     * @return StringBuffer
     */
    public static StringBuffer stripCharsIgnoreCase(final StringBuffer s,
            final CharSequence chars) {
        stripChars(s, MoreCharSequenceSupport.of(s), CharFilter.of(chars, true));
        return s;
    }

    /**
     *
     * @param t ModifiableCharSequence
     * @param chars CharSequence
     * @return ModifiableCharSequence
     */ /*
    public static <M extends ModifiableCharSequence> M stripCharsIgnoreCase(final M t,
            final CharSequence chars) {
        stripChars(t, CharFilter.of(chars, true));
        return t;
    } */

    /**
     * 
     * @param cs
     * @param mcs
     * @param filter 
     */
    protected static void stripChars(final CharSequence cs, 
            final MoreCharSequence mcs,
            final CharMatcher filter) {
        int len;
        if (cs == null || (len = cs.length()) == 0 || filter == null) {
            return;
        }
        int newLen = len;
        while (filter.matches(cs.charAt(newLen - 1)) && --newLen >= 0) {
        }
        if (newLen != len) {
            mcs.setLength(newLen);
        }
        if (newLen > 0) {
            int end = -1;
            while (filter.matches(cs.charAt(++end)) && end < newLen) {
            }
            if (end > 0) {
                mcs.delete(0, end);
            }
        }
    }

    /**
     * 
     * @param cs
     * @param mcs
     * @param removeFilter 
     */
    protected static void removeChars(final CharSequence cs, 
            final MoreCharSequence mcs,
            final CharMatcher removeFilter) {
        int l = 0;
        if (cs == null || (l = cs.length()) == 0 || removeFilter == null) {
            return;
        }
        for (int i = 0; i < l; i++) {
            final char c = cs.charAt(i);
            if (removeFilter.matches(c)) {
                mcs.deleteCharAt(i);
                i--;
                l--;
            }
        }
    }

    /**
     *
     * @param cs
     * @param removeFilter CharFilter
     * @return CharSequence
     */
    public static String removeChars(final CharSequence cs,
            final CharMatcher removeFilter) {
        if (cs == null) {
            return null;
        }
        if (cs.length() == 0) {
            return EMPTY;
        }
        final StringBuilder b = new StringBuilder(cs);
        removeChars(b, MoreCharSequenceSupport.of(b), removeFilter);
        return b.toString();
    }

    /**
     *
     * @param s StringBuilder
     * @param removeFilter CharFilter
     * @return StringBuilder
     */
    public static StringBuilder removeChars(final StringBuilder s,
            final CharMatcher removeFilter) {
        removeChars(s, MoreCharSequenceSupport.of(s), removeFilter);
        return s;
    }

    /**
     *
     * @param s StringBuffer
     * @param removeFilter CharFilter
     * @return StringBuffer
     */
    public static StringBuffer removeChars(final StringBuffer s,
            final CharMatcher removeFilter) {
        removeChars(s, MoreCharSequenceSupport.of(s), removeFilter);
        return s;
    }


    /**
     * 
     * @param s
     * @param removeFilter
     * @return 
     */
    public static org.apache.commons.text.TextStringBuilder removeChars(final org.apache.commons.text.TextStringBuilder s,
            final CharMatcher removeFilter) {
        removeChars(s, MoreCharSequenceSupport.of(s), removeFilter);
        return s;
    }

    /**
     *
     * @param cs CharSequence
     * @param passFilter CharFilter
     * @return Object
     */ /*
    protected static String removeCharsV1(final CharSequence cs,
            final CharMatcher passFilter) {
        int l = 0;
        if (cs == null || (l = cs.length()) == 0 || passFilter == null) {
            return null;
        }
        StringBuilder ret = null;
        StringBuffer strbuf = cs instanceof StringBuffer ? (StringBuffer) cs : null;
        StringBuilder strbld = cs instanceof StringBuilder ? (StringBuilder) cs : null;
        StrBuilder sbld = cs instanceof StrBuilder ? (StrBuilder) cs : null;
        for (int i = 0; i < l; i++) {
            final char c = cs.charAt(i);
            if (passFilter.matches(c)) {
                if (ret != null) {
                    ret.append(ret);
                }
            } else {
                if (strbuf != null) {
                    strbuf.deleteCharAt(i);
                    i--;
                    l--;
                } else if (strbld != null) {
                    strbld.deleteCharAt(i);
                    i--;
                    l--;
                } else if (sbld != null) {
                    sbld.deleteCharAt(i);
                    i--;
                    l--;
                } else if (ret == null) {
                    ret = new StringBuilder(l);
                    ret.append(cs.subSequence(0, i));
                }
            }
        }
        return ret != null ? ret.toString() : null;
    } */

    /**
     * 
     * @param <A>
     * @param dest
     * @param str
     * @param count
     * @return 
     */
    public static <A extends Appendable> A repeat(final A dest,
            final CharSequence str,
            int count) {
        if (dest == null || StringUtils.isEmpty(str) || count <= 0) {
            return dest;
        }
        try {
            while (count-- > 0) {
                    dest.append(str);
            }
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
        return dest;
    }

    /**
     * 
     * @param cs
     * @param mcs
     * @param str
     * @param count 
     */
    protected static void repeat(final CharSequence cs,
            final MoreCharSequence mcs,
            final CharSequence str,
            int count) {
        if (cs == null || StringUtils.isEmpty(str) || count <= 0) {
            return;
        }
        mcs.ensureCapacity(cs.length() + count * str.length() + 32);
        repeat(mcs, str, count);
    }

    /**
     * 
     * @param <M>
     * @param mcs
     * @param str
     * @param count
     * @return 
     */ /*
    public static <M extends ModifiableCharSequence> M repeat(final M mcs,
            final CharSequence str,
            int count) {
        if (mcs == null || StringUtils.isEmpty(str) || count <= 0) {
            return mcs;
        }
        mcs.ensureCapacity(mcs.length() + count * str.length() + 16);
        repeat(mcs, str, count);
        return mcs;
    } */

    /**
     * 
     * @param s
     * @param str
     * @param count
     * @return 
     */
    public static StringBuilder repeat(final StringBuilder s,
            final CharSequence str,
            int count) {
        repeat(s, MoreCharSequenceSupport.of(s), str, count);
        return s;
    }

    /**
     * 
     * @param s
     * @param str
     * @param count
     * @return 
     */
    public static StringBuffer repeat(final StringBuffer s,
            final CharSequence str,
            int count) {
        repeat(s, MoreCharSequenceSupport.of(s), str, count);
        return s;
    }

    /**
     * 
     * @param s
     * @param str
     * @param count
     * @return 
     */
    public static org.apache.commons.text.TextStringBuilder repeat(final org.apache.commons.text.TextStringBuilder s,
            final CharSequence str,
            int count) {
        repeat(s, MoreCharSequenceSupport.of(s), str, count);
        return s;
    }

    /**
     * 
     * @param <S>
     * @param lines
     * @return 
     */
    public static <S extends CharSequence> String joinLines(final S... lines) {
        return join(lines, System.lineSeparator());
    }
    
    /**
     * 
     * @param <S>
     * @param lines
     * @return 
     */
    public static <S extends CharSequence> String joinLines(final Iterable<S> lines) {
        return join(lines, System.lineSeparator());
    }

    /**
     * 
     * @param iterable
     * @param separator
     * @return 
     */
    public static String join(final Iterable<?> iterable, 
            final CharSequence separator) {
        return join(iterable, toString(separator));
    }

    /**
     * 
     * @param <A>
     * @param dest
     * @param i
     * @param del
     * @param prefix
     * @param postfix
     * @param max
     * @param t
     * @return 
     */
    protected static <A extends Appendable> Object join0(final A dest,
            final Iterator i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final int max,
            final Function t) {
        if (dest == null) {
            return null;
        }
        try {
            if (i != null) {
                final boolean remove = max > 0;
                for (int index = 0;
                        i.hasNext() && ((!remove) || (remove && index < max)); index++) {
                    if (index > 0 && !isEmpty(del)) {
                        dest.append(del);
                    }
                    if (!isEmpty(prefix)) {
                        dest.append(prefix);
                    }
                    final Object o = i.next();
                    String s = o.toString();
                    if (t != null) {
                        final Object ss = t.apply(s);
                        s = ss == null ? null : ss.toString();
                    }
                    dest.append(s);
                    if (!isEmpty(postfix)) {
                        dest.append(postfix);
                    }
                    if (remove) {
                        i.remove();
                    }
                }
            }
            return dest;
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    /**
     *
     * @param i Iterator
     * @param del String
     * @param prefix String
     * @param postfix String
     * @param max int
     * @param t
     * @return String
     */
    public static String join(final Iterator i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final int max,
            final Function t) {
        final StringBuilder b = new StringBuilder();
        join0(b, i, del, prefix, postfix, max, t);
        return b.toString();
    }

    /**
     * 
     * @param i
     * @param del
     * @param prefix
     * @param postfix
     * @param max
     * @param t
     * @return 
     */
    public static String join(final Iterable i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final int max,
            final Function t) {
        final StringBuilder b = new StringBuilder();
        join0(b, i == null ? null : i.iterator(), del, prefix, postfix, max, t);
        return b.toString();
    }

    
    /**
     *
     * @param <A>
     * @param buf
     * @param i Iterator
     * @param del String
     * @param prefix String
     * @param postfix String
     * @param max int
     * @param t Transformer
     * @return StrBuilder
     */
    public static <A extends Appendable> A join(final A buf,
            final Iterator i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final int max,
            final Function t) {
        join0(buf, i, del, prefix, postfix, max, t);
        return buf;
    }

    /**
     * 
     * @param <A>
     * @param buf
     * @param i
     * @param del
     * @param prefix
     * @param postfix
     * @param max
     * @param t
     * @return 
     */
    public static <A extends Appendable> A join(final A buf,
            final Iterable i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final int max,
            final Function t) {
        join0(buf, i == null ? null : i.iterator(), del, prefix, postfix, max, t);
        return buf;
    }

    /**
     *
     * @param <A>
     * @param buf
     * @param i Iterator
     * @param del String
     * @param prefix String
     * @param postfix String
     * @param max int
     * @return StrBuilder
     */
    public static <A extends Appendable> A join(final A buf,
            final Iterator i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final int max) {
        join0(buf, i, del, prefix, postfix, max, null);
        return buf;
    }

    /**
     * 
     * @param <A>
     * @param buf
     * @param i
     * @param del
     * @param prefix
     * @param postfix
     * @param max
     * @return 
     */
    public static <A extends Appendable> A join(final A buf,
            final Iterable i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final int max) {
        join0(buf, i == null ? null : i.iterator(), del, prefix, postfix, max, null);
        return buf;
    }

    /**
     * 
     * @param <A>
     * @param buf
     * @param i
     * @param del
     * @param prefix
     * @param postfix
     * @return 
     */
    public static <A extends Appendable> A join(final A buf,
            final Iterator i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix) {
        join0(buf, i, del, prefix, postfix, 0, null);
        return buf;
    }

    /**
     * 
     * @param <A>
     * @param buf
     * @param i
     * @param del
     * @param prefix
     * @param postfix
     * @return 
     */
    public static <A extends Appendable> A join(final A buf,
            final Iterable i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix) {
        join0(buf, i == null ? null : i.iterator(), del, prefix, postfix, 0, null);
        return buf;
    }

    /**
     *
     * @param i Iterator
     * @param del String
     * @param max int
     * @return String
     */
    public static String join(final Iterator i,
            final CharSequence del,
            final int max) {
        final StringBuilder b = new StringBuilder();
        join0(b, i, del, null, null, max, null);
        return b.toString();
    }

    /**
     * 
     * @param i
     * @param del
     * @param max
     * @return 
     */
    public static String join(final Iterable i,
            final CharSequence del,
            final int max) {
        final StringBuilder b = new StringBuilder();
        join0(b, i == null ? null : i.iterator(), del, null, null, max, null);
        return b.toString();
    }

    /**
     *
     * @param i Iterator
     * @param del String
     * @param t Transformer
     * @return String
     */
    public static String join(final Iterator i,
            final CharSequence del,
            final Function t) {
        final StringBuilder b = new StringBuilder();
        join0(b, i, del, null, null, 0, t);
        return b.toString();
    }

    /**
     *
     * @param i Iterator
     * @param del String
     * @param max int
     * @param t
     * @return String
     */
    public static String join(final Iterator i,
            final CharSequence del,
            final int max,
            final Function t) {
        final StringBuilder b = new StringBuilder();
        join0(b, i, del, null, null, max, t);
        return b.toString();
    }

    /**
     *
     * @param i Iterator
     * @param del String
     * @param prefix String
     * @param postfix String
     * @param t
     * @return String
     */
    public static String join(final Iterator i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final Function t) {
        final StringBuilder b = new StringBuilder();
        join0(b, i, del, prefix, postfix, 0, t);
        return b.toString();
    }

    /**
     * 
     * @param i
     * @param del
     * @param prefix
     * @param postfix
     * @param t
     * @return 
     */
    public static String join(final Iterable i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final Function t) {
        final StringBuilder b = new StringBuilder();
        join0(b, i == null ? null : i.iterator(), del, prefix, postfix, 0, t);
        return b.toString();
    }

    /**
     *
     * @param i Iterator
     * @param del String
     * @param prefix String
     * @param postfix String
     * @return String
     */
    public static String join(final Iterator i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix) {
        final StringBuilder b = new StringBuilder();
        join0(b, i, del, prefix, postfix, 0, null);
        return b.toString();
    }

    /**
     * 
     * @param i
     * @param del
     * @param prefix
     * @param suffix
     * @return 
     */
    public static String join(final Iterable i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence suffix) {
        final StringBuilder b = new StringBuilder();
        join0(b, i == null ? null : i.iterator(), del, prefix, suffix, 0, null);
        return b.toString();
    }

    /**
     *
     * @param i Iterator
     * @param del String
     * @param prefix String
     * @param postfix String
     * @param max int
     * @return String
     */
    public static String join(final Iterator i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final int max) {
        final StringBuilder b = new StringBuilder();
        join0(b, i, del, prefix, postfix, max, null);
        return b.toString();
    }

    /**
     * 
     * @param i
     * @param del
     * @param prefix
     * @param postfix
     * @param max
     * @return 
     */
    public static String join(final Iterable i,
            final CharSequence del,
            final CharSequence prefix,
            final CharSequence postfix,
            final int max) {
        final StringBuilder b = new StringBuilder();
        join0(b, i == null ? null : i.iterator(), del, prefix, postfix, max, null);
        return b.toString();
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static String trimToNull(final CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return null;
        }
        return StringUtils.trimToNull(cs.toString());
    }
    
    /**
     * 
     * @param cs
     * @return 
     */
    public static Option<String> trimToOption(final CharSequence cs) {
        final String s = trimToNull(cs);
        if (s == null) {
            return None();
        }
        return Some(s);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static Optional<String> trimToOptional(final CharSequence cs) {
        final String s = trimToNull(cs);
        if (s == null) {
            return Optional.empty();
        }
        return Optional.of(s);
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static String trimToEmpty(final CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return EMPTY;
        }
        return StringUtils.trimToEmpty(cs.toString());
    }

    /**
     * 
     * @param cs
     * @return 
     */
    @Deprecated
    public static String[] splitLines_old(final CharSequence cs) {
        return toLines_old(cs).to(Java.array(String[]::new));
    }

    /**
     * 
     * @param cs
     * @return 
     */
    @Deprecated
    public static IterableAccessor<String> toLines_old(final CharSequence cs) {
        final Iterable<String> itr = () -> linesIterator_old(cs);
        return IterableAccessors.from(itr);
    }

    /**
     * 
     * @param cs
     * @return
     * @deprecated
     */
    @Deprecated
    public static List<String> old_toLines(final CharSequence cs) {
        final List<String> lines = new ArrayList();
        for (final Iterator<String> itr = linesIterator_old(cs); itr.hasNext(); ) {
            lines.add(itr.next());
        }
        return lines;
    }
    
    /**
     * 
     */
    // private final static char CR = '\r'; // Carriage Return, U+000D

    /**
     * 
     */
    // private final static char LF = '\n'; // Line Feed, U+000A
    
    /**
     * 
     * @param cs
     * @return 
     */
    @Deprecated
    public static Iterator<String> linesIterator_old(final CharSequence cs) {
        if (isEmpty(cs)) {
            return IteratorUtils.EMPTY_ITERATOR;
        }
        final Reader reader = cs instanceof String ? 
                new StringReader(cs.toString()) : 
                new CharSequenceReader(cs);
        final BufferedReader lineReader = new BufferedReader(reader);
        return new AbstractIterator<String>() {
            @Override
            protected String computeNext() {
                String s = null;
                try {
                    s = lineReader.readLine();
                } catch (final IOException e) { 
                    throw new UncheckedIOException(e);
                }
                if (s != null) {
                    return s;
                }
                try {
                    lineReader.close();
                } catch (final IOException e) { 
                    throw new UncheckedIOException(e);
                }
                return endOfData();
            }
        };
    }
    
    /**
     * 
     * @param cs
     * @return 
     */
    @Deprecated
    public static IterableAccessor<String> linesIterable_old(final CharSequence cs) {
        return IterableAccessors.from(linesIterator_old(cs));
    }

    /**
     * 
     * @param input
     * @return 
     */
    public static CharSequence toCharSequence(final Object input) {
        if (input == null) {
            return null;
        }
        if (input instanceof CharSequence cs) {
            return cs;
        }
        return input.toString();
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static String toString(final Object o) {
        return o == null ? null : o.toString();
    }
    
    /**
     * 
     * @param o
     * @return 
     */
    public static String toNullableString(final Object o) {
        if (o == null) {
            return null;
        }
        final String s = o.toString();
        if (s == null || s.length() == 0) {
            return null;
        }
        return s;
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static String toEmptyString(final Object o) {
        if (o == null) {
            return EMPTY;
        }
        final String s = o.toString();
        if (s == null || s.length() == 0) {
            return EMPTY;
        }
        return s;
    }
    
    /**
     * 
     * @param csq
     * @param index
     * @return 
     */
    public static String[] splitAt(final CharSequence csq, final int index) {
        final String[] res = new String[2];
        if (csq == null) {
            return res;
        }
        res[0] = EMPTY;
        res[1] = EMPTY;
        final int len = csq.length();
        if (len == 0) {
            return res;
        }
        int start = index;
        if (start < 0) {
            start += len;
        }
        if (start < 0) {
            res[1] = csq.toString();
            return res;
        }
        if (start >= len) {
            res[0] = csq.toString();
            return res;
        }
        res[0] = csq.subSequence(0, start).toString();
        res[1] = csq.subSequence(start, len).toString();
        return res;
    }

    /**
     * 
     * @param str
     * @param separatorChar
     * @return 
     */
    public static String[] split(final CharSequence str, final Predicate<Character> separatorChar) {
        return splitWorker(str, separatorChar, false);
    }

    /**
     * 
     * @param str
     * @param separatorChar
     * @return 
     */
    public static String[] splitPreserveAllTokens(final CharSequence str, final Predicate<Character> separatorChar) {
        return splitWorker(str, separatorChar, true);
    }

    /**
     * 
     * @param str
     * @param separatorChar
     * @param preserveAllTokens
     * @return 
     */
    private static String[] splitWorker(final CharSequence str, final Predicate<Character> separatorChar, final boolean preserveAllTokens) {
        // Performance tuned for 2.0 (JDK1.4)

        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final List<String> list = new ArrayList<>();
        int i = 0;
        int start = 0;
        boolean match = false;
        boolean lastMatch = false;
        while (i < len) {
            if (separatorChar.test(str.charAt(i))) {
                if (match || preserveAllTokens) {
                    list.add(str.subSequence(start, i).toString());
                    match = false;
                    lastMatch = true;
                }
                start = ++i;
                continue;
            }
            lastMatch = false;
            match = true;
            i++;
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.subSequence(start, i).toString());
        }
        return list.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * 
     * @param csq
     * @param firstIndex
     * @param otherIndexes
     * @return 
     */
    public static String[] splitAll(final CharSequence csq, 
            final int firstIndex,
            final Integer... otherIndexes) { 
        int len = length(csq);
        if (len == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final SortedSet<Integer> indexesSet = new TreeSet();
        int index = firstIndex;
        final Iterator<Integer> indexesItr = IterableAccessors.from(otherIndexes).iterator();
        while (true) {
            if (index < 0) {
                index += len; // handle negatives
            }
            if (index < 0 || index >= len) {
                throw new IllegalArgumentException("Invalid index: " + index);
            }
            if (!indexesSet.add(index)) {
                throw new IllegalArgumentException("Duplicated index: " + index);
            }
            if (indexesItr.hasNext()) {
                index = indexesItr.next();
            } else {
                break;
            }
        } 
        final String[] res = new String[indexesSet.size() + 1];
        int start = 0;
        int stop = 0;
        index = 0;
        for (int i : indexesSet) {
            stop = i;
            res[index++] = csq.subSequence(start, stop).toString();
            start = stop;
        }
        res[index] = csq.subSequence(start, len).toString();
        return res;
    }

    /**
     * 
     * @param csq
     * @param start
     * @return 
     */
    public static String slice(final CharSequence csq, int start) {
        if (csq == null) {
            return null;
        }
        final int len = csq.length();
        if (len == 0) {
            return EMPTY;
        }
        if (start < 0) {
            start += len;
        }
        if (start < 0) {
            start = 0;
        }
        if (start >= len) {
            return EMPTY;
        }
        return csq.subSequence(start, len).toString();
    }

    /**
     * 
     * @param csq
     * @param startIndex
     * @param endIndex
     * @return 
     */
    public static String slice(final CharSequence csq, int startIndex, int endIndex) {
        if (csq == null) {
            return null;
        }
        final int len = csq.length();
        if (len == 0) {
            return EMPTY;
        }
        if (startIndex < 0) {
            startIndex += len;
        }
        if (endIndex < 0) {
            endIndex += len;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex >= len) {
            endIndex = len;
        }
        if (startIndex >= endIndex) {
            return EMPTY;
        }
        return csq.subSequence(startIndex, endIndex).toString();
    }

    /**
     * 
     * @param csq
     * @param count
     * @return 
     */
    public static String strip(final CharSequence csq, 
            final int count) {
        return strip(csq, count, count);
    }

    /**
     * 
     * @param csq
     * @param beginCount
     * @param endCount
     * @return 
     */
    public static String strip(final CharSequence csq, 
            final int beginCount,
            final int endCount) {
        if (csq == null) {
            return null;
        }
        int len = csq.length();
        if (len == 0) {
            return EMPTY;
        }
        int startIndex = Math.max(beginCount, 0);
        int endIndex = len - Math.max(endCount, 0);
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (startIndex >= endIndex) {
            return EMPTY;
        }
        return csq.subSequence(startIndex, endIndex).toString();
    }
    
    /**
     * 
     * @param sequence
     * @param searchStrings
     * @return 
     */
    public static boolean startsWithAnyIgnoreCase(final CharSequence sequence, final CharSequence... searchStrings) {
        if (isEmpty(sequence) || ArrayUtils.isEmpty(searchStrings)) {
            return false;
        }
        for (final CharSequence searchString : searchStrings) {
            if (startsWithIgnoreCase(sequence, searchString)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param sequence
     * @param searchStrings
     * @return 
     */
    public static boolean endsWithAnyIgnoreCase(final CharSequence sequence, final CharSequence... searchStrings) {
        if (isEmpty(sequence) || ArrayUtils.isEmpty(searchStrings)) {
            return false;
        }
        for (final CharSequence searchString : searchStrings) {
            if (endsWithIgnoreCase(sequence, searchString)) {
                return true;
            }
        }
        return false;
    }

    
}
