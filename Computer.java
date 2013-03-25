import java.util.*;

public abstract class Computer extends Player
{
	public final static String COMPUTER_NAME = "Computer";
	
	private Card[] computerHand;
	
	public Computer()
	{
		super(COMPUTER_NAME);
		computerHand = new Card[Hand.NUM_CARDS];
		initializeHand(computerHand);
	}
	
	/**
		Method initializes the cards in specified array with null
		@param cards The array of cards to be initalized with null
	*/
	
	public static void initializeHand(Card[] cards)
	{
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			cards[i] = null;
		}
	}
	
	/**
		Method checks the outer cards in computer's hand and stores them in memory
	*/
	
	public void checkOuterCards()
	{
		computerHand[Game.CARD_1] = (getHand()).getCard(Game.CARD_1);
		computerHand[Game.CARD_4] = (getHand()).getCard(Game.CARD_4);
	}
	
	/**
		Method returns a boolean value indicating whether all cards in computer's hand memory
		are below a specified value
		@param value The specified value
		@return true if all cards in computer's hand memory are below the specified value
		@return false if not all cards in computer's hand memory are below the specified value
	*/
	
	public boolean allCardsBelowValue(int value)
	{
		boolean allBelow = true;
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			if(computerHand[i] == null || computerHand[i].getValue() > value)
			{
				allBelow = false;
			}
		}
		return allBelow;
	}
	
	/**
		Method places a card in computer's hand memory at specified index
		@param Index index to place card
		@param newCard The card to place at index
		@return The card removed from the index
	*/
	
	public Card setCompMemory(int index, Card newCard)
	{
		Card oldCard = computerHand[index];
		computerHand[index] = newCard;
		return oldCard;
	}
	
	/**
		Method returns a card at a specified index from computer's hand memory
		@param index Index to return a card from
		@return The card at the specified index 
	*/
	
	public Card getCardFromMemory(int index)
	{
		return computerHand[index];
	}
	
	/**
		Method returns the sum of all the cards in the computer's hand memory
		@param expectedValue The expected value of the deck
		@return The sum of all the cards in the computer's hand memory
	*/
	
	public double getHandValue(double expectedValue)
	{
		return getHandValue(computerHand, expectedValue);
	}
	
	/**
		Method returns the sum of all the cards in the specified card array
		@param cards The specified card array
		@param expectedValue The expected value of the deck
		@return The sum of all the cards in the specified card array
	*/
	
	public static double getHandValue(Card[] cards, double expectedValue)
	{
		double handValue = 0.0;
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			handValue = handValue + getCardValue(cards, i, expectedValue);
		}
		return handValue;
	}
	
	/**
		Method returns the card value of the card in the computer's hand memory at the specified index
		@param index The index to return the card value
		@param expectedValue The expected value of the deck
		@return The card value at the specified index in the computer's hand memory
	*/
	
	public double getCardValue(int index, double expectedValue)
	{
		return getCardValue(computerHand, index, expectedValue);
	}
	
	/**
		Method returns the card value of the card in the specified card array at the specified index
		@param cards The specified card array
		@param index The specified index
		@param expectedValue The expected value of the deck
		@return The card value at the specified index in the specified card array
	*/
	
	public static double getCardValue(Card[] cards, int index, double expectedValue)
	{
		if(cards[index] == null || cards[index].isPowerCard())
		{
			return expectedValue;
		}
		else
		{
			return ((double)(cards[index].getValue()));
		}
	}
	
	/**
		Method returns the index of the highest valued card in the computer's hand memory
		@param expectedValue The expected value of the deck
		@return The index of the highest valued card in the computer's hand memory
		If two or more cards are tied for highest value, randomly choose a card
	*/

	public int getHighestValuedCard(double expectedValue)
	{
		return getHighestValuedCard(computerHand, expectedValue);
	}
	
	/**
		Method returns the index of the highest valued card in the specified card array
		@param cards The specified card array
		@param expectedValue The expected value of the deck
		@return The index of the highest valued card in the specified card array
		If two or more cards are tied for highest value, randomly choose a card
	*/
	
	public static int getHighestValuedCard(Card[] cards, double expectedValue)
	{
		double highValue = 0.0;
		double curValue = 0.0;
		highValue = getCardValue(cards, 0, expectedValue);
			
		for(int i = 1 ; i < Hand.NUM_CARDS ; i++)
		{
			curValue = getCardValue(cards, i, expectedValue);
			if(curValue > highValue)
			{
				highValue = curValue;
			}
		}
		
		ArrayList<Integer> highestCards = new ArrayList<Integer>();
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			curValue = getCardValue(cards, i, expectedValue);
			if(Math.abs(curValue - highValue) < 0.001)
			{
				highestCards.add(i);
			}
		}
		
		int highIndex = ((int)(Math.random() * highestCards.size()));
		return highestCards.get(highIndex);
	}
	
	/**
		Method returns the index of the lowest valued card in the computer's hand memory
		@param expectedValue The expected value of the deck
		@return The index of the lowest valued card in the computer's hand memory
		If two or more cards are tied for lowest value, randomly choose a card
	*/
	
	public int getLowestValuedCard(double expectedValue)
	{
		return getLowestValuedCard(computerHand, expectedValue);
	}
	
	/**
		Method returns the index of the lowest valued card in the specified card array
		@param cards The specified card array
		@param expectedValue The expected value of the deck
		@return The index of the lowest valued card in the specified card array
		If two or more cards are tied for lowest value, randomly choose a card
	*/
	
	public static int getLowestValuedCard(Card[] cards, double expectedValue)
	{
		double lowValue = 0.0;
		double curValue = 0.0;
		lowValue = getCardValue(cards, 0, expectedValue);
			
		for(int i = 1 ; i < Hand.NUM_CARDS ; i++)
		{
			curValue = getCardValue(cards, i, expectedValue);
			if(curValue < lowValue)
			{
				lowValue = curValue;
			}
		}
		
		ArrayList<Integer> lowestCards = new ArrayList<Integer>();
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			curValue = getCardValue(cards, i, expectedValue);
			if(Math.abs(curValue - lowValue) < 0.001)
			{
				lowestCards.add(i);
			}
		}
		
		int lowIndex = ((int)(Math.random() * lowestCards.size()));
		return lowestCards.get(lowIndex);
	}
	
	/**
		Method randomly chooses an unknown card in the computer's hand memory
		@return The index of the randomly chosen card
	*/
	
	public int randomlyChooseUnknownCard()
	{
		ArrayList<Integer> unknownCards = new ArrayList<Integer>();
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			if(computerHand[i] == null)
			{
				unknownCards.add(i);
			}
		}
		if(unknownCards.size() == 0)
		{
			return -1;
		}
		else
		{
			int randomIndex = unknownCards.get((int)(Math.random() * unknownCards.size()));
			return randomIndex;
		}
	}
	
	/**
		Method returns the chosen pile (Draw Pile or Discard Pile) that the computer would like
		to take a card from
		@param deck The deck
		@param expectedValue The expected value of the deck
		@return An integer representing the computer's chosen pile
	*/
	
	public int getPile(Deck deck, double expectedValue)
	{
		Card discardCard = deck.getTopDiscard();
		if(discardCard.isPowerCard())
		{
			return Game.DRAW_PILE;
		}
		else if(allCardsBelowValue(discardCard.getValue()))
		{
			return Game.DRAW_PILE;
		}
		else if(((double)(discardCard.getValue())) < expectedValue)
		{
			return Game.DISCARD_PILE;
		}
		else
		{
			return Game.DRAW_PILE;
		}
	}
	
	/**
		Method returns the computer's move (where to place the drawn card)
		@param deck The deck
		@param choice The computer's chosen pile to take a card from (Draw Pile or Discard Pile)
		@param expectedValue The expected value of the deck
		@return An integer representing the computer's chosen move
	*/
	
	public int getMove(Deck deck, int choice, double expectedValue)
	{
		Card card;
		if(choice == Game.DRAW_PILE)
		{
			card = deck.removeTopDraw();
		}
		else
		{
			card = deck.removeTopDiscard();
		}
		if(card.isPowerCard())
		{
			if((card.getType()).equals(Card.PICK_2))
			{
				deck.addToDiscard(card);
				return Game.DRAW_2_CARD;		
			}
			else if((card.getType()).equals(Card.SWAP))
			{
				deck.addToDiscard(card);
				return Game.SWAP_CARD;
			}
			else
			{
				deck.addToDiscard(card);
				return Game.PEEK_CARD;
			}
		}
		else
		{
			int move = getHighestValuedCard(expectedValue);
			double highCardValue = getCardValue(computerHand, move, expectedValue);
			if(((double)(card.getValue())) > highCardValue)
			{
				move = Game.PLACE_IN_DISCARD;
			}
			if(move != Game.PLACE_IN_DISCARD)
			{
				computerHand[move] = card;
			}
			if(choice == Game.DRAW_PILE)
			{
				deck.addToDraw(card);
			}
			else
			{
				deck.addToDiscard(card);
			}
			return move;
		}
	}
	
	/**
		Method returns the computer's hand memory
		@return The computer's hand memory
	*/
	
	public Card[] getHandMemory()
	{
		return computerHand;
	}
	
	/**
		Method simulates when the computer picks a draw 2 card
		@param deck The deck
		@param opponent The computer's opponent
		@return DrawTwo object which keeps track of computer's moves during Draw 2 turn
	*/
	
	public DrawTwo draw2(Deck deck, Player opponent)
	{
		int move1 = chooseMove(deck, Game.DRAW_PILE);
		boolean drawAgain = false;
		DrawTwo turn;
		if(move1 == Game.PLACE_IN_DISCARD)
		{
			drawAgain = true;
			deck.addToDiscard(deck.removeTopDraw());
			turn = new DrawTwo(move1, deck.getTopDiscard());
		}
		else if(move1 == Game.DRAW_2_CARD)
		{
			DrawTwo drawTwo = draw2(deck, opponent);
			turn = new DrawTwo(move1, deck.getTopDiscard(), drawTwo);
		}
		else if(move1 == Game.SWAP_CARD)
		{
			if(useSwap())
			{
				Swap swap = swap(opponent);
				turn = new DrawTwo(move1, deck.getTopDiscard(), swap);
			}
			else
			{
				turn = new DrawTwo(move1, deck.getTopDiscard());
				drawAgain = true;
			}
		}
		else if(move1 == Game.PEEK_CARD)
		{
			if(usePeek())
			{
				int peek = peek();
				turn = new DrawTwo(move1, deck.getTopDiscard(), peek);
			}
			else
			{
				turn = new DrawTwo(move1, deck.getTopDiscard());
				drawAgain = true;
			}
		}
		else
		{
			turn = new DrawTwo(move1, deck.getTopDraw(), getHand());
			makeMove(deck, Game.DRAW_PILE, move1);
		}
		if(drawAgain)
		{
			int move2 = chooseMove(deck, Game.DRAW_PILE);
			if(move2 == Game.DRAW_2_CARD)
			{
				DrawTwo drawTwo = draw2(deck, opponent);
				turn.recordMove2(move2, deck.getTopDiscard(), drawTwo);
			}
			else if(move2 == Game.SWAP_CARD)
			{
				Swap swap = swap(opponent);
				turn.recordMove2(move2, deck.getTopDiscard(), swap);
			}
			else if(move2 == Game.PEEK_CARD)
			{
				int peek = peek();
				turn.recordMove2(move2, deck.getTopDiscard(), peek);
			}
			else
			{
				turn.recordMove2(move2, deck.getTopDraw(), getHand());
				makeMove(deck, Game.DRAW_PILE, move2);
			}
		}
		return turn;
	}
	
	/**
		Method randomly chooses an unknown card and stores that card's value in its hand memory
		@return The index of the card the computer peeked at
		@return -1 If the computer already knows every card in its hand
	*/
	
	public int peek()
	{
		int cardIndex = randomlyChooseUnknownCard();
		if(cardIndex != -1)
		{
			computerHand[cardIndex] = (getHand()).getCard(cardIndex);
		}
		return cardIndex;
	}
	
	/**
		Method returns a boolean value indicating whether it should use a peek card if it is the
		first card drawn after picking a draw 2 card
		@return true If the computer would like to use the peek card
		@return false If the computer would not like to use the peek card
	*/
	
	public boolean usePeek()
	{
		if(randomlyChooseUnknownCard() != -1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public abstract void recordHumanSwap(int computerIndex, int humanIndex);
	public abstract int choosePile(Deck deck, boolean finalTurn);
	public abstract int chooseMove(Deck deck, int choice);
	public abstract Swap swap(Player opponent);
	public abstract boolean useSwap();
}