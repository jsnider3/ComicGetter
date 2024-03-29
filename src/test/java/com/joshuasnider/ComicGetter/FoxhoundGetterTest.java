/** Test class for FoxhoundGetter.java. @Author: Josh Snider */
package com.joshuasnider.ComicGetter;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FoxhoundGetterTest {

  @Test
  public void testFirst() {
    FoxhoundGetter fox = new FoxhoundGetter();
    assertEquals("001", fox.iterator().next());
  }

  @Test
  public void testNext() {
    FoxhoundGetter fox = new FoxhoundGetter();
    List<String> contents = new ArrayList<>();
    fox.iterator().forEachRemaining(contents::add);
    assertTrue(contents.contains("001"));
    assertEquals("002", contents.get(contents.indexOf("001") + 1));
    assertTrue(contents.contains("499"));
    assertEquals("500", contents.get(contents.indexOf("499") + 1));
    assertEquals("500", contents.get(contents.size() - 1));
  }

  @Test
  public void testSize() {
    BaseComicGetter comic = new FoxhoundGetter();
    List<String> contents = new ArrayList<>();
    comic.iterator().forEachRemaining(contents::add);
    assertTrue(contents.size() >= 500);
  }

  @Test
  public void testConnection() {
    BaseComicGetter comic = new FoxhoundGetter();
    try {
      new URL(comic.getSrc("001")).openStream();
    } catch (Exception e) {
      fail("Could not connect to " + comic.getSrc("001") + ".");
    }
  }
}
