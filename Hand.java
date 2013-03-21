//CS 205
//Hand class

///////Not completely sure if we need to use an array list
///////for this, but some of the java methods are useful.
///////Array lists due have an initial size of 10 I think, so
///////this can be up for discussion.

import javax.swing.*;
import java.util.*;

//******************************************************/
public class Hand
{
   ArrayList<Card> handList=new ArrayList<Card>();
   ListIterator<Card> handIter;
   private ListIterator<Card> handIter2;
   Card nextCard;
   
   
//***********constructor****************/
/**
   Constructor of Hand object
**/
   public Hand()
   {
      
   }//Hand


/************************************/
/**
   method adds a card to the hand.
   @param cardAdd -card dealt to hand/card from pile
**/
   public ArrayList addCard(Card cardAdd)
   {
      handList.add(cardAdd);
      handIter2=handList.listIterator();
      return handList;
   }//addCard
   
   
   
   
   
/************************************/
/**
   method gets the hand's value
   
**/
   public int getHandValue()
   {
      int cardScore;
      int handScore=0;

      for(int i=0;i<handList.size();i++)
      {
         cardScore=handList.get(i).getValue();
         //if the card is not a power card then calc. the value
         if(cardScore!=-1)
         {
            handScore+=cardScore;
         }//if
      }//for
      
      return handScore;
   }//getHandValue





/************************************/
/**
   method shows the card at the array location
   @param int index -location of card
**/
   public void showCard(int index)
   {

      /////what will this method return specifically
      /////in order to work with the GUI. 
      ///I'm thinking this could actually return the info
      ///of the card at the index, which would then be sent
      ///to the function in the GameGUI, that will actually 
      ///show the card's image 
   }//addCard






/************************************/
/**
     method checks if hand is empty.
**/
   public boolean isEmpty()
   {
      boolean answer=handList.isEmpty();
      return answer;
   }



/*********************************/
/**
	method clears the hand of cards.
**/
	public void clearHand(){
		handIter=handList.listIterator();
		while(handIter.hasNext())
      {
			handList.clear();
		}//while
	}//clearHand
   
   
   

   
/************************************/
/**
   method removes card from hand at specified index location
   @param int index
**/
   public void remove(int index)
   {
      handList.remove(index);
   }



/************************************/
/**
	method returns arrayList containing hand
**/
	public ArrayList<Card> getHandList(){
		return handList;
	}//getHandList
 
 
 
 
 
}//Class