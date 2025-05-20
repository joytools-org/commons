/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.text;

import java.util.Iterator;
import org.joytools.commons.text.TextLinesBuffer;

/**
 *
 * @author AndreaR
 */
public class TestTextLines {
    
    public static void main(final String... args) {
        final TextLinesBuffer lines = TextLinesBuffer.empty();
        lines.add("uno", "due", "tre");
        final Iterator<String> itr = lines.iterator();
        System.out.println(itr.next());
        lines.add("quattro");
        System.out.println(itr.next());
    }
    
}
