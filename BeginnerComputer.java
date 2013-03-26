public class BeginnerComputer extends Computer
{
	private final static double DECK_EXPECTED_VALUE = 5.0;  //fixed expected value of the deck
	
	private final static double PROB_OF_RAT_A_TAT_CAT = 0.1;  //fixed probability that computer will declare Rat-A-Tat-Cat
	
	private final static int NUM_TURNS_BEFORE_RAT_A_TAT_CAT = 3;  //number of turns before comptuer will consider
	//declaring Rat-A-Tat-Cat
	
	private final static double PROB_OF_FORGET_CARD = 0.5;  //probabilty that the computer will forget a card previously
	//memorized in its hand (card's value will return to null)
	
	private int turn;  //computer's turn number
	
	/**
		Constructor creates an instance of BeginnerComputer
	*/
	
	public BeginnerComputer()
	{
		super();  //call Computer superclass
		turn = 1;  //initialize turn number to 1
	}
	
	/**
		Method randomly removes a card from memory at the determined probablity
		of the constant PROB_OF_FORGET_CARD
	*/
	
	private void forgetCards()
	{
		Card[] computerHand = getHandMemory();  //computer's hand memory
		
		//for every card in the computer's hand memory, there is fixed probability that the card's value will return to null
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			if(Math.random() < PROB_OF_FORGET_CARD)
			{
				computerHand[i] = null;
			}
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
		//if the human did not declare Rat-A-Tat-Cat on previous turn and there have been a certain number of turns,
		//the computer will randomly consider declaring Rat-A-Tat-Cat
		
		if(!finalTurn && turn > NUM_TURNS_BEFORE_RAT_A_TAT_CAT)
		{
			if(Math.random() < PROB_OF_RAT_A_TAT_CAT)
			{
				return Game.RAT_A_TAT_CAT;
			}
		}
		
		forgetCards();  //randomly forget cards in its hand memory
		
		return getPile(deck, DECK_EXPECTED_VALUE);  //return the pile (draw or discard) that the computer would like to take a card from
	}
	
	/**
		Method returns the computer's move (where to place the drawn card)
		@param deck The deck
		@param choice The computer's chosen pile to take a card from (Draw Pile or Discard Pile)
		@return An integer representing the computer's chosen move
	*/
	
	public int chooseMove(Deck deck, int choice)
	{
		turn++;  //increase turn counter by 1
		return getMove(deck, choice, DECK_EXPECTED_VALUE);  //return the computer's move (as an integer)
	}
	
	/**
		Method updates the computer's memory after the human makes a swap
		@param computerIndex The index of the card in computer's hand that swap occurred
		@param humanIndex The index of the card in the human's hand that swap occurred
	*/
	
	public void recordHumanSwap(int computerIndex, int humanIndex)
	{
		setCompMemory(computerIndex, null);  //update computer's hand memory after human swaps with 1 of its cards
	}
	
	/**
		Method simulates when the computer picks a draw 2 card
		@param deck The deck
		@param opponent The computer's opponent
		@return DrawTwo object which keeps track of computer's moves during Draw 2 turn
	*/
	
	public DrawTwo draw2(Deck deck, Player opponent)
	{
		return super.draw2(deck, opponent);  //call superclass's draw 2 method and return DrawTwo object keeping track of what occurred during turn
	}
	
	/**
		Method swaps a card in the computer's hand with a card in the human's hand
		@param The computer's opponent (human)
		@return Swap object detailing what occurred during swap
	*/
	
	public Swap swap(Player opponent)
	{
		int computerCard = getHighestValuedCard(DECK_EXPECTED_VALUE);  //index of the highest valued card in computer's hand
		int opponentCard = (int)(Math.random() * Hand.NUM_CARDS);  //index of a random card in human's hand
		
		swapWithOpponent(opponent, opponentCard, computerCard);  //swap computer's card with human's card
		setCompMemory(computerCard, null);  //update computer's hand memory
		
		return new Swap(computerCard, opponentCard);  //return Swap object keeping track of what occurred during swap
	}
	
	/**
		Method enables computer to peek at an unknown card
		@return index of the card that the computer peeked at
		@return -1 If the computer did not peek at any cards
	*/
	
	public int peek()
	{
		return super.peek();  //return index of card computer would like to peek at or -1 if computer did peek at a card
	}
	
	/**
		Method returns a boolean value indicating whether the computer would like to use a swap card
		if it is the first card drawn after picking a draw 2 card
		@return true If the computer would like to use the swap card
		@return false If the computer would not like to use the swap card
	*/
	
	public boolean useSwap()
	{
		//if the highest valued card in the computer's hand is greater than or equal to the deck's expected value,
		//use the Swap card
		
		if(getHighestValuedCard(DECK_EXPECTED_VALUE) >= DECK_EXPECTED_VALUE)
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
		//use peek card if the computer does not know the value of card in its hand
		
		return super.usePeek();
	}

}