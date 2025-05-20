/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.Map;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;

/**
 *
 * @author AndreaR
 */
class Utils {

  /**
   *
   * @param dest
   * @param values
   * @return
   */
   static int setAll(final Map dest,
                           final Map values) {
    return putAll(dest, values, true);
  }

  /**
   *
   * @param dest
   * @param values
   * @return
   */
  public static int putAll(final Map dest,
                           final Map values) {
    return putAll(dest, values, false);
  }

  /**
   *
   * @param dest
   * @param values
   * @param existingOnly
   * @return
   */
  private static int putAll(final Map dest,
                            final Map values,
                            final boolean existingOnly) {
    int count = 0;
    for (final Iterator i = values.entrySet().iterator(); i.hasNext(); ) {
      final Map.Entry e = (Map.Entry)i.next();
      final Object key = e.getKey();
      final Object value = e.getValue();
      if (existingOnly) {
        if (dest.containsKey(key)) {
          dest.put(key, value);
          count++;
        }
      } else {
        dest.put(key, value);
        count++;
      }
    }
    return count;
  }

  /**
   *
   * @param dest
   * @param values
   * @param existingOnly
   * @return
   */
  public static int setAll(final IndexedMap dest,
                           final Collection values,
                           final ElementIndexMatcher matcher) {
    return setAll(dest, values.iterator(), matcher);
  }

  /**
   *
   * @param dest
   * @param values
   * @param existingOnly
   * @return
   */
  public static int setAll(final IndexedMap dest,
                           final Iterator values,
                           final ElementIndexMatcher matcher) {
    int count = 0;
    for (int index = 0; values.hasNext(); index++) {
      final Object value = values.next();
      final int pos = matcher.indexOf(index);
      if (pos >= 0) {
        dest.put(pos, value);
        count++;
      }
    }
    return count;
  }

  /**
   *
   * @param dest
   * @param values
   * @return
   */
  public static int setAll(final IndexedMap dest,
                           final Map values) {
    return putAll(dest, values, true);
  }

  /**
   *
   * @param dest
   * @param values
   * @return
   */
  public static int putAll(final IndexedMap dest,
                           final Map values) {
    return putAll(dest, values, false);
  }

  /**
   *
   * @param dest
   * @param values
   * @param existingOnly
   * @return
   */
  private static int putAll(final IndexedMap dest,
                            final Map values,
                            final boolean existingOnly) {
    int count = 0;
    for (final Iterator i = values.entrySet().iterator(); i.hasNext(); ) {
      final Map.Entry e = (Map.Entry)i.next();
      final Object key = e.getKey();
      final Object value = e.getValue();
      if (existingOnly) {
        final int pos = dest.indexOf(key);
        if (pos >= 0) {
          dest.put(pos, value);
          count++;
        }
      } else {
        dest.put(key, value);
        count++;
      }
    }
    return count;
  }

  /**
   *
   * @param dest
   * @param values
   * @param matcher
   * @return
   */
  public static int setAll(final List dest,
                           final Iterator values,
                           final ElementIndexMatcher matcher) {
    int count = 0;
    for (int index = 0; values.hasNext(); index++) {
      final Object value = values.next();
      final int pos = matcher.indexOf(index);
      if (pos >= 0) {
        dest.set(pos, value);
        count++;
      }
    }
    return count;
  }

  /**
   *
   * @param dest
   * @param values
   * @param matcher
   * @return
   */
  public static int setAll(final List dest,
                           final Collection values,
                           final ElementIndexMatcher matcher) {
    return setAll(dest, values.iterator(), matcher);
  }
   
}
