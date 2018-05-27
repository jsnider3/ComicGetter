/**
 * Dilbert is a comic about office life.
 *  It started as a print comic, but there's an online archive.
 *
 * @author: Josh Snider
 */

package com.joshuasnider.comicgetter;

import java.io.File;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;

public class DilbertGetter extends ComicGetter {

  public static void main(String[] args) {
    new DilbertGetter().getAll();
  }

  public String getDest(String index) {
    return index + ".jpg";
  }

  public String getName() {
    return "Dilbert";
  }

  public String getSrc(String index) {
    String src = null;
    //FIXME Keeps running into throttling.
    /*try {
      String page = "http://dilbert.com/strip/" + index;
      System.out.println(page);
      Document doc = Jsoup.connect(page).get();
      for (Element div : doc.select("div")) {
        if (div.hasAttr("class") && div.attr("class").contains("comic-item-container"))
          src = div.attr("data-image");
      }
    } catch (Exception ex) {ex.printStackTrace();}*/
    return src;
  }

  private class ComicIterator implements Iterator<String> {

    private Calendar index = null;
    private Calendar now = null;

    public ComicIterator() throws ParseException {
      index = Calendar.getInstance();
      now = Calendar.getInstance();
      index.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("1989-04-16"));
    }

    @Override
    public boolean hasNext() {
      return index.compareTo(now) <= 0;
    }

    @Override
    public String next() {
      String ret;
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      ret = dateFormat.format(index.getTime());
      index.add(Calendar.DATE, 1);
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
