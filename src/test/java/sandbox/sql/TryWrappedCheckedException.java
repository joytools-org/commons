/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.sql;

import java.io.IOException;
import java.sql.SQLException;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.lang.WrappedCheckedException;

/**
 *
 * @author AndreaR
 */
public class TryWrappedCheckedException {
    
    public static void main(final String... args) throws SQLException, IOException {
        try {
            final Exception cause = new IOException("Test");
            ExceptionUtils.wrapAndThrow(cause);
        } catch (final WrappedCheckedException sql) {
            // sql.throwCauseOfType(SQLException.class);
            sql.throwCauseOfType(IOException.class);
            sql.throwCauseOfType(SQLException.class);
            throw sql.unexpectedCondition();
        }
    }
    
}
