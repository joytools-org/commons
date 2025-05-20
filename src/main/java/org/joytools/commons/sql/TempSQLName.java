/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import java.sql.SQLException;
import org.joytools.commons.function.FailableAutoCloseable;

/**
 *
 * @author AndreaR
 */
public interface TempSQLName extends SQLName, FailableAutoCloseable<SQLException> {
    
}
