//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with WordList objects
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

// A list of words that can grow as needed.
public final class WordList extends Object implements IWordList {
   private ArrayList<Word> words = new ArrayList<Word>(); // Note: must not be empty
   
   // Creates a word list from a file
   public WordList(String filename) {
      super();
      try {
         Scanner s = new Scanner(new File(filename));
         readWords(s);
         s.close();         
      } catch (FileNotFoundException e) {
         System.out.println("File " + filename + " not found.");
         System.exit(-1);
      } // try
   } // Constructor

   // Reads in a word list from a file
   private void readWords(Scanner in) { 
      while (in.hasNextLine()) { 
         add(new Word(in));
      } // while
   } // readWords
   
   // Adds a new word to the list
   public void add(Word wd) {
      words.add(wd);
   } // add

   // See interface (IWordList.java file).
   public boolean contains(String aWord) {
      for (Word word : words) {
         if (word.getWord().equals(aWord)) {
            return true;
         } // if
      } // for
      
      return false;
   } // contains

   // See interface (IWordList.java file).
   public int numWords() {
      return words.size();
   } // numWords

   // Gets a word from the list at index i.
   private Word get(int i) {
      return words.get(i);
   } // get

   // See interface (IWordList.java file).
   public Word randomWord() {
      int r = (int) (Math.random() * words.size());
      return words.get(r);
   } // randomWord

   // See interface (IWordList.java file).
   public Word randomWord(int difficulty) {
      int r = (int) (Math.random() * words.size());
      
      // Checks the end of the array
      for (int i = r; i < words.size(); i++) {
         if (words.get(i).getDifficulty() == difficulty) {
            return words.get(i);	
         } // if
      } // for
   	
      // Nothing in the end of the array, so check the beginning
      for (int i = 0; i < r; i++) {
         if (words.get(i).getDifficulty() == difficulty) {
            return words.get(i);	
         } // if
      } // for
      
      return null;
   } // randomWord

   // See interface (IWordList.java file).
   public Word getWord(IWordPredicate test) {
      int r = (int) (Math.random() * words.size());
   
      // Checks the end of the array
      for (int i = r; i < words.size(); i++) {
         if (test.isOK(words.get(i))) {
            return words.get(i);	
         } // if
      } // for
   	
      // Nothing in the end of the array, so check the beginning
      for (int i = 0; i < r; i++) {
         if (test.isOK(words.get(i))) {
            return words.get(i);
         } // if
      } // for
      
      // Didn't find one
      return null;
   } // getWord

   // See interface (IWordList.java file).
   public ArrayList<Word> getWords(IWordPredicate test) {
      ArrayList<Word> answer = new ArrayList<Word>();
      
      for (Word word : words) {
         if (test.isOK(word)) {
            answer.add(new Word(word));
         } // if
      } // for
      
      return answer;
   } // getWords
   
   // See interface (IWordList.java file).
   public ArrayList<Word> getWords(int maxDesired, IWordPredicate test) {
      int count = 0;
      final int start = (int) (Math.random() * words.size());
      for (int i = 0; i < words.size() && count < maxDesired; i++) {
         if (test.isOK(words.get((start + i) % words.size()))) {
            count++;
         } // if
      } // for
   
      int numToGet = Math.min(count, maxDesired);
      ArrayList<Word> answer = new ArrayList<Word>();
      int next = 0;
      for (int i = 0; i < words.size() && next < numToGet; i++) {
         int w = (start + i) % words.size();
         if (test.isOK(words.get(w))) {
            answer.add(new Word(words.get(w)));
            next++;
         } // if
      } // for

      return answer;
   } // getWords
}
