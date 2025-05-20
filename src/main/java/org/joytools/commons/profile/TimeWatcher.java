package org.joytools.commons.profile;

import org.apache.commons.lang3.time.StopWatch;

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
public class TimeWatcher {

    /**
     *
     * @param container TimeWatchers
     * @param name String
     */
    public TimeWatcher(final TimeWatchers container,
            final String name) {
        m_container = container;
        m_name = name;
    }

    /**
     *
     */
    public void enable() {
        m_enabled = true;
    }

    /**
     *
     */
    public void disable() {
        m_enabled = false;
    }

    /**
     *
     * @param name String
     */
    public TimeWatcher(final String name) {
        m_name = name;
    }

    /**
     *
     * @param name String
     * @return TimeWatcher
     */
    public TimeWatcher child(final String name) {
        if (m_container == null) {
            throw new IllegalStateException("No container available");
        }
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("No name specified");
        }
        final String childName = getName() + "." + name;
        return m_container.get(childName);
    }

    /**
     *
     * @return String
     */
    public String getName() {
        return m_name;
    }

    /**
     *
     */
    public void reset() {
        m_totalNanoTime = -1;
        m_count = 0;
        m_watch.reset();
    }

    /**
     *
     * @return 
     */
    public long start() {
        if (!m_watch.isStarted()) {
            m_watch.start();
        }
        return ++m_count;
    }

    /**
     *
     * @return 
     */
    public long stop() {
        long elapsed = 0;
        if (m_watch.isStarted()) {
            m_watch.stop();
            elapsed = m_watch.getNanoTime();
        }
        m_watch.reset();
        if (m_enabled) {
            if (m_totalNanoTime < 0) {
                m_totalNanoTime = 0;
            }
            m_totalNanoTime += elapsed;
        }
        return elapsed;
    }

    /**
     *
     * @return
     */
    public long getCount() {
        return m_count;
    }

    /**
     *
     * @return long
     */
    public long getTime() {
        long t = getNanoTime();
        if (t < 0) {
            return t;
        }
        return t / 1000000;
    }

    /**
     *
     * @return long
     */
    public long getNanoTime() {
        return m_totalNanoTime;
    }

    /**
     *
     * @return boolean
     */
    public boolean hasElapsed() {
        return m_totalNanoTime >= 0;
    }

    /**
     *
     */
    private long m_totalNanoTime = -1;

    /**
     *
     */
    private TimeWatchers m_container = null;

    /**
     *
     */
    private StopWatch m_watch = new StopWatch();

    /**
     *
     */
    private String m_name;

    /**
     *
     */
    private boolean m_enabled = true;

    /**
     *
     */
    private long m_count = 0;

}
