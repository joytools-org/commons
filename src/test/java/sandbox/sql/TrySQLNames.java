/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.sql;

import org.joytools.commons.sql.SQLNames;
import org.joytools.commons.sql.SQLName;

/**
 *
 * @author AndreaR
 */
public class TrySQLNames {
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final SQLName n1 = SQLNames.forTable("dbo", "Ciao");
        final SQLName n2 = SQLNames.forTable("dbo", "Ciao");
        System.out.println(n1.hashCode());
        System.out.println(n2.hashCode());
        System.out.println(n1.equals(null));
        System.out.println(n1.equals(n1));
        System.out.println(n1.equals(n2));
    }
    
}
