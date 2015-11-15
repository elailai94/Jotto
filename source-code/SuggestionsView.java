//==============================================================================
// Jotto
//
// @description: Module for providing functions to work with SuggestionsView
// objects
// @author: Elisha Lai
// @version: 1.0 05/11/2015
//==============================================================================

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import java.util.Collections;

public class SuggestionsView extends JPanel implements IView {
   private JFrame frame;
   private JottoModel model;
   private JList suggestionsList;

   public SuggestionsView(JFrame aFrame, JottoModel aModel) {
      super();
      frame = aFrame;
      model = aModel;
      layoutView();
      registerControllers();
   } // Constructor

   // Lays the widget out in the view
   private void layoutView() {
      setOpaque(false);
   	Border emptyBorder = BorderFactory.createEmptyBorder(50, 10, 20, 20);
      TitledBorder titledBorder =
         BorderFactory.createTitledBorder(emptyBorder, "Words Suggested");
      titledBorder.setTitleFont(
      	 new Font(titledBorder.getTitleFont().getName(), Font.BOLD, 18));
      titledBorder.setTitleColor(Color.WHITE);
      setBorder(titledBorder);
      setLayout(new GridLayout(1, 1));

      // Lays the word suggestions list out in the view
      suggestionsList = new JList<String>(model.getSuggestionsListModel());
      JScrollPane scrollPane = new JScrollPane(suggestionsList);
      scrollPane.setPreferredSize(new Dimension(220, getHeight()));
      add(scrollPane);
   } // layoutView

   // Registers controllers for each widget
   private void registerControllers() {
      suggestionsList.addMouseListener(new MouseAdapter() {
      	 public void mouseClicked(MouseEvent e) {
            JList list = (JList) e.getSource();
            if (e.getClickCount() == 2) {
               int index = list.locationToIndex(e.getPoint());
               if (index >= 0) {
                    disableMenuItems();
               	  DefaultListModel<String> suggestionsListModel =
               	     model.getSuggestionsListModel();
               	  String guess =
               	     suggestionsListModel.getElementAt(index).toString();
               	  model.setGuess(guess);
               } // if
            } // if
      	 } // mouseClicked
      });
   } // registerControllers

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
   	;
   } // update
}
