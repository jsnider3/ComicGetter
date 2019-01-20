/**
 * VG Cats is a webcomic about two cats that play video games.
 *
 * @author: Josh Snider
 */

package com.joshuasnider.comicgetter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VGCatsGetter extends ComicGetter {
	private List<String> archive;

  public static void main(String[] args) {
    new VGCatsGetter().getAll();
  }
  
  public VGCatsGetter() {
    archive = new ArrayList<String>();
    //TODO The archive page is out of date.
    String archive_page = "http://www.vgcats.com/archive/";
    try {
      Document doc = Jsoup.connect(archive_page).get();
      for (Element e : doc.select("a"))
      {
        String link = e.attr("href");
        if (link.contains("strip_id"))
        {
          link = link.substring(link.indexOf("strip_id") + 9);
          archive.add(link);
        }
      }
		} catch (IOException ex) {ex.printStackTrace();}
  }

  public String getDest(String index) {
    return String.format("%03d", Integer.parseInt(index));
  }

  public String getName() {
    return "VGCats";
  }

  /**
   * Get the image URL for the given comic.
   */
  public String getSrc(String index) {
    String src = null;
    try {
      Document doc = Jsoup.connect("http://www.vgcats.com/comics/?strip_id=" + index).get();
      Element comic_body = doc.select("tbody").get(0);
      for (Element e: comic_body.select("img")) {
        if (e.hasAttr("src") && e.attr("src").startsWith("images/"))
          src = "http://www.vgcats.com/comics/" + e.attr("src");
      }
    } catch (Exception ex) {ex.printStackTrace();}
    return src;
  }

  public Iterator<String> iterator() {
    return archive.iterator();
  }

}
