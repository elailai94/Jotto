//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with IWordPredicate
// interface
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

// Classes implementing IWordPredicate are used to find
// out whether or not a given word meets some criteria
// determined by the implementation of isOK(Word w).
public interface IWordPredicate {

   // Does the given Word meet a condition?
   // @param w The word-difficulty object to test
   // @return true if it meets the condition; false otherwise.
   public boolean isOK(Word aWord);
}
