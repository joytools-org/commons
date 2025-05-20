/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author AndreaR
 */
public abstract class UpdateQueryDef extends QueryDef {

    /**
     * 
     * @return 
     */
    @Override
    public final Type type() {
        return Type.UPDATE;
    }
    
    /**
     * 
     * @param psc
     * @return
     * @throws SQLException 
     */
    public int execute(final PreparedStatementContainer psc) throws SQLException {
        final PreparedStatement ps = psc.get(sql());
        return SQLUtils.executeUpdate(ps, parameters());
    }

    /**
     * 
     * @param cn
     * @return
     * @throws SQLException 
     */
    public int execute(final Connection cn) throws SQLException {
        try (final PreparedStatement ps = cn.prepareStatement(sql())) {
            return SQLUtils.executeUpdate(ps, parameters());
        }
    }

    /**
     * 
     * @param ds
     * @return
     * @throws SQLException 
     */
    public int execute(final DataSource ds) throws SQLException {
        try (final Connection cn = ds.getConnection()) {
            return UpdateQueryDef.this.execute(cn);
        }
    }

}
