//==============================================================================
// CS349 Assignment 03, Jotto
//
// @description: Module for providing functions to work with IView interface
// @author: Ah Hoe Lai
// @userid: ahlai
// @version: 1.0 05/11/2015
//==============================================================================

public interface IView {
   
   // Updates the view
   // Note: This method is generally called by the model
   // whenever it has changed state.
   public void update();
}
