/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author AndreaR
 */
public abstract class Name {

    /**
     * 
     * @return
     * @throws SQLException 
     */
    public static Builder on() throws SQLException {
        return on0(".");
    }

    /**
     * 
     * @param md
     * @return
     * @throws SQLException 
     */
    public static Builder on(final DatabaseMetaData md) throws SQLException {
        Objects.requireNonNull(md, "Database MetaData");
        return on0(md);
    }
    
    /**
     * 
     * @param md
     * @return
     * @throws SQLException 
     */
    protected static Builder on0(final DatabaseMetaData md) throws SQLException {
        return on0(md.getCatalogSeparator());
    }
    
    /**
     * 
     * @param sep
     * @return
     * @throws SQLException 
     */
    protected static Builder on0(final String sep) throws SQLException {
        return new Builder() {
            @Override
            public Name table(final String... parts) {
                final String name = StringUtils.join(parts, sep);
                return new Name() {
                    @Override
                    protected String name() {
                        return name;
                    }
                };
            }

            @Override
            public Name view(final String... parts) {
                return table(parts);
            }
            
            @Override
            public Name column(final String... parts) {
                final String name = StringUtils.join(parts, sep);
                return new Name() {
                    @Override
                    protected String name() {
                        return name;
                    }
                };
            }
        };
    }

    /**
     * 
     * @param cn
     * @return
     * @throws SQLException 
     */
    public static Name.Builder on(final Connection cn) throws SQLException {
        Objects.requireNonNull(cn, "Connection");
        return on0(cn.getMetaData());
    }

    /**
     * 
     * @param stm
     * @return
     * @throws SQLException 
     */
    public static Name.Builder on(final Statement stm) throws SQLException {
        Objects.requireNonNull(stm, "Statement");
        return on0(stm.getConnection().getMetaData());
    }

    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    public static Name.Builder on(final ResultSet rs) throws SQLException {
        Objects.requireNonNull(rs, "ResultSet");
        return on0(rs.getStatement().getConnection().getMetaData());
    }

    /**
     * 
     * @return 
     */
    abstract protected String name();
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return name();
    }
    
    /**
     * 
     */
    public static abstract class Builder {
        
        public abstract Name table(final String... parts);

        public abstract Name view(final String... parts);

        public abstract Name column(final String... parts);
        
    }

}
