//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with Word objects
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

// package jotto;

import java.util.Scanner;

// Stores one word together with its difficulty.
public class Word extends Object {
   private String theWord;
   private int difficulty;
	
   // Reads one word from an open file.  
   // Note: File format is a word followed by its difficulty, one pair per line.
   // The difficulty must be in the range of 0..2. The file cursor is expected
   // immediately before the word and will be left immediately before the
   // following word in the file. For example:
   // TRUCK 0
   // FOLLY 1
   // LIGHT 0
   // EMBAY 2
   // ...
   public Word(Scanner in) {
      super();
      theWord = in.next();
      difficulty = in.nextInt();
      in.nextLine();
		
      checkWord();
      checkDifficulty();
   } // Constructor
	
   // Constructs a new Word object.
   public Word(String aWord, int aLevel) {
      super();
      theWord = aWord;
      difficulty = aLevel;

      checkWord();
      checkDifficulty();
   } // Constructor

   // Throws an error if the word isn't the length specified by
   // JottoModel.NUM_LETTERS
   private void checkWord() {
      if (theWord.length() != JottoModel.NUM_LETTERS) {
         throw new Error(
            "The word '" + theWord + "' does not have "
            + JottoModel.NUM_LETTERS + " letters.");
      } // if
   } // checkLength

   // Throws an error if the difficulty level isn't one of {0, 1, 2}
   private void checkDifficulty() {
      if (difficulty < 0 || 
         difficulty >= JottoModel.LEVELS.values().length - 1) {
         throw new Error(
            "The word '" + theWord + "' has an invalid difficulty: "
            + difficulty);
      } // if
   } // checkDifficulty

   // Constructs a new Word object from another Word object.
   public Word(Word otherWord) {
      theWord = otherWord.getWord();
      difficulty = otherWord.getDifficulty();    
   } // Copy constructor
	
   // Returns the difficulty level.
   public int getDifficulty() {
      return difficulty;
   } // getDifficulty
	
   // Returns the word.
   public String getWord() {
      return theWord;
   } // getWord
   
   /*
    public boolean containsLetter(char letter)
    {  return this.theWord.indexOf(letter) >= 0;
    }
    */
}
