/**
 * Girl Genius is a webcomic by Phil Foglio about a lost princess.
 *
 * @author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GirlGeniusGetter extends BaseComicGetter {

  public static String home = "http://www.girlgeniusonline.com";
  public static String title = home + "/ggmain/strips/ggmain";

  public static void main(String[] args) {
    new GirlGeniusGetter().getAll();
  }

  public String getDest(String index) {
    return index;
  }

  /**
   * Try to find the link to the double page if the given comic is one. If it isn't or we can't find
   * it, return null. FIXME: This is a performance issue.
   */
  public String getDoublePage(String url) {
    String link = null;
    try {
      Document doc = Jsoup.connect(url).get();
      Elements els = doc.select("a[href*=doublespreads]");
      if (els.size() > 0) {
        link = home + els.get(0).attr("href");
        link = link.substring(0, link.lastIndexOf('.')) + ".jpg";
      }
    } catch (Exception e) {
      System.err.println(url + " failed");
    }
    return link;
  }

  public String getName() {
    return "GirlGenius";
  }

  public String getSrc(String index) {
    /*String doublePage = getDoublePage(
      "http://www.girlgeniusonline.com/comic.php?date=" + index);
    if (doublePage != null) {
      tofrom[0] = doublePage;
    }*/
    return title + index + ".jpg";
  }

  private class ComicIterator implements Iterator<String> {

    private Calendar index = null;
    private Calendar now = null;

    public ComicIterator() throws ParseException {
      index = Calendar.getInstance();
      now = Calendar.getInstance();
      index.setTime(new SimpleDateFormat("yyyyMMdd").parse("20021104"));
    }

    @Override
    public boolean hasNext() {
      return index.compareTo(now) <= 0;
    }

    @Override
    public String next() {
      String ret;
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
      ret = dateFormat.format(index.getTime());
      switch (index.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.FRIDAY:
          index.add(Calendar.DATE, 1);
        case Calendar.MONDAY:
        case Calendar.WEDNESDAY:
        case Calendar.SATURDAY:
          index.add(Calendar.DATE, 1);
        case Calendar.TUESDAY:
        case Calendar.THURSDAY:
        case Calendar.SUNDAY:
          index.add(Calendar.DATE, 1);
          break;
      }
      return ret;
    }
  }

  @Override
  public Iterator<String> iterator() {
    try {
      return new ComicIterator();
    } catch (ParseException e) {
      e.printStackTrace();
      return Collections.emptyIterator();
    }
  }
}
