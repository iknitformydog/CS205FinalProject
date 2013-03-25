public class Move
{
	private Card oldCard;
	private Card newCard;
	private DrawTwo turn;
	private Swap swap;
	private int peek;
	private int move;
	
	/**
		Constructor for when the computer picks a card and discards it
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
	*/
	
	public Move(int move, Card newCard)
	{
		oldCard = null;
		this.newCard = newCard;
		turn = null;
		swap = null;
		peek = -1;
		this.move = move;
	}
	
	/**
		Constructor for when the computer picks another draw 2 card
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param turn DrawTwo object keeping track of what occurred during the draw 2
	*/
	
	public Move(int move, Card newCard, DrawTwo turn)
	{
		oldCard = null;
		this.newCard = newCard;
		this.turn = turn;
		swap = null;
		peek = -1;
		this.move = move;
	}
	
	/**
		Constructor for when the computer picks a swap card during a draw 2
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param Swap object keeping track of what occurred during swap
	*/
	
	public Move(int move, Card newCard, Swap swap)
	{
		oldCard = null;
		this.newCard = newCard;
		turn = null;
		this.swap = swap;
		peek = -1;
		this.move = move;
	}
	
	/**
		Constructor for when the computer picks a peek card during a draw 2
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param peek The index of the card that the computer chose to peek at
	*/
	
	public Move(int move, Card newCard, int peek)
	{
		oldCard = null;
		this.newCard = newCard;
		turn = null;
		swap = null;
		this.peek = peek;
		this.move = move;
	}
	
	/**
		Constructor for when the computer picks a card and swaps it with a card in its hand
		@param move The computer's move (as an integer)
		@param oldCard The card in the computer's hand that it discarded
		@param newCard The card that the computer picked and added to its hand
	*/
	
	public Move(int move, Card oldCard, Card newCard)
	{
		this.oldCard = oldCard;
		this.newCard = newCard;
		turn = null;
		swap = null;
		peek = -1;
		this.move = move;
	}
	
	/**
		Method returns the card in the computer's hand that it discarded
		@return The card in the computer's hand that it discarded
		@return null if the computer did not discard a card in its hand
	*/
	
	public Card getOldCard()
	{
		return oldCard;
	}
	
	/**
		Method returns the card that the computer picked
		@return The card that the computer picked
	*/
	
	public Card getNewCard()
	{
		return newCard;
	}
	
	/**
		Method returns a DrawTwo object keeping track of what occurred during that draw 2 turn
		@return DrawTwo object keeping track of what occurred during that draw 2 turn
		@return null if the computer did not pick a draw 2 card during this move
	*/
	
	public DrawTwo getDrawTwo()
	{
		return turn;
	}
	
	/**
		Method returns a Swap object keeping track of what occurred during swap
		@return Swap object keeping track of what occurred during swap
		@return null if the computer did not use a swap card during this move
	*/
	
	public Swap getSwap()
	{
		return swap;
	}
	
	/**
		Method returns the index of the card in its hand that the computer peeked at
		@return The index of the card that the computer peeked at
		@return -1 if the computer did not use a peek card during this move
	*/
	
	public int getPeek()
	{
		return peek;
	}
	
	/**
		Method returns the computer's move (as an integer)
		@return The computer's move (as an integer)
	*/
	
	public int getMove()
	{
		return move;
	}
	
}