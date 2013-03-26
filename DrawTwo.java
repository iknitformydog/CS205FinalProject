public class DrawTwo
{
	private Move move1;  //Move object to keep track of first move of a draw 2 turn
	private Move move2;  //Move object to keep track of a second move of a draw 2 turn
	
	/**
		Constructor for when the computer picks a card and discards it
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
	*/

	public DrawTwo(int move, Card newCard)
	{
		move1 = new Move(move, newCard);
		move2 = null;
	}
	
	/**
		Constructor for when the computer picks a draw 2 card
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param drawTwo The DrawTwo object keeping track of
		the computer's actions during this draw 2
	*/
	
	public DrawTwo(int move, Card newCard, DrawTwo drawTwo)
	{
		move1 = new Move(move, newCard, drawTwo);
		move2 = null;
	}
	
	/**
		Constructor for when the computer picks a swap card and uses it
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param swap Swap object keeping track of what occurred during swap
	*/
	
	public DrawTwo(int move, Card newCard, Swap swap)
	{
		move1 = new Move(move, newCard, swap);
		move2 = null;
	}
	
	/**
		Constructor for when the computer picks a peek card and uses it
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param peek The index of the card that the computer chose to peek at
	*/
	
	public DrawTwo(int move, Card newCard, int peek)
	{
		move1 = new Move(move, newCard, peek);
		move2 = null;
	}
	
	/**
		Constructor for when the computer swaps its first drawn card with a card in its hand
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param hand The computer's hand
	*/
	
	public DrawTwo(int move, Card newCard, Hand hand)
	{
		move1 = new Move(move, hand.getCard(move), newCard);
		move2 = null;
	}
	
	/**
		Method records the second move for when the computer picks another draw 2 card
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param drawTwo DrawTwo object keeping track of what occurred during this draw 2 turn
	*/
	
	public void recordMove2(int move, Card newCard, DrawTwo drawTwo)
	{
		move2 = new Move(move, newCard, drawTwo);
	}
	
	/**
		Method records the second move for when the computer picks a swap card
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param swap Swap object keeping track of what occurred during swap
	*/
	
	public void recordMove2(int move, Card newCard, Swap swap)
	{
		move2 = new Move(move, newCard, swap);
	}
	
	/**
		Method records the second move for when the computer picks a peek card
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param peek The index of the card that the computer peeked at
	*/
	
	public void recordMove2(int move, Card newCard, int peek)
	{
		move2 = new Move(move, newCard, peek);
	}
	
	/**
		Method records the second move for when the computer picks a card and
		swaps it with a card in its hand
		@param move The computer's move (as an integer)
		@param newCard The card that the computer picked
		@param hand The computer's hand
	*/
	
	public void recordMove2(int move, Card newCard, Hand hand)
	{
		if(move != Game.PLACE_IN_DISCARD)
		{
			move2 = new Move(move, hand.getCard(move), newCard);
		}
		else
		{
			move2 = new Move(move, newCard);
		}
	}
	
	/**
		Method returns the computer's first move of a Draw 2
		@return Move object keeping track of the computer's first move
	*/
	
	public Move getMove1()
	{
		return move1;
	}
	
	/**
		Method returns the computer's second move of a Draw 2
		@return Move object keeping track of the computer's second move
		@return null if the computer did not use its second move
	*/
	
	public Move getMove2()
	{
		return move2;
	}
	
}