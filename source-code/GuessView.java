//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with GuessView objects
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JOptionPane;

import java.util.Observable;
import java.util.Observer;

public class GuessView extends JPanel implements IView {
   private JFrame frame;
   private JottoModel model;
   private JTextField guessTextField = new JTextField(10);
   private JButton guessButton = new JButton("Guess");

   public GuessView(JFrame aFrame, JottoModel aModel) {
      super();
      frame = aFrame;
      model = aModel;
      layoutView();
      registerControllers();
   } // Constructor

   // Lays the widgets out in the view
   private void layoutView() {
   	setBackground(Color.decode("0x14283C"));
   	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();

      JLabel guessLabel = new JLabel("Enter a 5-letter word:");
      guessLabel.setForeground(Color.WHITE);
      guessLabel.setFont(new Font(guessLabel.getFont().getName(), Font.BOLD, 12));
      add(guessLabel, c);
      add(Box.createRigidArea(new Dimension(5, getHeight())));
      add(guessTextField, c);
      add(Box.createRigidArea(new Dimension(5, getHeight())));
      add(guessButton, c);
   } // layoutView

   // Registers controllers for each widget
   private void registerControllers() {
      guessTextField.getDocument().addDocumentListener(new DocumentListener() {
         public void insertUpdate(DocumentEvent e) {
            model.setInput(guessTextField.getText());
         } // insertUpdate

         public void removeUpdate(DocumentEvent e) {
            model.setInput(guessTextField.getText());
         } // removeUpdate

         public void changedUpdate(DocumentEvent e) {
            ;
         } // changedUpdate
      });

      guessTextField.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String guess = guessTextField.getText();
            guessTextField.setText(null);
            processGuess(guess);
         } // actionPerformed
      });

      guessButton.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
            String guess = guessTextField.getText();
            guessTextField.setText(null);
      	   processGuess(guess);
      	} // actionPerformed
      });
   } // registerControllers

   // Processes a guess by validating it before passing it onto
   // the model
   private void processGuess(String aGuess) {
      if (!isCorrectLength(aGuess)) {
         showErrorMessageDialog(
            "You haven't entered a 5-letter word. Please try again.");
      } else if (!containsOnlyLetters(aGuess)) {
         showErrorMessageDialog(
            "You haven't entered a valid word. Please try again.");
      } else if (!isInWordList(aGuess)) {
         showErrorMessageDialog(
            "You haven't entered a word that is in the dictionary.\n" +
            "You might like to try one of the words listed under\n" +
            "'Words Suggested'. Please try again.");
      } else {
         disableMenuItems();
         model.setGuess(aGuess);
      } // if
   } // processGuess

   // Checks if a string contains only five letters
   private boolean isCorrectLength(String aString) {
      if (aString.length() == JottoModel.NUM_LETTERS) {
         return true;
      } else {
         return false;
      } // if
   } // checkGuess

   // Checks if a string contains only letters
   private boolean containsOnlyLetters(String aString) {
      for (char c : aString.toCharArray()) {
         if (!Character.isLetter(c)) {
            return false;
         } // if
      } // for
      return true;
   } // containsOnlyLetters

   // Checks if a string is in the list of words
   private boolean isInWordList(String aString) {
      return model.isInWords(aString);
   } // isInWordList

   // Displays an error message dialog to the screen
   private void showErrorMessageDialog(String errorMessage) {
      JOptionPane.showMessageDialog(frame,
         errorMessage,
         "Input Error",
         JOptionPane.ERROR_MESSAGE);
   } // showErrorMessageDialog

   // Disables some menu items once the game starts
   // (i.e.: the player makes their first valid guess)
   private void disableMenuItems() {
      frame.getJMenuBar().getMenu(0).getItem(2).setEnabled(false);
      for (int i = 0; i < 4; i++) {
         frame.getJMenuBar().getMenu(1).getItem(i).setEnabled(false);
      } // for
   } // disableMenuItems

   // Updates the view using info from the model
   public void update() {
      if (model.isWon()) {
         
         while (true) {
            String guessStr = (model.getGuessCount() < 2)? "guess" : "guesses";

            int response = JOptionPane.showConfirmDialog(frame,
               ("Congratulations! You have successfully guessed\n" +
               "the target word in " + model.getGuessCount() +
               " " + guessStr + ". Would you like to\n" +
               "play again?"),
               "Game Won",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
               enableMenuItems();
               model.reset();
               break;
            } else if (response == JOptionPane.NO_OPTION) {
               System.exit(0);
            } else {
               continue;
            } // if
         } // while
      
      } else if (model.getNumOfGuessesLeft() <= 0) {
         
         while (true) {
            int response = JOptionPane.showConfirmDialog(frame,
               ("You haven't been able to guess the target word\n" +
               "in 10 guesses. The target word was " + model.getTarget() + ".\n" +
               "Would you like to play again?"),
               "Game Over",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE);
         
            if (response == JOptionPane.YES_OPTION) {
               enableMenuItems();
               model.reset();
               break;
            } else if (response == JOptionPane.NO_OPTION) {
               System.exit(0);
            } else {
               continue;
            } // if
         } // while
      
      } // if

      if (model.getResetStatus()) {
         guessTextField.setText(null);
      } // if
   } // update

   // Enables some menu items once the game ends
   // (i.e.: the game is won or lost)
   private void enableMenuItems() {
      frame.getJMenuBar().getMenu(0).getItem(2).setEnabled(true);
      for (int i = 0; i < 4; i++) {
         frame.getJMenuBar().getMenu(1).getItem(i).setEnabled(true);
      } // for
   } // enableMenuItems
}
