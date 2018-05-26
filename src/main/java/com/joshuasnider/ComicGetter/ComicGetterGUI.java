package com.joshuasnider.comicgetter;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;

public class ComicGetterGUI {

//TODO Comments
public static void main(String[] args) {
  new ComicGetterGUI();
}

public ComicGetterGUI()
{
  JFrame guiFrame = new JFrame();

  guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  guiFrame.setTitle("Comic Getter GUI");
  guiFrame.setSize(300,250);

  guiFrame.setLocationRelativeTo(null);

  final JPanel comboPanel = new JPanel();
  JLabel comboLbl = new JLabel("Comics:");
  String[] comics = ComicGetter.getComicGetters().stream().map(ComicGetter::getName).toArray(String[]::new);
  JComboBox comic_box = new JComboBox(comics);
  comboPanel.add(comboLbl);
  comboPanel.add(comic_box);
  guiFrame.add(comboPanel, BorderLayout.NORTH);
  guiFrame.setVisible(true);
  }
}