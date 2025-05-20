/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.profile;

/**
 *
 * @author AndreaR
 */
public class LimitCounter extends Number {

    public LimitCounter() {
    }
    
    @Override
    public int intValue() {
        return m_counter;
    }

    @Override
    public long longValue() {
        return m_counter;
    }

    @Override
    public float floatValue() {
        return m_counter;
    }

    @Override
    public double doubleValue() {
        return m_counter;
    }
    
    public LimitCounter limit(final int limit) {
        this.m_limit = limit;
        return this;
    }
    
    public int limit() {
        return m_limit;
    }
    
    public boolean increment() {
        final boolean result = limitReached();
        m_counter++;
        return !result;
    }
    
    public boolean limitReached() {
        return m_limit >= 0 && m_counter >= m_limit;
    }
    
    public LimitCounter start() {
        m_counter = 0;
        return this;
    }
    
    @Override
    public String toString() {
        return String.valueOf(m_counter);
    }
    
    private int m_counter = 0;
    
    private int m_limit = -1;
    
}
