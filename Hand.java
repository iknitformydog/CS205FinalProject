public class Hand
{
	public final static int NUM_CARDS = 4;  //the number of cards in a player's hand
	
	private Card[] cards;  //array containing the cards in a player's hand
	
	/**
		Constructor creates a new Hand object
	*/
	
	public Hand()
	{
		cards = new Card[NUM_CARDS];  //create a new hand array
		initializeHand();  //initialize cards in hand array to null
	}
	
	/**
		Method initalizes all cards in hand to null
	*/
	
	private void initializeHand()
	{
		//initialize all cards in hand array to null
	
		for(int i = 0 ; i < NUM_CARDS ; i++)
		{
			cards[i] = null;
		}
	}
	
	/**
		Method swaps a card with a card in hand at specified index
		@param newCard The card to add to hand
		@param index The index to make the swap
		@return The card removed from the hand
	*/
		
	public Card swapCard(Card newCard, int index)
	{
		Card oldCard = cards[index];  //the card currently stored in the index that the swap will occur
		cards[index] = newCard;  //set the hand array at the specified index to equal the card to be added
		return oldCard;  //return the card previously held at the specified index
	}
	
	/**
		Method returns a card in hand at a specified index
		@param index The specified index to return card
		@return The card at the specified index
	*/
	
	public Card getCard(int index)
	{
		return cards[index];
	}
	
	/**
		Method returns the number of power cards in hand
		@return The number of power cards in hand
	*/
	
	public int getNumPowerCards()
	{
		int sum = 0;  //total number of power cards in a player's hand
		
		//for every card in a player's hand, check to see if it is a power card
		//if it is a power card, add 1 to the sum counter
		
		for(int i = 0 ; i < NUM_CARDS ; i++)
		{
			if(cards[i].isPowerCard())
			{
				sum++;
			}
		}
		
		return sum;  //return the number of power cards in the player's hand
	}
	
	/**
		Method returns the sum of cards in hand
		@return Sum of cards in hand
	*/
	
	public int getHandValue()
	{
		int sum = 0;  //the sum of all cards in a player's hand
		
		//for every card in a player's hand, retrieve its value and add to sum
		
		for(int i = 0 ; i < NUM_CARDS ; i++)
		{
			sum = sum + cards[i].getValue();
		}
		
		return sum;  //return the sum of all cards in the player's hand
	}
	
	/**
		Method returns hand as a String
		@return The hand in the form of a String
	*/
		
	public String toString()
	{
		StringBuilder hand = new StringBuilder();
		hand.append("[");
		
		for(int i = 0 ; i < NUM_CARDS ; i++)
		{
			hand.append(cards[i]);
			if(i != NUM_CARDS - 1)
			{
				hand.append(", ");
			}
		}
		
		hand.append("]");
		return hand.toString();
	}
}