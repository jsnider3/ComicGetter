/**
 * Foxford is a thought-criminal webcomic.
 *
 * @author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import java.util.Iterator;

public class FoxfordGetter extends BaseComicGetter {

  public static void main(String[] args) {
    new FoxfordGetter().getAll();
  }

  public String getDest(String index) {
    return index;
  }

  public String getName() {
    return "Foxford";
  }

  public String getSrc(String index) {
    return "TODO";
  }

  private class ComicIterator implements Iterator<String> {

    public ComicIterator() {
      // TODO
    }

    @Override
    public boolean hasNext() {
      // TODO
      return false;
    }

    @Override
    public String next() {
      return "TODO";
    }
  }

  public Iterator<String> iterator() {
    return new ComicIterator();
  }
}
