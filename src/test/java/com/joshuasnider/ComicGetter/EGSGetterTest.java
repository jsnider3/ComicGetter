/**
 * Test class for EGSGetter.java.
 *
 * @Author: Josh Snider
 */

package com.joshuasnider.comicgetter;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class EGSGetterTest {

  @Test
  public void testLayout() {
    ComicGetter comic = new EGSGetter();
    assertNotNull(comic.getSrc("1"));
  }

}
