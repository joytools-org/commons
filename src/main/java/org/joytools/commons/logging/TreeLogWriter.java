/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.logging;

import org.joytools.commons.util.FailableOpenable;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public interface TreeLogWriter<E extends Exception> extends FailableOpenable<E> {
    
    public void treeLogWrite(final TreeLogRecord logRecord,
        final TreeLogWriterOperationEnum operation) throws E;  
    
}

