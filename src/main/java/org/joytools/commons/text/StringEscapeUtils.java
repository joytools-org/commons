package org.joytools.commons.text;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.lang.StringFunction;
import org.joytools.commons.lang.StringUtils;

/**
 * <p>
 * Title: </p>
 *
 * <p>
 * Description: </p>
 *
 * <p>
 * Copyright: Copyright (c) 2003</p>
 *
 * <p>
 * Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class StringEscapeUtils extends org.apache.commons.text.StringEscapeUtils {

    /**
     *
     */
    public StringEscapeUtils() {
    }

    /**
     * 
     */
    public final static StringFunction ESCAPE_CSV_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeCsv(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction UNESCAPE_CSV_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return unescapeCsv(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction ESCAPE_SQL_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeSql(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction ESCAPE_XML_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeXml11(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction UNESCAPE_XML_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return unescapeXml(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction ESCAPE_XML10_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeXml10(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction UNESCAPE_XML10_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return unescapeXml(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction ESCAPE_XML11_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeXml11(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction UNESCAPE_XML11_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return unescapeXml(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction ESCAPE_HTML3_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeHtml3(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction UNESCAPE_HTML3_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return unescapeHtml3(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction ESCAPE_HTML4_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeHtml4(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction UNESCAPE_HTML4_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return unescapeHtml4(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction ESCAPE_JAVA_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeJava(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction UNESCAPE_JAVA_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return unescapeJava(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction ESCAPE_JSON_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeJson(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction UNESCAPE_JSON_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return unescapeJson(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction ESCAPE_ECMASCRIPT_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeEcmaScript(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction UNESCAPE_ECMASCRIPT_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return unescapeEcmaScript(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction ESCAPE_URL_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return escapeUrl(s);
        }
    };

    /**
     * 
     */
    public final static StringFunction UNESCAPE_URL_FUNCTION = new StringFunction() {
        @Override
        public String eval(final String s) {
            return unescapeUrl(s);
        }
    };

    /**
     *
     * @param sql String
     * @return String
     */
    public static String escapeSql(final String sql) {
        return StringUtils.replace(sql, "'", "''");
    }
    
    /**
     * 
     */
    private final static URLCodec URL_CODEC = new URLCodec();
    
    /**
     * 
     * @param url
     * @return 
     */
    public static String escapeUrl(final String url) {
        try {
            final String enc = URL_CODEC.encode(url);
            return StringUtils.replace(enc, "%20", "+");
        } catch (final EncoderException ex) {
            return ExceptionUtils.wrapAndThrow(ex);
        }
    }

    /**
     * 
     * @param url
     * @return 
     */
    public static String unescapeUrl(final String url) {
        try {
            return URL_CODEC.decode(url);
        } catch (final DecoderException ex) {
            return ExceptionUtils.wrapAndThrow(ex);
        }
    }
    
}
