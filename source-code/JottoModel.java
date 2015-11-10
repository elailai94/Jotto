//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with JottoModel objects
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import java.util.Observable;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class JottoModel extends Observable {
	public static int NUM_LETTERS = 5;
	public static int NUM_GUESSES = 10;
    public static enum LEVELS {
       Easy, Medium, Hard, Any
    }
    
    private static WordList words = new WordList("words.txt");
    private static LEVELS difficulty = LEVELS.Easy;
    private static Word target = words.randomWord(difficulty.ordinal());
    private static String input = "";
    private static DefaultListModel suggestionsListModel = new DefaultListModel();
    private static String guess = "";
    private static int guessCount = 0;
    private static boolean[] exactMatches = {false, false, false, false, false};
    private static int exactMatchesCount = 0;
    private static boolean[] partialMatches = {false, false, false, false, false};
    private static int partialMatchesCount = 0;
    private static DefaultTableModel guessesTableModel = new DefaultTableModel();
    private static boolean started = false; 
    private static boolean won = false;

    JottoModel() {
       System.out.println(target.getWord());
       updateSuggestionsListModel();
       initializeGuessesTableModel();
       setChanged();
    } // Constructor

    // Initializes the guesses table model
    public void initializeGuessesTableModel() {
       guessesTableModel.addColumn("Words");
       guessesTableModel.addColumn("Exact Matches");
       guessesTableModel.addColumn("Partial Matches");
    } // initializeGuessesTableModel

    // Returns the difficulty as a numerical representation
    public int getDifficultyNum() {
       return difficulty.ordinal();
    } // getLevelNum

    // Returns the difficulty as a string representation
    public String getDifficultyStr() {
       return difficulty.name();
    } // getLevelStr

    // Returns the suggestions list model
    public DefaultListModel getSuggestionsListModel() {
       return suggestionsListModel;
    } // getSuggestionsListModel

    // Returns the guess
    public String getGuess() {
       return guess;
    } // getGuess

    // Returns the number of guesses left
    public int getNumOfGuessesLeft() {
       return (NUM_GUESSES - guessCount);
    } // getNumOfGuessesLeft

    // Returns the number of exact matches for the guess
    public int getExactMatchesCount() {
       return exactMatchesCount;
    } // getExactMatchesCount

    // Returns the number of partial matches for the guess
    public int getPartialMatchesCount() {
       return partialMatchesCount;
    } // getPartialMatchesCount

    // Returns the letters of the alphabet in the guess
    public HashSet<Character> getGuessLetters() {
       HashSet<Character> guessLetters = new HashSet<Character>();
       
       for (char c : guess.toCharArray()) {
       	  guessLetters.add(c);
       } // for
       
       return guessLetters;
    } // getGuessLetters

    // Returns the guesses table model
    public DefaultTableModel getGuessesTableModel() {
       return guessesTableModel;
    } // getGuessesTableModel

    // Checks if a game has started
    public boolean hasStarted() {
       return started;
    } // hasStarted

    // Checks if a game has been won
    public boolean isWon() {
       return won;
    } // isWon

    // Sets the difficulty to a new difficulty level
    public void setDifficulty(LEVELS aLevel) {
       if (aLevel == LEVELS.Any) {
       	  int r = (int) (Math.random() * (JottoModel.LEVELS.values().length - 1));
          difficulty = LEVELS.values()[r];
       } else {
          difficulty = aLevel;
       } // if

       regenerateTarget();
       updateSuggestionsListModel();
       setChanged();
       notifyObservers();
       System.out.println(target.getWord() + ", " + target.getDifficulty());
    } // setLevel

    // Regenerates the target randomly from words
    private void regenerateTarget() {
       target = words.randomWord(difficulty.ordinal());
    } // generateTarget

    // Sets the target to a new string
    public void setTarget(String aTarget) {
       target = new Word(aTarget.toUpperCase(), LEVELS.Easy.ordinal());
    } // setTarget

    // Sets the input to a new string
    public void setInput(String anInput) {
       input = anInput.toUpperCase();
       updateSuggestionsListModel();
       setChanged();
       notifyObservers();
    } // setInput

    // Updates the suggestions list model to have the correct
    // auto-suggested words with the difficulty that start with
    // the input
    private void updateSuggestionsListModel() {
       ArrayList<String> autoSuggestedWords = new ArrayList<String>();
       StartsWithPredicate test = new StartsWithPredicate(input);

       for (Word word : words.getWords(test)) {
       	  if (word.getDifficulty() == difficulty.ordinal()) {
       	  	 autoSuggestedWords.add(word.getWord());
       	  } // if
       } // for

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
       started = true;
       updateGuessesTableModel();
       setChanged();
       notifyObservers();
    } // setGuess

    // Finds exact matches and partial matches between the guess
    // and the target
    private void findMatches() {
       boolean[] targetLetterParticipated = {false, false, false, false, false};
       
       // Finds exact matches between the guess and the target
       for (int i = 0; i < guess.length(); i++) {
       	  if (guess.charAt(i) == target.getWord().charAt(i)) {
             exactMatches[i] = true;
             exactMatchesCount += 1;
             targetLetterParticipated[i] = true;
       	  } // if
       } // for

       // Finds partial matches between the guess and the target

    } // findMatches

    // Updates the guesses table model with a new row
    private void updateGuessesTableModel() {
       String exactMatchesCountStr = Integer.toString(exactMatchesCount);
       String partialMatchesCountStr = Integer.toString(partialMatchesCount);
       String[] row = {guess, exactMatchesCountStr, partialMatchesCountStr};
       guessesTableModel.addRow(row);
    } // updateGuessesTableModel

    // Resets the model
    public void reset() {
       regenerateTarget();
       guess = "";
       guessCount = 0;
    } // reset
}
