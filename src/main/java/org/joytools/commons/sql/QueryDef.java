/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import io.vavr.collection.IndexedSeq;
import io.vavr.collection.Seq;
import java.util.Objects;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public abstract class QueryDef {

    /**
     * 
     */
    public enum Type {
        SELECT,
        UPDATE,
        CALL;
    }
    
    /**
     * 
     * @return 
     */
    public abstract String sql();
    
    /**
     * 
     * @return 
     */
    public abstract Type type();
    
    /**
     * 
     * @return 
     */
    public final boolean isSelect() {
        return Type.SELECT == type();
    }
    
    /**
     * 
     * @return 
     */
    public final boolean isUpdate() {
        return Type.UPDATE == type();
    }

    /**
     * 
     * @return 
     */
    public final boolean isCall() {
        return Type.CALL == type();
    }

    /**
     * 
     * @return 
     */
    public abstract Seq<?> parameters();

    /**
     * 
     * @return 
     */
    public abstract CaseSensitivity caseSensitivity();
    
    /**
     * 
     * @return 
     */
    @Override
    public final String toString() {
        final ToStringBuilder sb = new ToStringBuilder(this)
                .append("type", type())
                .append("caseSensitivity", caseSensitivity())
                .append("sql", sql())
                .append("paramenters", parameters());
        return sb.build();
    };
    
    /**
     * 
     * @param other
     * @return 
     */
    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof QueryDef)) {
            return false;
        }
        final QueryDef q = QueryDef.class.cast(other);
        final CaseSensitivity cs = caseSensitivity();
        if (cs != q.caseSensitivity()
                || type() != q.type()) {
            return false;
        }
        if (!cs.equals(sql(), q.sql())) {
            return false;
        }
        final Seq<?> pa = parameters();
        final Seq<?> pb = q.parameters();
        final int size = pa.size();
        if (size != pb.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!equals(cs, pa.get(i), pb.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * @param cs
     * @param a
     * @param b
     * @return 
     */
    private static boolean equals(final CaseSensitivity cs, final Object a, final Object b) {
        if (a != null 
                && b != null 
                && a instanceof CharSequence 
                && b instanceof CharSequence) {
            return cs.equals(CharSequence.class.cast(a), CharSequence.class.cast(b));
        }
        return Objects.equals(a, b);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public final int hashCode() {
        final CaseSensitivity cs = caseSensitivity();
        final HashCodeBuilder b = new HashCodeBuilder()
                .append(cs.hashCode(sql()))
                .append(cs)
                .append(type());
        for (final Object o : parameters()) {
            if (o != null && o instanceof CharSequence) {
                b.append(cs.hashCode(CharSequence.class.cast(o)));
            } else {
                b.append(o);
            }
        }
        return b.build();
    }

    /**
     * 
     * @return 
     */
    public final SelectQueryDef asSelect() {
        if (this instanceof SelectQueryDef) {
            return SelectQueryDef.class.cast(this);
        }
        throw new UnsupportedOperationException("This QueryDef is for " + type().name());
    }
    
    /**
     * 
     * @return 
     */
    public final UpdateQueryDef asUpdate() {
        if (this instanceof UpdateQueryDef) {
            return UpdateQueryDef.class.cast(this);
        }
        throw new UnsupportedOperationException("This QueryDef is for " + type().name());
    }

}
