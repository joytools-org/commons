/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.naming.directory;

import io.vavr.Tuple2;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import org.joytools.commons.function.ProcessorOperation;

/**
 *
 * @author AndreaR
 */
public class DirContextUtils {
    
    /**
     * 
     * @param op
     * @return 
     */
    public static int attributeOperationCode(final ProcessorOperation op) {
        Objects.requireNonNull(op, "Operation");
        switch (op) {
            case ADD:
                return DirContext.ADD_ATTRIBUTE;
            case MODIFY:
                return DirContext.REPLACE_ATTRIBUTE;
            case DELETE:
                return DirContext.REMOVE_ATTRIBUTE;
        }
        throw new IllegalArgumentException(op.name() + " is not an attribute operation");
    }
    
}
