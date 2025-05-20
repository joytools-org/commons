/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.joytools.commons.function.FailableAutoCloseable;
import org.joytools.commons.function.FailableAutoCloseables;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.util.DefaultDisposer;
import org.joytools.commons.util.FailableAutoCloseableContainer;

/**
 *
 * @author AndreaR
 */
public class ConnectionSettings extends FailableAutoCloseableContainer<SQLException> {

    protected ConnectionSettings(final Connection cn) {
        this.cn = Objects.requireNonNull(cn, "Connection");
    }
    
    public static ConnectionSettings of(final Connection cn) {
        return new ConnectionSettings(cn);
    }
    
    /**
     * 
     * @param flag
     * @return
     * @throws SQLException 
     */
    public ConnectionSettings finalCommit(final Boolean flag) throws SQLException {
        if (flag != null && flag) {
            dispose(FailableAutoCloseables.of(() -> cn.commit()));
        }
        return this;
    }

    /**
     * 
     * @param cn
     * @param autoCommit
     * @return
     * @throws SQLException 
     */
    public ConnectionSettings autoCommit(final Boolean autoCommit) throws SQLException {
        if (autoCommit == null) {
            return this;
        }
        final boolean prevAutoCommit = cn.getAutoCommit();
        dispose(FailableAutoCloseables.of(() -> cn.setAutoCommit(prevAutoCommit)));
        cn.setAutoCommit(autoCommit);
        return this;
    }
    
    /**
     * 
     * @param level
     * @return
     * @throws SQLException 
     */
    public ConnectionSettings transactionIsolation(final Integer level) throws SQLException {
        if (level == null) {
            return this;
        }
        final int prevTransIsolation = cn.getTransactionIsolation();
        dispose(FailableAutoCloseables.of(() -> cn.setTransactionIsolation(prevTransIsolation)));
        cn.setTransactionIsolation(level);
        return this;
    }

    /**
     * 
     * @param levelName
     * @return
     * @throws SQLException 
     */
    public ConnectionSettings transactionIsolation(final String levelName) throws SQLException {
        if (StringUtils.isEmpty(levelName)) {
            return this;
        }
        return transactionIsolation(TransactionIsolation.of(levelName));
    }

    /**
     * 
     * @param level
     * @return
     * @throws SQLException 
     */
    public ConnectionSettings transactionIsolation(final TransactionIsolation level) throws SQLException {
        if (level == null) {
            return this;
        }
        return transactionIsolation(level.value());
    }

    private final Connection cn; 
    
    // private final Variable<Boolean> prevAutoCommit = Variables.forClass(Boolean.class);

    // private final Variable<Integer> prevTransIsolation = Variables.forClass(Integer.class);
    
    // private final Variable<Boolean> finalCommit = Variables.forClass(Boolean.class);

}
