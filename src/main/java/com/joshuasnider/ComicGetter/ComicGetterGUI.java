/**
 * GUI Wrapper for the Comic Getter project.
 *
 * @author: Josh Snider
 *
 */
package com.joshuasnider.ComicGetter;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ComicGetterGUI implements ActionListener {

  private List<BaseComicGetter> comics;
  private List<JCheckBox> check_boxes;

  public static void main(String[] args) {
    new ComicGetterGUI();
  }

  public ComicGetterGUI()
  {
    JFrame guiFrame = new JFrame();

    guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    guiFrame.setTitle("Comic Getter GUI");
    guiFrame.setSize(500, 300);

    guiFrame.setLocationRelativeTo(null);

    final JPanel comboPanel = new JPanel();
    check_boxes = new ArrayList<>();
    comics = BaseComicGetter.getComicGetters();
    for (BaseComicGetter gtr : comics) {
      JCheckBox cb = new JCheckBox(gtr.getName());
      check_boxes.add(cb);
      comboPanel.add(cb);
    }
    comboPanel.setLayout(new GridLayout(comics.size()/3 + 1, 3));
    guiFrame.add(comboPanel, BorderLayout.NORTH);

    JPanel panel2 = new JPanel();
    JLabel comboLbl = new JLabel("Select which to download:");
    panel2.add(comboLbl);
    JButton download = new JButton("Download");
    panel2.add(download);
    download.addActionListener(this);
    guiFrame.add(panel2, BorderLayout.CENTER);
    JPanel panel3 = new JPanel();
    JTextArea output = new JTextArea(3, 40);
    output.setEditable(false);
    panel3.add(output);
    PrintStream printStream = new PrintStream(new TextAreaOutputStream(output));
    System.setOut(printStream);
    System.setErr(printStream);
    guiFrame.add(panel3, BorderLayout.SOUTH);
    guiFrame.setVisible(true);
  }

  public class TextAreaOutputStream extends OutputStream {
    private JTextArea textArea;

    public TextAreaOutputStream(JTextArea textArea) {
      this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
      textArea.append(String.valueOf((char)b));
      textArea.setCaretPosition(textArea.getDocument().getLength());
    }
  }

  public void actionPerformed(ActionEvent e) {
    // TODO FIXME Download button is hidden after textbox is written to.
    List<String> downloads = new ArrayList<>();
    for (int index = 0; index < check_boxes.size(); index++)
    {
      if (check_boxes.get(index).isSelected())
      {
        downloads.add(check_boxes.get(index).getText());
      }
    }
    System.out.println("Will download the following: " + String.join(", ", downloads) + ".");
    for (int index = 0; index < check_boxes.size(); index++)
    {
      if (check_boxes.get(index).isSelected())
      {
        comics.get(index).getAllSafe();
      }
    }
  }
}