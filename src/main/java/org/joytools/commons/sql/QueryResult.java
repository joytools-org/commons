/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.function.FailableSupplier;
import org.joytools.commons.accessors.sql.ResultSetAccessor;
import org.joytools.commons.accessors.sql.ResultSetAccessors;
import org.joytools.commons.util.AbstractFailableOpenable;
import org.joytools.commons.accessors.sql.ResultSetMetaDataAccessor;
import org.joytools.commons.collections.IterableAccessor;

/**
 * 
 */
public abstract class QueryResult extends AbstractFailableOpenable<SQLException> implements FailableSupplier<IterableAccessor<ResultSetAccessor<Object>>, SQLException> {

    /**
     * 
     */
    protected QueryResult() {
    }

    @Override
    protected boolean isLazyOpen() {
        return true;
    }
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    protected final ResultSet getRS() throws SQLException {
        checkedMakeOpen();
        return getRS0();
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    protected abstract ResultSet getRS0() throws SQLException;

    /**
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public final IterableAccessor<ResultSetAccessor<Object>> get() throws SQLException {
        return rows();
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    public final IterableAccessor<ResultSetAccessor<Object>> rows() throws SQLException {
        final ResultSet rs = getRS();
        final ResultSetAccessor<Object> acc = ResultSetAccessors.of(rs);
        // return Iterations.from(ResultSetUtils.iterator(rs)).map(r -> acc);
        return ResultSetUtils.iteration(rs).map(r -> acc);
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    public final IterableAccessor<ResultSetAccessor<Object>> rows2() throws SQLException {
        final ResultSet rs = getRS();
        final ResultSetAccessor<Object> acc = ResultSetAccessors.of(rs);
        return ResultSetUtils.iteration2(rs).map(r -> acc);
    }

    /**
     * 
     * @return 
     * @throws java.sql.SQLException 
     */
    public ResultSetMetaDataAccessor metaData() throws SQLException {
        if (metaData == null) {
            final ResultSet rs = getRS();
            /* metaData = new AbstractResultSetColumnMetaDataMutator() {
                @Override
                protected ResultSet resultSet() {
                    return rs;
                }
            }; */
            metaData = ResultSetAccessors.of(rs.getMetaData());
        }
        return metaData;
    }

    /**
     * 
     */
    private ResultSetMetaDataAccessor metaData;

}
