package org.joytools.commons.profile;

import java.util.List;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

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
public class TimeWatchers {

    /**
     *
     */
    public TimeWatchers() {
    }

    /**
     *
     * @param key Object
     * @return TimeWatcher
     */
    public TimeWatcher get(final String key) {
        String s = key == null ? null : key;
        if (s == null) {
            s = "default";
        }
        TimeWatcher tw = m_timeWatchers.get(s);
        if (tw == null) {
            tw = new TimeWatcher(this, s);
            m_timeWatchers.put(s, tw);
        }
        return tw;
    }

    /**
     *
     */
    protected void clear() {
        m_timeWatchers.clear();
    }

    /**
     *
     * @return List
     */
    public List<TimeWatcher> list() {
        return list(false);
    }

    /**
     *
     * @param withElapsedOnly
     * @return List
     */
    public List<TimeWatcher> list(final boolean withElapsedOnly) {
        final List<TimeWatcher> result = new ArrayList<>();
        for (final TimeWatcher tw : m_timeWatchers.values()) {
            if (!withElapsedOnly || withElapsedOnly && tw.hasElapsed()) {
                result.add(tw);
            }
        }
        return result;
    }

    /**
     *
     */
    private final SortedMap<String, TimeWatcher> m_timeWatchers = new TreeMap<String, TimeWatcher>();

}
