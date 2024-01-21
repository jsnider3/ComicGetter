/**
 * El Goonish Shive is a webcomic about a group of teenagers who routinely run into bizarre
 * paranormal occurences.
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

public class EGSGetter extends BaseComicGetter {

  public static void main(String[] args) {
    new EGSGetter().getAll();
  }

  public String getDest(String index) {
    String[] parts = index.split("/");
    return parts[1];
  }

  public String getName() {
    return "EGS";
  }

  /** Get the image URL for the given comic number. */
  public String getSrc(String index) {
    String fileLoc = null;
    try {
      Document doc = Jsoup.connect("http://www.egscomics.com/" + index).get();
      for (Element e : doc.select("img#cc-comic")) {
        if (e.hasAttr("src")) fileLoc = e.attr("src");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileLoc;
  }

  public Iterator<String> iterator() {
    try {
      List<String> archive = new ArrayList<>();
      Document doc = Jsoup.connect("http://egscomics.com/comic/archive").get();
      for (Element e : doc.select("option")) {
        if (e.hasAttr("value")) if (e.attr("value").length() > 0) archive.add(e.attr("value"));
      }
      return archive.iterator();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
