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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import java.util.Observable;
import java.util.Observer;
import java.util.HashMap;
import java.util.HashSet;

public class LettersView extends JPanel implements Observer {
   private JottoModel model;
   private HashMap<Character, JLabel> lettersLabels = new HashMap<Character, JLabel>();

   public LettersView(JottoModel aModel) {
      super();
      model = aModel;
      layoutView();
   } // Constructor

   // Lays the widgets out in the view
   private void layoutView() {
      setBackground(Color.decode("0x222533"));
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

   // Updates the view using info from the model
   public void update(Observable o, Object arg) {
      HashSet<Character> guessLetters = model.getGuessLetters();
      
      for (char c : guessLetters) {
      	 JLabel letterLabel = lettersLabels.get(c);
      	 letterLabel.setOpaque(true);
      	 letterLabel.setBackground(Color.decode("0x00ABA9"));
      	 letterLabel.setBorder(null);
      } // for
   } // update
}
