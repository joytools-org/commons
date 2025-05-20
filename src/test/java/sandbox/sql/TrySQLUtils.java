/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.sql;

import java.sql.Connection;
import org.joytools.commons.sql.SQLUtils;
import org.joytools.commons.sql.TransactionIsolation;

/**
 *
 * @author AndreaR
 */
public class TrySQLUtils {
    
    public static void main(final String... args) {
        System.out.println(TransactionIsolation.of("transaction_no_lock").name());
        System.out.println(TransactionIsolation.of(Connection.TRANSACTION_SERIALIZABLE).name());
        System.out.println(SQLUtils.uniqueObjectName());
        System.out.println(SQLUtils.uniqueObjectName("#T"));
        System.out.println(SQLUtils.uniqueObjectName("#T", 0));
        System.out.println(SQLUtils.uniqueObjectName("#T", 7));
    }
    
}
