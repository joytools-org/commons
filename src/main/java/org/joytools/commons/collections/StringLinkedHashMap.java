/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import static com.google.common.base.Preconditions.checkNotNull;
import org.apache.commons.collections4.Equator;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class StringLinkedHashMap<V> extends EquatorLinkedHashMap<String, V> implements StringMap<V> {

    public StringLinkedHashMap(final CaseSensitivity cs,
            final Equator<V> valueEq) {
        super(checkNotNull(cs, "Case Sensitivity").equator(), valueEq);
        this.cs = cs;
    }

    public StringLinkedHashMap(final CaseSensitivity cs) {
        this(cs, null);
    }

    @Override
    public CaseSensitivity caseSensitivity() {
        return cs;
    }

    private final CaseSensitivity cs;
    
}
