/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.io;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.joytools.commons.io.AtomicFileReplacer;

/**
 *
 * @author AndreaR
 */
public class TryFileReplacer {
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(final String... args) throws Exception {
        // final Timestamp ts = null; // new Timestamp(System.currentTimeMillis());
        // final Timestamp ts = new Timestamp(System.currentTimeMillis());
        final String ts = "2023-06-06 08:00:00";
        final AtomicFileReplacer tfw = AtomicFileReplacer.of("c:\\temp\\o365\\TempWorker.csv")
                .with((f) -> {
                    final List lines = Arrays.asList(
                            "\"sAMAccountName\",\"sAMDomainName\",\"whenChangedTS\"", 
                            "\"ANDREAR\",\"LUXGROUP\",\"" + ts + "\"", 
                            "\"MPASTORE\",LUXGROUP\",\"" + ts + "\"", 
                            "\"MPRATESI\",LUXGROUP\",\"" + ts + "\"");
                    FileUtils.writeLines(f, lines);
                    return true;
                });
        if (tfw.replace()) {
            System.out.println("destFile has been changed");
        } else {
            System.out.println("destFile has NOT been changed");            
        }
    }
    
}
