/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import java.sql.SQLException;
import java.util.Objects;
import org.joytools.commons.function.FailableAutoCloseable;

/**
 *
 * @author AndreaR
 */
class TempSQLNameImpl extends SQLNameImpl implements TempSQLName {
    
    protected TempSQLNameImpl(final SQLNameType type, 
            final String server, 
            final String catalog, 
            final String schema, 
            final String name, 
            final String column,
            final FailableAutoCloseable<SQLException> autoCloseable) {
        super(type, server, catalog, schema, name, column);
        this.autoCloseable = Objects.requireNonNull(autoCloseable, "AutoCloseable");
    }

    @Override
    public void close() throws SQLException {
        autoCloseable.close();
    }
    
    private final FailableAutoCloseable<SQLException> autoCloseable;
    
}
