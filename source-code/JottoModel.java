//==============================================================================
// Jotto
//
// @description: Module for providing functions to work with JottoModel objects
// @author: Elisha Lai
// @version: 1.0 05/11/2015
//==============================================================================

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class JottoModel {
   public static int NUM_LETTERS = 5;
   public static int NUM_GUESSES = 10;
   public static enum LEVELS {
      Easy, Medium, Hard, Any
   }
    
   private static WordList words = new WordList("words.txt");
   private static LEVELS difficulty = LEVELS.Easy;
   private static Word target = words.randomWord(difficulty.ordinal());
   private static HashMap<Character, Integer> lettersFrequencies =
      new HashMap<Character, Integer>();
   private static String input = "";
   private static DefaultListModel<String> suggestionsListModel =
      new DefaultListModel<String>();
   private static String guess = "";
   private static int guessCount = 0;
   private static int exactMatchesCount = 0;
   private static int partialMatchesCount = 0;
   private static DefaultTableModel guessesTableModel =
      new DefaultTableModel() {
         public boolean isCellEditable(int row, int col) {
            return false;
         } // isCellEditable
      };
   private static HashMap<Character, Boolean> lettersGuessed =
      new HashMap<Character, Boolean>();
   private static boolean resetStatus = false;
   private static ArrayList<IView> views = new ArrayList<IView>();

   JottoModel() {
      findLettersFrequencies();
      updateSuggestionsListModel();
      initializeGuessesTableModel();
      initializeLettersGuessed();
   } // Constructor

   // Initializes the guesses table model
   private void initializeGuessesTableModel() {
      guessesTableModel.addColumn("Words");
      guessesTableModel.addColumn("Exact");
      guessesTableModel.addColumn("Partial");
   } // initializeGuessesTableModel

   // Initializes the letters guessed
   private void initializeLettersGuessed() {
      for (char c = 'A'; c <= 'Z'; c++) {
         lettersGuessed.put(c, false);
      } // for
   } // initializeLettersGuessed

   // Returns the difficulty as a numerical representation
   public int getDifficultyNum() {
      return difficulty.ordinal();
   } // getLevelNum

   // Returns the difficulty as a string representation
   public String getDifficultyStr() {
      return difficulty.name();
   } // getLevelStr

   // Returns the target
   public String getTarget() {
      return target.getWord();
   } // getTarget

   // Returns the frequency that the letter occurs in the target
   public int getLetterFrequency(char aLetter) {
      return lettersFrequencies.get(aLetter);
   } // getLetterFrequency

   // Returns the suggestions list model
   public DefaultListModel<String> getSuggestionsListModel() {
      return suggestionsListModel;
   } // getSuggestionsListModel

   // Returns the guess count
   public int getGuessCount() {
      return guessCount;
   } // getGuessCount

   // Returns the number of guesses left
   public int getNumOfGuessesLeft() {
      return (NUM_GUESSES - guessCount);
   } // getNumOfGuessesLeft

   // Returns the guesses table model
   public DefaultTableModel getGuessesTableModel() {
      return guessesTableModel;
   } // getGuessesTableModel

   // Returns the letters guessed
   public HashMap<Character, Boolean> getLettersGuessed() {
      return lettersGuessed;
   } // getLettersGuessed

   // Returns whether the letter has been guessed or not
   public boolean getLetterGuessed(char aLetter) {
      return lettersGuessed.get(aLetter);
   } // getLetterGuessed

   // Checks if a game has been won
   public boolean isWon() {
      return guess.equals(target.getWord());
   } // isWon

   // Returns the reset status
   public boolean getResetStatus() {
      return resetStatus;
   } // getReset

   // Sets the difficulty to a new difficulty level
   public void setDifficulty(LEVELS aLevel) {
      if (aLevel == LEVELS.Any) {
       	 int r = (int) (Math.random() * (JottoModel.LEVELS.values().length - 1));
         difficulty = LEVELS.values()[r];
      } else {
         difficulty = aLevel;
      } // if

      regenerateTarget();
      findLettersFrequencies();
      updateSuggestionsListModel();
      notifyObservers();
   } // setLevel

   // Regenerates the target randomly from words
   private void regenerateTarget() {
      target = words.randomWord(difficulty.ordinal());
   } // generateTarget

   // Sets the target to a new string
   public void setTarget(String aTarget) {
      target = new Word(aTarget.toUpperCase(), difficulty.ordinal());
      findLettersFrequencies();
      notifyObservers();
   } // setTarget

   // Finds the frequencies with which each letter in the alphabet
   // occurs in the target
   private void findLettersFrequencies() {       
      for (char c = 'A'; c <= 'Z'; c++) {
         lettersFrequencies.put(c, 0);
      } // for

      for (char c : target.getWord().toCharArray()) {
         int newLetterFrequency = (lettersFrequencies.get(c) + 1);
         lettersFrequencies.put(c, newLetterFrequency);
      } // for
   } // findLettersFrequencies

   // Sets the input to a new string
   public void setInput(String anInput) {
      input = anInput.toUpperCase();
      updateSuggestionsListModel();
      notifyObservers();
   } // setInput

   // Updates the suggestions list model to have the correct
   // auto-suggested words with the difficulty that start with
   // the input
   private void updateSuggestionsListModel() {
      ArrayList<String> guesses = new ArrayList<String>();
       
      for (int i = 0; i < guessesTableModel.getRowCount(); i++) {
         guesses.add((String) guessesTableModel.getValueAt(i, 0));
      } // for

      ArrayList<String> autoSuggestedWords = new ArrayList<String>();
      StartsWithPredicate test = new StartsWithPredicate(input, difficulty.ordinal());
       
      for (Word word : words.getWords(test)) {
         autoSuggestedWords.add(word.getWord());
      } // for

      autoSuggestedWords.removeAll(guesses);
      Collections.sort(autoSuggestedWords);

      suggestionsListModel.removeAllElements();
      for (String word : autoSuggestedWords) {
         suggestionsListModel.addElement(word);
      } // for
   } // updateSuggestionsListModel

   // Sets the guess to a new string
   public void setGuess(String aGuess) {
      guess = aGuess.toUpperCase();
      guessCount += 1;
      exactMatchesCount = 0;
      partialMatchesCount = 0;
      findMatches();
      updateGuessesTableModel();
      updateSuggestionsListModel();
      findLettersGuessed();
      notifyObservers();
   } // setGuess

    // Finds exact matches and partial matches between the guess
    // and the target
    private void findMatches() {
       boolean[] targetLetterParticipated = {false, false, false, false, false};
       
       // Finds exact matches between the guess and the target
       for (int i = 0; i < guess.length(); i++) {
       	  if (guess.charAt(i) == target.getWord().charAt(i)) {
             exactMatchesCount += 1;
             targetLetterParticipated[i] = true;
       	  } // if
       } // for

       // Finds partial matches between the guess and the target
       for (int i = 0; i < guess.length(); i++) {
          for (int j = 0; j < guess.length(); j++) {
             if (!targetLetterParticipated[j]) {
                if (guess.charAt(i) == target.getWord().charAt(j)) {
                   partialMatchesCount += 1;
                   targetLetterParticipated[j] = true;
                } // if
             } else {
                j++;
             } // if
          } // for
       } // for
    } // findMatches

    // Updates the guesses table model with a new row
    private void updateGuessesTableModel() {
       String exactMatchesCountStr = Integer.toString(exactMatchesCount);
       String partialMatchesCountStr = Integer.toString(partialMatchesCount);
       String[] row = {guess, exactMatchesCountStr, partialMatchesCountStr};
       guessesTableModel.addRow(row);
    } // updateGuessesTableModel

    // Finds
    private void findLettersGuessed() {       
       for (char c : guess.toCharArray()) {
          lettersGuessed.put(c, true);
       } // for
    } // findLettersGuessed

    // Checks if a word is in the list of words
    public boolean isInWords(String aWord) {
       return words.contains(aWord.toUpperCase());
    } // isInWords

    // Resets the model
    public void reset() {
       regenerateTarget();
       findLettersFrequencies();
       guess = "";
       guessCount = 0;
       partialMatchesCount = 0;
       exactMatchesCount = 0;
       guessesTableModel.setRowCount(0);
       initializeLettersGuessed();
       resetStatus = true;
       notifyObservers();
       resetStatus = false;
    } // reset

    // Adds a view observer to the model
    public void addObserver(IView aView) {
       views.add(aView);
       aView.update();
    } // addObserver

    // Updates all the views observing the model
    private void notifyObservers() {
       for (IView view : views) {
          view.update();
       } // for
    } // notifyObservers
}
