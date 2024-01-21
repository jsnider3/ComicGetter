/**
 * Base class for a variety of small scripts whose purpose is to download every episode of a
 * webcomic. This was largely a hobby project during my freshmen year of college. I am currently
 * refactoring it as a hobby. @Author: Josh Snider
 */
package com.joshuasnider.ComicGetter;

import com.google.common.io.Files;
import com.google.common.reflect.ClassPath;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseComicGetter implements Iterable<String> {

  /** Downloads every webcomic defined for this interface. */
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    getComicGetters().parallelStream().forEach(gtr -> gtr.getAllSafe());
    long endTime = System.currentTimeMillis();
    System.out.println("That took " + (endTime - startTime) + " milliseconds");
  }

  public void getAllSafe() {
    try {
      getAll();
    } catch (Exception e) {
      System.err.println(String.format("ERROR: Failed to download %s.", getName()));
      e.printStackTrace();
    }
  }

  /** Download every comic. */
  public void getAll() {
    new File(getDir()).mkdirs();
    for (String index : this) {
      System.out.println(getName() + ": " + index);
      try {
        String base = getDest(index);
        String dest = getDir() + base;
        if (dest != null && !isAlreadyDownloaded(base)) {
          String src = getSrc(index);
          if (src != null) {
            dest = dest + "." + Files.getFileExtension(src);
            saveImage(src, dest);
          }
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  /** Use reflection to get the list of comic getters available. */
  public static List<BaseComicGetter> getComicGetters() {
    List<BaseComicGetter> comics = new ArrayList<BaseComicGetter>();
    String pkg_name = "com.joshuasnider.ComicGetter";
    try {
      Class cls = Class.forName(pkg_name + ".BaseComicGetter");
      // returns the ClassLoader object associated with this Class.
      ClassLoader cLoader = cls.getClassLoader();
      ClassPath class_path = ClassPath.from(cLoader);
      System.out.println("BaseComicGetter.getComicGetters");
      for (ClassPath.ClassInfo clas : class_path.getTopLevelClasses(pkg_name)) {
        System.out.println("Class: " + clas.getSimpleName());
        Class loaded = clas.load();
        if (loaded != cls && cls.isAssignableFrom(loaded)) {
          try {
            BaseComicGetter gtr = (BaseComicGetter) loaded.newInstance();
            comics.add(gtr);
          } catch (ClassCastException e) {
            System.err.println(
                String.format(
                    "ERROR: Non-BaseComicGetter class in %s.BaseComicGetter: %s",
                    pkg_name, clas.getSimpleName()));
          } catch (InstantiationException e) {
            System.err.println(
                String.format(
                    "ERROR: Could not instantiate class in %s.BaseComicGetter: %s",
                    pkg_name, clas.getSimpleName()));
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return comics;
  }

  /** Get the location to save the image corresponding to the given webcomic index. */
  public abstract String getDest(String index);

  /** Get the directory for saving this webcomic. */
  public final String getDir() {
    return "Webcomics" + File.separator + getName() + File.separator;
  }

  /** Get the name of the webcomic in string form. */
  public abstract String getName();

  /**
   * Get the location from which to download the image corresponding to the given webcomic index.
   */
  public abstract String getSrc(String index);

  public boolean isAlreadyDownloaded(String dest) {
    Pattern pat = Pattern.compile(dest + ".*");
    for (final File fileEntry : new File(getDir()).listFiles()) {
      Matcher match = pat.matcher(fileEntry.getName());
      if (match.matches()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get an image from the URL at fileLoc and save it as title.
   *
   * @return: The size of the saved image.
   */
  public static long saveImage(String fileLoc, String title) {
    long saved = 0;
    System.out.println("Saving from " + fileLoc);
    try (ReadableByteChannel in1 = Channels.newChannel(new URL(fileLoc).openStream());
        FileOutputStream out = new FileOutputStream(title)) {
      saved = out.getChannel().transferFrom(in1, 0, Long.MAX_VALUE);
    } catch (UnknownHostException e) {
      System.err.println("UnknownHostException: " + fileLoc); // e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return saved;
  }
}
