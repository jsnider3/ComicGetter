/**
 * Sandra and Woo is a slice-of-life webcomic about a girl with with a pet raccoon.
 *
 * @author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SandraGetter extends BaseComicGetter {
  private List<String> archive;

  public static void main(String[] args) {
    new SandraGetter().getAll();
  }

  public SandraGetter() {
    archive = new ArrayList<String>();
    try {
      Document doc = Jsoup.connect("http://www.sandraandwoo.com/archive/").get();
      for (Element e : doc.select("a")) {
        if ("bookmark".equals(e.attr("rel"))) {
          String link = e.attr("href");
          archive.add(link);
        }
      }
      Collections.reverse(archive);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String getDest(String index) {
    index = index.substring(index.indexOf("com/") + 4);
    return index.replaceAll("/$", "").replace("/", "_");
  }

  public String getName() {
    return "SandraAndWoo";
  }

  /** Get the image URL for the given comic. */
  public String getSrc(String index) {
    String src = null;
    try {
      Document doc = Jsoup.connect(index).get();
      Element comic_body = doc.select("div#comic").get(0);
      src = "http://www.sandraandwoo.com" + comic_body.select("img").get(0).attr("src");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return src;
  }

  public Iterator<String> iterator() {
    return archive.iterator();
  }
}
