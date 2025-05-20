/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.io;

import java.io.File;
import org.joytools.commons.io.FileUtils;
import org.joytools.commons.io.TempFileSupplier;
import org.joytools.commons.io.TempFileSuppliers;

/**
 *
 * @author AndreaR
 */
public class TryTempFileSupplier {
    
    public static void main(final String... args) throws Exception {
        try (final TempFileSupplier tfs = TempFileSuppliers.on(
                    FileUtils.getExistingDirectory("c:\\temp1"))
                .forFile("ciao.csv-")) {
            for (int i = 0; i < 10; i++) {
                final File f = tfs.get();
                System.out.println(f);
            }
        }
    }
}
