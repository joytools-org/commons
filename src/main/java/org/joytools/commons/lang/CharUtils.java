package org.joytools.commons.lang;

/**
 * <p>
 * Title: </p>
 *
 * <p>
 * Description: </p>
 *
 * <p>
 * Copyright: Copyright (c) 2005</p>
 *
 * <p>
 * Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CharUtils extends org.apache.commons.lang3.CharUtils {

    /**
     * 
     */
    public CharUtils() {
    }

    /**
     *
     * @param ignoreCase boolean
     * @param c1 char
     * @param c2 char
     * @return boolean
     */
    public static boolean charMatches(final boolean ignoreCase,
            final char c1,
            final char c2) {
        if (c1 == c2) {
            return true;
        }
        if (ignoreCase) {
            // If characters don't match but case may be ignored,
            // try converting both characters to uppercase.
            // If the results match, then the comparison scan should
            // continue.
            final char u1 = Character.toUpperCase(c1);
            final char u2 = Character.toUpperCase(c2);
            if (u1 == u2) {
                return true;
            }
            // Unfortunately, conversion to uppercase does not work properly
            // for the Georgian alphabet, which has strange rules about case
            // conversion.  So we need to make one last check before
            // exiting.
            if (Character.toLowerCase(u1) == Character.toLowerCase(u2)) {
                return true;
            }
        }
        return false;
    }

}
