//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with StartsWithPredicate
// objects
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

public class StartsWithPredicate implements IWordPredicate {
   private String testString;
   private int difficulty;

   public StartsWithPredicate(String aString, int aLevel) {
      testString = aString;
      difficulty = aLevel;
   } // Constructor

   // See interface (IWordPredicate.java file).
   public boolean isOK(Word aWord) {
      return (aWord.getWord().startsWith(testString) &&
         (aWord.getDifficulty() == difficulty));
   } // isOK
}
