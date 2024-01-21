/**
 * Saturday Morning Breakfast Cereal is sort of like XKCD, but by Zach Weinersmith.
 *
 * @author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SMBCGetter extends BaseComicGetter {
  private List<String> archive;

  public static void main(String[] args) {
    new SMBCGetter().getAll();
  }

  public SMBCGetter() {
    archive = new ArrayList<String>();
    try {
      Document doc = Jsoup.connect("https://www.smbc-comics.com/comic/archive").get();
      for (Element e : doc.select("option")) {
        String value = e.attr("value");
        archive.add(value);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String getDest(String index) {
    int lastIndex = index.lastIndexOf('/');
    String retVal = index;
    if (lastIndex != -1) {
      retVal = index.substring(lastIndex + 1);
    }
    return retVal;
  }

  public String getName() {
    return "SMBC";
  }

  /** Get the image URL for the given comic. */
  public String getSrc(String index) {
    String src = null;
    try {
      String page = "https://www.smbc-comics.com/" + index;
      System.out.println(page);
      Document doc = Jsoup.connect(page).get();
      src = doc.select("img#cc-comic").get(0).attr("src");
      src = src.replace(" ", "%20");
      // TODO Add mouseover text.
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return src;
  }

  public Iterator<String> iterator() {
    return archive.iterator();
  }
}
