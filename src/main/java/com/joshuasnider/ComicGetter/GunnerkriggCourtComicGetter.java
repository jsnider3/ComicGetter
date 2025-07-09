package com.joshuasnider.ComicGetter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GunnerkriggCourtComicGetter extends BaseComicGetter {

  public static void main(String[] args) {
    new GunnerkriggCourtComicGetter().getAll();
  }

  @Override
  public String getName() {
    return "Gunnerkrigg Court";
  }

  @Override
  public String getDest(String index) {
    return "page-" + index;
  }

  @Override
  public String getSrc(String index) {
    return "https://www.gunnerkrigg.com/comics/"
        + String.format("%08d", Integer.parseInt(index))
        + ".jpg";
  }

  @Override
  public Iterator<String> iterator() {
    List<String> indices = new ArrayList<>();
    for (int i = 1; i <= 2894; i++) {
      indices.add(String.valueOf(i));
    }
    return indices.iterator();
  }
}
