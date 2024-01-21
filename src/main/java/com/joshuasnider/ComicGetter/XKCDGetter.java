/**
 * XKCD is a webcomic by Randall Munroe. It's funny, I swear.
 *
 * @author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import com.google.common.io.Files;
import java.io.IOException;
import java.util.Iterator;
import org.jsoup.Jsoup;

public class XKCDGetter extends BaseComicGetter {

  public static void main(String[] args) {
    new XKCDGetter().getAll();
  }

  private final int newest;

  public XKCDGetter() {
    int recent = -1;
    try {
      recent = getNewestComic();
    } catch (IOException e) {
    }
    newest = recent;
  }

  public String getDest(String index) {
    String src = getSrc(index);
    if (src != null) {
      src = src.substring(29);
      String exten = Files.getFileExtension(src);
      src = src.substring(0, src.length() - exten.length());
      return String.format("%04d_%s", Integer.parseInt(index), src);
    } else {
      return null;
    }
  }

  public String getName() {
    return "XKCD";
  }

  /** Get the index of the newest xkcd comic. */
  public int getNewestComic() throws IOException {
    String html = Jsoup.connect("http://www.xkcd.com").get().html();
    int num = html.indexOf("Permanent link to this comic");
    num = html.indexOf("com/", num) + 4;
    int end = html.indexOf("/", num);
    int comicnumber = Integer.parseInt(html.substring(num, end));
    return comicnumber;
  }

  /** Get the image URL for the given comic number. */
  public String getSrc(String index) {
    String fileLoc = null;
    try {
      String input = Jsoup.connect("http://www.xkcd.com/" + index).get().html();
      int start = input.indexOf("https://imgs.xkcd.com/comics");
      int end = input.indexOf('<', start);
      fileLoc = input.substring(start, end);
      fileLoc = fileLoc.trim();
    } catch (IOException e) {
    }
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
      if (current == 404) {
        current += 1;
      }
      return ret;
    }
  }

  public Iterator<String> iterator() {
    return new ComicIterator();
  }
}
