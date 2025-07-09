package com.joshuasnider.ComicGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TheOatmealComicGetter extends BaseComicGetter {

  public static void main(String[] args) {
    new TheOatmealComicGetter().getAll();
  }

  @Override
  public String getName() {
    return "The Oatmeal";
  }

  @Override
  public String getDest(String index) {
    int lastSlashIndex = index.lastIndexOf('/');
    if (lastSlashIndex != -1) {
      return "comic-" + index.substring(lastSlashIndex + 1) + ".png";
    } else {
      return "comic-" + index + ".png";
    }
  }

  @Override
  public String getSrc(String index) {
    try {
      Document doc = Jsoup.connect(index).get();
      Element comicDiv = doc.select("div#comic").first();
      Element img = comicDiv.select("img").first();
      return img.attr("abs:src");
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Iterator iterator() {
    List comicLinks = new ArrayList<>();
    int currentPage = 1;

    while (true) {
      String indexPageUrl = "https://theoatmeal.com/c2index/page:" + currentPage;
      try {
        Document doc = Jsoup.connect(indexPageUrl).get();
        Element comicsListDiv = doc.select("div.comics_list").first();
        Elements comicLinkElements = comicsListDiv.select("a[href]");

        for (Element linkElement : comicLinkElements) {
          String comicLink = linkElement.attr("abs:href");
          comicLinks.add(comicLink);
        }

        // Check if the "Next Page" link is clickable
        Element nextPageLink = doc.select("a.next_page").first();
        if (nextPageLink != null && nextPageLink.hasAttr("href")) {
          currentPage++;
        } else {
          break;
        }
      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
    }

    return comicLinks.iterator();
  }
  /*  @Override
  public Iterator<String> iterator() {
    List<String> comicLinks = new ArrayList<>();

    for (int i = 1; i <= 13; i++) {
      String indexPageUrl = "https://theoatmeal.com/c2index/page:" + i;
      try {
        Document doc = Jsoup.connect(indexPageUrl).get();
        Element comicsListDiv = doc.select("div.comics_list").first();
        Elements comicLinkElements = comicsListDiv.select("a[href]");

        for (Element linkElement : comicLinkElements) {
          String comicLink = linkElement.attr("abs:href");
          comicLinks.add(comicLink);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return comicLinks.iterator();
  }*/
}
