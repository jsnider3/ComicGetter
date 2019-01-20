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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.StreamSupport;

public abstract class ComicGetter implements Iterable<String> {

  /**
   * Downloads every webcomic defined for this interface.
   */
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
      System.err.println(
        String.format("ERROR: Failed to download %s.", getName()));
      e.printStackTrace();
    }
  }

  /**
   * Download every comic.
   */
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
            dest = dest + "." + getFileExtension(src);
            saveImage(src, dest);
          }
        }
      } catch (Exception ex) {ex.printStackTrace();}
    }

  }

  /**
   * Use reflection to get the list of comic getters available.
   */
  public static List<ComicGetter> getComicGetters() {
    List<ComicGetter> comics = new ArrayList<ComicGetter>();
    String pkg_name = "com.joshuasnider.comicgetter";
    try {
      Class cls = Class.forName(pkg_name + ".ComicGetter");
      // returns the ClassLoader object associated with this Class.
      ClassLoader cLoader = cls.getClassLoader();
      ClassPath class_path = ClassPath.from(cLoader);
      System.out.println("ComicGetter.getComicGetters");
      for (ClassPath.ClassInfo clas : class_path.getTopLevelClasses(pkg_name)) {
        System.out.println("Class: " + clas.getSimpleName());
        Class loaded = clas.load();
        if (loaded != cls && cls.isAssignableFrom(loaded))
        {
          try {
            ComicGetter gtr = (ComicGetter)loaded.newInstance();
            comics.add(gtr);
          } catch (ClassCastException e) {
            System.err.println(String.format(
              "ERROR: Non-ComicGetter class in %s.ComicGetter: %s", pkg_name, clas.getSimpleName()));
          } catch (InstantiationException e) {
            System.err.println(String.format(
              "ERROR: Could not instantiate class in %s.ComicGetter: %s", pkg_name, clas.getSimpleName()));
          }
        }
      }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return comics;
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
  
  public final String getFileExtension(String filePath) {
    File f = new File(filePath);
    if (f.isDirectory()) return "";
    String name = f.getName();
    if ((name.startsWith(".") && name.lastIndexOf('.') == name.indexOf('.'))
        || !name.contains("."))
      return "";
    return name.substring(name.lastIndexOf('.') + 1);
  }

  /**
   * Get the name of the webcomic in string form.
   */
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
