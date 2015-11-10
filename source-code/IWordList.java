//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with IWordList interface
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

import java.util.ArrayList;

// Classes implementing IWordList provide a list of
// word-difficulty pairs (Word objects).
public interface IWordList {
   // Does the list of words contain a word-difficulty pair with the
   // given word?
   // @param aWord a word to search for in the list
   // @return true if the word is contained in the list; false otherwise.
   public boolean contains(String aWord);
   
   // The number of words in the list.
   // @return the number of words in the list.
   public int numWords();

   // Find a random word. The list must contain at least one word.
   // @return a random word from the list.
   public Word randomWord();
	   
   // Find a random word with the given difficulty in the list.  The list
   // must contain at least one such word.
   // @param difficulty The required difficulty, one of 0..2
   // @return a word with the required difficulty
   public Word randomWord(int difficulty);
   
   // Find one word that passes the given test; the test is typically
   // given by a Hint object (which implements IWordPredicate).  
   // The method should start
   // with a random word in the list and then proceed sequentially through
   // the list until it either finds a word that passes the test or it
   // it exhausts the list, in which case it returns null.
   // @param test The test that the Word must pass
   // @return a word passing the test or null if no such word exists.
   public Word getWord(IWordPredicate test);
   
   // Find all the words that pass the given test; the test is typically
   // given by a Hint object (which implements IWordPredicate).
   // @param test The test that each returned Word must pass.
   // @return a filled arraylist of Words that pass the test; an arraylist with length
   // 0 if no such words exist.
   public ArrayList<Word> getWords(IWordPredicate test);
   
   // Find at most maxDesired words that pass the given test;  the test is typically
   // given by a Hint object (which implements IWordPredicate).
   // @param maxDesired the maximum number of words to return.
   // @param test The test that each returned Word must pass.
   // @return a filled arraylist of Words that pass the test; an arraylist with length
   // 0 if no such words exist.
   public ArrayList<Word> getWords(int maxDesired, IWordPredicate test);
}
