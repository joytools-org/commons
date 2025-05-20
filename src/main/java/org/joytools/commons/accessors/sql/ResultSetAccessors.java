/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Objects;
import org.joytools.commons.sql.ResultSetUtils;
import org.joytools.commons.sql.UncheckedSQLException;

/**
 *
 * @author AndreaR
 */
public class ResultSetAccessors {

    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    public static ResultSetAccessor<Object> of(final ResultSet rs) throws SQLException {
        return new RSAccessorImpl(rs);
    }

    /**
     * 
     * @param rs
     * @return 
     * @throws java.sql.SQLException 
     */
    public static ResultSetAccessor<Object> ofV1(final ResultSet rs) throws SQLException {
        Objects.requireNonNull(rs, "ResultSet");
        ResultSetUtils.columnNames(rs);
        throw new UnsupportedOperationException();
        /* return new AbstractResultSetMutator() {
            @Override
            protected ResultSet resultSet() {
                return rs;
            }
        }; */
    }
    
    /**
     * 
     * @param md
     * @return
     * @throws SQLException 
     */
    public static ResultSetMetaDataAccessor of(final ResultSetMetaData md) throws SQLException {
        return new RSMetaDataAccessorImpl(md);
    }

}
