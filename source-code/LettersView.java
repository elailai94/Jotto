//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with LettersView objects
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import java.util.HashMap;
import java.util.Map;

public class LettersView extends JPanel implements IView {
   private JottoModel model;
   private HashMap<Character, JLabel> lettersLabels = new HashMap<Character, JLabel>();

   public LettersView(JottoModel aModel) {
      super();
      model = aModel;
      layoutView();
      registerControllers();
   } // Constructor

   // Lays the widgets out in the view
   private void layoutView() {
      setOpaque(false);
      Border emptyBorder = BorderFactory.createEmptyBorder(50, 20, 20, 10);
      TitledBorder titledBorder =
         BorderFactory.createTitledBorder(emptyBorder, "Letters Guessed");
      titledBorder.setTitleFont(new Font(titledBorder.getTitleFont().getName(), Font.BOLD, 18));
      titledBorder.setTitleColor(Color.WHITE);
      setBorder(titledBorder);
      setLayout(new GridLayout(6, 5, 10, 10));

      // Lays the letters grid out in the view
      for (char c = 'A'; c <= 'Z'; c++) {
      	JLabel letterLabel = new JLabel(Character.toString(c));
      	letterLabel.setForeground(Color.WHITE);
      	letterLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
      	letterLabel.setFont(new Font(letterLabel.getFont().getName(), Font.BOLD, 30));
      	letterLabel.setHorizontalAlignment(JLabel.CENTER);
      	letterLabel.setVerticalAlignment(JLabel.CENTER);
         lettersLabels.put(c, letterLabel);
         add(letterLabel);
      } // for
   } // layoutView

   // Registers controllers for each widget
   private void registerControllers() {
      for (JLabel letterLabel : lettersLabels.values()) {
         char letterChar = letterLabel.getText().charAt(0);
         
         letterLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
               int letterFrequency = model.getLetterFrequency(letterChar);
               letterLabel.setOpaque(true);
               letterLabel.setBackground(Color.decode("0x7E3878"));
               letterLabel.setBorder(null);
               letterLabel.setText(Integer.toString(letterFrequency));
            } // mouseEntered

            public void mouseExited(MouseEvent e) {
               if (model.getLetterGuessed(letterChar)) {
                  letterLabel.setOpaque(true);
                  letterLabel.setBackground(Color.decode("0x00ABA9"));
                  letterLabel.setBorder(null);
               } else {
                  letterLabel.setOpaque(false);
                  letterLabel.setBackground(null);
                  letterLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
               } // if

               letterLabel.setText(String.valueOf(letterChar));
            } // mouseExited
         });
      } // for
      ;
   } // registerControllers

   // Updates the view using info from the model
   public void update() {
      for (Map.Entry<Character, Boolean> entry : model.getLettersGuessed().entrySet()) {
         JLabel letterLabel = lettersLabels.get(entry.getKey());

         if (entry.getValue()) {
            letterLabel.setOpaque(true);
            letterLabel.setBackground(Color.decode("0x00ABA9"));
            letterLabel.setBorder(null);
         } else {
            letterLabel.setOpaque(false);
            letterLabel.setBackground(null);
            letterLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
         } // if
      } // for
   } // update
}
