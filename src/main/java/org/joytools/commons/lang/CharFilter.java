package org.joytools.commons.lang;
      
import com.google.common.base.CharMatcher;
import java.util.Objects;
import java.util.function.Predicate;
import org.apache.commons.lang3.BooleanUtils;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class CharFilter extends CharMatcher // implements Predicate<Character> {
{

    /**
     *
     */
    public final static CharFilter AsciiPrintableCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return CharUtils.isAsciiPrintable(c);
        }
    };

    /**
     *
     */
    public final static CharFilter NonAsciiPrintableCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return !CharUtils.isAsciiPrintable(c);
        }
    };

    /**
     *
     */
    public final static CharFilter AsciiCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return CharUtils.isAscii(c);
        }
    };

    /**
     *
     */
    public final static CharFilter NonAsciiCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return !CharUtils.isAscii(c);
        }
    };

    /**
     * 
     */
    public final static CharFilter AsciiControlCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return CharUtils.isAsciiControl(c);
        }
    };

    /**
     *
     */
    public final static CharFilter NonAsciiControlCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return !CharUtils.isAsciiControl(c);
        }
    };

    /**
     *
     */
    public final static CharFilter AsciiAlphanumericCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return CharUtils.isAsciiAlphanumeric(c);
        }
    };

    /**
     *
     */
    public final static CharFilter NonAsciiAlphanumericCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return !CharUtils.isAsciiAlphanumeric(c);
        }
    };

    /**
     *
     */
    public final static CharFilter WhiteSpaceCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return Character.isWhitespace(c);
        }
    };

    /**
     * 
     */
    public final static CharFilter NonWhiteSpaceCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return !Character.isWhitespace(c);
        }
    };

    /**
     *
     */
    public final static CharFilter SpaceCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return c == ' ';
        }
    };

    /**
     * 
     */
    public final static CharFilter NonSpaceCharFilter = new CharFilter() {
        @Override
        public boolean matches(final char c) {
            return c != ' ';
        }
    };

    /**
     * 
     * @param p
     * @return 
     */
    public static CharFilter of(final Predicate<Character> p) {
        if (p instanceof CharFilter) {
            return (CharFilter)p;
        }
        return new CharFilter() {
            @Override
            public boolean matches(char c) {
                return p != null && BooleanUtils.isTrue(p.test(c));
            }
        };
    }

    /**
     * 
     * @param p
     * @return 
     */
    public static CharFilter of(final org.apache.commons.collections4.Predicate<Character> p) {
        if (p instanceof CharFilter) {
            return (CharFilter)p;
        }
        return new CharFilter() {
            @Override
            public boolean matches(char c) {
                return p != null && p.evaluate(c);
            }
        };
    }

    /**
     * 
     * @param p
     * @return 
     */
    public static CharFilter of(final com.google.common.base.Predicate<Character> p) {
        if (p instanceof CharFilter) {
            return (CharFilter)p;
        }
        return new CharFilter() {
            @Override
            public boolean matches(char c) {
                return p != null && BooleanUtils.isTrue(p.apply(c));
            }
        };
    }

    /**
     * 
     * @param c
     * @return 
     */
    @Override
    public boolean apply(final Character c) {
        return matches(Objects.requireNonNull(c, "Character"));
    }
    
    /**
     *
     * @param ch char
     * @param ignoreCase boolean
     * @return CharFilter
     */
    public static CharFilter of(final char ch,
            final boolean ignoreCase) {
        return new CharFilter() {
            @Override
            public boolean matches(final char c) {
                if (c == ch) {
                    return true;
                }
                if (ignoreCase) {
                    return CharUtils.charMatches(true, c, ch);
                }
                return false;
            }
        };
    }

    /**
     *
     * @param ch char
     * @return CharFilter
     */
    public static CharFilter of(final char ch) {
        return new CharFilter() {
            @Override
            public boolean matches(final char c) {
                return c == ch;
            }
        };
    }

    /**
     *
     * @param chars char[]
     * @return CharFilter
     */
    public static CharFilter of(final char[] chars) {
        return of(chars, false);
    }

    /**
     *
     * @param chars char[]
     * @param ignoreCase boolean
     * @return CharFilter
     */
    public static CharFilter of(final char[] chars,
            final boolean ignoreCase) {
        CharFilter filter = null;
        if (chars != null) {
            filter = new CharFilter() {
                @Override
                public boolean matches(final char c) {
                    if (chars.length == 1 && chars[0] == c) {
                        return true;
                    }
                    for (final char ch : chars) {
                        if (c == ch) {
                            return true;
                        }
                        if (ignoreCase) {
                            return CharUtils.charMatches(true, c, ch);
                        }
                    }
                    return false;
                }
            };
        }
        return filter;
    }

    /**
     *
     * @param chars CharSequence
     * @param ignoreCase boolean
     * @return CharFilter
     */
    public static CharFilter of(final CharSequence chars,
            final boolean ignoreCase) {
        if (chars == null || chars.length() == 0) {
            return null;
        }
        return of(chars.toString().toCharArray(), ignoreCase);
    }

    /**
     *
     * @param chars CharSequence
     * @return CharFilter
     */
    public static CharFilter of(final CharSequence chars) {
        if (chars == null || chars.length() == 0) {
            return null;
        }
        return of(chars.toString().toCharArray());
    }
    
}
