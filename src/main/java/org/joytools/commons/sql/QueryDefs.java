/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import static com.google.common.base.Preconditions.checkArgument;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class QueryDefs {
    
    /**
     * 
     * @param cs
     * @param sql
     * @param parameters
     * @return 
     */
    public static SelectQueryDef select(final CaseSensitivity cs, final CharSequence sql, final Object... parameters) {
        final String s = StringUtils.trimToNull(sql);
        checkArgument(StringUtils.isNotEmpty(s), "Empty query");
        final Vector<?> p = toVector(parameters);
        return new SelectQueryDef() {
            @Override
            public String sql() {
                return s;
            }

            @Override
            public Seq<?> parameters() {
                return p;
            }

            @Override
            public CaseSensitivity caseSensitivity() {
                return cs;
            }
        };
    }

    /**
     * 
     * @param cs
     * @param sql
     * @param parameters
     * @return 
     */
    public static SelectQueryDef select(final CaseSensitivity cs, final Supplier<? extends CharSequence> sql, final Object... parameters) {
        return select(cs, sql.get(), parameters);
    }

    /**
     * 
     * @param sql
     * @param parameters
     * @return 
     */
    public static SelectQueryDef select(final CharSequence sql, final Object... parameters) {
        return select(CaseSensitivity.SENSITIVE, sql, parameters);
    }
    
    /**
     * 
     * @param sql
     * @param parameters
     * @return 
     */
    public static SelectQueryDef select(final Supplier<? extends CharSequence> sql, final Object... parameters) {
        return select(CaseSensitivity.SENSITIVE, sql.get(), parameters);
    }

    /**
     * 
     * @param sql
     * @param parameters
     * @return 
     */
    public static SelectQueryDef selectIgnoreCase(final CharSequence sql, final Object... parameters) {
        return select(CaseSensitivity.INSENSITIVE, sql, parameters);
    }
    
    /**
     * 
     * @param sql
     * @param parameters
     * @return 
     */
    public static SelectQueryDef selectIgnoreCase(final Supplier<? extends CharSequence> sql, final Object... parameters) {
        return select(CaseSensitivity.INSENSITIVE, sql.get(), parameters);
    }

    /**
     * 
     * @param cs
     * @param sql
     * @param parameters
     * @return 
     */
    public static UpdateQueryDef update(final CaseSensitivity cs, final CharSequence sql, final Object... parameters) {
        final String s = StringUtils.trimToNull(sql);
        checkArgument(StringUtils.isNotEmpty(s), "Empty query");
        final Vector<?> p = toVector(parameters);
        return new UpdateQueryDef() {
            @Override
            public String sql() {
                return s;
            }

            @Override
            public Seq<?> parameters() {
                return p;
            }

            @Override
            public CaseSensitivity caseSensitivity() {
                return cs;
            }
        };
    }

    /**
     * 
     * @param cs
     * @param sql
     * @param parameters
     * @return 
     */
    public static UpdateQueryDef update(final CaseSensitivity cs, final Supplier<? extends CharSequence> sql, final Object... parameters) {
        return update(cs, sql.get(), parameters);
    }

    /**
     * 
     * @param sql
     * @param parameters
     * @return 
     */
    public static UpdateQueryDef update(final CharSequence sql, final Object... parameters) {
        return update(CaseSensitivity.INSENSITIVE, sql, parameters);
    }
    
    /**
     * 
     * @param sql
     * @param parameters
     * @return 
     */
    public static UpdateQueryDef update(final Supplier<? extends CharSequence> sql, final Object... parameters) {
        return update(CaseSensitivity.INSENSITIVE, sql.get(), parameters);
    }

    /**
     * 
     * @param parameters
     * @return 
     */
    protected static Vector<?> toVector(final Object... parameters) {
        return parameters == null || parameters.length == 0 ? Vector.empty() : Vector.of(parameters);
    }
    
    /**
     * 
     * @param a 
     */
    public static void main(final String... a) {
        final CaseSensitivity cs = CaseSensitivity.INSENSITIVE;
        final String P1 = "SELECT * FROM A";
        final String P2 = "A";
        
        final QueryDef q1 = QueryDefs.select(cs, P1, P2);
        final QueryDef q2 = QueryDefs.select(cs, P1.toLowerCase(), P2.toLowerCase());
        final boolean b = q1.equals(q2);
        System.out.println(b);
        System.out.println(q1.hashCode());
        System.out.println(q2.hashCode());
        
        final Set<QueryDef> set = new HashSet<>();
        set.add(q1);
        set.add(q2);
        set.forEach(System.out::println);
    }
    
    
}
