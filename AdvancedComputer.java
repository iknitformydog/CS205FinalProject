import java.util.*;

public class AdvancedComputer extends Computer
{
	private final static double RAT_A_TAT_CAT_PERCENT = 0.2;  //the minimum percent lower than the human
	//expected hand value that the computer expected hand value has to be before the computer declares RAT-A-TAT-CAT
	
	private final static int NUM_VALUED_CARDS = 10;  //number of card ranks in the deck that have a numerical value (Ex. 0-9)
	//includes Cat and Rat Cards (not power cards)
	
	private final static int[] NUM_CARDS = {4, 4, 4, 4, 4, 4, 4, 4, 4, 9};  //quantity of cards in deck at each numerical rank
	//index of array represents the card value and the value at each index represents the number of cards with that value
	
	private int[] memorizedDeck;  //an array of size NUM_VALUED_CARDS that holds that number of valued cards (Ex. 0-9) in the
	//deck that that have not been flagged (seen by the computer and recorded)
	
	private Card[] humanHand; 	//an array holding the cards that the human has in his/her hand
	//if the computer does not know the card at an index, the card in represented as null
	
	/**
		Constructor creates an instance of AdvancedComputer
	*/
	
	public AdvancedComputer()
	{
		super();  //call to Computer superclass
		
		memorizedDeck = new int[NUM_VALUED_CARDS];  //initialize memorizedDeck array to size NUM_VALUED_CARDS
		loadMemorizedDeck();  //inititalize memorizedDeck to same values as NUM_CARDS (start values before cards are flagged)
		
		humanHand = new Card[Hand.NUM_CARDS];  //initialize humanHand array
		initializeHand(humanHand);  //initalize human hand memory to null for every card value
	}
	
	/**
		Method loads the memorized deck array (representing the number of cards at each value remaining in the deck)
		At start of game, computer has not yet flagged cards, so all valued cards will be represented
	*/
	
	private void loadMemorizedDeck()
	{
		//for each card value of memorizedDeck, assign it the total number of cards of that rank in deck at start of game
		
		for(int i = 0 ; i < NUM_VALUED_CARDS ; i++)
		{
			memorizedDeck[i] = NUM_CARDS[i];
		}
	}
		
	/**
		Method re-creates deck memory after the draw pile ran out of cards and needed to be re-created
		@param deck The deck
	*/
	
	private void reCreateMemorizedDeck(Deck deck)
	{
		loadMemorizedDeck();  //re-initialize memorizedDeck to start values (assume no cards are flagged)
		
		ArrayList<Card> cards = deck.getAllCards();  //temporarily holds all cards in deck
		
		Hand compHand = getHand();  //the computer's hand
		
		Card card;  //card object to temporarily hold a returned card
		
		//for each card in the computer's hand, add it to the ArrayList holding all cards in deck	
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			card = compHand.getCard(i);
			cards.add(card);
		}
		
		//for each card in the human's hand that the computer memorized, add it to the ArrayList holding all cards
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			card = humanHand[i];
			
			//if computer does not know one of human's cards (card = null), do not add to ArrayList of all cards
			
			if(card != null)
			{
				cards.add(card);
			}
		}
		
		int index;  //temporarily holds the index of memorizedDeck (same as card value) to decrease number of cards by 1
		
		//for every card in ArrayList, check to see if it is flagged, and if flagged, record flag in memorizedDeck
		//only cards that may be flagged will be cards that were in a player's hand after deck ran out of cards and was re-created
				
		for(int i = 0 ; i < cards.size() ; i++)
		{
			card = cards.get(i);
			if(card.isFlagged())
			{
				index = card.getValue();
				memorizedDeck[index] = memorizedDeck[index] - 1;
			}
		}
	}
	
	/**
		Method records the value of its two outer cards and flags them
	*/
	
	public void checkOuterCards()
	{
		super.checkOuterCards();  //call superclass's method, which records cards in computer's hand memory
		
		Card card1 = getCardFromMemory(Game.CARD_1);  //get first memorized outer card
		recordCard(card1);  //flag the card
		
		Card card2 = getCardFromMemory(Game.CARD_4);  //get second memorized outer card
		recordCard(card2);  //flag the card
		
	}
	
	/**
		Method returns the calculated expected value of the deck
		@return The calculated expected value of the deck
	*/
	
	private double getExpectedValue()
	{
		int sum = 0;  //sum of all cards which are not flagged
		int numCards = 0;  //number of cards which are not flagged
		
		//for every card value (0-9), calculate sum of unflagged cards and number of unflagged cards
		
		for (int i = 0 ; i < NUM_VALUED_CARDS ; i++)
		{
			sum = sum + (memorizedDeck[i] * i);
			numCards = numCards + memorizedDeck[i];
		}
		
		return ((double)(sum)) / (numCards);  //return the average value of an unflagged card (expected value)
	}
	
	/**
		Method flags a card and removes the card from its deck memory
		@param card The card to flag
	*/
	
	public void recordCard(Card card)
	{
		//if card seen by computer is not a power card and is not already flagged, flag the card
		
		if(!card.isPowerCard() && !card.isFlagged())
		{
			card.flagCard();  //flag the card
			int index = card.getValue();  //get the value of flagged card
			memorizedDeck[index] = memorizedDeck[index] - 1;  //decrease number of unflagged cards of that value by 1
		}
	}
	
	/**
		Method chooses a pile (Draw Pile or Discard Pile) to take a card from or declares RAT-A-TAT-CAT
		@param deck The deck
		@param finalTurn A boolean value indicating whether the human declared RAT-A-TAT-CAT on his/her
		previous turn
		@return An integer representing the pile that the computer would like to take a card from or 
		the computer's decision to declare RAT-A-TAT-CAT
	*/
	
	public int choosePile(Deck deck, boolean finalTurn)
	{
		//if the deck has been re-created during last turn, update flagged card memory
		
		if(deck.isNewDeck())
		{
			reCreateMemorizedDeck(deck);
		}
				
		Card discard = deck.getTopDiscard();  //card currently in the discard pile
		recordCard(discard);  //flag the card (since it is visible)
		
		//if the human did not declare Rat-A-Tat-Cat on last turn and computer wants to declare Rat-A-Tat-Cat,
		//then declare Rat-A-Tat-Cat (return integer constant representing Rat-A-Tat-Cat)
		//otherwise, return the pile (draw pile or discard pile) that computer would like to take a card from
		
		if(!finalTurn && declareRatATatCat())
		{
			return Game.RAT_A_TAT_CAT;
		}
		else
		{
			return getPile(deck, getExpectedValue());
		}
	}
	
	/**
		Method returns the computer's move (where to place the drawn card)
		@param deck The deck
		@param choice The computer's chosen pile to take a card from (Draw Pile or Discard Pile)
		@return An integer representing the computer's chosen move
	*/
	
	public int chooseMove(Deck deck, int choice)
	{
		//if computer chose draw pile, take the top card on draw pile and flag it
		
		if(choice == Game.DRAW_PILE)
		{
			Card draw = deck.getTopDraw();
			recordCard(draw);
		}

		return getMove(deck, choice, getExpectedValue());  //return integer representing the computer's move
	}
	
	/**
		Method updates the computer's memory after the human makes a swap
		@param computerIndex The index of the card in computer's hand that swap occurred
		@param humanIndex The index of the card in the human's hand that swap occurred
	*/
	
	public void recordHumanSwap(int computerIndex, int humanIndex)
	{
		Card humanCard = humanHand[humanIndex];  //the card computer memorized in human's hand (or null if card is unknown)
		Card computerCard = setCompMemory(computerIndex, humanCard);  //add human card to computer's card memory and return card 
		//at that index in computer's hand
		humanHand[humanIndex] = computerCard;  //add computer's swapped card to human's hand
	}
	
	/**
		Method records human move if computer knows the card chosen
		@param index The index that the human placed the new card
		@param card The card that the human placed at this index
	*/
	
	public void recordHumanMove(int index, Card card)
	{
		humanHand[index] = card;  //records the card that human added to its hand at specified index
	}
	
	/**
		Method simulates when the computer picks a draw 2 card
		@param deck The deck
		@param opponent The computer's opponent
		@return DrawTwo object which keeps track of computer's moves during Draw 2 turn
	*/
	
	public DrawTwo draw2(Deck deck, Player opponent)
	{
		return super.draw2(deck, opponent);  //call superclass's draw2 method
	}
	
	/**
		Method swaps a card in the computer's hand with a card in the human's hand
		@param The computer's opponent (human)
		@return Swap object detailing what occurred during swap
	*/
	
	public Swap swap(Player opponent)
	{
		int computerCard = getHighestValuedCard(getExpectedValue()); //index of computer card with the highest value
		int opponentCard = getLowestValuedCard(humanHand, getHumanExpectedValue());  //index of human card with lowest value
		
		//if the highest valued card in computer's hand is greater than lowest valued card in human's hand, swap the cards
		
		if(getCardValue(computerCard, getExpectedValue()) > getCardValue(humanHand, opponentCard, getHumanExpectedValue()))
		{
			swapWithOpponent(opponent, opponentCard, computerCard);  //swap cards with human
			
			Card card = setCompMemory(computerCard, humanHand[opponentCard]);  //record card taken from human and return card given to human
			humanHand[opponentCard] = card;  //store old card in memorized human hand
			
			return new Swap(computerCard, opponentCard);  //return what happened during swap as Swap object
		}
		
		//if computer decides not to swap, return Swap object with indicies as -1
		
		else
		{
			return new Swap(-1, -1);
		}
	}
	
	/**
		Method enables computer to peek at an unknown card
		@return index of the card that the computer peeked at
		@return -1 If the computer did not peek at any cards
	*/
	
	public int peek()
	{
		int index = super.peek();  //index of card that computer would like to peek at
		
		//if the computer would like to peek, flag the card peeked at
		
		if(index != -1)
		{
			recordCard(getCardFromMemory(index));
		}
		
		return index;  //return index of card peeked at or -1 if computer did not peek at any cards
	}
	
	/**
		Method returns a boolean value representing whether to declare RAT-A-TAT-CAT
		@return true If the computer should declare RAT-A-TAT-CAT
		@return false If the computer should not declare RAT-A-TAT-CAT
	*/
	
	private boolean declareRatATatCat()
	{
		double computerHandValue = getHandValue(getExpectedValue());  //expected value of computer's hand
		double humanHandValue = getHandValue(humanHand, getHumanExpectedValue());  //expected value of human's hand
		
		//if expected value of computer's hand is RAT_A_TAT_CAT_PECENT lower than expected value of human's hand,
		//then declare RAT-A-TAT-CAT
		
		if(humanHandValue * (1 - RAT_A_TAT_CAT_PERCENT) > computerHandValue)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
		Method returns the expected value of an unknown card in the human's hand
		The human expected value with equal half the deck's expected value
		@return The expected value of an unknown card in the human's hand
	*/
	
	private double getHumanExpectedValue()
	{
		return (getExpectedValue() / 2);  //expected value of card in human's hand is half expected value of the deck
	}
	
	/**
		Method returns a boolean value indicating whether the computer would like to use a swap card
		if it is the first card drawn after picking a draw 2 card
		@return true If the computer would like to use the swap card
		@return false If the computer would not like to use the swap card
	*/
	
	public boolean useSwap()
	{
		int computerCard = getHighestValuedCard(getExpectedValue());  //index of highest valued card in computer's hand
		int opponentCard = getLowestValuedCard(humanHand, getExpectedValue());  //index of lowest valued card in human's hand
		
		//if the highest valued card in computer's hand is higher than the lowest valued card in computer's hand, use Swap card
		
		if(getCardValue(computerCard, getExpectedValue()) > getCardValue(humanHand, opponentCard, getExpectedValue()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
		Method returns a boolean value indicating whether tha computer would like to use a peek card
		if it the first card drawn after picking a draw 2 card
		@return true If the computer would like to use the peek card
		@return false If the computer would not like to use the peek card
	*/
	
	public boolean usePeek()
	{
		return super.usePeek();  //use peek card is computer does not know value of every card in its hand
	}
}