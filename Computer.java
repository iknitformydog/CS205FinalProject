import java.util.*;

public abstract class Computer extends Player
{
	public final static String COMPUTER_NAME = "Computer";  //The computer's name
	
	private Card[] computerHand;  //array holding the cards in the computer's hand that have been memorized
	//a card is stored as null if the computer does not yet know the card's value
	
	/**
		Constructor creates a new Computer object
	*/
	
	public Computer()
	{
		super(COMPUTER_NAME);  //call Player superclass
		
		computerHand = new Card[Hand.NUM_CARDS];  //initialize the computer's memorized hand
		initializeHand(computerHand);  //initialize all cards in computer's memorized hand to null (has not yet seen of its cards)
	}
	
	/**
		Method initializes the cards in specified array with null
		@param cards The array of cards to be initalized with null
	*/
	
	public static void initializeHand(Card[] cards)
	{
		//for every card in computer's memorized hand array, set card value to null
	
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
		computerHand[Game.CARD_1] = (getHand()).getCard(Game.CARD_1);  //store first outer card in computer's hand memory
		computerHand[Game.CARD_4] = (getHand()).getCard(Game.CARD_4);  //sotre second outer card in computer's hand memory
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
		boolean allBelow = true;  //boolean value indicating whether all cards in computer's memory at below a specified value
		
		//for all cards in the computer's memory, if the card's value if null or greater than the specified value, set
		//the allBelow boolean value to false (not all cards are below the specified value)
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			if(computerHand[i] == null || computerHand[i].getValue() > value)
			{
				allBelow = false;
			}
		}
		
		return allBelow;  //return true if all cards are below specified value or false if all cards are not below specified value
	}
	
	/**
		Method places a card in computer's hand memory at specified index
		@param Index index to place card
		@param newCard The card to place at index
		@return The card removed from the index
	*/
	
	public Card setCompMemory(int index, Card newCard)
	{
		Card oldCard = computerHand[index];  //get card stored in computer's memory at specified index
		computerHand[index] = newCard;  //add new card to computer's memory at specified index
		return oldCard;  //return old card stored in computer's memory at specified index
	}
	
	/**
		Method returns a card at a specified index from computer's hand memory
		@param index Index to return a card from
		@return The card at the specified index 
	*/
	
	public Card getCardFromMemory(int index)
	{
		return computerHand[index];  //return card stored in computer's memory at specified index
	}
	
	/**
		Method returns the sum of all the cards in the computer's hand memory
		@param expectedValue The expected value of the deck
		@return The sum of all the cards in the computer's hand memory
	*/
	
	public double getHandValue(double expectedValue)
	{
		return getHandValue(computerHand, expectedValue);  //return the computer's hand value (memorized hand)
	}
	
	/**
		Method returns the sum of all the cards in the specified card array
		@param cards The specified card array
		@param expectedValue The expected value of the deck
		@return The sum of all the cards in the specified card array
	*/
	
	public static double getHandValue(Card[] cards, double expectedValue)
	{
		double handValue = 0.0;  //the sum of the value of the cards in the computer's memorized hand array
		
		//for every card in the computer's hand memory, add its value to handValue
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			handValue = handValue + getCardValue(cards, i, expectedValue);
		}
		
		return handValue;  //return the sum of the cards in memorized hand array
	}
	
	/**
		Method returns the card value of the card in the computer's hand memory at the specified index
		@param index The index to return the card value
		@param expectedValue The expected value of the deck
		@return The card value at the specified index in the computer's hand memory
	*/
	
	public double getCardValue(int index, double expectedValue)
	{
		return getCardValue(computerHand, index, expectedValue);  //return the value of card in memory at specified index
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
		//if a card in memory is unknown or is a power card, return the expected value of unknown cards
		//otherwise, return the value of the card at specified index
		
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
		return getHighestValuedCard(computerHand, expectedValue);  //return the highest valued card in computer's memorized hand
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
		double highValue = 0.0;  //highest card value found in array
		double curValue = 0.0; //value of current card accessed from array
		
		highValue = getCardValue(cards, 0, expectedValue);  //initialize highValue to value of firstCard in array
		
		//for every card in array, check to see if its value is greater than highValue
		//if it is greater, set highValue to curValue
		
		for(int i = 1 ; i < Hand.NUM_CARDS ; i++)
		{
			curValue = getCardValue(cards, i, expectedValue);
			if(curValue > highValue)
			{
				highValue = curValue;
			}
		}
		
		ArrayList<Integer> highestCards = new ArrayList<Integer>(); //ArrayList containing index of all cards in array tied for highest value
		
		//for every card in array, check to see if its value is equal to highValue
		//if it is equal, add to the highestCards ArrayList
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			curValue = getCardValue(cards, i, expectedValue);
			if(Math.abs(curValue - highValue) < 0.001)
			{
				highestCards.add(i);
			}
		}
		
		int highIndex = ((int)(Math.random() * highestCards.size()));  //random index of card index in highestCards ArrayList
		return highestCards.get(highIndex);  //return a randomly selected index
	}
	
	/**
		Method returns the index of the lowest valued card in the computer's hand memory
		@param expectedValue The expected value of the deck
		@return The index of the lowest valued card in the computer's hand memory
		If two or more cards are tied for lowest value, randomly choose a card
	*/
	
	public int getLowestValuedCard(double expectedValue)
	{
		return getLowestValuedCard(computerHand, expectedValue);  //return index of the lowest valued card in computer's memorized hand
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
		double lowValue = 0.0;  //lowest value of cards in array
		double curValue = 0.0; //current value of card accessed
		
		lowValue = getCardValue(cards, 0, expectedValue); //initialize lowValue to first card in memorized hand
		
		//for every card in array, check to see if value is lower than lowValue
		//if it is lower, lowValue = curValue
			
		for(int i = 1 ; i < Hand.NUM_CARDS ; i++)
		{
			curValue = getCardValue(cards, i, expectedValue);
			if(curValue < lowValue)
			{
				lowValue = curValue;
			}
		}
		
		ArrayList<Integer> lowestCards = new ArrayList<Integer>();  //ArrayList containing index of all cards tied for lowest value
		
		//for every card in array tied for lowest value, add card's index to the ArrayList
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			curValue = getCardValue(cards, i, expectedValue);
			if(Math.abs(curValue - lowValue) < 0.001)
			{
				lowestCards.add(i);
			}
		}
		
		int lowIndex = ((int)(Math.random() * lowestCards.size()));  //randomly select an index of from ArrayList of lowest valued card indicies
		return lowestCards.get(lowIndex);  //return the randomly selected index
	}
	
	/**
		Method randomly chooses an unknown card in the computer's hand memory
		@return The index of the randomly chosen card
	*/
	
	public int randomlyChooseUnknownCard()
	{
		ArrayList<Integer> unknownCards = new ArrayList<Integer>();  //ArrayList containing the index of all cards in memory that are unknown
		
		//for all cards in computer's hand memory, check to see if card's value is null
		//if null, add the card to the ArrayList
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			if(computerHand[i] == null)
			{
				unknownCards.add(i);
			}
		}
		
		//if all cards are known, return -1
		//otherwise return the index of a randomly selcted unknown card
		
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
		Card discardCard = deck.getTopDiscard();  //top card in the discard pile
		
		//if card is a power card, return integer inidicating that computer would like to take a card from the draw pile
		//if card is higher than all cards in computer's hand memory, return integer indicating draw pile
		//if card is lower than the expected value of deck, return integer indicating discard pile
		//if card is greater than or equal to expected value of deck, return integer indicating draw pile
		
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
		Card card;  //card object to hold the card that computer picked
		
		//if computer chose draw pile, remove card from draw pile
		//if computer chose discard pile, remove card from discard pile
		
		if(choice == Game.DRAW_PILE)
		{
			card = deck.removeTopDraw();
		}
		else
		{
			card = deck.removeTopDiscard();
		}
		
		//if drawn card is a power card, return integer representing that computer picked a power card
		//place power card in the discard pile
		
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
		
		//if drawn card
		
		else
		{
			int move = getHighestValuedCard(expectedValue);  //index of highest valued card in computer's memorized hand
			
			double highCardValue = getCardValue(computerHand, move, expectedValue);  //value of highest valued card in hand
			
			//if drawn card is higher than highest card in computer's hand, return integer indicating that the computer would
			//like to place the drawn card in the discard pile 
			
			if(((double)(card.getValue())) > highCardValue)
			{
				move = Game.PLACE_IN_DISCARD;
			}
			
			//if drawn card is lower than or equal to highest valued card, replace drawn card with highest valued card in memorized hand
			
			if(move != Game.PLACE_IN_DISCARD)
			{
				computerHand[move] = card;
			}
			
			//if the computer picked a card from draw pile, place card back in draw pile
			//if the computer picked a card from discard pile, place card back in discard pile
			//cards will be permanently placed in discard pile in makeMove method in Player class
			
			if(choice == Game.DRAW_PILE)
			{
				deck.addToDraw(card);
			}
			else
			{
				deck.addToDiscard(card);
			}
			
			return move;  //return the index of card that the computer would like to swap with drawn card
		}
	}
	
	/**
		Method returns the computer's hand memory
		@return The computer's hand memory
	*/
	
	public Card[] getHandMemory()
	{
		return computerHand;  //return the computer's memorized hand array
	}
	
	/**
		Method simulates when the computer picks a draw 2 card
		@param deck The deck
		@param opponent The computer's opponent
		@return DrawTwo object which keeps track of computer's moves during Draw 2 turn
	*/
	
	public DrawTwo draw2(Deck deck, Player opponent)
	{
		int move1 = chooseMove(deck, Game.DRAW_PILE);  //integer representing computer's first move of draw 2 card turn
		boolean drawAgain = false;  //boolean value indicating whether the computer would like to draw another card
		DrawTwo turn;  //DrawTwo object keeping track of what the computer does during its draw 2 turn
		
		//if the computer choses to place its first drawn card in discard pile:
		//the computer will get to draw another card
		//drawn card will be added to the discard pile
		//A DrawTwo object will be created to keep track of this decision
		
		if(move1 == Game.PLACE_IN_DISCARD)
		{
			drawAgain = true;
			deck.addToDiscard(deck.removeTopDraw());
			turn = new DrawTwo(move1, deck.getTopDiscard());
		}
		
		//if the computer picks another draw 2 card:
		//a DrawTwo object will be created to keep track of what happened during second draw 2 turn
		//computer will not draw again for first draw 2 turn
		//DrawTwo object will be created to keep track of what happened during first draw 2 turn
		
		else if(move1 == Game.DRAW_2_CARD)
		{
			DrawTwo drawTwo = draw2(deck, opponent);
			turn = new DrawTwo(move1, deck.getTopDiscard(), drawTwo);
		}
		
		//if the computer picks a swap card:
		//first determine whether computer would like to use the swap card
		//if the computer would like to use swap card, perform swap and create Swap object keeping track of swap
		//if computer chooses not to use swap card, computer will get to draw another card
		//create a DrawTwo object to keep track of this decision
		
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
		
		//if the computer picks a peek card:
		//first determine whether computer would like to use peek card
		//if the computer would like to use peek card, perform peek and keep track of index of card peeked at
		//if the computer would not like to use the peek card, the computer will get to draw another card
		//create a DrawTwo object to keep track of this decision
		
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
		
		//if the computer would like to use its first draw card (not a power card):
		//create a DrawTwo object to keep track of the computer's move
		//permanently make the move
		//computer will not draw another card
		
		else
		{
			turn = new DrawTwo(move1, deck.getTopDraw(), getHand());
			makeMove(deck, Game.DRAW_PILE, move1);
		}
		
		//if the computer did not use the first drawn card, it will get to draw another card
		
		if(drawAgain)
		{
			int move2 = chooseMove(deck, Game.DRAW_PILE);  //integer representing second move of computer's draw 2 turn
			
			//if computer draws another draw 2 card:
			//create a DrawTwo object ot keep track of computer's second draw 2 turn
			//record the computer's second move to already created DrawTwo object
			
			if(move2 == Game.DRAW_2_CARD)
			{
				DrawTwo drawTwo = draw2(deck, opponent);
				turn.recordMove2(move2, deck.getTopDiscard(), drawTwo);
			}
			
			//if the computer draws a swap card:
			//perform swap operation and record second move to already created DrawTwo object
			
			else if(move2 == Game.SWAP_CARD)
			{
				Swap swap = swap(opponent);
				turn.recordMove2(move2, deck.getTopDiscard(), swap);
			}
			
			//if computer draws a peek card:
			//perform peek operation and record second move to already created DrawTwo object
			
			else if(move2 == Game.PEEK_CARD)
			{
				int peek = peek();
				turn.recordMove2(move2, deck.getTopDiscard(), peek);
			}
			
			//if computer draws a regular valued card:
			//permanently record the computer's second move and record to already created DrawTwo object
			
			else
			{
				turn.recordMove2(move2, deck.getTopDraw(), getHand());
				makeMove(deck, Game.DRAW_PILE, move2);
			}
		}
		
		return turn;  //return DrawTwo object keeping track of what occurred during computer's draw 2 turn
	}
	
	/**
		Method randomly chooses an unknown card and stores that card's value in its hand memory
		@return The index of the card the computer peeked at
		@return -1 If the computer already knows every card in its hand
	*/
	
	public int peek()
	{
		int cardIndex = randomlyChooseUnknownCard();  //index of a random unknown card in its hand
		
		//if the there is an unknown card in memorized hand, peek at the randomly chosen card
		
		if(cardIndex != -1)
		{
			computerHand[cardIndex] = (getHand()).getCard(cardIndex);
		}
		
		return cardIndex;  //return index of card peeked at or -1 if computer already knows value of every card in hand
	}
	
	/**
		Method returns a boolean value indicating whether it should use a peek card if it is the
		first card drawn after picking a draw 2 card
		@return true If the computer would like to use the peek card
		@return false If the computer would not like to use the peek card
	*/
	
	public boolean usePeek()
	{
		//if there is an unknown card in its hand, use peek card
		//if computer knows value of every card in its hand, do not use peek card
		
		if(randomlyChooseUnknownCard() != -1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//abstract methods that each computer level will override
	
	public abstract void recordHumanSwap(int computerIndex, int humanIndex);
	public abstract int choosePile(Deck deck, boolean finalTurn);
	public abstract int chooseMove(Deck deck, int choice);
	public abstract Swap swap(Player opponent);
	public abstract boolean useSwap();
}