//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: A directly manipulatable excavator in Java
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

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
   	  // Creates a model and initializes it
      JottoModel model = new JottoModel();
      
      // Creates all the views and tells them about model and controller
      InfoView infoView = new InfoView(model);
      LettersView lettersView = new LettersView(model);
      GuessesView guessesView = new GuessesView(model);
      SuggestionsView suggestionsView = new SuggestionsView(model);
      GuessView guessView = new GuessView(model);
      
      // Tells model about all the views
      model.addObserver(infoView);
      model.addObserver(lettersView);
      model.addObserver(guessesView);
      model.addObserver(suggestionsView);
      model.addObserver(guessView);
      
      // Notifies all the views that they're connected to the model
      model.notifyObservers();

      // Creates the window
      JFrame frame = new JFrame("Jotto");
      frame.setJMenuBar(makeMenuBar(frame, model));
      frame.getContentPane().setLayout(new BorderLayout());
      frame.getContentPane().add(infoView, BorderLayout.NORTH);
      JPanel gameView = new JPanel();
      gameView.setLayout(new BoxLayout(gameView, BoxLayout.X_AXIS));
      gameView.add(lettersView);
      gameView.add(guessesView);
      gameView.add(suggestionsView);
      frame.getContentPane().add(gameView, BorderLayout.CENTER);
      frame.getContentPane().add(guessView, BorderLayout.SOUTH);
      frame.pack();
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setVisible(true);
   } // main

   // Makes a menu bar
   private static JMenuBar makeMenuBar(JFrame aFrame, JottoModel aModel) {
      JMenuBar menuBar = new JMenuBar();

      JMenu file = new JMenu("File");
      final JMenuItem newGame = new JMenuItem("New Game");
      newGame.setAccelerator(KeyStroke.getKeyStroke("control N"));
      final JMenuItem restartGame = new JMenuItem("Restart Game");
      restartGame.setAccelerator(KeyStroke.getKeyStroke("control R"));
      final JMenuItem setTargetWord = new JMenuItem("Set Target Word");
      final JMenuItem quit = new JMenuItem("Quit");

      file.add(newGame);
      file.add(restartGame);
      file.addSeparator();
      file.add(setTargetWord);
      file.addSeparator();
      file.add(quit);

      newGame.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         	if (newGame.getText().equals("New Game")) {
         	   ;
         	} else {
         	   assert false;
         	} // if
         } // actionPerformed
      });

      restartGame.addActionListener(new ActionListener() {
      	 public void actionPerformed(ActionEvent e) {
      	 	if (restartGame.getText().equals("Restart Game")) {
               ;
      	 	} else {
      	 	   assert false;
      	 	} // if
      	 } // actionPerformed
      });

      setTargetWord.addActionListener(new ActionListener() {
      	 public void actionPerformed(ActionEvent e) {
            if (setTargetWord.getText().equals("Set Target Word")) {
               String target = "";
               
               while (true) {
               	  target = JOptionPane.showInputDialog(aFrame,
               	     "Enter a 5-letter word: ",
               	     "Set Target Word",
               	     JOptionPane.PLAIN_MESSAGE);
                  
                  if (target == null) {
                     break;
                  } else if (checkTarget(target)) {
                     aModel.setTarget(target);
                     break;
                  } else {
                     JOptionPane.showMessageDialog(aFrame,
                        "You have entered an invalid word. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
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

      JMenu help = new JMenu("Help");
      final JMenuItem jottoHelp = new JMenuItem("Jotto Help");
      jottoHelp.setAccelerator(KeyStroke.getKeyStroke("control H"));

      help.add(jottoHelp);

      jottoHelp.addActionListener(new ActionListener() {
      	 public void actionPerformed(ActionEvent e) {
      	 	if (jottoHelp.getText().equals("Jotto Help")) {
               ;
      	 	} else {
      	 	   assert false;
      	 	} // if
      	 } // actionPerformed
      });

      menuBar.add(file);
      menuBar.add(difficulty);
      menuBar.add(help);
      return menuBar;
   } // makeMenuBar

   // Checks if a target word is the length specified by
   // JottoModel.NUM_LETTERS and contains ony letters
   private static boolean checkTarget(String aTarget) {
      if ((aTarget.length() != JottoModel.NUM_LETTERS) ||
         (!containsOnlyLetters(aTarget))) {
      	 return false;
      } else {
      	 return true;
      } // if
   } // checkTarget

   // Checks if a string contains only letters
   private static boolean containsOnlyLetters(String aString) {
      for (char c : aString.toCharArray()) {
      	 if (!Character.isLetter(c)) {
      	 	return false;
      	 } // if
      } // for
      return true;
   } // containsNonLetters
}
