/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import java.util.Iterator;
import static org.joytools.commons.collections.SeqAccImpl.COULD_NOT_BE_ITERATED_AGAIN;

/**
 * 
 * @param <E> 
 */
abstract class SeqAccImplSingleUseIterator<E> extends SeqAccImplIterator<E> {

    abstract protected Iterator<E> delegated();

    private boolean used = false;

    @Override
    public final Iterator<E> iterator() {
        if (used) {
            throw new IllegalStateException(COULD_NOT_BE_ITERATED_AGAIN);
        }
        used = true;
        return delegated();
    }

}
