//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with GuessesView objects
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import java.util.Observable;
import java.util.Observer;

public class GuessesView extends JPanel implements IView {
   private JottoModel model;
   private JTable guessesTable;

   public GuessesView(JottoModel aModel) {
      super();
      model = aModel;
      layoutView();
   } // Constructor

   // Lays the widgets out in the view
   private void layoutView() {
      setOpaque(false);
      Border emptyBorder = BorderFactory.createEmptyBorder(50, 10, 20, 10);
      TitledBorder titledBorder =
         BorderFactory.createTitledBorder(emptyBorder, "Words Guessed");
      titledBorder.setTitleFont(new Font(titledBorder.getTitleFont().getName(), Font.BOLD, 18));
      titledBorder.setTitleColor(Color.WHITE);
      setBorder(titledBorder);
      setLayout(new GridLayout(1, 1));
      
      // Lays the guesses table out in the view
      guessesTable = new JTable(model.getGuessesTableModel());
      guessesTable.getTableHeader().setReorderingAllowed(false);
      guessesTable.getTableHeader().setResizingAllowed(false);
      guessesTable.setFocusable(false);
      guessesTable.setRowSelectionAllowed(false);
      guessesTable.setColumnSelectionAllowed(false);
      guessesTable.setCellSelectionEnabled(false);
      JScrollPane scrollPane = new JScrollPane(guessesTable);
      scrollPane.setPreferredSize(new Dimension(220, getHeight()));
      add(scrollPane);
   } // layoutView

   // Updates the view using info from the model
   public void update() {
      ;
   } // update
}
