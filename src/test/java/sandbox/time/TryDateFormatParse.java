/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.time.DateFormats;

/**
 *
 * @author AndreaR
 */
public class TryDateFormatParse {
    
    public static void main(final String... args) throws Exception {
        final Map<String, DateFormat> m = new LinkedHashMap();
        Arrays.asList(
                "yyyy-MM-dd",
                "yyyyMMdd",
                "ddMMyyyy",
                "dd-MM-yyyy",
                "yyyy.MM.dd",
                "dd.MM.yyyy")
                .forEach(f -> m.put(f, DateFormats.strict(f)));
        final List<String> dates = Arrays.asList(
                "20220101",
                "2022.01.01",
                "2022-01-01",
                "01012022",
                "01.01.2022",
                "01-01-2022");
        dates.forEach(d -> {
            System.out.println("****************************************");
            System.out.println("*** Testing date: " + d);
            m.entrySet().forEach(e -> {
                System.out.println("  " + e.getKey());
                try {
                    final DateFormat fmt = e.getValue();
                    final Date v = fmt.parse(d);
                    System.out.println("    ==> " + DateFormats.isoDateTime().format(v));
                    final String d2 = fmt.format(v);
                    if (!StringUtils.equalsIgnoreCase(d, d2)) {
                        System.out.println("    ==> ERROR: " + d + " <> " + d2 + " (isLenient:" + fmt.isLenient() + ")");
                    }
                } catch (final ParseException ex) {
                    System.out.println("    ==> ERROR: " + ex);
                }
            });
        });
    }
    
}
