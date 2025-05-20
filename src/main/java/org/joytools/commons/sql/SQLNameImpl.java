/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import static com.google.common.base.Preconditions.checkArgument;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import java.util.List;
import java.util.Objects;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.interop.Java;
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
class SQLNameImpl implements SQLName {

    /**
     * 
     * @param type
     * @param server
     * @param catalog
     * @param schema
     * @param name
     * @param column 
     */
    protected SQLNameImpl(
            final SQLNameType type,
            final String server,
            final String catalog,
            final String schema,
            final String name,
            final String column) {
        this.type = Objects.requireNonNull(type, "Type");
        this.name = StringUtils.trimToNull(name);
        checkArgument(this.name != null, "Empty name");
        this.catalog = StringUtils.trimToEmpty(catalog);
        this.schema = StringUtils.trimToEmpty(schema);
        this.server = StringUtils.trimToEmpty(server);
        Seq<String> items = buildItems(this.server, this.catalog, this.schema, this.name);
        if (this.type.isColumn()) {
            this.column = StringUtils.trimToNull(column);
            checkArgument(this.column != null, "Empty column");
            items = items.append(this.column);
        } else {
            this.column = null;
        }
        this.items = items;
        this.fullName = items.mkString(".");
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        return Objects.hash(fullName, type);
    }
    
    /**
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof SQLName)) {
            return false;
        }
        final SQLName other = SQLName.class.cast(obj);
        return fullName.equals(other.fullName()) && type.equals(other.type());
    }
    
    /**
     * 
     * @param type
     * @param name
     * @return 
     */
    protected static SQLNameImpl parse(final SQLNameType type, final CharSequence fullName) {
        final String n = StringUtils.trimToEmpty(fullName);
        if (!n.isEmpty()) {
            final List<String> parts = IterableAccessors.of(StringUtils.split(n, '.'))
                    .to(Java.arrayList());
            final int ds = type.isColumn() ? 1 : 0;
            final int minSize = 1 + ds;
            final int maxSize = 4 + ds;
            if (parts.size() >= minSize && parts.size() <= maxSize) {
                final String column = type.isColumn() ? lastOrNull(parts) : null;
                final String name = lastOrNull(parts);
                final String schema = lastOrNull(parts);
                final String catalog = lastOrNull(parts);
                final String server = lastOrNull(parts);
                return new SQLNameImpl(type, server, catalog, schema, name, column);
            }
        }
        throw new IllegalArgumentException("Invalid name for " + type + ": " + n);
    }
    
    /**
     * 
     * @param list
     * @return 
     */
    private static String lastOrNull(final List<String> list) {
        if (list.isEmpty()) {
            return null;
        }
        final int index = list.size() - 1;
        final String ret = list.get(index);
        list.remove(index);
        return ret;
    }
            
    @Override
    public SQLNameType type() {
        return type;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String schema() {
        return schema;
    }

    @Override
    public String catalog() {
        return catalog;
    }

    @Override
    public String server() {
        return server;
    }
    
    @Override
    public String fullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName + "[" + type.name() + "]";
    }
    
    public String column() {
        return column;
    }
    
    private final SQLNameType type;
    
    private final String name;

    private final String schema;

    private final String catalog;

    private final String server;
    
    private final String column;
    
    private final String fullName;

    private final Seq<String> items;
    
    static Seq<String> buildItems(final String... args) {
        final Seq<String> items = Vector.of(args);
        final int firstNonEmpty = items.indexWhere(StringPredicates.isNotBlank());
        return items.subSequence(firstNonEmpty);
    }

}
