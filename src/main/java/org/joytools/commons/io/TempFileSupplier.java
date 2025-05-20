/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.io;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

/**
 *
 * @author AndreaR
 */
public interface TempFileSupplier extends Supplier<File>, AutoCloseable {
    
    @Override
    void close() throws IOException;
    
}
