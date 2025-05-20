/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.sql;

import java.util.Arrays;
import java.util.List;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryDuplicates {
    
    public static void main(final String... args) {
        final List<String> keyColumns = Arrays.asList("firstName", "lastName", "birthDate");
        final List<String> dataColumns = Arrays.asList("FirstName", "lastName", "CompanyName");
        System.out.println(CaseSensitivity.INSENSITIVE.findDuplicates(keyColumns, dataColumns));
        System.out.println(CaseSensitivity.SENSITIVE.findDuplicates(keyColumns, dataColumns));
    }
    
}
