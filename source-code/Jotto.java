//==============================================================================
// Jotto
//
// @description: An implementation of Jotto in Java and Swing
// @author: Elisha Lai
// @version: 1.0 05/11/2015
//==============================================================================

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class Jotto {
   public static void main(String[] args) {
      // Creates the window
      JFrame frame = new JFrame("Jotto");

      // Creates a model and initializes it
      JottoModel model = new JottoModel();

      // Sets up the menubar in the window
      frame.setJMenuBar(makeMenuBar(frame, model));

      // Creates all the views and tells them about model and controller
      InfoView infoView = new InfoView(model);
      LettersView lettersView = new LettersView(model);
      GuessesView guessesView = new GuessesView(model);
      SuggestionsView suggestionsView = new SuggestionsView(frame, model);
      GuessView guessView = new GuessView(frame, model);
      
      // Tells model about all the views
      model.addObserver(infoView);
      model.addObserver(lettersView);
      model.addObserver(guessesView);
      model.addObserver(suggestionsView);
      model.addObserver(guessView);
      
      // Notifies all the views that they're connected to the model
      // model.notifyObservers();

      // Sets up other elements in the window
      frame.getContentPane().setLayout(new BorderLayout());
      frame.getContentPane().add(infoView, BorderLayout.NORTH);
      
      JPanel gameView = new JPanel();
      gameView.setBackground(Color.decode("0x222533"));
      gameView.setLayout(new BoxLayout(gameView, BoxLayout.X_AXIS));
      gameView.add(lettersView);
      gameView.add(guessesView);
      gameView.add(suggestionsView);
      
      frame.getContentPane().add(gameView, BorderLayout.CENTER);
      frame.getContentPane().add(guessView, BorderLayout.SOUTH);
      frame.setMinimumSize(new Dimension(900, 600));
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   } // main

   // Makes a menu bar
   private static JMenuBar makeMenuBar(JFrame aFrame, JottoModel aModel) {
      JMenuBar menuBar = new JMenuBar();

      JMenu game = new JMenu("Game");
      final JMenuItem newGame = new JMenuItem("New Game");
      newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
      final JMenuItem setTargetWord = new JMenuItem("Set Target Word");
      final JMenuItem quit = new JMenuItem("Quit");
      quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));

      game.add(newGame);
      game.addSeparator();
      game.add(setTargetWord);
      game.addSeparator();
      game.add(quit);

      JMenu difficulty = new JMenu("Difficulty");
      ButtonGroup difficulties = new ButtonGroup();
      final JRadioButtonMenuItem easy =
         new JRadioButtonMenuItem("Easy", true);
      final JRadioButtonMenuItem medium =
         new JRadioButtonMenuItem("Medium", false);
      final JRadioButtonMenuItem hard =
         new JRadioButtonMenuItem("Hard", false);
      final JRadioButtonMenuItem any =
         new JRadioButtonMenuItem("Any", false);

      difficulty.add(easy);
      difficulty.add(medium);
      difficulty.add(hard);
      difficulty.add(any);

      difficulties.add(easy);
      difficulties.add(medium);
      difficulties.add(hard);
      difficulties.add(any);

      menuBar.add(game);
      menuBar.add(difficulty);

      newGame.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (newGame.getText().equals("New Game")) {
               // Enables some menu items
               setTargetWord.setEnabled(true);
               easy.setEnabled(true);
               medium.setEnabled(true);
               hard.setEnabled(true);
               any.setEnabled(true);

               aModel.reset();
            } else {
               assert false;
            } // if
         } // actionPerformed
      });

      setTargetWord.addActionListener(new ActionListener() {
      	 public void actionPerformed(ActionEvent e) {
            if (setTargetWord.getText().equals("Set Target Word")) {
               
               while (true) {
               	String target = JOptionPane.showInputDialog(aFrame,
               	   "Enter a 5-letter word: ",
               	   "Set Target Word",
               	   JOptionPane.PLAIN_MESSAGE);
                  
                  if (target == null) {
                     break;
                  } else if (!isCorrectLength(target)) {
                     showErrorMessageDialog(aFrame,
                        "You haven't entered a 5-letter word. Please try again.");
                  } else if (!containsOnlyLetters(target)) {
                     showErrorMessageDialog(aFrame,
                        "You haven't entered a valid word. Please try again.");
                  } else if (!isInWordList(aModel, target)) {
                     showErrorMessageDialog(aFrame,
                        ("You haven't entered a word that is in the dictionary.\n" +
                        "You might like to try one of the words listed under\n" +
                        "'Words Suggested'. Please try again."));
                  } else {
                     aModel.setTarget(target);
                     break;
                  } // if
               } // while

            } else {
               assert false;
            }// if
      	 } // actionPerformed
      });

      quit.addActionListener(new ActionListener() {
      	 public void actionPerformed(ActionEvent e) {
      	    if (quit.getText().equals("Quit")) {
      	       System.exit(0);
      	    } else {
      	       assert false;
      	    } // if
      	 } // actionPerformed
      });

      easy.addActionListener(new ActionListener() {
      	 public void actionPerformed(ActionEvent e) {
            if (easy.getText().equals("Easy")) {
               aModel.setDifficulty(JottoModel.LEVELS.Easy);
            } else {
               assert false;
            } // if
      	 } // actionPerformed
      });

      medium.addActionListener(new ActionListener() {
      	 public void actionPerformed(ActionEvent e) {
            if (medium.getText().equals("Medium")) {
               aModel.setDifficulty(JottoModel.LEVELS.Medium);
            } else {
               assert false;
            } // if
      	 } // actionPerformed
      });

      hard.addActionListener(new ActionListener() {
      	 public void actionPerformed(ActionEvent e) {
            if (hard.getText().equals("Hard")) {
               aModel.setDifficulty(JottoModel.LEVELS.Hard);
            } else {
               assert false;
            } // if
      	 } // actionPerformed
      });

      any.addActionListener(new ActionListener() {
      	 public void actionPerformed(ActionEvent e) {
            if (any.getText().equals("Any")) {
               aModel.setDifficulty(JottoModel.LEVELS.Any);
            } else {
               assert false;
            } // if
      	 } // actionPerformed
      });

      return menuBar;
   } // makeMenuBar

   // Checks if a string contains only five letters
   private static boolean isCorrectLength(String aString) {
      if (aString.length() == JottoModel.NUM_LETTERS) {
         return true;
      } else {
         return false;
      } // if
   } // isCorrectLength

   // Checks if a string contains only letters
   private static boolean containsOnlyLetters(String aString) {
      for (char c : aString.toCharArray()) {
      	 if (!Character.isLetter(c)) {
      	    return false;
      	 } // if
      } // for
      return true;
   } // containsOnlyLetters

   // Checks if a string is in the list of words
   private static boolean isInWordList(JottoModel aModel, String aString) {
      return aModel.isInWords(aString);
   } // isInWordList

   // Displays an error message dialog to the screen
   private static void showErrorMessageDialog(JFrame aFrame, String errorMessage) {
      JOptionPane.showMessageDialog(aFrame,
         errorMessage,
         "Input Error",
         JOptionPane.ERROR_MESSAGE);
   } // showErrorMessageDialog
}
