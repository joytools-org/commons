/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

/**
 *
 */
public interface ElementIndexMatcher {

  /**
   *
   * @param sourceIndex
   * @return
   */
  public int indexOf(final int sourceIndex);

  /**
   *
   * @return
   */
  public int unmatchingCount();

  /**
   *
   * @return
   */
  public int matchingCount();

}
