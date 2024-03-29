/** Test class for SchlockGetter.java. @Author: Josh Snider */
package com.joshuasnider.ComicGetter;

import static org.junit.Assert.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

public class SchlockGetterTest {

  @Test
  public void testFirst() {
    SchlockGetter schlock = new SchlockGetter();
    assertEquals("20000612", schlock.iterator().next());
  }

  @Test
  public void testNext() {
    SchlockGetter schlock = new SchlockGetter();
    List<String> contents = new ArrayList<>();
    schlock.iterator().forEachRemaining(contents::add);
    assertTrue(contents.contains("20000612"));
    assertEquals("20000613", contents.get(contents.indexOf("20000612") + 1));
    assertTrue(contents.contains("20120815"));
    assertEquals("20120816", contents.get(contents.indexOf("20120815") + 1));
    assertEquals(
        new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()),
        contents.get(contents.size() - 1));
  }

  @Test
  public void testSize() {
    BaseComicGetter comic = new SchlockGetter();
    List<String> contents = new ArrayList<>();
    comic.iterator().forEachRemaining(contents::add);
    assertTrue(contents.size() >= 365 * 17);
  }

  @Test
  public void testConnection() {
    BaseComicGetter comic = new SchlockGetter();
    try {
      new URL(comic.getSrc("20000612")).openStream();
    } catch (Exception e) {
      fail("Could not connect to " + comic.getSrc("20000612") + ".");
    }
  }
}
