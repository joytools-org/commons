/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.io;

import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.io.LineIterator;

/**
 *
 * @author AndreaR
 */
public class TryLineIterator2 {

    public static void main(final String... args) throws IOException {
        try (final LineIterator itr = new LineIterator(new FileReader("c:/temp/netstat.txt"))) {
            while (itr.hasNext()) {
                System.out.println(itr.next());
            }
        }
    }
    
}
