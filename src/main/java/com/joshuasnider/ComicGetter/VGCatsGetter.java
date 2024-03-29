/**
 * VG Cats is a webcomic about two cats that play video games.
 *
 * @author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class VGCatsGetter extends BaseComicGetter {
  private SortedSet<String> archive;

  public static void main(String[] args) {
    new VGCatsGetter().getAll();
  }

  public VGCatsGetter() {
    archive = new TreeSet<String>();
    String archive_page = "http://www.vgcats.com/archive.php";
    try {
      Document doc = Jsoup.connect(archive_page).get();
      for (Element e : doc.select("a")) {
        String link = e.attr("href");
        if (link.contains("strip_id")) {
          link = link.substring(link.indexOf("strip_id") + 9);
          archive.add(link);
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String getDest(String index) {
    return String.format("%03d", Integer.parseInt(index));
  }

  public String getName() {
    return "VGCats";
  }

  /** Get the image URL for the given comic. */
  public String getSrc(String index) {
    String src = null;
    try {
      Document doc = Jsoup.connect("http://www.vgcats.com/comics/?strip_id=" + index).get();
      Element comic_body = doc.select("tbody").get(1);
      for (Element e : comic_body.select("img")) {
        if (e.hasAttr("src") && e.attr("src").startsWith("images/"))
          src = "http://www.vgcats.com/comics/" + e.attr("src");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return src;
  }

  public Iterator<String> iterator() {
    return archive.iterator();
  }
}
