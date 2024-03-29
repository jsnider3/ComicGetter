/**
 * PHD Comics is a webcomic about life as a PhD student.
 *
 * @author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PHDGetter extends BaseComicGetter {
  private List<String> archive;

  public static void main(String[] args) {
    new PHDGetter().getAll();
  }

  public PHDGetter() {
    archive = new ArrayList<String>();
    try {
      Document doc = Jsoup.connect("http://phdcomics.com/comics/archive_list.php").get();
      for (Element e : doc.select("a")) {
        if (e.attr("href").contains("comics/archive.php")) {
          String date = e.children().get(0).html();
          archive.add(date);
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String getDest(String index) {
    String dest = null;
    try {
      index =
          new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("MM/dd/yyyy").parse(index));
      dest = index;
    } catch (ParseException ex) {
      ex.printStackTrace();
    }
    return dest;
  }

  public String getName() {
    return "PhD";
  }

  /** Get the image URL for the given comic. */
  public String getSrc(String index) {
    // TODO Doesn't work for videos.
    String src = null;
    try {
      String page =
          "http://www.phdcomics.com/comics/archive.php?comicid="
              + Integer.toString(archive.indexOf(index) + 1);
      Document doc = Jsoup.connect(page).get();
      Element comic_body = doc.select("img#comic2").get(0);
      src = comic_body.attr("src");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return src;
  }

  public Iterator<String> iterator() {
    return archive.iterator();
  }
}
