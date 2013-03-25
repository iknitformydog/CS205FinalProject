public abstract class Player
{
	private String name;
	private Hand hand;
	
	public Player(String name)
	{
		this.name = name;
		hand = new Hand();
	}
	
	/**
		Method returns the player's name
		@return The player's name	
	*/

	public String getName()
	{
		return name;
	}
	
	/**
		Method swaps a card in a player's hand at a specified index
		@param newCard The card to be added
		@param index The index to make the swap
		@return The card removed at the specified index
	*/
	
	public Card swapCard(Card newCard, int index)
	{
		return hand.swapCard(newCard, index);
	}
	
	/**
		Method returns a player's hand
		@return The player's hand
	*/
	
	public Hand getHand()
	{
		return hand;
	}
	
	/**
		Method makes a player's move
		@param deck The deck
		@param choice The pile (draw pile or discard pile) to take a card from
		@param move Integer representing the player's move (where to place drawn card)
	*/
	
	public void makeMove(Deck deck, int choice, int move)
	{
		Card newCard;
		Card oldCard;
		if(choice == Game.DRAW_PILE)
		{
			newCard = deck.removeTopDraw();
		}
		else
		{
			newCard = deck.removeTopDiscard();
		}
		if(move == Game.PLACE_IN_DISCARD)
		{
			deck.addToDiscard(newCard);
		}
		else
		{
			oldCard = swapCard(newCard, move);
			deck.addToDiscard(oldCard);
		}
	}
	
	/**
		Method swaps a card in a player's hand at a specified index with a card in its opponent's hand
		at a specified index
		@param opponent The player's opponent
		@param opponentIndex Opponent's card number (index) to take a card from
		@param playerIndex The player's card number (index) to swap with its opponent's card
	*/
	
	public void swapWithOpponent(Player opponent, int opponentIndex, int playerIndex)
	{
		Card card = hand.getCard(playerIndex);
		hand.swapCard((opponent.hand).getCard(opponentIndex), playerIndex);
		(opponent.hand).swapCard(card, opponentIndex);
	}
	
	/**
		Method returns a card in a player's hand at a specified index
		@param index The index to return a card from
		@return The card at the specified index
	*/
	
	public Card getCard(int index)
	{
		return hand.getCard(index);
	}
	
	/**
		Method returns the number of power cards in a player's hand
		@return The number of power cards in a player's hand
	*/
	
	public int getNumPowerCards()
	{
		return hand.getNumPowerCards();
	}
	
	/**
		Method replaces all power cards in a player's hand with numerical cards
		@param deck The deck
	*/
	
	public void replacePowerCards(Deck deck)
	{
		Card card;
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			card = hand.getCard(i);
			while(card.isPowerCard())
			{
				makeMove(deck, Game.DRAW_PILE, i);
				card = hand.getCard(i);
			} 
		}
	}
	
	/**
		Method returns the sum of the cards in a player's hand
		@return The sum of the cards in a player's hand
	*/
	
	public int getFinalHandValue()
	{
		return hand.getHandValue();
	}
}