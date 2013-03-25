public class Hand
{
	public final static int NUM_CARDS = 4;
	private Card[] cards;
	
	public Hand()
	{
		cards = new Card[NUM_CARDS];
		initializeHand();
	}
	
	/**
		Method initalizes all cards in hand to null
	*/
	
	private void initializeHand()
	{
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
		Card oldCard = cards[index];
		cards[index] = newCard;
		return oldCard;
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
		int sum = 0;
		for(int i = 0 ; i < NUM_CARDS ; i++)
		{
			if(cards[i].isPowerCard())
			{
				sum++;
			}
		}
		return sum;
	}
	
	/**
		Method returns the sum of cards in hand
		@return Sum of cards in hand
	*/
	
	public int getHandValue()
	{
		int sum = 0;
		for(int i = 0 ; i < NUM_CARDS ; i++)
		{
			sum = sum + cards[i].getValue();
		}
		return sum;
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