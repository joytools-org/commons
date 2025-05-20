/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

import com.google.common.collect.Multimap;
import io.vavr.collection.Seq;
import java.sql.Timestamp;
import java.util.Arrays;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapName;

import org.apache.commons.text.TextStringBuilder;
import org.joytools.commons.collections.IterableAccessors;
import static org.joytools.commons.function.Functions.asString;
import org.joytools.commons.interop.Java;
import org.joytools.commons.lang.ObjectUtils;
import org.joytools.commons.lang.StringUtils;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.naming.UncheckedNamingException;
import org.joytools.commons.naming.NamingUtils;

import org.joytools.commons.time.DateTimeUtils;
import org.joytools.commons.time.TimeValue;
import org.joytools.commons.time.TimeValues;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class LdapUtils {

    /**
     * 
     */
    public LdapUtils() {      
    }

    /**
     * 
     * @param other
     * @return 
     */
    public static SearchControls clone(final SearchControls other) {
        Objects.requireNonNull(other, "Other");
        final SearchControls sc = new SearchControls();
        sc.setCountLimit(other.getCountLimit());
        sc.setDerefLinkFlag(other.getDerefLinkFlag());
        sc.setReturningAttributes(other.getReturningAttributes());
        sc.setReturningObjFlag(other.getReturningObjFlag());
        sc.setSearchScope(other.getSearchScope());
        sc.setTimeLimit(other.getTimeLimit());
        return sc;
    }
    
    /**
     * 
     * @param ctrls
     * @param ctrl
     * @return 
     */ /*
    public static Control[] append(final Control[] ctrls, final Control ctrl) {
        Objects.requireNonNull(ctrl, "Control");
        if (ctrls == null || ctrls.length == 0) {
            return new Control[] { ctrl };
        }
        return Iterations.from(ctrls).append(ctrl).to(Java.array(Control[]::new));
    } */

    /**
     * 
     * @param ctrls
     * @param others
     * @return 
     */
    public static Control[] append(final Control[] ctrls, final Control... others) {
        return IterableAccessors.of(ctrls)
                .append(others)
                .to(Java.array(Control[]::new));
    }

    /**
     * 
     * @param attrs
     * @param name
     * @param value
     * @return 
     */
    public static Attributes putNonEmpty(final Attributes attrs, final String name, final Object value) {
        if (ObjectUtils.isNotEmpty(value)) {
            if (value instanceof CharSequence) {
                attrs.put(name, value.toString());
            } else {
                attrs.put(name, value);
            }
        }
        return attrs;
    }
    
    /**
     * 
     * @param <T>
     * @param name
     * @param values
     * @return 
     */
    public static <T> Attribute buildAttribute(final String name, final T... values) {
        final Attribute attr = new BasicAttribute(name);
        if (values != null) {
            for (final T val : values) {
                if (val != null) {
                    attr.add(val);
                }
            }
        }
        return attr;
    }

    /**
     * 
     * @param name
     * @param value
     * @return 
     */
    public static Attributes buildAttributes(final String name, final Object value) {
        final Attributes attrs = new BasicAttributes();
        putNonEmpty(attrs, name, value);
        return attrs;
    }
    
    /**
     * 
     * @param name0
     * @param value0
     * @param name1
     * @param value1
     * @return 
     */
    public static Attributes buildAttributes(
            final String name0, final Object value0,
            final String name1, final Object value1) {
        final Attributes attrs = new BasicAttributes();
        putNonEmpty(attrs, name0, value0);
        putNonEmpty(attrs, name1, value1);
        return attrs;
    }

    /**
     * 
     * @param name0
     * @param value0
     * @param name1
     * @param value1
     * @param name2
     * @param value2
     * @return 
     */
    public static Attributes buildAttributes(
            final String name0, final Object value0,
            final String name1, final Object value1,
            final String name2, final Object value2) {
        final Attributes attrs = new BasicAttributes();
        putNonEmpty(attrs, name0, value0);
        putNonEmpty(attrs, name1, value1);
        putNonEmpty(attrs, name2, value2);
        return attrs;
    }

    /**
     * 
     * @param name0
     * @param value0
     * @param name1
     * @param value1
     * @param name2
     * @param value2
     * @param name3
     * @param value3
     * @return 
     */
    public static Attributes buildAttributes(
            final String name0, final Object value0,
            final String name1, final Object value1,
            final String name2, final Object value2,
            final String name3, final Object value3) {
        final Attributes attrs = new BasicAttributes();
        putNonEmpty(attrs, name0, value0);
        putNonEmpty(attrs, name1, value1);
        putNonEmpty(attrs, name2, value2);
        putNonEmpty(attrs, name3, value3);
        return attrs;
    }

    /**
     * 
     * @param name0
     * @param value0
     * @param name1
     * @param value1
     * @param name2
     * @param value2
     * @param name3
     * @param value3
     * @param name4
     * @param value4
     * @return 
     */
    public static Attributes buildAttributes(
            final String name0, final Object value0,
            final String name1, final Object value1,
            final String name2, final Object value2,
            final String name3, final Object value3,
            final String name4, final Object value4) {
        final Attributes attrs = new BasicAttributes();
        putNonEmpty(attrs, name0, value0);
        putNonEmpty(attrs, name1, value1);
        putNonEmpty(attrs, name2, value2);
        putNonEmpty(attrs, name3, value3);
        putNonEmpty(attrs, name4, value4);
        return attrs;
    }

    /**
     * 
     * @param name0
     * @param value0
     * @param name1
     * @param value1
     * @param name2
     * @param value2
     * @param name3
     * @param value3
     * @param name4
     * @param value4
     * @param name5
     * @param value5
     * @return 
     */
    public static Attributes buildAttributes(
            final String name0, final Object value0,
            final String name1, final Object value1,
            final String name2, final Object value2,
            final String name3, final Object value3,
            final String name4, final Object value4,
            final String name5, final Object value5) {
        final Attributes attrs = new BasicAttributes();
        putNonEmpty(attrs, name0, value0);
        putNonEmpty(attrs, name1, value1);
        putNonEmpty(attrs, name2, value2);
        putNonEmpty(attrs, name3, value3);
        putNonEmpty(attrs, name4, value4);
        putNonEmpty(attrs, name5, value5);
        return attrs;
    }

    /**
     * 
     * @param values
     * @return 
     */
    public static Attributes buildAttributes(final String... values) {
        final Attributes attrs = new BasicAttributes();
        if (values != null) {
            if (values.length % 2 != 0) {
                throw new IllegalArgumentException("Invalid argument length: " + values.length);
            }
            int index = 0;
            while (index < values.length) {
                final String name = values[index++];
                final String value = values[index++];
                putNonEmpty(attrs, name, value);
            }
        }
        return attrs;
    }
    
    /**
     * 
     * @param values
     * @return 
     */
    public static Attributes buildAttributes(final Iterable<CharSequence> values) {
        return buildAttributes(StringUtils.toArray(IterableAccessors.from(values).map(asString())));
    }

    /**
     * 
     * @param <M>
     * @param m
     * @param attrs
     * @return 
     * @throws javax.naming.NamingException 
     */
    public static <M extends Map<String, Object>> M putAll(final M m, final Attributes attrs) throws NamingException {
        return putAll(m, attrs, null, false);
    }

    /**
     * 
     * @param <M>
     * @param m
     * @param attrs
     * @param mandatoryAttrs
     * @param missingToNull
     * @return 
     * @throws javax.naming.NamingException 
     */
    public static <M extends Map<String, Object>> M putAll(
            final M m, 
            final Attributes attrs, 
            final String[] mandatoryAttrs, 
            final boolean missingToNull) throws NamingException {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(attrs, "Attributes");
        final SortedSet<String> multiValue = CaseSensitivity.INSENSITIVE.sortedSetSupplier().get();
        // Nullable mode:
        // Pre fill with null values
        if (mandatoryAttrs != null) {
            IterableAccessors.from(mandatoryAttrs).forEach(a -> {
                m.put(a, null);
            });
        }
        for (final Attribute a : NamingUtils.toIteration(attrs)) {
            // final List values = ImmutableList.copyOf(StreamIterables.of(itr2));
            final Seq values = NamingUtils.toVector(a);
            Object value = null;
            switch (values.size()) {
                case 0:
                    break;
                case 1:
                    value = values.get(0);
                    break;
                default:
                    value = values;
                    multiValue.add(a.getID());
                    break;
            }
            m.put(a.getID(), value);
        }
        multiValue.removeAll(Arrays.asList("objectClass"));
        m.put("multiValueAttributes", StringUtils.trimToNull(StringUtils.join(multiValue, " ")));
        if (missingToNull && mandatoryAttrs != null) {
            for (final String k : mandatoryAttrs) {
                m.putIfAbsent(k, null);
            }
        }
        return m;
    }

    /**
     * 
     * @param <M>
     * @param m
     * @param attrs
     * @return
     * @throws NamingException 
     */
    public static <M extends Multimap<String, Object>> M putAll(
            final M m, 
            final Attributes attrs) throws NamingException {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(attrs, "Attributes");
        // Nullable mode:
        // Pre fill with null values
        // Fill with current values
        final NamingEnumeration<? extends Attribute> itr1 = attrs.getAll();
        try {
            for (final Attribute a : NamingUtils.ofAll(itr1)) {
                // Defensive copy
                // Avoid issues when cloning payloads whose List attributes have been modified
                final NamingEnumeration<?> itr2 = a.getAll();
                try {
                    for (final Object value : NamingUtils.ofAll(itr2)) {
                        m.put(a.getID(), value);
                    }
                } finally {
                    itr2.close();
                }
            }
        } finally {
            itr1.close();
        }
        return m;
    }

    /**
     * 
     * @param <M>
     * @param m
     * @param attrs
     * @param mandatoryAttrs
     * @param missingToNull
     * @return 
     */
    public static <M extends Map<String, Object>> M putAllV1BeforeNamingEnumClose(final M m, final Attributes attrs, final String[] mandatoryAttrs, final boolean missingToNull) {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(attrs, "Attributes");
        final SortedSet<String> multiValue = CaseSensitivity.INSENSITIVE.sortedSetSupplier().get();
        // Nullable mode:
        // Pre fill with null values
        if (mandatoryAttrs != null) {
            IterableAccessors.from(mandatoryAttrs).forEach(a -> {
                m.put(a, null);
            });
        }
        // Fill with current values
        NamingUtils.toVector(attrs).forEach(a -> {
            // Defensive copy
            // Avoid issues when cloning payloads whose List attributes have been modified
            // final List values = ImmutableList.copyOf(StreamIterables.of(a.getAll()));
            final Seq values = NamingUtils.toVector(a);
            Object value = null;
            switch (values.size()) {
                case 0:
                    break;
                case 1:
                    value = values.get(0);
                    break;
                default:
                    value = values;
                    multiValue.add(a.getID());
                    break;
            }
            m.put(a.getID(), value);
        });
        multiValue.removeAll(Arrays.asList("objectClass"));
        m.put("multiValueAttributes", org.joytools.commons.lang.StringUtils.trimToNull(org.joytools.commons.lang.StringUtils.join(multiValue, " ")));
        if (missingToNull && mandatoryAttrs != null) {
            for (final String k : mandatoryAttrs) {
                m.putIfAbsent(k, null);
            }
        }
        return m;
    }

    /**
     * 
     * @param <M>
     * @param m
     * @param sr
     * @return 
     */
    public static <M extends Map<String, Object>> M putAll(final M m, 
            final SearchResult sr) throws NamingException {
        return putAll(m, sr, null);
    }

    /**
     * 
     * @param <M>
     * @param m
     * @param sr
     * @param mandatoryAttrNames
     * @return 
     */
    public static <M extends Map<String, Object>> M putAll(final M m, 
            final SearchResult sr, 
            final String[] mandatoryAttrNames) throws NamingException {
        return putAll(m, sr, mandatoryAttrNames, false);
    }

    /**
     * 
     * @param <M>
     * @param m
     * @param sr
     * @param mandatoryAttrNames
     * @param missingToNull
     * @return 
     */
    public static <M extends Map<String, Object>> M putAll(final M m, 
            final SearchResult sr, 
            final List<String> mandatoryAttrNames, 
            final boolean missingToNull) throws NamingException {
        return putAll(m, sr, org.joytools.commons.lang.StringUtils.toArray(mandatoryAttrNames), missingToNull);
    }
    
    /**
     * 
     * @param <M>
     * @param m
     * @param sr
     * @param mandatoryAttrNames
     * @param missingToNull
     * @return 
     * @throws javax.naming.NamingException 
     */
    public static <M extends Map<String, Object>> M putAll(final M m, 
            final SearchResult sr, 
            final String[] mandatoryAttrNames, 
            final boolean missingToNull) throws NamingException {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(sr, "Search Result");
        putAll(m, sr.getAttributes(), mandatoryAttrNames, missingToNull);
        m.put("$dn", sr.getNameInNamespace());
        return m;
    }

    /**
     * 
     * @param <M>
     * @param m
     * @param sr
     * @return
     * @throws NamingException 
     */
    public static <M extends Multimap<String, Object>> M putAll(final M m, final SearchResult sr) throws NamingException {
        Objects.requireNonNull(m, "Map");
        Objects.requireNonNull(sr, "Search Result");
        m.put("$dn", sr.getNameInNamespace());
        putAll(m, sr.getAttributes());
        return m;
    }

    /**
     *
     * @param nodeName
     * @param ldapName
     * @return
     */
    public static String toCanonicalName(final String nodeName, final LdapName ldapName) {
        final TextStringBuilder str = new TextStringBuilder(nodeName);
        for (int i = 0; i < ldapName.size(); i++) {
            str.append("/").append(ldapName.getRdn(i).getValue());
        }
        return str.toString();
    }
    
    /**
     * 
     * http://stackoverflow.com/questions/6038136/how-do-i-parse-rfc-3339-datetimes-with-java
     * http://stackoverflow.com/questions/2580925/simpledateformat-parsing-date-with-z-literal
     * http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html#rfc822timezone
     * http://opendj.forgerock.org/opendj-ldap-sdk/apidocs/org/forgerock/opendj/ldap/GeneralizedTime.html
     * 
     * Parses 
     *      20110113234245[.0]Z
     *      2011-01-13T23:42:45[.0]Z
     * 
     * @param ts
     * @param lenient
     * @return 
     */
    static TimeValue<?> parseLdapTimestamp(final CharSequence ts,
            final boolean lenient) {
        String s = ts == null ? null : StringUtils.trimToNull(ts.toString());
        if (s == null) {
            return TimeValues.empty();
        }
        s = s.toUpperCase();
        TimeValue<?> ret;
        if (s.length() >= 12 && s.endsWith("Z")) {
            // Test for 20110113234245[.0]Z
            // Test for 2011-01-13T23:42:45[.0]Z
            s = s.substring(0, s.length() - 1);
            if (s.endsWith(".0")) {
                s = s.substring(0, s.length() - 2);
            }
            if ((ret = parseLdapTimestampOrNullType1(s)) != null) {
                return ret;
            }
            if ((ret = parseLdapTimestampOrNullType2(s)) != null) {
                return ret;
            }
        } else {
        }
        if (lenient) {
            return TimeValues.empty();
        } else {
            throw new IllegalArgumentException(ts + " is not a valid LDAP timestamp");
        }
    }

    /**
     * 
     * @param ts
     * @return 
     */
    public static TimeValue<?> parseLdapTimestampOrEmpty(final CharSequence ts) {
        return parseLdapTimestamp(ts, true);
    }

    /**
     * 
     * @param ts
     * @return 
     */
    public static TimeValue<?> parseLdapTimestampOrFail(final CharSequence ts) {
        return parseLdapTimestamp(ts, false);
    }

    /**
     * 
     * @param o
     * @param lenient
     * @return 
     */
    /* public static Object parseLdapTimestamp(final Object o,
            final boolean lenient) {
        if (DateTimeUtils.isGenericDateTime(o)) {
            return o;
        }
        return parseLdapTimestamp(o.toString(), lenient);
    } */
    static TimeValue<?> findLdapTimestampWithParse(final Object o,
            final boolean lenient) {
        if (o == null) {
            return TimeValues.empty();
        }
        if (o instanceof CharSequence cs) {
            try {
                return parseLdapTimestamp(cs, lenient);
            } catch (final Exception e) {
            }
        }
        if (lenient) {
            return TimeValues.findOrEmptyWithParseAll(o);
        }
        return TimeValues.findOrFailWithParseAll(o);
    }
    
    /**
     * 
     * @param o
     * @return 
     */
    public static TimeValue<?> findLdapTimestampOrFailWithParse(final Object o) {
        return findLdapTimestampWithParse(o, false);
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static TimeValue<?> findLdapTimestampOrEmptyWithParse(final Object o) {
        return findLdapTimestampWithParse(o, true);
    }

    /**
     * 
     * @param ts
     * @return 
     */
    private static TimeValue<Calendar> parseLdapTimestampOrNullType1(final String ts) {
        // System.out.println("Parsing ldapTimestampType1: " + ts);
        if (ts == null || ts.length() < 12) {
            return null;
        }
        try {
            long dateTime = Long.parseLong(ts);
            final int second = (int) (dateTime % 100);
            dateTime /= 100;
            final int minute = (int) (dateTime % 100);
            dateTime /= 100;
            final int hourOfDay = (int) (dateTime % 100);
            dateTime /= 100;
            final int dayOfMonth = (int) (dateTime % 100);
            dateTime /= 100;
            final int month = (int) (dateTime % 100);
            dateTime /= 100;
            final int year = (int) dateTime;
            if (!DateTimeUtils.maybeDateTime(year, month, dayOfMonth, hourOfDay, minute, second)) {
                return null;
            }
            final Calendar c = Calendar.getInstance();
            c.setLenient(true);
            c.set(year, month - 1, dayOfMonth, hourOfDay, minute, second);
            c.set(Calendar.MILLISECOND, 0);
            c.getTimeInMillis();
            // System.out.println("  ==> returning " + c);
            return TimeValues.of(c);
        } catch (final Exception e) {
            // System.out.println("  ==> returning null due to exception " + e);
            return null;
        }
    }

    /**
     * 
     * @param ts
     * @return 
     */
    private static TimeValue<?> parseLdapTimestampOrNullType2(final String ts) {
        // System.out.println("Parsing ldapTimestampType2: " + ts);
        final String s = StringUtils.replace(ts, "T", " ");
        return TimeValues.parseAllOrNull(s);
    }

}
