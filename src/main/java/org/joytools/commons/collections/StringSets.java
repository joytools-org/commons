/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.CopyOnWriteArraySet;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 * @author AndreaR
 */
public class StringSets {
    
    public static <T> CaseSensitivity caseSensitivityOf(final Set<T> set) {
        checkNotNull(set, "Set");
        if (set instanceof CaseSensitivitySupport css) {
            return css.caseSensitivity();
        }
        /*
        if (map instanceof EquatorMap) {
            final CaseSensitivity cs = CaseSensitivity.from(EquatorMap.class.cast(map).keyEquator());
            if (!cs.isUnknown()) {
                return cs;
            }
        } 
        if (set instanceof CaseInsensitiveSet) {
            return CaseSensitivity.INSENSITIVE;
        } */
        if (set instanceof HashSet
                || set instanceof EnumSet
                || set instanceof CopyOnWriteArraySet) {
            return CaseSensitivity.SENSITIVE;
        }
        if (set instanceof SortedSet sortedSet) {
            final CaseSensitivity cs = CaseSensitivity.from(sortedSet.comparator());
            if (!cs.isUnknown()) {
                return cs;
            }
        }
        return CaseSensitivity.UNKNOWN;
    }
    
}
