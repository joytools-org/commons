/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author AndreaR
 * @param <T>
 */
public class CaseSpaceNormalizer<T> implements StringFunction<T> {

    /**
     * 
     * @param caseNormalizer
     * @param spaceNormalizer
     * @param nullEmptyNormalizer 
     */
    public CaseSpaceNormalizer(
            final CaseNormalizer caseNormalizer,
            final SpaceNormalizer spaceNormalizer,
            final NullEmptyNormalizer nullEmptyNormalizer) { 
        this.caseNormalizer = caseNormalizer;
        this.spaceNormalizer = spaceNormalizer;
        this.nullEmptyNormalizer = nullEmptyNormalizer;
    }

    public final static CaseSpaceNormalizer<Object> ORIGINAL = new CaseSpaceNormalizer(null, null, null);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, null, null);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_NO_SPACE = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, SpaceNormalizer.NONE, null);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_NO_SPACE_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, SpaceNormalizer.NONE, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_NO_SPACE_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, SpaceNormalizer.NONE, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_SINGLE_SPACE = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, SpaceNormalizer.SINGLE, null);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_SINGLE_SPACE_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, SpaceNormalizer.SINGLE, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_SINGLE_SPACE_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, SpaceNormalizer.SINGLE, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, null, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, null, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_TRIM = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, SpaceNormalizer.TRIM, null);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_TRIM_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, SpaceNormalizer.TRIM, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> CAPITALIZE_TRIM_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.CAPITALIZED, SpaceNormalizer.TRIM, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> LOWERCASE = new CaseSpaceNormalizer(CaseNormalizer.LOWER, null, null);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_NO_SPACE = new CaseSpaceNormalizer(CaseNormalizer.LOWER, SpaceNormalizer.NONE, null);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_NO_SPACE_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.LOWER, SpaceNormalizer.NONE, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_NO_SPACE_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.LOWER, SpaceNormalizer.NONE, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_SINGLE_SPACE = new CaseSpaceNormalizer(CaseNormalizer.LOWER, SpaceNormalizer.SINGLE, null);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_SINGLE_SPACE_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.LOWER, SpaceNormalizer.SINGLE, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_SINGLE_SPACE_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.LOWER, SpaceNormalizer.SINGLE, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.LOWER, null, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.LOWER, null, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_TRIM = new CaseSpaceNormalizer(CaseNormalizer.LOWER, SpaceNormalizer.TRIM, null);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_TRIM_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.LOWER, SpaceNormalizer.TRIM, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> LOWERCASE_TRIM_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.LOWER, SpaceNormalizer.TRIM, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> NO_SPACE = new CaseSpaceNormalizer(null, SpaceNormalizer.NONE, null);
    public final static CaseSpaceNormalizer<Object> NO_SPACE_TO_EMPTY = new CaseSpaceNormalizer(null, SpaceNormalizer.NONE, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> NO_SPACE_TO_NULL = new CaseSpaceNormalizer(null, SpaceNormalizer.NONE, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> SINGLE_SPACE = new CaseSpaceNormalizer(null, SpaceNormalizer.SINGLE, null);
    public final static CaseSpaceNormalizer<Object> SINGLE_SPACE_TO_EMPTY = new CaseSpaceNormalizer(null, SpaceNormalizer.SINGLE, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> SINGLE_SPACE_TO_NULL = new CaseSpaceNormalizer(null, SpaceNormalizer.SINGLE, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> TRIM = new CaseSpaceNormalizer(null, SpaceNormalizer.TRIM, null);
    public final static CaseSpaceNormalizer<Object> TRIM_TO_EMPTY = new CaseSpaceNormalizer(null, SpaceNormalizer.TRIM, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> TRIM_TO_NULL = new CaseSpaceNormalizer(null, SpaceNormalizer.TRIM, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> UPPERCASE = new CaseSpaceNormalizer(CaseNormalizer.UPPER, null, null);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_NO_SPACE = new CaseSpaceNormalizer(CaseNormalizer.UPPER, SpaceNormalizer.NONE, null);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_NO_SPACE_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.UPPER, SpaceNormalizer.NONE, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_NO_SPACE_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.UPPER, SpaceNormalizer.NONE, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_SINGLE_SPACE = new CaseSpaceNormalizer(CaseNormalizer.UPPER, SpaceNormalizer.SINGLE, null);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_SINGLE_SPACE_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.UPPER, SpaceNormalizer.SINGLE, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_SINGLE_SPACE_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.UPPER, SpaceNormalizer.SINGLE, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.UPPER, null, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.UPPER, null, NullEmptyNormalizer.NULL);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_TRIM = new CaseSpaceNormalizer(CaseNormalizer.UPPER, SpaceNormalizer.TRIM, null);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_TRIM_TO_EMPTY = new CaseSpaceNormalizer(CaseNormalizer.UPPER, SpaceNormalizer.TRIM, NullEmptyNormalizer.EMPTY);
    public final static CaseSpaceNormalizer<Object> UPPERCASE_TRIM_TO_NULL = new CaseSpaceNormalizer(CaseNormalizer.UPPER, SpaceNormalizer.TRIM, NullEmptyNormalizer.NULL);

    private final CaseNormalizer caseNormalizer;

    private final SpaceNormalizer spaceNormalizer;

    private final NullEmptyNormalizer nullEmptyNormalizer;

    /**
     * 
     * @param input
     * @return 
     */
    @Override
    public final String eval(final String input) {
        String s = input;
        
        // Null or Empty
        if (s == null || s.length() == 0) {
            if (nullEmptyNormalizer == null) {
                return s;
            }
            return nullEmptyNormalizer.eval(s);
        }
        
        // Space
        if (spaceNormalizer != null) {
            s = spaceNormalizer.eval(s);
        }

        // Case
        if (caseNormalizer != null) {
            s = caseNormalizer.eval(s);
        }

        // Null or Empty
        if (s == null || s.length() == 0) {
            if (nullEmptyNormalizer == null) {
                return s;
            }
            return nullEmptyNormalizer.eval(s);
        }

        return s;
    }

    /**
     * 
     * @return 
     */
    protected String nullString() {
        if (nullEmptyNormalizer == null) {
            return null;
        }
        return nullEmptyNormalizer.eval(null);
    }

    /**
     * 
     * @return 
     */
    protected String emptyString() {
        if (nullEmptyNormalizer == null) {
            return StringUtils.EMPTY;
        }
        return nullEmptyNormalizer.eval(StringUtils.EMPTY);
    }

}

