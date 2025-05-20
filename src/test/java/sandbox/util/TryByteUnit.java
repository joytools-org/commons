/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.util;

import java.util.Arrays;
import java.util.List;
import org.joytools.commons.io.FileUtils;
import org.joytools.commons.util.ByteUnit;

/**
 *
 * @author AndreaR
 */
public class TryByteUnit {
    
    public static void main(final String... args) {
        final List<Integer> bytes = Arrays.asList(0, 1, 2, 1022, 1023, 1024, 1025, 1026, 1600, 2047, 2048);
        bytes.forEach((b) -> {
            System.out.println(b + ": " + FileUtils.byteCountToDisplaySize(b));
        });
        
        System.out.println(FileUtils.byteCountToDisplaySize((long)ByteUnit.MIB.toBytes(10)));
    }
    
}
