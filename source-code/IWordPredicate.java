//==============================================================================
// Jotto
//
// @description: Module for providing functions to work with IWordPredicate
// interface
// @author: Elisha Lai
// @version: 1.0 05/11/2015
//==============================================================================

// Classes implementing IWordPredicate are used to find
// out whether or not a given word meets some criteria
// determined by the implementation of isOK(Word w).
public interface IWordPredicate {

   // Does the given Word meet a condition?
   public boolean isOK(Word aWord);
}
