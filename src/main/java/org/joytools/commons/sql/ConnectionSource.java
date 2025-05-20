/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.lang3.function.FailableSupplier;
import org.joytools.commons.function.FailableAutoCloseables;
import org.joytools.commons.naming.ContextUtils;

/**
 *
 * @author AndreaR
 */
public abstract class ConnectionSource {

    /**
     * 
     * @return 
     * @throws java.sql.SQLException 
     */
    public abstract ConnectionSession newSession() throws SQLException;
    
    /**
     * 
     * @param ds
     * @return 
     */
    public static ConnectionSource of(final DataSource ds) {
        Objects.requireNonNull(ds, "DataSource");
        return new ConnectionSource() {
            @Override
            public ConnectionSession newSession() {
                return new ConnectionSession() {
                    @Override
                    public Connection connection() throws SQLException {
                        if (conn == null) {
                            conn = ds.getConnection();
                            dispose(conn, FailableAutoCloseables.of(conn::close));
                        }
                        return conn;
                    }
                    private Connection conn = null;
                };
            }
        };
    }
    
    /**
     * 
     * @param ds
     * @return 
     */
    public static ConnectionSource of(final FailableSupplier<Connection, SQLException> ds) {
        Objects.requireNonNull(ds, "DataSource");
        return new ConnectionSource() {
            @Override
            public ConnectionSession newSession() {
                return new ConnectionSession() {
                    @Override
                    public Connection connection() throws SQLException {
                        if (conn == null) {
                            conn = ds.get();
                            dispose(conn, FailableAutoCloseables.of(conn::close));
                        }
                        return conn;
                    }
                    private Connection conn = null;
                };
            }
        };
    }

    public static ConnectionSource of(final Context ctx,
            final String name) {
        Objects.requireNonNull(ctx, "Context");
        Objects.requireNonNull(name, "Name");
        return new ConnectionSource() {
            @Override
            public ConnectionSession newSession() {
                return new ConnectionSession() {
                    @Override
                    public Connection connection() throws SQLException {
                        if (conn == null) {
                            try {
                                final DataSource ds = ContextUtils.lookupDataSource(ctx, name);
                                conn = ds.getConnection();
                                dispose(conn, FailableAutoCloseables.of(conn::close));
                            } catch (final NamingException ne) {
                                throw new SQLException("Could not create the named DataSource: " + name, ne);
                            }
                        }
                        return conn;
                    }
                    private Connection conn = null;
                };
            }
        };
    }

    /**
     * 
     * @param conn
     * @return 
     */
    public static ConnectionSource of(final Connection conn) {
        Objects.requireNonNull(conn, "Connection");
        return new ConnectionSource() {
            @Override
            public ConnectionSession newSession() {
                return new ConnectionSession() {
                    @Override
                    public Connection connection() throws SQLException {
                        return conn;
                    }
                };
            }
        };
    }

}
