/** Test class for EGSGetter.java. @Author: Josh Snider */
package com.joshuasnider.ComicGetter;

import static org.junit.Assert.*;

import org.junit.Test;

public class EGSGetterTest {

  @Test
  public void testLayout() {
    BaseComicGetter comic = new EGSGetter();
    assertNotNull(comic.getSrc("1"));
  }
}
