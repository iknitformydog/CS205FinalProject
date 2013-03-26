public abstract class Player
{
	private String name;  //The player's name
	private Hand hand;  //The player's hand
	
	/**
		Constructor instantiates a new player
		@param name The player's name
	*/
	
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
		Card newCard;  //card to be added to hand
		Card oldCard;  //card to be removed from hand
		
		//if player takes card from draw pile, newCard will be top card in draw pile
		//if player takes card from discard pile, newCard will be top card in discard pile
		
		if(choice == Game.DRAW_PILE)
		{
			newCard = deck.removeTopDraw();
		}
		else
		{
			newCard = deck.removeTopDiscard();
		}
		
		//if player chooses to place card back in discard pile, place card in discard pile
		//if player would like to swap card with a card in its hand, perform swap and place old card in discard pile
		
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
		Card card = hand.getCard(playerIndex);  //card in player's hand that would like to be swapped
		hand.swapCard((opponent.hand).getCard(opponentIndex), playerIndex);  //place card in opponent's hand in player's hand at specified indicies
		(opponent.hand).swapCard(card, opponentIndex);  //place card in player's hand in opponent's hand at specified indicies
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
		Card card;  //card object to temporarily hold card during transfer
		
		//for every card in player's hand, check to see if card is a power card
		//if it is a power card, swap it with a card in the draw pile
		
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