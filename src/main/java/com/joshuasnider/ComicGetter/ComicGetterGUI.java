package com.joshuasnider.comicgetter;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ComicGetterGUI implements ActionListener {

  private List<ComicGetter> comics;
  private List<JCheckBox> check_boxes;

  //TODO Comments
  public static void main(String[] args) {
    new ComicGetterGUI();
  }

  public ComicGetterGUI()
  {
    JFrame guiFrame = new JFrame();

    guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    guiFrame.setTitle("Comic Getter GUI");
    guiFrame.setSize(500, 250);

    guiFrame.setLocationRelativeTo(null);

    final JPanel comboPanel = new JPanel();
    check_boxes = new ArrayList<>();
    comics = ComicGetter.getComicGetters();
    for (ComicGetter gtr : comics) {
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
    guiFrame.add(panel3, BorderLayout.SOUTH);
    guiFrame.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    System.out.println("TODO Download the following:");
    for (int index = 0; index < check_boxes.size(); index++)
    {
      if (check_boxes.get(index).isSelected())
      {
        System.out.println(check_boxes.get(index).getText());
        comics.get(index).getAllSafe();
      }
    }
  }
}