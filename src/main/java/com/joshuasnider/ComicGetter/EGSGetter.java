/**
 * El Goonish Shive is a ...
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
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class EGSGetter extends ComicGetter {

  public static void main(String[] args) {
    new EGSGetter().getAll();
  }

  private final int newest;

  public EGSGetter() {
    newest = 2499;
    try {
      newest = getNewestComic();
    } catch (IOException e) {e.printStackTrace();}
  }

  public String getDest(String index) {
    int numsize = Integer.toString(newest).length();
    return getDir() + String.format("%0" + Integer.toString(numsize) + "d", Integer.parseInt(index)) + ".gif";
  }

  public String getName() {
    return "EGS";
  }

  /**
   * Get the index of the newest xkcd comic.
   */
  public int getNewestComic() throws IOException {
    String html = Jsoup.connect("http://www.egscomics.com/").get().html();
    //TODO
    int comicnumber = 2499;
    return comicnumber;
  }

  /**
   * Get the image URL for the given comic number.
   */
  public String getSrc(String index) {
    String fileLoc = null;
    try {
      Document doc = Jsoup.connect("http://www.egscomics.com/index.php?id=" + index).get();
      for (Element e : doc.select("img#comic")) {
        if (e.hasAttr("src"))
          fileLoc = "http://www.egscomics.com/" + e.attr("src");
      }
    } catch (IOException e) {}
    return fileLoc;
  }

  private class ComicIterator implements Iterator<String> {

    private int current = 1;

    @Override
    public boolean hasNext() {
      return current <= newest;
    }

    @Override
    public String next() {
      String ret = Integer.toString(current);
      current += 1;
      return ret;
    }

  }

  public Iterator<String> iterator() {
    return new ComicIterator();
  }

}
