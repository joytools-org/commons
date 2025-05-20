/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import io.vavr.Function1;
import io.vavr.collection.Vector;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.TimeUnit;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import org.joytools.commons.cache.CachedFunction1;
import org.joytools.commons.collections.IterableAccessors;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.naming.UncheckedNamingException;
import org.joytools.commons.naming.NamingUtils;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class LdapFunctions {

    /**
     *
     * @return
     */
    public static Function1<SearchResult, Map<String, Object>> searchResultToIgnoreCaseMap() {
        return searchResultToIgnoreCaseMap((Iterable)null);
    }

    /**
     *
     * @param mandatoryAttrNames
     * @return
     */
    public static Function1<SearchResult, Map<String, Object>> searchResultToIgnoreCaseMap(final String[] mandatoryAttrNames) {
        return searchResultToIgnoreCaseMap(mandatoryAttrNames, false);
    }

    /**
     * 
     * @param mandatoryAttrNames
     * @return 
     */
    public static Function1<SearchResult, Map<String, Object>> searchResultToIgnoreCaseMap(final Iterable<? extends CharSequence> mandatoryAttrNames) {
        return searchResultToIgnoreCaseMap(mandatoryAttrNames, false);
    }

    /**
     *
     * @param mandatoryAttrNames
     * @param missingToNull
     * @return
     */
    public static Function1<SearchResult, Map<String, Object>> searchResultToIgnoreCaseMap(final Iterable<? extends CharSequence> mandatoryAttrNames, final boolean missingToNull) {
        return searchResultToIgnoreCaseMap(StringUtils.toArray(mandatoryAttrNames), missingToNull);
    }

    /**
     * OKKIO, MULTI VALUE CHE NON TI ASPETTI:
     * cn
     * telephoneNumber
     * mobile
     * description
     * employeeStatus
     * employeeType
     * title
     * givenName
     * sn
     * displayName
     * mail
     * fullName
     * co
     * @param mandatoryAttrNames
     * @param missingToNull
     * @return
     */
    public static Function1<SearchResult, Map<String, Object>> searchResultToIgnoreCaseMap(final String[] mandatoryAttrNames, final boolean missingToNull) {
        return (final SearchResult sr) -> {
            final Map<String, Object> m = CaseSensitivity.INSENSITIVE.newLinkedMap();
            try {
                return LdapUtils.putAll(m, sr, mandatoryAttrNames, missingToNull);
            } catch (final NamingException ne) {
                throw new UncheckedNamingException(ne);
            }
        };
    }

    /**
     *
     * @return
     */
    public static Function1<Attributes, Map<String, Object>> attributesToSortedMapIgnoreCase() {
        return attributesToSortedMapIgnoreCase(null, false);
    }

    /**
     * 
     * @param <S>
     * @return 
     */
    private static final Function1<CharSequence, LdapName> TO_LDAP_NAME =
        (cs) -> {
            final String s = StringUtils.trimToEmpty(cs);
            if (s.isEmpty()) {
                throw new IllegalArgumentException("Empty Ldap Name");
            }
            try {
                return new LdapName(s);
            } catch (final NamingException ne) {
                throw new UncheckedNamingException(ne);
            }
        };
    
    /**
     * 
     */
    private static final CachedFunction1<CharSequence, LdapName> CACHED_TO_LDAP_NAME =
            CachedFunction1.of(TO_LDAP_NAME, CacheBuilder.newBuilder()
                    // .recordStats()
                    .maximumSize(8192)
                    .expireAfterWrite(2, TimeUnit.MINUTES));
    
    /**
     * 
     */
    @Deprecated
    private static final Function1<CharSequence, Vector<Rdn>> CACHED_TO_RDN_VECTOR =
            CACHED_TO_LDAP_NAME.andThen(LdapNames::toRdnVectorV1);

    /**
     * 
     * @return 
     */
    public static CachedFunction1<CharSequence, LdapName> toLdapName() {
        return CACHED_TO_LDAP_NAME;
    }
    
    /**
     * 
     * @return 
     */
    @Deprecated
    public static Function1<CharSequence, Vector<Rdn>> toRdnVectorV1() {
        return CACHED_TO_RDN_VECTOR;
    }

    /**
     *
     * @param mandatoryAttrs
     * @param missingToNull
     * @return
     */
    public static Function1<Attributes, Map<String, Object>> attributesToSortedMapIgnoreCase(final String[] mandatoryAttrs, final boolean missingToNull) {
        return (final Attributes attrs) -> {
            final Map<String, Object> m = CaseSensitivity.INSENSITIVE.newLinkedMap();
            try {
                return LdapUtils.putAll(m, attrs, mandatoryAttrs, missingToNull);
            } catch (final NamingException ne) {
                throw new UncheckedNamingException(ne);
            }
        };
    }

    @Deprecated
    public static Function1<SearchResult, SortedMap<String, Object>> searchResultToCISortedMapIgnoreCaseV1(final String[] mandatoryAttrNames, final boolean missingToNull) {
        return (final SearchResult sr) -> {
            Objects.requireNonNull(sr, "Search Result");
            final SortedSet<String> multiValue = CaseSensitivity.INSENSITIVE.sortedSetSupplier().get();
            final SortedMap<String, Object> m = CaseSensitivity.INSENSITIVE.sortedMapSupplier().get();
            m.put("$dn", sr.getNameInNamespace());
            // Nullable mode:
            // Pre fill with null values
            if (mandatoryAttrNames != null) {
                IterableAccessors.from(mandatoryAttrNames).forEach(a -> {
                    m.put(a, null);
                });
            }
            // Fill with current values
            NamingUtils.ofAll(sr.getAttributes().getAll()).forEach(a -> {
                try {
                    // Defensive copy
                    // Avoid issues when cloning payloads whose List attributes have been modified
                    final List values = ImmutableList.copyOf(NamingUtils.ofAll(a.getAll()));
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
                } catch (final NamingException ne) {
                    throw new UncheckedNamingException(ne);
                }
            });
            multiValue.removeAll(Arrays.asList("objectClass"));
            m.put("multiValueAttributes", StringUtils.trimToNull(StringUtils.join(multiValue, " ")));
            if (missingToNull && mandatoryAttrNames != null) {
                for (final String k : mandatoryAttrNames) {
                    m.putIfAbsent(k, null);
                }
            }
            return m;
        };
    }
    
}
