/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import java.sql.SQLException;
import java.util.Objects;
import org.joytools.commons.function.FailableAutoCloseable;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class SQLNames {
    
    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static boolean fullNameEquals(final SQLName a, final SQLName b) {
        return StringUtils.equals(fullName0(a), fullName0(b));
    }

    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static boolean fullNameEquals(final CharSequence a, final SQLName b) {
        return StringUtils.equals(StringUtils.trimToNull(a), fullName0(b));
    }

    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static boolean fullNameEquals(final SQLName a, final CharSequence b) {
        return StringUtils.equals(fullName0(a), StringUtils.trimToNull(b));
    }

    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static boolean fullNameEqualsIgnoreCase(final SQLName a, final SQLName b) {
        return StringUtils.equalsIgnoreCase(fullName0(a), fullName0(b));
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static boolean fullNameEqualsIgnoreCase(final CharSequence a, final SQLName b) {
        return StringUtils.equalsIgnoreCase(StringUtils.trimToNull(a), fullName0(b));
    }

    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static boolean fullNameEqualsIgnoreCase(final SQLName a, final CharSequence b) {
        return StringUtils.equalsIgnoreCase(fullName0(a), StringUtils.trimToNull(b));
    }

    /**
     * 
     * @param n
     * @return 
     */
    static String fullName0(final SQLName n) {
        if (n == null) {
            return null;
        }
        return n.fullName();
    }
    
    /**
     * 
     * @param fullName
     * @return 
     */
    public static SQLName parseTable(final CharSequence fullName) {
        return parseObject(SQLNameType.TABLE, fullName);
    }

    /**
     * 
     * @param fullName
     * @return 
     */
    public static SQLName parseView(final CharSequence fullName) {
        return parseObject(SQLNameType.VIEW, fullName);
    }

    /**
     * 
     * @param fullName
     * @return 
     */
    public static SQLName parseColumn(final CharSequence fullName) {
        return parseObject(SQLNameType.COLUMN, fullName);
    }

    /**
     * 
     * @param type
     * @param fullName
     * @return 
     */
    public static SQLName parseObject(final SQLNameType type, final CharSequence fullName) {
        return SQLNameImpl.parse(type, fullName);
    }

    /**
     * 
     * @param table
     * @return 
     */
    public static SQLName forTable(
            final String table) {
        return forObject(SQLNameType.TABLE, null, null, null, table);
    }

    /**
     * 
     * @param schema
     * @param table
     * @return 
     */
    public static SQLName forTable(
            final String schema,
            final String table) {
        return forObject(SQLNameType.TABLE, null, null, schema, table);
    }

    /**
     * 
     * @param catalog
     * @param schema
     * @param table
     * @return 
     */
    public static SQLName forTable(
            final String catalog,
            final String schema,
            final String table) {
        return forObject(SQLNameType.TABLE, null, catalog, schema, table);
    }

    /**
     * 
     * @param server
     * @param catalog
     * @param schema
     * @param table
     * @return 
     */
    public static SQLName forTable(final String server,
            final String catalog,
            final String schema,
            final String table) {
        return forObject(SQLNameType.TABLE, server, catalog, schema, table);
    }
    
    /**
     * 
     * @param view
     * @return 
     */
    public static SQLName forView(
            final String view) {
        return forObject(SQLNameType.VIEW, null, null, null, view);
    }

    /**
     * 
     * @param schema
     * @param view
     * @return 
     */
    public static SQLName forView(
            final String schema,
            final String view) {
        return forObject(SQLNameType.VIEW, null, null, schema, view);
    }

    /**
     * 
     * @param catalog
     * @param schema
     * @param view
     * @return 
     */
    public static SQLName forView(
            final String catalog,
            final String schema,
            final String view) {
        return forObject(SQLNameType.VIEW, null, catalog, schema, view);
    }

    /**
     * 
     * @param server
     * @param catalog
     * @param schema
     * @param view
     * @return 
     */
    public static SQLName forView(final String server,
            final String catalog,
            final String schema,
            final String view) {
        return forObject(SQLNameType.VIEW, server, catalog, schema, view);
    }

    /**
     * 
     * @param type
     * @param catalog
     * @param schema
     * @param name
     * @return 
     */
    public static SQLName forObject(
            final SQLNameType type,
            final String catalog,
            final String schema,
            final String name) {
        return new SQLNameImpl(type, null, catalog, schema, name, null);
    }

    /**
     * 
     * @param type
     * @param schema
     * @param name
     * @return 
     */
    public static SQLName forObject(
            final SQLNameType type,
            final String schema,
            final String name) {
        return new SQLNameImpl(type, null, null, schema, name, null);
    }

    /**
     * 
     * @param type
     * @param name
     * @return 
     */
    public static SQLName forObject(
            final SQLNameType type,
            final String name) {
        return new SQLNameImpl(type, null, null, null, name, null);
    }

    /**
     * 
     * @param type
     * @param server
     * @param catalog
     * @param schema
     * @param name
     * @return 
     */
    public static SQLName forObject(
            final SQLNameType type,
            final String server,
            final String catalog,
            final String schema,
            final String name) {
        return new SQLNameImpl(type, server, catalog, schema, name, null);
    }

    /**
     * 
     * @param type
     * @param name
     * @param autoCloseable
     * @return 
     */
    public static TempSQLName forTempObject(
            final SQLNameType type,
            final String name,
            final FailableAutoCloseable<SQLException> autoCloseable) {
        return SQLNames.forTempObject(type, null, null, null, name, autoCloseable);
    }

    /**
     * 
     * @param type
     * @param schema
     * @param name
     * @param autoCloseable
     * @return 
     */
    public static TempSQLName forTempObject(
            final SQLNameType type,
            final String schema,
            final String name,
            final FailableAutoCloseable<SQLException> autoCloseable) {
        return SQLNames.forTempObject(type, null, null, schema, name, autoCloseable);
    }

    /**
     * 
     * @param type
     * @param catalog
     * @param schema
     * @param name
     * @param autoCloseable
     * @return 
     */
    public static TempSQLName forTempObject(
            final SQLNameType type,
            final String catalog,
            final String schema,
            final String name,
            final FailableAutoCloseable<SQLException> autoCloseable) {
        return SQLNames.forTempObject(type, null, catalog, schema, name, autoCloseable);
    }

    /**
     * 
     * @param type
     * @param server
     * @param catalog
     * @param schema
     * @param name
     * @param autoCloseable
     * @return 
     */
    public static TempSQLName forTempObject(
            final SQLNameType type,
            final String server,
            final String catalog,
            final String schema,
            final String name,
            final FailableAutoCloseable<SQLException> autoCloseable) {
        return new TempSQLNameImpl(type, server, catalog, schema, name, null, autoCloseable);
    }

    /**
     * 
     * @param object
     * @param column
     * @return 
     */
    public static SQLColumnName forColumn(
            final String object,
            final String column) {
        return new SQLColumnNameImpl(null, null, null, object, column);
    }

    /**
     * 
     * @param schema
     * @param object
     * @param column
     * @return 
     */
    public static SQLColumnName forColumn(
            final String schema,
            final String object,
            final String column) {
        return new SQLColumnNameImpl(null, null, schema, object, column);
    }

    /**
     * 
     * @param catalog
     * @param schema
     * @param object
     * @param column
     * @return 
     */
    public static SQLColumnName forColumn(
            final String catalog,
            final String schema,
            final String object,
            final String column) {
        return new SQLColumnNameImpl(null, catalog, schema, object, column);
    }

    /**
     * 
     * @param server
     * @param catalog
     * @param schema
     * @param object
     * @param column
     * @return 
     */
    public static SQLColumnName forColumn(
            final String server,
            final String catalog,
            final String schema,
            final String object,
            final String column) {
        return new SQLColumnNameImpl(server, catalog, schema, object, column);
    }

    /**
     * 
     * @param name
     * @return 
     */
    public static SQLName tableOrView(final SQLName name) {
        if (!name.type().isTableOrView()) {
            throw new IllegalArgumentException(String.format("Invalid type of object %s: %s", name.fullName(), name.type()));
        }
        return name;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public static SQLName nonNullTableOrView(final SQLName name) {
        Objects.requireNonNull(name, "SQL Object Name");
        return tableOrView(name);
    }
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final SQLName a = parseTable("dbo.Test");
        System.out.println(a);
        final SQLName b = parseView("dbo.Test");
        System.out.println(b);
        final SQLName z = parseColumn("dbo.Test.col1");
        System.out.println(z);
        final SQLName t = forTable("dbo", "test");
        System.out.println(t);
        final SQLColumnName c = forColumn("dbo", "test", "id");
        System.out.println(c);
    }
    
}
