/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.json;

import javax.json.JsonObject;
import org.joytools.commons.json.JsonUtils;

/**
 *
 * @author AndreaR
 */
public class TryJson {
    
    public static void main(final String... args) throws Exception {
        final String s = "{\"name\":\"test\",\"note\":\"<p><b>These accounts are expected to be moved under the new DXC organization unit.</b></p>\"}";
        final JsonObject jso = JsonUtils.readObject(s);
        System.out.println(JsonUtils.getStringIgnoreCase(jso, "NAME"));
        System.out.println(JsonUtils.getStringIgnoreCase(jso, "NOTE"));
    }
    
}
