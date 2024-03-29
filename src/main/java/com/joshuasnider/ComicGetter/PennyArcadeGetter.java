/**
 * Penny Arcade is a webcomic about two guys who play video games.
 *
 * @author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PennyArcadeGetter extends BaseComicGetter {

  public static String home = "https://www.penny-arcade.com/comic/";

  public static void main(String[] args) {
    new PennyArcadeGetter().getAll();
  }

  public String getDest(String index) {
    return index.replaceAll("/", "");
  }

  public String getName() {
    return "PennyArcade";
  }

  public String getSrc(String index) {
    // TODO FIXME Only getting one panel at a time.
    String fileLoc = null;
    try {
      Document doc = Jsoup.connect(home + index).get();
      for (Element e : doc.select("div.comic-panel")) {
        for (Element e2 : e.select("img")) {
          if (e2.hasAttr("src")) fileLoc = e2.attr("src");
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(home + " " + index + " " + fileLoc);
    return fileLoc;
  }

  private class ComicIterator implements Iterator<String> {

    private Calendar index = null;
    private Calendar now = null;

    public ComicIterator() throws ParseException {
      index = Calendar.getInstance();
      now = Calendar.getInstance();
      index.setTime(new SimpleDateFormat("yyyy/MM/dd").parse("1998/11/18"));
    }

    @Override
    public boolean hasNext() {
      return index.compareTo(now) <= 0;
    }

    @Override
    public String next() {
      // TODO FIXME Inefficient and not guaranteed reliable.
      String ret;
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
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
