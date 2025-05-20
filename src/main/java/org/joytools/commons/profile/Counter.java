package org.joytools.commons.profile;

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
public class Counter extends Number {

    /**
     *
     */
    public Counter() {
    }

    /**
     *
     */
    public Counter(final int threshold) {
        setThreshold(threshold);
    }

    /**
     * 
     * @param threshold
     * @param timeoutMillis 
     */
    public Counter(final int threshold,
            final int timeoutMillis) {
        setThreshold(threshold);
        setTimeout(timeoutMillis);
    }

    /**
    *
    * @param threshold int
    * @return int
    */
    public void setThreshold(final int threshold) {
        this.threshold = threshold;
    }

    /**
     * 
     * @param timeout 
     */
    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }

    /**
    *
    * @return int
    */
    public int getThreshold() {
        return threshold;
    }

    /**
     * 
     * @return 
     */
    public int getTimeout() {
        return timeout;
    }

    /**
    *
     * @param pattern
    */ /*
    public void setPattern(final String pattern) {
        throw new UnsupportedOperationException("setPattern()");
    } */

    /**
    *
    * @return String
    */ /*
    public String getPattern() {
        throw new UnsupportedOperationException("getPattern()");
    } */

    /**
    *
    * @return int
    */
    /*
    public boolean inc() {
        return increment();
    }
    */
    
    /**
     * 
     * @return 
     */
    public boolean increment() {
        ++value;
        if (isThreshold()) {
            lastThresholdTimeMillis = System.currentTimeMillis();
            return true;
        }
        final int appliedTimeOut = getTimeout();
        if (appliedTimeOut > 0) {
            final long timeMillis = System.currentTimeMillis();
            if (timeMillis >= lastThresholdTimeMillis + appliedTimeOut) {
                lastThresholdTimeMillis = timeMillis;
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @return 
     */
    public boolean isThreshold() {
        final int t = getThreshold();
        if (t <= 0) {
            return false;
        }
        return (value == 1) || (value % t == 0);
    }
    
    /**
     *
     * @return String
     */
    public String toString() {
        return String.valueOf(value);
        /*
        final String val = String.valueOf(m_val);
        if (m_pattern == null) {
            return val;
        }
        return StringUtils.replace(m_pattern, "%n", val);
        */
    }

    /**
     *
     * @return int
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @return boolean
     */
    public boolean isBeforeFirst() {
        return value() == 0;
    }

    /**
    *
    * @return boolean
    */
    public boolean isFirst() {
        return value() == 1;
    }

    /**
    *
    * @return int
    */
    public int value() {
        return value;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int intValue() {
        return value;
    }

    /**
     * 
     * @return 
     */
    @Override
    public long longValue() {
        return value;
    }

    /**
     * 
     * @return 
     */
    @Override
    public double doubleValue() {
        return value;
    }

    /**
     * 
     * @return 
     */
    @Override
    public float floatValue() {
        return value;
    }

    /**
     * 
     */
    public void reset() {
        value = 0;
        lastThresholdTimeMillis = System.currentTimeMillis();
    }
    
    /**
    *
    */
    private int value = 0;

    /**
     * 
     */
    private long lastThresholdTimeMillis = System.currentTimeMillis();

    /**
    *
    */
    private int threshold = 0;

    /**
     * 
     */
    private int timeout = 0;

    /**
    *
    */
    // private String m_pattern;

}
