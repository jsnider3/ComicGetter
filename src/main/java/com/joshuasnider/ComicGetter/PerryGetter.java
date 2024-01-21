/**
 * Perry Bible Fellowship is a webcomic.
 *
 * @author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PerryGetter extends BaseComicGetter {

  public static void main(String[] args) {
    new PerryGetter().getAll();
  }

  public String getDest(String index) {
    String dest = getSrc(index);
    if (dest != null) {
      int lastSlashIndex = dest.lastIndexOf("/");
      if (lastSlashIndex != -1) {
        dest = dest.substring(lastSlashIndex + 1);
      }
    }
    return dest;
  }

  public String getName() {
    return "PerryBibleFellowship";
  }

  public String getSrc(String index) {
    try {
      Document doc = Jsoup.connect(index.toString()).get();
      Element comic_div = doc.select("div[id=comic]").first();
      Element comic_img = comic_div.select("img").first();
      return comic_img.attr("data-src");
    } catch (IOException e) {
      return null;
    }
  }

  private class ComicIterator implements Iterator<String> {

    private URL currentUrl;

    public ComicIterator() {
      try {
        String startingUrl = "https://pbfcomics.com/comics/stiff-breeze/";

        currentUrl = new URL(startingUrl);
      } catch (MalformedURLException e) {
      }
    }

    @Override
    public boolean hasNext() {
      try {
        Document doc = Jsoup.connect(currentUrl.toString()).get();

        // Find <a> with rel="next"
        Elements nextLink = doc.select("a[rel=next]");
        return !nextLink.isEmpty();
      } catch (Exception e) {
        return false;
      }
    }

    @Override
    public String next() {
      try {
        String link = currentUrl.toString();
        if (hasNext()) {
          Document doc = Jsoup.connect(currentUrl.toString()).get();
          // Find <a> with rel="next"
          Elements nextLink = doc.select("a[rel=next]");
          String nextUrlString = nextLink.first().attr("href");
          currentUrl = new URL(currentUrl, nextUrlString); // Resolve relative URLs
        }
        return link;
      } catch (Exception e) {
        return null;
      }
    }
  }

  public Iterator<String> iterator() {
    return new ComicIterator();
  }
}
