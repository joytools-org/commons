/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.lang;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.joytools.commons.interop.Java;
import org.joytools.commons.io.LineIterators;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.text.TextLinesBuffer;

/**
 *
 * @author AndreaR
 */
public class TryLineIterator {
    
    public static void main(final String... args) throws Exception {
        /*
        compare("");
        compare("ANDREA");
        compare("UNO\nDUE");
        compare("UNO\nDUE\r");
        compare("UNO\r\nDUE");
        compare("UNO\n\nDUE\n\n");
        compare("\n\rUNO\n\nDUE\n\n");
        */
        // compare(FileUtils.readFileToString(new File("c:\\temp\\msonline\\O365EmailActivityUserDetailVIANET.csv"), StandardCharsets.UTF_8.displayName()));
        // compare(FileUtils.readFileToString(new File("c:\\temp\\msonline\\MFAEnabledUserReport_2020-Aug-28-Fri 04-00 AM.txt")));
        compare(FileUtils.readFileToString(new File("c:\\temp\\netstat.txt")));
    }
    
    public static void compare(final CharSequence cs) {
        System.out.println("*** COMPARING " + StringUtils.repeat("*", 80));
        System.out.println(cs);
        final List<String> lines1 = TextLinesBuffer.of(cs).to(Java.arrayList());
        final List<String> lines2 = new ArrayList();
        for (final Iterator<String> i = LineIterators.of(cs); i.hasNext(); ) {
            lines2.add(i.next());
        }
        
        boolean equals = lines1.equals(lines2);
        
        if (!equals) {
            System.out.println(" !!! DIFFERENCES !!!");
            System.out.println(lines1.hashCode() + ": " + lines1);
            System.out.println(lines2.hashCode() + ": " + lines2);
        }
    }
    
}
