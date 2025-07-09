/**
 * Dinosaur Comics
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
import org.jsoup.select.Elements;

public class DinosaurGetter extends BaseComicGetter {

  public static void main(String[] args) {
    new DinosaurGetter().getAll();
  }

  public String getDest(String index) {
    return "dinosaur_" + index;
  }

  public String getName() {
    return "DinosaurComics";
  }

  public String getSrc(String comicNumber) {
    String comicUrl = "https://www.qwantz.com/index.php?comic=" + comicNumber;

    try {
      Document doc = Jsoup.connect(comicUrl).get();
      Element imageElement = doc.selectFirst("img.comic"); // Assuming only one "comic" img

      if (imageElement != null) {
        return imageElement.absUrl("src"); // Get the absolute image source URL
      } else {
        System.err.println("Comic image not found for number: " + comicNumber);
        return null;
      }
    } catch (IOException e) {
      System.err.println("Error fetching comic page: " + e.getMessage());
      return null;
    }
  }

  public static String getImageName(String index) {
    return "dinosaur_" + index + ".png";
  }

  public Iterator<String> iterator() {
    String baseUrl = "https://www.qwantz.com/archive.php";
    List<String> comicLinks = new ArrayList<>();

    try {
      Document doc = Jsoup.connect(baseUrl).get();
      Elements container = doc.select("div.container");
      Elements links = container.select("a[href]");

      for (Element link : links) {
        String absoluteUrl = link.absUrl("href");
        if (absoluteUrl.contains("comic=")) {
          // System.out.println(absoluteUrl);
          comicLinks.add(absoluteUrl.split("=")[1]);
        }
      }

    } catch (IOException e) {
      System.err.println("Error fetching webpage: " + e.getMessage());
    }

    return comicLinks.iterator();
  }
}
