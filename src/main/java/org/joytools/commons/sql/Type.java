/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import static com.google.common.base.Preconditions.checkArgument;
import java.util.HashSet;
import java.util.Set;
import org.joytools.commons.lang.CaseSpaceNormalizer;
import org.joytools.commons.lang.EnumLookup;

import java.sql.Types;
import java.sql.JDBCType;
import org.joytools.commons.collections.IterableAccessors;
// import org.joytools.commons.collections.StreamIterables;
/**
 *
 * @author AndreaR
 */
public enum Type {

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>BIT</code>.
 */
        BIT(Types.BIT),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>TINYINT</code>.
 */
        TINYINT(Types.TINYINT),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>SMALLINT</code>.
 */
        SMALLINT(Types.SMALLINT),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>INTEGER</code>.
 */
        INTEGER(Types.INTEGER),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>BIGINT</code>.
 */
        BIGINT(Types.BIGINT),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>FLOAT</code>.
 */
        FLOAT(Types.FLOAT),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>REAL</code>.
 */
        REAL(Types.REAL),


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>DOUBLE</code>.
 */
        DOUBLE(Types.DOUBLE),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>NUMERIC</code>.
 */
        NUMERIC(Types.NUMERIC),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>DECIMAL</code>.
 */
        DECIMAL(Types.DECIMAL),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>CHAR</code>.
 */
        CHAR(Types.CHAR),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>VARCHAR</code>.
 */
        VARCHAR(Types.VARCHAR),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>LONGVARCHAR</code>.
 */
        LONGVARCHAR(Types.LONGVARCHAR),


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>DATE</code>.
 */
        DATE(Types.DATE),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>TIME</code>.
 */
        TIME(Types.TIME),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>TIMESTAMP</code>.
 */
        TIMESTAMP(Types.TIMESTAMP),


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>BINARY</code>.
 */
        BINARY(Types.BINARY),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>VARBINARY</code>.
 */
        VARBINARY(Types.VARBINARY),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type
 * <code>LONGVARBINARY</code>.
 */
        LONGVARBINARY(Types.LONGVARBINARY),

/**
 * <P>The constant in the Java programming language
 * that identifies the generic SQL value
 * <code>NULL</code>.
 */
        NULL(Types.NULL),

    /**
     * The constant in the Java programming language that indicates
     * that the SQL type is database-specific and
     * gets mapped to a Java object that can be accessed via
     * the methods <code>getObject</code> and <code>setObject</code>.
     */
        OTHER(Types.OTHER),



    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>JAVA_OBJECT</code>.
     * @since 1.2
     */
        JAVA_OBJECT(Types.JAVA_OBJECT),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>DISTINCT</code>.
     * @since 1.2
     */
        DISTINCT(Types.DISTINCT),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>STRUCT</code>.
     * @since 1.2
     */
        STRUCT(Types.STRUCT),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>ARRAY</code>.
     * @since 1.2
     */
        ARRAY(Types.ARRAY),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>BLOB</code>.
     * @since 1.2
     */
        BLOB(Types.BLOB),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>CLOB</code>.
     * @since 1.2
     */
        CLOB(Types.CLOB),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>REF</code>.
     * @since 1.2
     */
        REF(Types.REF),

    /**
     * The constant in the Java programming language, somtimes referred to
     * as a type code, that identifies the generic SQL type <code>DATALINK</code>.
     *
     * @since 1.4
     */
    DATALINK(Types.DATALINK),

    /**
     * The constant in the Java programming language, somtimes referred to
     * as a type code, that identifies the generic SQL type <code>BOOLEAN</code>.
     *
     * @since 1.4
     */
    BOOLEAN(Types.BOOLEAN),

    //------------------------- JDBC 4.0 -----------------------------------

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>ROWID</code>
     *
     * @since 1.6
     *
     */
    ROWID(Types.ROWID),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>NCHAR</code>
     *
     * @since 1.6
     */
    NCHAR(Types.NCHAR),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>NVARCHAR</code>.
     *
     * @since 1.6
     */
    NVARCHAR(Types.NVARCHAR),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>LONGNVARCHAR</code>.
     *
     * @since 1.6
     */
    LONGNVARCHAR(Types.LONGNVARCHAR),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>NCLOB</code>.
     *
     * @since 1.6
     */
    NCLOB(Types.NCLOB),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>XML</code>.
     *
     * @since 1.6
     */
    SQLXML(Types.SQLXML),

    //--------------------------JDBC 4.2 -----------------------------

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type {@code REF CURSOR}.
     *
     * @since 1.8
     */
    REF_CURSOR(Types.REF_CURSOR),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code TIME WITH TIMEZONE}.
     *
     * @since 1.8
     */
    TIME_WITH_TIMEZONE(Types.TIME_WITH_TIMEZONE),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * {@code TIMESTAMP WITH TIMEZONE}.
     *
     * @since 1.8
     */
    TIMESTAMP_WITH_TIMEZONE(Types.TIMESTAMP_WITH_TIMEZONE);

    /**
     *
     * @param value
     * @param names
     */
    Type(final int value,
            final String... names) {
        this.value = value;
        this.names = names;
    }

    /**
     *
     * @param name
     * @return
     */
    public static Type of(final String name) {
        return LOOKUP.getExisting(name);
    }

    /**
     *
     * @param value
     * @return
     */
    public static Type of(final int value) {
        for (final Type t : values()) {
            if (t.value == value) {
                return t;
            }
        }
        throw new IllegalArgumentException("No Type found with value: " + value);
    }

    /**
     *
     * @return
     */
    public int value() {
        return value;
    }

    /**
     * 
     * @param types
     * @return 
     */
    public boolean isAnyOf(final Type... types) {
        checkArgument(types != null && types.length > 0, "Empty types");
        for (final Type t : types) {
            if (this == t) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isAnyCharOrVarchar() {
        return isAnyOf(CHAR, LONGNVARCHAR, LONGVARCHAR, NCHAR, NVARCHAR, VARCHAR);
    }
    
    /**
     *
     */
    private final static EnumLookup<String, Type> LOOKUP = new EnumLookup<String, Type>(
            values(),
            CaseSpaceNormalizer.UPPERCASE_TRIM.castAsString()) {

        @Override
        protected Iterable<String> keysOf(final Type item) {
            final Set<String> keys = new HashSet();
            for (final String n : IterableAccessors.from(item.name()).append(item.names)) {
                keys.addAll(expand(n));
            }
            return keys;
        }

    };

    /**
     *
     */
    private final int value;

    /**
     *
     */
    private final String[] names;

}
