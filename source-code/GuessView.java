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
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

import java.util.Observable;
import java.util.Observer;

public class GuessView extends JPanel implements Observer {
   private JottoModel model;
   private JTextField guessTextField = new JTextField(10);
   private JButton guessButton = new JButton("Guess");

   public GuessView(JottoModel aModel) {
      super();
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

      JLabel guessLabel = new JLabel("Enter a 5-letter word: ");
      guessLabel.setForeground(Color.WHITE);
      guessLabel.setFont(new Font(guessLabel.getFont().getName(), Font.BOLD, 12));
      add(guessLabel, c);
      add(guessTextField, c);
      add(guessButton, c);
   } // layoutView

   // Registers controllers for each widget
   private void registerControllers() {
      guessTextField.addKeyListener(new KeyAdapter() {
      	 public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
               model.setGuess(guessTextField.getText());
               model.setInput("");
               guessTextField.setText(null);
            } else {
               model.setInput(guessTextField.getText());
            } // if
      	 } // keyReleased
      });

      guessButton.addActionListener(new ActionListener() {
      	 public void actionPerformed(ActionEvent e) {
      	    model.setGuess(guessTextField.getText());
      	    model.setInput("");
      	    guessTextField.setText(null);
      	 } // actionPerformed
      });
   } // registerControllers

   // Checks 
   private void checkGuess(String aGuess) {
      ;
   } // checkGuess

   // Updates the view using info from the model
   public void update(Observable o, Object arg) {
   	  ;
   } // update
}
