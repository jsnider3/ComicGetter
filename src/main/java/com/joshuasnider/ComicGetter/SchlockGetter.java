/**
 * Schlock Mercenary is a daily webcomic about a band of mercenaries with a spaceship.
 *
 * @author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import java.time.LocalDate;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SchlockGetter extends BaseComicGetter {

  public static void main(String[] args) {
    new SchlockGetter().getAll();
  }

  public String getDest(String index) {
    return index;
  }

  @Override
  public String getName() {
    return "Schlock";
  }

  public String getSrc(String index) {
    String src = null;
    try {
      String url = "https://www.schlockmercenary.com/" + index;
      Document doc = Jsoup.connect(url).get();

      Element stripImageWrapper = doc.selectFirst("div.strip-image-wrapper");
      if (stripImageWrapper != null) {
        if (stripImageWrapper != null) {
          Element img = stripImageWrapper.selectFirst("img");
          // TODO Handle pages with multiple images.
          if (img != null) {
            src = img.absUrl("src"); // Use absUrl to get the full URL
            int iend = src.indexOf("?");
            if (iend != -1) {
              src = src.substring(0, iend);
            }
          } else {
            System.out.println("img not found within the div");
          }
        }
      } else {
        System.out.println("div.strip-image-wrapper not found");
      }
    } catch (Exception e) {
      System.err.println("Error fetching webpage: " + e.getMessage());
    }
    return src;
  }

  private class ComicIterator implements Iterator<String> {

    private LocalDate current;
    private final LocalDate end;

    public ComicIterator() {
      this.current = LocalDate.of(2000, 6, 12);
      this.end = LocalDate.of(2020, 9, 29);
    }

    @Override
    public boolean hasNext() {
      return !current.isAfter(end);
    }

    @Override
    public String next() {
      String dateStr = current.toString();
      current = current.plusDays(1);
      return dateStr;
    }
  }

  public Iterator<String> iterator() {
    return new ComicIterator();
  }
}
