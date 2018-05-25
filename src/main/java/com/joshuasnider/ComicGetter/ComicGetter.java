/**
 * Base class for a variety of small scripts whose purpose is
 *  to download every episode of a webcomic. This was largely
 *  a hobby project during my freshmen year of college. I am
 *  currently refactoring it as a hobby.
 *
 * @Author: Josh Snider
 */

package com.joshuasnider.comicgetter;

import com.google.common.reflect.ClassPath;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

public abstract class ComicGetter implements Iterable<String> {

  /**
   * Downloads every webcomic defined for this interface.
   */
  public static void main(String[] args) {
    String pkg_name = "com.joshuasnider.comicgetter";
    try {
      Class cls = Class.forName(pkg_name + ".ComicGetter");
      // returns the ClassLoader object associated with this Class.
      ClassLoader cLoader = cls.getClassLoader();
      ClassPath class_path = ClassPath.from(cLoader);
      System.out.println("ComicGetter.main");
      for (ClassPath.ClassInfo clas : class_path.getTopLevelClasses(pkg_name)) {
        System.out.println("Class: " + clas.getSimpleName());
        Class loaded = clas.load();
        if (loaded != cls)
        {
          try {
            ComicGetter gtr = (ComicGetter)loaded.newInstance();
            gtr.getAll();
          } catch (ClassCastException e) {
            System.err.println(String.format(
              "ERROR: Non-ComicGetter class in %s.ComicGetter: %s", pkg_name, clas.getSimpleName()));
          } catch (InstantiationException e) {
            System.err.println(String.format(
              "ERROR: Could not instantiate class in %s.ComicGetter: %s", pkg_name, clas.getSimpleName()));
          } catch (Exception e) {
            System.err.println(
              String.format("ERROR: Failed to download %s.", clas.getSimpleName()));
            e.printStackTrace();
          }
        }
      }
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  /**
   * Download every comic.
   */
  public void getAll() {
    new File(getDir()).mkdirs();
    for (String index : this) {
      System.out.println(index);
      try {
        String dest = getDest(index);
        if (dest != null && !(new File(getDir() + dest).exists())) {
          String src = getSrc(index);
          if (src != null) {
            saveImage(src, getDir() + dest);
          }
        }
      } catch (Exception ex) {ex.printStackTrace();}
    }
  }

  /**
   * Get the location to save the image corresponding to the given webcomic index.
   */
  public abstract String getDest(String index);

  /**
   * Get the directory for saving this webcomic.
   */
  public final String getDir() {
    return "Webcomics" + File.separator + getName() + File.separator;
  }

  /**
   * Get the name of the webcomic in string form.
   */
  public abstract String getName();

  /**
   * Get the location from which to download the image corresponding to the given webcomic index.
   */
  public abstract String getSrc(String index);

  /**
   * Get an image from the URL at fileLoc and save it as title.
   * @return: The size of the saved image.
   */
  public static long saveImage(String fileLoc, String title){
    long saved = 0;
    try (ReadableByteChannel in1 = Channels.newChannel(
          new URL(fileLoc).openStream());
        FileOutputStream out = new FileOutputStream(title))
    {
      saved = out.getChannel().transferFrom(in1, 0, Long.MAX_VALUE);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return saved;
  }
}
