/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.joytools.commons.sql.AbstractResultSetColumnData;
import org.joytools.commons.sql.ResultSetColumnData;
import org.joytools.commons.sql.ResultSetColumnMetaData;
import org.joytools.commons.sql.ResultSetColumnMetaDataBean;

/**
 *
 * @author AndreaR
 */
class RSColumnDataAccessorImpl extends NakedRSGenericMutator<ResultSetColumnData> implements ResultSetColumnDataAccessor {

    protected RSColumnDataAccessorImpl(ResultSet rs) throws SQLException {
        super(rs);
        arrData = new ResultSetColumnData[md.size];
        arrMetaData = new ResultSetColumnMetaData[md.size];
        for (int i = 0; i < arrData.length; i++) {
            final int index = i;
            final int index1 = i + 1;
            arrMetaData[i] = ResultSetColumnMetaDataBean.of(rs.getMetaData(), index1);
            arrData[i] = new AbstractResultSetColumnData() {
                @Override
                protected ResultSet rs() {
                    return rs;
                }

                @Override
                protected int index() {
                    return index1;
                }

                @Override
                public ResultSetColumnMetaData getMetaData() {
                    return arrMetaData[index];
                }
            };
        }
    }

    @Override
    protected ResultSetColumnData get0(int index) throws SQLException {
        return arrData[index];
    }

    @Override
    protected void set0(final int index, final ResultSetColumnData value) throws SQLException {
        throw new UnsupportedOperationException("set");
    }
    
    final ResultSetColumnData[] arrData;
    
    final ResultSetColumnMetaData[] arrMetaData;
    
}
