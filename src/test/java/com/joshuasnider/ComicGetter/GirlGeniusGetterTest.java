/** Test class for GirlGeniusGetter.java. @Author: Josh Snider */
package com.joshuasnider.ComicGetter;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class GirlGeniusGetterTest {

  @Test
  public void testFirst() {
    GirlGeniusGetter genius = new GirlGeniusGetter();
    assertEquals("20021104", genius.iterator().next());
  }

  @Test
  public void testNext() {
    GirlGeniusGetter genius = new GirlGeniusGetter();
    List<String> contents = new ArrayList<>();
    genius.iterator().forEachRemaining(contents::add);
    assertTrue(contents.contains("20021106"));
    assertEquals("20021106", contents.get(contents.indexOf("20021104") + 1));
    assertTrue(contents.contains("20021108"));
    assertEquals("20021108", contents.get(contents.indexOf("20021106") + 1));
    assertTrue(contents.contains("20021111"));
    assertEquals("20021111", contents.get(contents.indexOf("20021108") + 1));
  }

  @Test
  public void testSize() {
    BaseComicGetter comic = new GirlGeniusGetter();
    List<String> contents = new ArrayList<>();
    comic.iterator().forEachRemaining(contents::add);
    assertTrue(contents.size() >= 3 * 52 * 15);
  }

  @Test
  public void testConnection() {
    BaseComicGetter comic = new GirlGeniusGetter();
    try {
      new URL(comic.getSrc("20021106")).openStream();
    } catch (Exception e) {
      fail("Could not connect to " + comic.getSrc("20021106") + ".");
    }
  }
}
