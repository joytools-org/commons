package org.joytools.commons.beans;

import java.beans.IntrospectionException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.accessors.reflect.BeanAccessors;
import org.joytools.commons.lang.ExceptionUtils;

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
public abstract class AbstractBean {

    /**
     *
     */
    public AbstractBean() {
    }

    /**
     *
     * @return int
     */
    @Override
    public int hashCode() {
        if (hasMutableValues()) {
            return computeHashCode();
        }
        if (m_cachedHashCode == null) {
            m_cachedHashCode = computeHashCode();
        }
        return m_cachedHashCode;
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        /**
        if (hasMutableValues()) {
            return buildToString();
        }
        if (m_cachedToString == null) {
            m_cachedToString = buildToString();
        }
        return m_cachedToString;
        **/
        return computeToString();
    }

    /**
     *
     */
    protected void assertModifiable() {
        if (!modifiable()) {
            throw new UnsupportedOperationException("The bean is read only");
        }
    }

    /**
     *
     */
    protected void makeReadOnly() {
        m_modifiable = false;
    }

    /**
     *
     * @return boolean
     */
    protected boolean hasMutableValues() {
        return false;
    }

    /**
     * 
     * @return 
     */
    protected boolean modifiable() {
        return m_modifiable;
    }

    /**
     *
     */
    protected void makeModified() {
        m_cachedHashCode = null;
        m_cachedToString = null;
    }

    /**
     * 
     * @return 
     */
    protected StringMapAccessor<Object> toAccessor() {
        if (m_acc == null) {
            try {
                m_acc = BeanAccessors.of(this);
            } catch (final IntrospectionException ex) {
                throw new UnsupportedOperationException(ex);
            }
        }
        return m_acc;
    }

    /**
     *
     * @return int
     */
    abstract protected int computeHashCode();

    /**
     * 
     * @return 
     */
    protected String computeToString() {
        final ToStringBuilder tsb = new ToStringBuilder(this);
        try {
            for (final var e : toAccessor().entriesByKey()) {
                tsb.append(e._1, e._2);
            }
        } catch (final Exception ex) {
            return ExceptionUtils.wrapAndThrow(ex);
        } 
        return tsb.build();
    }

    /**
     *
     */
    private Integer m_cachedHashCode = null;

    /**
     *
     */
    private String m_cachedToString = null;

    /**
     * 
     */
    private boolean m_modifiable = true;

    /**
     * 
     */
    private StringMapAccessor<Object> m_acc;
    
}
