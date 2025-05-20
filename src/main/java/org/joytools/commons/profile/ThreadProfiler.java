/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.profile;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;


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
public class ThreadProfiler {

    /**
     *
     */
    public ThreadProfiler() {
        reset();
    }

    /**
     *
     */
    private static ThreadLocal m_threadLocalData = new ThreadLocal<ThreadData>() {
        protected synchronized ThreadData initialValue() {
            final ThreadData data = new ThreadData();
            return data;
        }
    };

    /**
     *
     * @return ALThreadData
     */
    protected static ThreadData data() {
        return (ThreadData) m_threadLocalData.get();
    }

    /**
     *
     * @return UUID
     */
    public static UUID uuid() {
        return data().uuid();
    }

    /**
     *
     * @return List
     */
    public static List errors() {
        return data().errors();
    }

    /**
     *
     * @return List
     */
    public static List warnings() {
        return data().warnings();
    }

    /**
     *
     * @return List
     */
    public static List informations() {
        return data().informations();
    }

    /**
     *
     * @return Map
     */
    public static Map map() {
        return data().map();
    }

    /**
     *
     * @return TimeWatcher
     */
    public static TimeWatcher timeWatcher() {
        return data().timeWatchers().get(null);
    }

    /**
     *
     * @param key Object
     * @return TimeWatcher
     */
    public static TimeWatcher timeWatcher(final String key) {
        return data().timeWatchers().get(key);
    }

    /**
     * 
     * @param clazz
     * @return 
     */
    public static TimeWatcher timeWatcher(final Class clazz) {
        String className = clazz != null ? clazz.getCanonicalName() : null;
        return timeWatcher(className);
    }

    /**
     * 
     */
    public static TimeWatcher timeWatcher(final Class clazz, 
            final String name) {
        String className = clazz != null ? clazz.getCanonicalName() : null;
        final String key = className == null ? name : className + "." + name;
        return timeWatcher(key);
    }

    /**
     *
     * @return List
     */
    public static List<TimeWatcher> timeWatchers() {
        return data().timeWatchers().list();
    }

    /**
     *
     * @param hasElapsedOnly boolean
     * @return List
     */
    public static List<TimeWatcher> timeWatchers(final boolean hasElapsedOnly) {
        return data().timeWatchers().list(hasElapsedOnly);
    }

    /**
     *
     * @param key Object
     * @return Object
     */
    public static Object get(final Object key) {
        return data().map().get(key);
    }

    /**
     *
     * @param key Object
     * @return Object
     */
    public static String getString(final Object key) {
        final Object o = get(key);
        if (o == null) {
            return null;
        }
        return o.toString();
    }

    /**
     *
     */
    public static void reset() {
        data().clear();
    }

    /**
     *
     * @param cs CharSequence
     */
    public static boolean addError(final Object cs) {
        return doAdd(errors(), -1, cs, false, -1);
    }

    /**
     *
     * @param index int
     * @param cs CharSequence
     * @return boolean
     */
    public static boolean addError(final int index,
            final Object cs) {
        return doAdd(errors(), index, cs, false, -1);
    }

    /**
     *
     * @param index int
     * @param cs CharSequence
     * @param max int
     * @return boolean
     */
    public static boolean addError(final int index,
            final Object cs,
            final int max) {
        return doAdd(errors(), index, cs, false, max);
    }

    /**
     *
     * @param index int
     * @param cs CharSequence
     * @return boolean
     */
    public static boolean addUniqueError(final int index,
            final Object cs) {
        return doAdd(errors(), index, cs, true, -1);
    }

    /**
     *
     * @param index int
     * @param cs CharSequence
     * @param max int
     * @return boolean
     */
    public static boolean addUniqueError(final int index,
            final Object cs,
            final int max) {
        return doAdd(errors(), index, cs, true, max);
    }

    /**
     *
     * @param cs CharSequence
     * @param max int
     * @return boolean
     */
    public static boolean addError(final Object cs,
            final int max) {
        return doAdd(errors(), -1, cs, false, max);
    }

    /**
     *
     * @param cs CharSequence
     * @return boolean
     */
    public static boolean addUniqueError(final Object cs) {
        return doAdd(errors(), -1, cs, true, -1);
    }

    /**
     *
     * @param cs CharSequence
     * @param max int
     * @return boolean
     */
    public static boolean addUniqueError(final Object cs,
            final int max) {
        return doAdd(errors(), -1, cs, true, max);
    }

    /**
     *
     * @param cs CharSequence
     */
    public static boolean addWarning(final Object cs) {
        return doAdd(warnings(), -1, cs, false, -1);
    }

    /**
     *
     * @param cs CharSequence
     * @param max int
     * @return boolean
     */
    public static boolean addWarning(final Object cs,
            final int max) {
        return doAdd(warnings(), -1, cs, false, max);
    }

    /**
     *
     * @param cs CharSequence
     * @return boolean
     */
    public static boolean addUniqueWarning(final Object cs) {
        return doAdd(warnings(), -1, cs, true, -1);
    }

    /**
     *
     * @param cs CharSequence
     * @param max int
     * @return boolean
     */
    public static boolean addUniqueWarning(final Object cs,
            final int max) {
        return doAdd(warnings(), -1, cs, true, max);
    }

    /**
     *
     * @param cs CharSequence
     */
    public static boolean addInformation(final Object cs) {
        return doAdd(informations(), -1, cs, false, -1);
    }

    /**
     *
     * @param cs CharSequence
     * @param max int
     * @return boolean
     */
    public static boolean addInformation(final Object cs,
            final int max) {
        return doAdd(informations(), -1, cs, false, max);
    }

    /**
     *
     * @param cs CharSequence
     * @return boolean
     */
    public static boolean addUniqueInformation(final Object cs) {
        return doAdd(informations(), -1, cs, true, -1);
    }

    /**
     *
     * @param cs CharSequence
     * @param max int
     * @return boolean
     */
    public static boolean addUniqueInformation(final Object cs,
            final int max) {
        return doAdd(informations(), -1, cs, true, max);
    }

    /**
     *
     * @param l List
     * @param cs CharSequence
     * @param ifMissingOnly boolean
     * @param max int
     * @return boolean
     */
    protected static boolean doAdd(final List l,
            final int index,
            final Object cs,
            final boolean addAbsentOnly,
            final int max) {
        if (l == null) {
            return false;
        }
        if (max >= 0 && l.size() >= max) {
            return false;
        }
        String s;
        if (cs == null || (s = cs.toString()) == null) {
            return false;
        }
        if (addAbsentOnly ? l.indexOf(s) < 0 : true) {
            if (index < 0) {
                l.add(s);
            } else {
                l.add(index, s);
            }
            return true;
        }
        return false;
    }

    /**
     *
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
    static class ThreadData {

        private UUID m_uuid = UUID.randomUUID();
        private List m_errors = new ArrayList();
        private List m_warnings = new ArrayList();
        private List m_informations = new ArrayList();
        private Map m_map = new HashMap();
        private TimeWatchers m_timeWatchers = new TimeWatchers();

        ThreadData() {
        }

        public UUID uuid() {
            return m_uuid;
        }

        public List errors() {
            return m_errors;
        }

        public List warnings() {
            return m_warnings;
        }

        public List informations() {
            return m_informations;
        }

        public Map map() {
            return m_map;
        }

        public TimeWatchers timeWatchers() {
            return m_timeWatchers;
        }

        public void clear() {
            m_errors.clear();
            m_warnings.clear();
            m_informations.clear();
            m_map.clear();
            m_timeWatchers.clear();
        }

    }

}
