/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

/**
 *
 * @author AndreaR
 */
class SQLColumnNameImpl extends SQLNameImpl implements SQLColumnName {

    SQLColumnNameImpl(final String server,
            final String catalog,
            final String schema,
            final String name,
            final String column) {
        super(SQLNameType.COLUMN, server, catalog, schema, name, column);
    }
    
}
