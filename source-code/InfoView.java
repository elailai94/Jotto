//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with InfoView
// objects
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JLabel;

public class InfoView extends JPanel implements IView {
   private JottoModel model;
   private JLabel difficultyLabel;
   private JLabel guessesLeftLabel;
   
   public InfoView(JottoModel aModel) {
      super();
      model = aModel;
      layoutView();
   } // Constructor

   // Lays the widgets out in the view
   private void layoutView() {
   	setBackground(Color.decode("0x2D89EF"));
   	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      // Lays the difficulty indicator out in the view
      String difficultyValue = model.getDifficultyStr();
      difficultyLabel = new JLabel("Difficulty: " + difficultyValue);
      difficultyLabel.setForeground(Color.WHITE);
      difficultyLabel.setFont(new Font(difficultyLabel.getFont().getName(), Font.BOLD, 14));
      difficultyLabel.setHorizontalAlignment(JLabel.CENTER);
      difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      add(difficultyLabel);
      
      add(Box.createVerticalStrut(5));

      // Lays the number of guesses left indicator out in the view
      String guessesLeftValue = Integer.toString(model.getNumOfGuessesLeft());
      guessesLeftLabel = new JLabel("Guesses Left: " + guessesLeftValue);
      guessesLeftLabel.setForeground(Color.WHITE);
      guessesLeftLabel.setFont(new Font(guessesLeftLabel.getFont().getName(), Font.BOLD, 14));
      guessesLeftLabel.setHorizontalAlignment(JLabel.CENTER);
      guessesLeftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      add(guessesLeftLabel);
   } // layoutView

   // Updates the view using info from the model
   public void update() {
   	  // Updates the difficulty indicator in the view
      String difficultyValue = model.getDifficultyStr();
      difficultyLabel.setText("Difficulty: " + difficultyValue);

      // Updates the number of guesses left indicator in the view
      String guessesLeftValue = Integer.toString(model.getNumOfGuessesLeft());
      guessesLeftLabel.setText("Guesses Left: " + guessesLeftValue);
   } // update
}
