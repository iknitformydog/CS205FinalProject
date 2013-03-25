import java.util.*;

public class AdvancedComputer extends Computer
{
	private final static double RAT_A_TAT_CAT_PERCENT = 0.2;
	private final static int NUM_VALUED_CARDS = 10;
	private final static int[] NUM_CARDS = {4, 4, 4, 4, 4, 4, 4, 4, 4, 9};
	
	private int[] memorizedDeck;
	private Card[] humanHand;
	
	public AdvancedComputer()
	{
		super();
		memorizedDeck = new int[NUM_VALUED_CARDS];
		loadMemorizedDeck();
		humanHand = new Card[Hand.NUM_CARDS];
		initializeHand(humanHand);
	}
	
	/**
		Method loads the memorized deck array (representing the number of
		cards at each value remaining in the deck
	*/
	
	private void loadMemorizedDeck()
	{
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
		loadMemorizedDeck();
		ArrayList<Card> cards = deck.getAllCards();
		Hand compHand = getHand();
		Card card;
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			card = compHand.getCard(i);
			cards.add(card);
		}
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			card = humanHand[i];
			if(card != null)
			{
				cards.add(card);
			}
		}
		int index;
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
		super.checkOuterCards();
		Card card1 = getCardFromMemory(Game.CARD_1);
		recordCard(card1);
		Card card2 = getCardFromMemory(Game.CARD_4);
		recordCard(card2);
		
	}
	
	/**
		Method returns the calculated expected value of the deck
		@return The calculated expected value of the deck
	*/
	
	private double getExpectedValue()
	{
		int sum = 0;
		int numCards = 0;
		for (int i = 0 ; i < NUM_VALUED_CARDS ; i++)
		{
			sum = sum + (memorizedDeck[i] * i);
			numCards = numCards + memorizedDeck[i];
		}
		return ((double)(sum)) / (numCards);
	}
	
	/**
		Method flags a card and removes the card from its deck memory
		@param card The card to flag
	*/
	
	public void recordCard(Card card)
	{
		if(!card.isPowerCard() && !card.isFlagged())
		{
			card.flagCard();
			int index = card.getValue();
			memorizedDeck[index] = memorizedDeck[index] - 1;
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
		if(deck.isNewDeck())
		{
			reCreateMemorizedDeck(deck);
		}
				
		Card discard = deck.getTopDiscard();
		recordCard(discard);
		
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
		if(choice == Game.DRAW_PILE)
		{
			Card draw = deck.getTopDraw();
			recordCard(draw);
		}

		return getMove(deck, choice, getExpectedValue());
	}
	
	/**
		Method updates the computer's memory after the human makes a swap
		@param computerIndex The index of the card in computer's hand that swap occurred
		@param humanIndex The index of the card in the human's hand that swap occurred
	*/
	
	public void recordHumanSwap(int computerIndex, int humanIndex)
	{
		Card humanCard = humanHand[humanIndex];
		Card computerCard = setCompMemory(computerIndex, humanCard);
		humanHand[humanIndex] = computerCard;
	}
	
	/**
		Method records human move if computer knows the card chosen
		@param index The index that the human placed the new card
		@param card The card that the human placed at this index
	*/
	
	public void recordHumanMove(int index, Card card)
	{
		humanHand[index] = card;
	}
	
	/**
		Method simulates when the computer picks a draw 2 card
		@param deck The deck
		@param opponent The computer's opponent
		@return DrawTwo object which keeps track of computer's moves during Draw 2 turn
	*/
	
	public DrawTwo draw2(Deck deck, Player opponent)
	{
		return super.draw2(deck, opponent);
	}
	
	/**
		Method swaps a card in the computer's hand with a card in the human's hand
		@param The computer's opponent (human)
		@return Swap object detailing what occurred during swap
	*/
	
	public Swap swap(Player opponent)
	{
		int computerCard = getHighestValuedCard(getExpectedValue());
		int opponentCard = getLowestValuedCard(humanHand, getHumanExpectedValue());
		if(getCardValue(computerCard, getExpectedValue()) > getCardValue(humanHand, opponentCard, getHumanExpectedValue()))
		{
			swapWithOpponent(opponent, opponentCard, computerCard);
			Card card = setCompMemory(computerCard, humanHand[opponentCard]);
			humanHand[opponentCard] = card;
			return new Swap(computerCard, opponentCard);
		}
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
		int index = super.peek();
		if(index != -1)
		{
			recordCard(getCardFromMemory(index));
		}
		return index;
	}
	
	/**
		Method returns a boolean value representing whether to declare RAT-A-TAT-CAT
		@return true If the computer should declare RAT-A-TAT-CAT
		@return false If the computer should not declare RAT-A-TAT-CAT
	*/
	
	private boolean declareRatATatCat()
	{
		double computerHandValue = getHandValue(getExpectedValue());
		double humanHandValue = getHandValue(humanHand, getHumanExpectedValue());
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
		return (getExpectedValue() / 2);
	}
	
	/**
		Method returns a boolean value indicating whether the computer would like to use a swap card
		if it is the first card drawn after picking a draw 2 card
		@return true If the computer would like to use the swap card
		@return false If the computer would not like to use the swap card
	*/
	
	public boolean useSwap()
	{
		int computerCard = getHighestValuedCard(getExpectedValue());
		int opponentCard = getLowestValuedCard(humanHand, getExpectedValue());
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
		return super.usePeek();
	}
}