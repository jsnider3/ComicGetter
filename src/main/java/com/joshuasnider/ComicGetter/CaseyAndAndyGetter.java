/**
 * Casey and Andy is a webcomic by Andy Weir about
 *  a pair of mad scientists who go on wacky adventures.
 *
 * @author: Josh Snider
 */

package com.joshuasnider.ComicGetter;

import java.util.Iterator;

public class CaseyAndAndyGetter extends BaseComicGetter {

  public static void main(String[] args) {
    new CaseyAndAndyGetter().getAll();
  }

  public String getDest(String index) {
    return index;
  }

  public String getName() {
    return "CaseyAndAndy";
  }

  public String getSrc(String index) {
    return String.format("http://www.galactanet.com/comic/Strip%d.gif", Integer.parseInt(index));
  }
  
  private class ComicIterator implements Iterator<String> {

    private int index = 1;

    @Override
    public boolean hasNext() {
      return index <= 666;
    }

    @Override
    public String next() {
      String ret = Integer.toString(index);
      index = index + 1;
      return ret;
    }

  }

  public Iterator<String> iterator() {
    return new ComicIterator();
  }

}
