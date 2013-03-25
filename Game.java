import javax.swing.*;

public class Game
{
	public final static int DRAW_PILE = 0;
	public final static int DISCARD_PILE = 1;
	public final static int RAT_A_TAT_CAT = 2;
	
	public final static int CARD_1 = 0;
	public final static int CARD_2 = 1;
	public final static int CARD_3 = 2;
	public final static int CARD_4 = 3;
	public final static int PLACE_IN_DISCARD = 4;
	public final static int DRAW_2_CARD = 5;
	public final static int SWAP_CARD = 6;
	public final static int PEEK_CARD = 7;
	
	private Deck deck;
	private Human human;
	private Computer computer;
	
	public Game()
	{
		deck = new Deck();
		human = new Human(getHumanName());
		initializeComputer();
		dealCards();
		playGame();
	}
	
	/**
		Method returns user's name
		@return The user's name
	*/
	
	private String getHumanName()
	{
		String name;
		do
		{
			name = JOptionPane.showInputDialog("What is your name?");
		}
		while(name.equals(""));
		return name;
	}
	
	/**
		Method initializes the computer and determines its level
	*/
	
	private void initializeComputer()
	{
		Object[] options = {"Beginner", "Intermediate", "Advanced"};
		int choice = JOptionPane.showOptionDialog(null, "Choose a Computer Level!",
			"Computer Level", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
			null, options, options[0]);
		if(choice == 0)
		{
			computer = new BeginnerComputer();
		}
		else if(choice == 1)
		{
			computer = new IntermediateComputer();
		}
		else
		{
			computer = new AdvancedComputer();
		}
	}
	
	/**
		Method deals cards to each player and adds them to their hands
	*/
	
	private void dealCards()
	{
		Card humanCard;
		Card computerCard;
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			humanCard = deck.removeTopDraw();
			human.swapCard(humanCard, i);
			
			computerCard = deck.removeTopDraw();
			computer.swapCard(computerCard, i);
		}
	}
	
	/**
		Method displays the user's two outer cards
	*/
	
	private void showHumanOuterCards()
	{
		JOptionPane.showMessageDialog(null, "Card 1: " + ((human.getHand()).getCard(CARD_1)) + 
				"\nCard 4: " + ((human.getHand()).getCard(CARD_4)));
	}
	
	/**
		Method simulates the game
	*/
	
	private void playGame()
	{
		boolean ratATatCat = false;
		int turn = 0;
		showHumanOuterCards();
		computer.checkOuterCards();
		if(computer instanceof AdvancedComputer)
		{
			((AdvancedComputer)computer).recordCard(deck.getTopDiscard());
		}
		while(!ratATatCat)
		{
			if(turn % 2 == 0)
			{
				ratATatCat = humanTurn(false);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Computer's Turn!");
				ratATatCat = computerTurn(false);
			}
			turn++;
		}
		if(turn % 2 == 0)
		{
			JOptionPane.showMessageDialog(null, "Since the computer declared Rat-A-Tat-Cat, this will be your last turn!");
			ratATatCat = humanTurn(true);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Since you declared Rat-A-Tat-Cat, the computer will have one more turn!");
			JOptionPane.showMessageDialog(null, "Computer's Turn!");
			ratATatCat = computerTurn(true);
		}
		endGame();
	}
	
	/**
		Method simulates the user's turn
		@return true If the user declared RAT-A-TAT-CAT
		@return false If the user did not declare RAT-A-TAT-CAT
	*/
	
	private boolean humanTurn(boolean finalTurn)
	{
		int choice = getHumanCard(finalTurn);
		int move;
		if(choice == RAT_A_TAT_CAT)
		{
			return true;
		}
		else
		{
			move = getHumanMove(choice);
			if(move == DRAW_2_CARD)
			{
				humanDraw2();
			}
			else if(move == SWAP_CARD)
			{
				humanSwap();
			}
			else if(move == PEEK_CARD)
			{
				humanPeek();
			}
			else
			{
				human.makeMove(deck, choice, move);
			}
			return false;
		}
	}
	
	/**
		Method asks user to choose a pile (Draw Pile or Discard Pile) to take a card or whether he/she
		would like to declare RAT-A-TAT-CAT
		@return An integer representing the user's choice
	*/
		
	private int getHumanCard(boolean finalTurn)
	{
		Card card = deck.getTopDiscard();
		int choice;
		if(!finalTurn)
		{
				Object[] options = {"Draw Pile", "Discard Pile", "Declare Rat-A-Tat-Cat"};
			do
			{
				choice = JOptionPane.showOptionDialog(null, "Card in Discard Pile: " + card +
					"\nWould you like to take a card from the draw pile or discard pile?",
					"Choose a Pile", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);
			}
			while(choice == -1);
		}
		else
		{
			Object[] options = {"Draw Pile", "Discard Pile"};
			do
			{
				choice = JOptionPane.showOptionDialog(null, "Card in Discard Pile: " + card +
					"\nWould you like to take a card from the draw pile or discard pile?",
					"Choose a Pile", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);
			}
			while(choice == -1);
		}
		while(card.isPowerCard() && choice == DISCARD_PILE)
		{
			Object[] newOptions = {"Draw Pile", "Discard Pile"};
			do
			{
				choice = JOptionPane.showOptionDialog(null, "Card in Discard Pile: " + card +
					"\nThe card in the discard pile is a power card. You must take a card from the draw pile.",
					"Choose a Pile", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, newOptions, newOptions[0]);
			}
			while(choice == -1);
		}
		if(computer instanceof AdvancedComputer)
		{
			((AdvancedComputer)computer).recordCard(card);
		}
		return choice;
	}
	
	/**
		Method asks the user make a move (to choose where to put the drawn card)
		@param choice The user's chosen pile to take a card (Draw Pile or Discard Pile)
		@return An integer representing the user's move
	*/
	
	private int getHumanMove(int choice)
	{
		Card card;
		if(choice == DRAW_PILE)
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
				return DRAW_2_CARD;		
			}
			else if((card.getType()).equals(Card.SWAP))
			{
				deck.addToDiscard(card);
				return SWAP_CARD;
			}
			else
			{
				deck.addToDiscard(card);
				return PEEK_CARD;
			}
		}
		else
		{
			Object[] options = {"Swap with Card 1", "Swap with Card 2", "Swap with Card 3", "Swap with Card 4", "Place in Dicard Pile"};
			int move;
			do
			{
				move = JOptionPane.showOptionDialog(null, "The card you picked: " + card +
					"\nWhat would you like to do with this card?", "Make your Move", JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			}
			while(move == -1);
			if(choice == DRAW_PILE)
			{
				if(computer instanceof AdvancedComputer)
				{
					if(move != PLACE_IN_DISCARD)
					{
						((AdvancedComputer)computer).recordHumanMove(move, null);
					}
				}
				deck.addToDraw(card);
			}
			else
			{
				if(computer instanceof AdvancedComputer)
				{
					if(move != PLACE_IN_DISCARD)
					{
						((AdvancedComputer)computer).recordHumanMove(move, card);
					}
				}
				deck.addToDiscard(card);
			}
			return move;
		}
	}
	
	/**
		Method simulates when the user picks a draw 2 card
	*/
	
	private void humanDraw2()
	{
		Object[] options = {"Pick a Card"};
		int choice = JOptionPane.showOptionDialog(null, "You picked a Draw 2 card! Draw another card!",
			"Pick a Card", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
			null, options, options[0]);
		int move1 = getHumanMove(DRAW_PILE);
		boolean drawAgain = false;
		if(move1 == PLACE_IN_DISCARD)
		{
			drawAgain = true;
			Card card = deck.removeTopDraw();
			if(computer instanceof AdvancedComputer)
			{
				((AdvancedComputer)computer).recordCard(card);
			}
			deck.addToDiscard(card);
		}
		else if(move1 == DRAW_2_CARD)
		{
			humanDraw2();
		}
		else if(move1 == SWAP_CARD)
		{
			Object[] newOptions = {"Use Card", "Do Not Use Card"};
			int newChoice = JOptionPane.showOptionDialog(null, "You picked a Swap card! Would you like to use this card?",
				"Make Move", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, newOptions, newOptions[0]);
			if(newChoice == 0)
			{
				humanSwap();
			}
			else
			{
				drawAgain = true;
			}
		}
		else if(move1 == PEEK_CARD)
		{
			Object[] newOptions = {"Use Card", "Do Not Use Card"};
			int newChoice = JOptionPane.showOptionDialog(null, "You picked a Peek card! Would you like to use this card?",
				"Make Move", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, newOptions, newOptions[0]);
			if(newChoice == 0)
			{
				humanPeek();
			}
			else
			{
				drawAgain = true;
			}
		}
		else
		{
			human.makeMove(deck, DRAW_PILE, move1);
		}
		if(drawAgain)
		{
			Object[] newOptions = {"Draw Another Card"};
			int newChoice = JOptionPane.showOptionDialog(null, "Draw Another Card!",
				"Draw Card", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, newOptions, newOptions[0]);
			int move2 = getHumanMove(DRAW_PILE);
			if(move2 == DRAW_2_CARD)
			{
				humanDraw2();
			}
			else if(move2 == SWAP_CARD)
			{
				humanSwap();
			}
			else if(move2 == PEEK_CARD)
			{
				humanPeek();
			}
			else
			{
				human.makeMove(deck, DRAW_PILE, move2);
			}
		}
	}
	
	/**
		Method simulates when the user uses a swap card
	*/
	
	private void humanSwap()
	{
		Object[] options = {"Swap with Card 1", "Swap with Card 2", "Swap with Card 3", "Swap with Card 4", "Do not Swap"};
		int computerIndex = JOptionPane.showOptionDialog(null, "You picked a Swap card! Choose an opponent's card to swap with!",
			"Make Move", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if(computerIndex != PLACE_IN_DISCARD)
		{
			Object[] newOptions = {"Swap with Card 1", "Swap with Card 2", "Swap with Card 3", "Swap with Card 4"};
			int humanIndex = JOptionPane.showOptionDialog(null, "Choose one of your cards to swap with!",
				"Make Move", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, newOptions, newOptions[0]);
				
			computer.recordHumanSwap(computerIndex, humanIndex);
			
			human.swapWithOpponent(computer, computerIndex, humanIndex);
		}
	}
	
	/**
		Method simulates when the user uses a peek card
	*/
	
	private void humanPeek()
	{
		Object[] options = {"Peek at Card 1", "Peek at Card 2", "Peek at Card 3", "Peek at Card 4"};
		int move = JOptionPane.showOptionDialog(null, "You picked a Peek card! Choose a card to peek at!",
			"Make your Move", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		JOptionPane.showMessageDialog(null, "Your Card " + (move + 1) + ": " + human.getCard(move));
	}
	
	/**
		Method simulates the computer's turn
		@param finalTurn Boolean variable representing whether the human declared RAT-A-TAT-CAT
		on his/her previous turn
		@return true If the computer declared RAT-A-TAT-CAT
		@return false If the computer did not declare RAT-A-TAT-CAT
	*/
	
	private boolean computerTurn(boolean finalTurn)
	{
		int choice = computer.choosePile(deck, finalTurn);
		if(choice == RAT_A_TAT_CAT)
		{
			JOptionPane.showMessageDialog(null, "The computer declared Rat-A-Tat-Cat!");
			return true;
		}
		else
		{
			if(choice == DRAW_PILE)
			{
				JOptionPane.showMessageDialog(null, "Card in the discard pile: " + deck.getTopDiscard() + 
					"\nThe computer took a card from the draw pile!");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Card in the discard pile: " + deck.getTopDiscard() + 
					"\nThe computer took a card from the discard pile!");
			}
			int move = computer.chooseMove(deck, choice);
			
			if(move == DRAW_2_CARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked a Draw 2 card!");
				DrawTwo drawTwo = computer.draw2(deck, human);
				Move firstMove = drawTwo.getMove1();
				int move1 = firstMove.getMove();
				Move secondMove = drawTwo.getMove2();
				
				if(move1 == PLACE_IN_DISCARD)
				{
					JOptionPane.showMessageDialog(null, "The computer picked the card " + firstMove.getNewCard() +
						" and placed it in the discard pile! The computer will pick another card!");
				}
				else if(move1 == DRAW_2_CARD)
				{
					JOptionPane.showMessageDialog(null, "The computer picked another draw 2 card!");
					handleDrawTwoStatus(firstMove);
				}
				else if(move1 == SWAP_CARD)
				{
					JOptionPane.showMessageDialog(null, "The computer picked a swap card!");
					if(secondMove == null)
					{
						Swap swap = firstMove.getSwap();
						JOptionPane.showMessageDialog(null, "The computer swaped its Card " + (swap.getComputerCard() + 1) +
							" with your Card " + (swap.getHumanCard() + 1));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The computer has chosen not to swap! The computer will pick another card!");
					}
				}
				else if(move1 == PEEK_CARD)
				{
					JOptionPane.showMessageDialog(null, "The computer picked a peek card!");
					if(secondMove == null)
					{
						int cardIndex = firstMove.getPeek();
						JOptionPane.showMessageDialog(null, "The computer peeked at its Card " + (cardIndex + 1));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The computer has chosen not to use the peek card! " + 
								"The computer will pick another card!");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The computer picked a card and swapped it with its card " + (move1 + 1) +
							" and placed the card " + firstMove.getOldCard() + " in the discard pile!");
				}

				if(secondMove != null)
				{
					int move2 = secondMove.getMove();
					if(move2 == PLACE_IN_DISCARD)
					{
						JOptionPane.showMessageDialog(null, "The computer picked the card " + secondMove.getNewCard() +
							" and placed it in the discard pile!");
					}
					else if(move2 == DRAW_2_CARD)
					{
						JOptionPane.showMessageDialog(null, "The computer picked another draw 2 card!");
						handleDrawTwoStatus(secondMove);
					}
					else if(move2 == SWAP_CARD)
					{
						JOptionPane.showMessageDialog(null, "The computer picked a swap card!");
						Swap swap = secondMove.getSwap();
						if(swap.getComputerCard() != -1)
						{
							JOptionPane.showMessageDialog(null, "The computer swaped its Card " + (swap.getComputerCard() + 1) +
								" with your Card " + (swap.getHumanCard() + 1));
						}
						else
						{
							JOptionPane.showMessageDialog(null, "The computer has chosen not to swap!");
						}
					}
					else if(move2 == PEEK_CARD)
					{
						JOptionPane.showMessageDialog(null, "The computer picked a peek card!");
					
						int cardIndex = secondMove.getPeek();
						if(cardIndex != -1)
						{
							JOptionPane.showMessageDialog(null, "The computer peeked at its Card " + (cardIndex + 1));
						}
						else
						{
							JOptionPane.showMessageDialog(null, "The computer did not peek at any of its cards!");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The computer picked another card and swapped it with its card " + (move2 + 1) +
							" and placed the card " + secondMove.getOldCard() + " in the discard pile!");
					}
				}
			}
			else if(move == SWAP_CARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked a Swap card!");
				Swap swap = computer.swap(human);
				if(swap.getComputerCard() != -1)
				{
					JOptionPane.showMessageDialog(null, "The computer swaped its Card " + (swap.getComputerCard() + 1) +
						" with your Card " + (swap.getHumanCard() + 1));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The computer has chosen not to swap!");
				}
			}
			else if(move == PEEK_CARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked a Peek card!");
				int cardIndex = computer.peek();
				if(cardIndex != -1)
				{
					JOptionPane.showMessageDialog(null, "The computer peeked at its Card " + (cardIndex + 1));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The computer did not peek at any of its cards!");
				}
			}
			else
			{
				if(move == PLACE_IN_DISCARD)
				{
					Card card = deck.getTopDraw();
					JOptionPane.showMessageDialog(null, "The computer placed its drawn card in the discard pile!" +
						"\nCard placed in the discard pile: " + card);
				}
				else
				{
					Card card = computer.getCard(move);
					JOptionPane.showMessageDialog(null, "The computer swapped its drawn card with its Card " + (move + 1) +
						"\nCard placed in the discard pile: " + card);
				}
				computer.makeMove(deck, choice, move);
			}
			return false;
		}
	}
	
	/**
		Method handles situations when the computer picks a draw 2 card during a draw 2 turn
		@param The move that the computer picked the draw 2 card
	*/
	
	private void handleDrawTwoStatus(Move move)
	{
		DrawTwo drawTwo = move.getDrawTwo();
		Move firstMove = drawTwo.getMove1();
		int move1 = firstMove.getMove();
		Move secondMove = drawTwo.getMove2();
		if(move1 == PLACE_IN_DISCARD)
		{
			JOptionPane.showMessageDialog(null, "The computer picked the card " + firstMove.getNewCard() +
				" and placed it in the discard pile! The computer will pick another card!");
		}
		else if(move1 == DRAW_2_CARD)
		{
			JOptionPane.showMessageDialog(null, "The computer picked another draw 2 card!");
			handleDrawTwoStatus(firstMove);
		}
		else if(move1 == SWAP_CARD)
		{
			JOptionPane.showMessageDialog(null, "The computer picked a swap card!");
			if(secondMove == null)
			{
				Swap swap = firstMove.getSwap();
				JOptionPane.showMessageDialog(null, "The computer swaped its Card " + (swap.getComputerCard() + 1) +
					" with your Card " + (swap.getHumanCard() + 1));
			}
			else
			{
				JOptionPane.showMessageDialog(null, "The computer has chosen not to swap! The computer will pick another card!");
			}
		}
		else if(move1 == PEEK_CARD)
		{
			JOptionPane.showMessageDialog(null, "The computer picked a peek card!");
			if(secondMove == null)
			{
				int cardIndex = firstMove.getPeek();
				JOptionPane.showMessageDialog(null, "The computer peeked at its Card " + (cardIndex + 1));
			}
			else
			{
				JOptionPane.showMessageDialog(null, "The computer has chosen not to use the peek card! " + 
						"The computer will pick another card!");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The computer picked a card and swapped it with its card " + (move1 + 1) +
				" and placed the card " + firstMove.getOldCard() + " in the discard pile!");
		}
		if(secondMove != null)
		{
			int move2 = secondMove.getMove();
			if(move2 == PLACE_IN_DISCARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked the card " + secondMove.getNewCard() +
					" and placed it in the discard pile!");
			}
			else if(move2 == DRAW_2_CARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked another draw 2 card!");
				handleDrawTwoStatus(secondMove);
			}
			else if(move2 == SWAP_CARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked a swap card!");
				Swap swap = secondMove.getSwap();
				if(swap.getComputerCard() != -1)
				{
					JOptionPane.showMessageDialog(null, "The computer swaped its Card " + (swap.getComputerCard() + 1) +
						" with your Card " + (swap.getHumanCard() + 1));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The computer has chosen not to swap!");
				}
			}
			else if(move2 == PEEK_CARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked a peek card!");
				int cardIndex = secondMove.getPeek();
				if(cardIndex != -1)
				{
					JOptionPane.showMessageDialog(null, "The computer peeked at its Card " + (cardIndex + 1));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The computer did not peek at any of its cards!");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "The computer picked another card and swapped it with its card " + (move2 + 1) +
					" and placed the card " + secondMove.getOldCard() + " in the discard pile!");
			}
		}
	}
	
	/**
		Method simulates the end of the game (after a player declared RAT-A-TAT-CAT)
	*/
	
	private void endGame()
	{
		JOptionPane.showMessageDialog(null, "The game is over!");
		JOptionPane.showMessageDialog(null, "Your hand: " + human.getHand() +
			"\n\nThe computer's hand: " + computer.getHand());
		int humanPowerCards = human.getNumPowerCards();
		int computerPowerCards = computer.getNumPowerCards();
		if(humanPowerCards > 0)
		{
			JOptionPane.showMessageDialog(null, "You had " + humanPowerCards + " power card(s) in your hand!" +
				"\nYou will need to replace these " + humanPowerCards + " power card(s) with " + humanPowerCards + 
				" new cards from the draw pile!");
			human.replacePowerCards(deck);
		}
		if(computerPowerCards > 0)
		{
			JOptionPane.showMessageDialog(null, "The computer had " + computerPowerCards + " power card(s) in its hand!" +
				"\nThe computer has replaced these " + computerPowerCards + " power card(s) with " + computerPowerCards +
				" new cards from the draw pile!");
			computer.replacePowerCards(deck);
		}
		if(humanPowerCards > 0 || computerPowerCards > 0)
		{
			JOptionPane.showMessageDialog(null, "Your final hand: " + human.getHand() +
				"\n\nThe computer's final hand: " + computer.getHand());
		}
		int humanHandValue = human.getFinalHandValue();
		int computerHandValue = computer.getFinalHandValue();
		if(humanHandValue < computerHandValue)
		{
			JOptionPane.showMessageDialog(null, "Your final hand value: " + humanHandValue +
				"\n\nThe computer's final hand value: " + computerHandValue +
				"\n\nYour hand value is lower, so you win!");
		}
		else if(humanHandValue > computerHandValue)
		{
			JOptionPane.showMessageDialog(null, "Your final hand value: " + humanHandValue +
				"\n\nThe computer's final hand value: " + computerHandValue +
				"\n\nThe computer's hand value is lower, so the computer wins!");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Your final hand value: " + humanHandValue +
				"\n\nThe computer's final hand value: " + computerHandValue +
				"\n\nYour hand value is the same as the computer's hand value! Tie Game! Nobody Wins!");
		}
	}
			
	public static void main(String[] args)
	{
		Game game = new Game();
	}
}