/**
 * Captain SNES is a video game themed webcomic, based off
 *  the premise of being a sequel to the cartoon Captain N.
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
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SNESGetter extends ComicGetter {
	private List<String> archive;

  public static void main(String[] args) {
    new SNESGetter().getAll();
  }
  
  public SNESGetter() {
    archive = new ArrayList<String>();
    try {
      Document doc = Jsoup.connect("http://www.captainsnes.com/archives/").get();
      for (Element e : doc.select("a"))
      {
        if (e.hasAttr("rel")) {
          String value = e.attr("href");
          archive.add(value);
        }
      }
      Collections.sort(archive);
		} catch (IOException ex) {ex.printStackTrace();}
  }

  public String getDest(String index) {
    index = index.substring(index.indexOf(".com") + 5, index.length() - 1);
    index = index.replace('/', '-');
    return index;
  }

  public String getName() {
    return "CaptainSNES";
  }

  /**
   * Get the image URL for the given comic.
   */
  public String getSrc(String index) {
    String src = null;
    try {
      String page = index;
      Document doc = Jsoup.connect(page).get();
      Element comic_body = doc.select("div#comic").get(0);
      src = comic_body.select("img").get(1).attr("src");
    } catch (Exception ex) {ex.printStackTrace();}
    return src;
  }

  public Iterator<String> iterator() {
    return archive.iterator();
  }

}
