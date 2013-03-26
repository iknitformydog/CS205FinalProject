import javax.swing.*;

public class Game
{
	//Part 1 of a player's turn: determine which pile to take a card from (or declare Rat-A-Tat-Cat)
	public final static int DRAW_PILE = 0;  //integer constant representing a player's decision to take a card from the draw pile
	public final static int DISCARD_PILE = 1;  //integer constant representing a player's decision to take a card from the discard pile
	public final static int RAT_A_TAT_CAT = 2;  //integer constant representing a player's decision to declare Rat-A-Tat-Cat
	
	//Part 2 of a player's turn: after a card is drawn, determine which card in hand to swap it with or whether to place drawn card into discard pile	
	public final static int CARD_1 = 0;  //integer constant representing a player's decision to swap the drawn card with card 1 in its hand
	public final static int CARD_2 = 1;  //integer constant representing a player's decision to swap the drawn card with card 2 in its hand
	public final static int CARD_3 = 2;  //integer constant representing a player's decision to swap the drawn card with card 3 in its hand
	public final static int CARD_4 = 3;  //integer constant representing a player's decision to swap the drawn card with card 4 in its hand
	public final static int PLACE_IN_DISCARD = 4;  //integer constant reprersenting a player's decision to place the drawn card into the discard pile
	public final static int DRAW_2_CARD = 5;  //integer constant indicating that the player picked a draw 2 card
	public final static int SWAP_CARD = 6;  //integer constant indicating that the player picked a swap card
	public final static int PEEK_CARD = 7;  //integer constant indicating that the player picked a peek card
	
	private Deck deck;  //the deck of cards
	private Human human;  //The human player
	private Computer computer;  //The computer player
	
	/**
		Constructor creates a new game
	*/
	
	public Game()
	{
		deck = new Deck();  //initialize the deck
		human = new Human(getHumanName());  //initialize the human object
		initializeComputer();  //initialize ther computer object
		dealCards();  //deal cards to each player's hand
		playGame();  //start game loop
	}
	
	/**
		Method returns user's name
		@return The user's name
	*/
	
	private String getHumanName()
	{
		String name;  //holds the human's name
		
		//ask for user's name
		//if user enters an empty string, ask for name again
		
		do
		{
			name = JOptionPane.showInputDialog("What is your name?");
		}
		while(name.equals(""));
		
		return name;  //return human's name
	}
	
	/**
		Method initializes the computer and determines its level
	*/
	
	private void initializeComputer()
	{
		Object[] options = {"Beginner", "Intermediate", "Advanced"};  //computer level choices
		
		//ask user to choose a computer level
		
		int choice = JOptionPane.showOptionDialog(null, "Choose a Computer Level!",
			"Computer Level", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
			null, options, options[0]);
			
		//if user chooses Beginner, create an instance of BeginnerComputer
		//if user chooses Intermediate, create an instance of IntermediateComputer
		//if user chooses Advanced, create an instance of AdvancedComputer	
		
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
		Card humanCard;  //card object to hold card to be added to human's hand
		Card computerCard;  //card object to hold card to be added to computer's hand
		
		//add cards to each player's hand until the hands are full
		
		for(int i = 0 ; i < Hand.NUM_CARDS ; i++)
		{
			humanCard = deck.removeTopDraw();  //take a card from top of draw pile
			human.swapCard(humanCard, i);  //add card to human's hand at index i
			
			computerCard = deck.removeTopDraw();  //take a card from top of draw pile
			computer.swapCard(computerCard, i);  //add card to computer's hand at index i
		}
	}
	
	/**
		Method displays the user's two outer cards
	*/
	
	private void showHumanOuterCards()
	{
		//show the user his/her outer cards
		
		JOptionPane.showMessageDialog(null, "Card 1: " + ((human.getHand()).getCard(CARD_1)) + 
				"\nCard 4: " + ((human.getHand()).getCard(CARD_4)));
	}
	
	/**
		Method simulates the game
	*/
	
	private void playGame()
	{
		boolean ratATatCat = false;  //boolean value indicating whether a player has declared Rat-A-Tat-Cat
		int turn = 0;  //the turn counter
		
		showHumanOuterCards();  //show user his/her outer cards
		computer.checkOuterCards();  //let computer check and record its outer cards
		
		//if the computer is at the advanced level, the computer will flag the card face up in the discard pile
		
		if(computer instanceof AdvancedComputer)
		{
			((AdvancedComputer)computer).recordCard(deck.getTopDiscard());
		}
		
		//alternate turns during game until a player declares Rat-A-Tat-Cat
		
		while(!ratATatCat)
		{
			if(turn % 2 == 0)
			{
				ratATatCat = humanTurn(false);  //call humanTurn method, false indicates that computer did not declare Rat-A-Tat-Cat during its previous turn
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Computer's Turn!");  //display to user that it is the computer's turn
				ratATatCat = computerTurn(false);  //call computerTurn method, false indicates that human did not declare Rat-A-Tat-Cat during his/her previous turn
			}
			turn++;  //turn is over, begin next turn
		}
		
		//after a player has declared Rat-A-Tat-Cat, determine who declared it and give other player one more turn
		
		if(turn % 2 == 0)
		{
			JOptionPane.showMessageDialog(null, "Since the computer declared Rat-A-Tat-Cat, this will be your last turn!");
			ratATatCat = humanTurn(true);  //call humanTurn method, true indicates that computer declared Rat-A-Tat-Cat during previous turn
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Since you declared Rat-A-Tat-Cat, the computer will have one more turn!");
			JOptionPane.showMessageDialog(null, "Computer's Turn!");
			ratATatCat = computerTurn(true);  //call computerTurn method, true indicates that human declared Rat-A-Tat-Cat during his/her previous turn
		}
		
		endGame();  //end the game and declare winner
	}
	
	/**
		Method simulates the user's turn
		@return true If the user declared RAT-A-TAT-CAT
		@return false If the user did not declare RAT-A-TAT-CAT
	*/
	
	private boolean humanTurn(boolean finalTurn)
	{
		int choice = getHumanCard(finalTurn);  //integer representing pile (draw or discard) human would like to take card from (or declare Rat-A-Tat-Cat)
		int move;  //integer reprrsenting the human's move (which card in hand to swap drawn card with or place drawn card in discard pile
		
		//if the human chooses to declare Rat-A-Tat-Cat, return true
		
		if(choice == RAT_A_TAT_CAT)
		{
			return true;
		}
		
		//if the user chooses a pile (draw or discard), get the human's move
		//Move choices: 0 indicates swap with card 1, 1 indicates swaps with card 2, 2 indicates swap with card 3, 3 indicates swap with card 4
		//4 indicates to place card in discard pile, 5 indicates a draw 2 card, 6 indicates a swap card, and 7 indicates a peek card
		
		else
		{
			move = getHumanMove(choice);  //get human's move
			
			//if user picks a draw 2 card, handle draw 2 turn
			//if user picks a swap card, handle swap turn
			//if user picks a peek card, handle peek turn
			//otherwise, make card swap or place in discard pile (depending on move choice)
			
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
			
			return false;  //human did not declare Rat-A-Tat-Cat, so return false
		}
	}
	
	/**
		Method asks user to choose a pile (Draw Pile or Discard Pile) to take a card or whether he/she
		would like to declare RAT-A-TAT-CAT
		@return An integer representing the user's choice
	*/
		
	private int getHumanCard(boolean finalTurn)
	{
		Card card = deck.getTopDiscard();  //card on top of discard pile
		int choice;  //integer representing the pile (draw or discard) that human would like to take card from (or declare Rat-a-Tat-Cat)
		
		//if computer has not already called Rat-A-Tat-Cat, give user the option of declaring Rat-A-Tat-Cat
		
		if(!finalTurn)
		{
			Object[] options = {"Draw Pile", "Discard Pile", "Declare Rat-A-Tat-Cat"};  //options for user to choose from
			
			//do-while loop has only been added to help fix problem of blank dialog boxes on my computer and prevent program from crashing
			//do not really need do-while loop, but it can't hurt to leave it there so that I can test
			
			do
			{
				//get the pile (draw or discard) that human would like to take card from (or declare Rat-A-Tat-Cat)
			
				choice = JOptionPane.showOptionDialog(null, "Card in Discard Pile: " + card +
					"\nWould you like to take a card from the draw pile or discard pile?",
					"Choose a Pile", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);
			}
			while(choice == -1);  //loop until user successfully chooses an option
		}
		
		//if computer has declared Rat-A-Tat-Cat and this is human's final turn, human cannot declare Rat-A-Tat-Cat again
		
		else
		{
			Object[] options = {"Draw Pile", "Discard Pile"};  //options for user to choose from
			
			//do-while loop has only been added to help fix problem of blank dialog boxes on my computer and prevent program from crashing
			//do not really need do-while loop, but it can't hurt to leave it there so that I can test
			
			do
			{
				//get the pile (draw or discard) that human would like to take card from
			
				choice = JOptionPane.showOptionDialog(null, "Card in Discard Pile: " + card +
					"\nWould you like to take a card from the draw pile or discard pile?",
					"Choose a Pile", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);
			}
			while(choice == -1);  //loop until user successfully chooses an option
		}
		
		//if human would like to take card from discard pile, but card in discard pile is a power card, human must take card from draw pile		

		while(card.isPowerCard() && choice == DISCARD_PILE)
		{
			Object[] newOptions = {"Draw Pile", "Discard Pile"};  //options for user to choose from (user will ultimately need to select draw pile)
			
			//do-while loop has only been added to help fix problem of blank dialog boxes on my computer and prevent program from crashing
			//do not really need do-while loop, but it can't hurt to leave it there so that I can test
			
			do
			{
				//get the pile that user would like to take card from
			
				choice = JOptionPane.showOptionDialog(null, "Card in Discard Pile: " + card +
					"\nThe card in the discard pile is a power card. You must take a card from the draw pile.",
					"Choose a Pile", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, newOptions, newOptions[0]);
			}
			while(choice == -1);  //loop until user successfully chooses an option
		}
		
		//if computer is at the advanced level, the computer will flag (record) the card face up in the discard pile
		
		if(computer instanceof AdvancedComputer)
		{
			((AdvancedComputer)computer).recordCard(card);
		}
		
		return choice;  //return the pile (draw or discard) that human would like to take card from (or declare Rat-A-Tat-Cat)
	}
	
	/**
		Method asks the user make a move (to choose where to put the drawn card)
		@param choice The user's chosen pile to take a card (Draw Pile or Discard Pile)
		@return An integer representing the user's move
	*/
	
	private int getHumanMove(int choice)
	{
		Card card;  //the card that the user picked
		
		//if user chose draw pile, take a card from draw pile
		//if user chose discard pile, take a card from discard pile
		
		if(choice == DRAW_PILE)
		{
			card = deck.removeTopDraw();
		}
		else
		{
			card = deck.removeTopDiscard();
		}
		
		//if card drawn is a power card, add card to discard pile, and return integer identifying type of power card
		
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
		
		//if card drawn is not a power card, give user the option to swap with card 1, card 2, card 3, card 4, or place in discard pile
		
		else
		{
			Object[] options = {"Swap with Card 1", "Swap with Card 2", "Swap with Card 3", "Swap with Card 4", "Place in Dicard Pile"};
			
			int move;  //integer representing the user's choice
			
			//do-while loop has only been added to help fix problem of blank dialog boxes on my computer and prevent program from crashing
			//do not really need do-while loop, but it can't hurt to leave it there so that I can test
			
			do
			{
				move = JOptionPane.showOptionDialog(null, "The card you picked: " + card +
					"\nWhat would you like to do with this card?", "Make your Move", JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			}
			while(move == -1);  //loop until the user chooses one of the options
			
			//if the user took a card from draw pile, temporarily place card back in the draw pile
			//if the user took a card from the discard pile, temporarily place card back in the discard pile
			//the drawn card will be permanently be placed in discard pile in makeMove method of Player class
			
			//if computer is at the advanced level and human swapped the drawn card with card in hand, update computer's hand memory
			//if human took a card from draw pile, computer does not know value of card and will record card as null
			
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
			
			return move;  //return the user's move as an integer
		}
	}
	
	/**
		Method simulates when the user picks a draw 2 card
	*/
	
	private void humanDraw2()
	{
		Object[] options = {"Pick a Card"};  //option for when user picks a draw 2 card
		
		//tell user to pick another card from draw pile
		int choice = JOptionPane.showOptionDialog(null, "You picked a Draw 2 card! Draw another card!",
			"Pick a Card", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
			null, options, options[0]);
		
		int move1 = getHumanMove(DRAW_PILE);  //get the user's move for when taking card from draw pile
		
		boolean drawAgain = false;  //boolean value indicating whether user will draw another card
		
		//if user chooses to place its first drawn card into discard pile, user will get to pick again
		//set drawAgain variable to true
		//add top card on draw pile to discard pile
		//if computer is at advanced level, flag (record) the card that user placed in discard pile
		
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
		
		//if user picks another draw 2 card, recursively call humanDraw2() method to begin second draw 2
		//for the first draw 2, user will not draw another card
		
		else if(move1 == DRAW_2_CARD)
		{
			humanDraw2();
		}
		
		//if user picks a swap card, determine whether user would like to use the card or discard it and go again
		
		else if(move1 == SWAP_CARD)
		{
			Object[] newOptions = {"Use Card", "Do Not Use Card"};  //options for user to choose from
			
			//integer representing user's choice to use the swap card
			
			int newChoice = JOptionPane.showOptionDialog(null, "You picked a Swap card! Would you like to use this card?",
				"Make Move", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, newOptions, newOptions[0]);
				
			//if user chooses to use the swap card, perform the swap, user will not pick another card
			//if user chooses not to use swap card, user will get to pick another card
			
			if(newChoice == 0)
			{
				humanSwap();
			}
			else
			{
				drawAgain = true;
			}
		}
		
		//if user picks a peek card, determine whether user would like to use the card or discard it and go again
		
		else if(move1 == PEEK_CARD)
		{
			Object[] newOptions = {"Use Card", "Do Not Use Card"};  //options for user to choose from
			
			//integer representing user's choice to use peek card
			
			int newChoice = JOptionPane.showOptionDialog(null, "You picked a Peek card! Would you like to use this card?",
				"Make Move", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, newOptions, newOptions[0]);
				
			//if user chooses to use peek card, perform the peek, user will not pick another card
			//if user chooses not to use peek card, user will get to pick another card	
			
			if(newChoice == 0)
			{
				humanPeek();
			}
			else
			{
				drawAgain = true;
			}
		}
		
		//if user chooses to use the first card drawn in a draw 2 turn, make the move
		//user will not pick another card
		
		else
		{
			human.makeMove(deck, DRAW_PILE, move1);
		}
		
		//if user discards the first drawn card, the user will pick another card
		
		if(drawAgain)
		{
			Object[] newOptions = {"Draw Another Card"};  //option for user to pick from
			
			//tell user to pick another card
			
			int newChoice = JOptionPane.showOptionDialog(null, "Draw Another Card!",
				"Draw Card", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, newOptions, newOptions[0]);
				
			int move2 = getHumanMove(DRAW_PILE);  //integer representing the user's second move
			
			//if user picks another draw 2 card, recursively call humanDraw2() method
			
			if(move2 == DRAW_2_CARD)
			{
				humanDraw2();
			}
			
			//if user picks a swap card, perform swap
			
			else if(move2 == SWAP_CARD)
			{
				humanSwap();
			}
			
			//if user picks a peek card, perform peek
			
			else if(move2 == PEEK_CARD)
			{
				humanPeek();
			}
			
			//if user picks a numerical card, make user's move
			
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
		//options for when human uses a swap card
		Object[] options = {"Swap with Card 1", "Swap with Card 2", "Swap with Card 3", "Swap with Card 4", "Do not Swap"};
		
		//integer representing the computer's card number that human would like to swap with
		int computerIndex = JOptionPane.showOptionDialog(null, "You picked a Swap card! Choose an opponent's card to swap with!",
			"Make Move", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			
		//if human does not choose "Do not swap", ask user for card in his/her hand to swap with the computer
		if(computerIndex != PLACE_IN_DISCARD)
		{
			//options for user to choose from
			Object[] newOptions = {"Swap with Card 1", "Swap with Card 2", "Swap with Card 3", "Swap with Card 4"};
			
			//integer representing the human's card number that user would like to swap
			int humanIndex = JOptionPane.showOptionDialog(null, "Choose one of your cards to swap with!",
				"Make Move", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, newOptions, newOptions[0]);
			
			//update computer's hand memory (changes after swap)
			computer.recordHumanSwap(computerIndex, humanIndex);
			
			//perform the swap
			human.swapWithOpponent(computer, computerIndex, humanIndex);
		}
	}
	
	/**
		Method simulates when the user uses a peek card
	*/
	
	private void humanPeek()
	{
		//options for when user chooses a peek card
		Object[] options = {"Peek at Card 1", "Peek at Card 2", "Peek at Card 3", "Peek at Card 4"};

		//integer representing the card that the user would like to peek at
		int move = JOptionPane.showOptionDialog(null, "You picked a Peek card! Choose a card to peek at!",
			"Make your Move", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		//show user the card (perform the peek)
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
		int choice = computer.choosePile(deck, finalTurn);  //the pile (draw or discard) that computer would like to take a card from (or declare Rat-A-Tat-Cat)
		
		//if computer chooses to declare Rat-A-Tat-Cat, tell the user and return true
		
		if(choice == RAT_A_TAT_CAT)
		{
			JOptionPane.showMessageDialog(null, "The computer declared Rat-A-Tat-Cat!");
			return true;
		}
		
		//if the computer chooses not to declare Rat-A-Tat-Cat, perform computer's move
		
		else
		{
			//if computer chooses draw pile, tell the user
			if(choice == DRAW_PILE)
			{
				JOptionPane.showMessageDialog(null, "Card in the discard pile: " + deck.getTopDiscard() + 
					"\nThe computer took a card from the draw pile!");
			}
			
			//if computer chooses discard pile, tell the user
			else
			{
				JOptionPane.showMessageDialog(null, "Card in the discard pile: " + deck.getTopDiscard() + 
					"\nThe computer took a card from the discard pile!");
			}
			
			//get the computer's move (integer representing card number (1, 2, 3, or 4) that computer would like to swap drawn card with)
			//or integer representing that computer would like to place drawn card in discard pile
			//or integer representing that computer picked a power card
			
			int move = computer.chooseMove(deck, choice);
			
			//if computer picks a draw 2 card, perform draw 2 turn
			
			if(move == DRAW_2_CARD)
			{
				//tell user that computer picked a draw 2 card
				JOptionPane.showMessageDialog(null, "The computer picked a Draw 2 card!");
				
				//perform draw 2 by calling computer.draw2(deck, human)
				//method will return a DrawTwo object that kept track of everything that happened during draw 2 turn
				//if you would wish to get this information to present to user, retrieve the DrawTwo object
				//if you do not wish to use the DrawTwo object, do not retrieve it, and let Java Garbage Collector pick it up
				
				DrawTwo drawTwo = computer.draw2(deck, human);
				
				//the following code applies for when you choose to retrieve the DrawTwo object to present information to user
				//if you did not retrieve the DrawTwo object, you do not need the following section of code
				//---------------------------------------------------------------------------------------------------
				
				Move firstMove = drawTwo.getMove1();  //retrieve the first move of the computer's draw 2 turn
				int move1 = firstMove.getMove();  //get the integer representing the computer's move
				
				Move secondMove = drawTwo.getMove2();  //retrieve the second move of the computer's draw 2 turn
				
				//if the computer chose to place its first drawn card in discard pile, tell the user what happened and what
				//card was placed in the discard pile
				
				if(move1 == PLACE_IN_DISCARD)
				{
					JOptionPane.showMessageDialog(null, "The computer picked the card " + firstMove.getNewCard() +
						" and placed it in the discard pile! The computer will pick another card!");
				}
				
				//if the computer picked another draw 2 card during its first move of draw 2, tell the user
				//call method handleDrawTwoStatus to explain to user what happened during this second draw 2
				
				else if(move1 == DRAW_2_CARD)
				{
					JOptionPane.showMessageDialog(null, "The computer picked another draw 2 card!");
					handleDrawTwoStatus(firstMove);
				}
				
				//if the computer picked a swap card during its first move of a draw 2, tell the user
				
				else if(move1 == SWAP_CARD)
				{
					JOptionPane.showMessageDialog(null, "The computer picked a swap card!");
					
					//if the computer chose to use the swap card (secondMove will equal null), tell user which cards were swapped
					//if computer did not use swap card, tell user and say that computer will pick another card
					
					if(secondMove == null)
					{
						Swap swap = firstMove.getSwap();  //swap object keeping track of what happened during swap
						
						JOptionPane.showMessageDialog(null, "The computer swaped its Card " + (swap.getComputerCard() + 1) +
							" with your Card " + (swap.getHumanCard() + 1));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The computer has chosen not to swap! The computer will pick another card!");
					}
				}
				
				//if the computer picks a peek card during its first move of a draw 2, tell the user
				
				else if(move1 == PEEK_CARD)
				{
					JOptionPane.showMessageDialog(null, "The computer picked a peek card!");
					
					//if computer chose to use peek card (secondMove will equal null), tell the user which card computer peeked at
					//if computer did not use peek card, tell user and say that computer will pick another card
					
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
				
				//if user used the first card drawn during draw 2 turn, tell the user which card was swapped and which card
				//was placed in the discard pile
				
				else
				{
					JOptionPane.showMessageDialog(null, "The computer picked a card and swapped it with its card " + (move1 + 1) +
							" and placed the card " + firstMove.getOldCard() + " in the discard pile!");
				}
				
				//if the computer chose to place its first drawn card in thev discard pile, tell user what happened during
				//the second part of computer's draw 2 turn

				if(secondMove != null)
				{
					int move2 = secondMove.getMove();  //integer representing the computer's second move of draw 2 turn
					
					//if computer chooses to place the second drawn card in discard pile, tell the user
					
					if(move2 == PLACE_IN_DISCARD)
					{
						JOptionPane.showMessageDialog(null, "The computer picked the card " + secondMove.getNewCard() +
							" and placed it in the discard pile!");
					}
					
					//if the computer picked a second draw 2 card, tell the user, and call handleDrawTwoStatus method
					//this method will tell the user what happened during the next draw 2 turn
					
					else if(move2 == DRAW_2_CARD)
					{
						JOptionPane.showMessageDialog(null, "The computer picked another draw 2 card!");
						handleDrawTwoStatus(secondMove);
					}
					
					//if the computer picked a swap card, tell the user
					
					else if(move2 == SWAP_CARD)
					{
						JOptionPane.showMessageDialog(null, "The computer picked a swap card!");
						
						Swap swap = secondMove.getSwap();  //swap object keeping track of what happened during swap
						
						//if computer chose to use the swap card, tell user what happened during the swap
						//if the computer chose not to use the swap card, tell the user that the computer chose not to swap
						
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
					
					//if the computer picked a peek card, tell the user which card the computer peeked at
					//if the computer chose not to peek at a card, tell the user
					
					else if(move2 == PEEK_CARD)
					{
						JOptionPane.showMessageDialog(null, "The computer picked a peek card!");
					
						int cardIndex = secondMove.getPeek();  //the index of the card the computer peeked at
						
						if(cardIndex != -1)
						{
							JOptionPane.showMessageDialog(null, "The computer peeked at its Card " + (cardIndex + 1));
						}
						else
						{
							JOptionPane.showMessageDialog(null, "The computer did not peek at any of its cards!");
						}
					}
					
					//if the computer picked a card and used it during second move of draw 2 turn, tell user
					//tell user which card wwas swapped and display the card placed in the discard pile
					
					else
					{
						JOptionPane.showMessageDialog(null, "The computer picked another card and swapped it with its card " + (move2 + 1) +
							" and placed the card " + secondMove.getOldCard() + " in the discard pile!");
					}
				}
				
				//this is the end of the optional DrawTwo object code
				//-----------------------------------------------------------------------------------------------------------------
			}
			
			//if computer picks a swap card, perform the swap move
			
			else if(move == SWAP_CARD)
			{
				//tell user that computer picked a swap card
				JOptionPane.showMessageDialog(null, "The computer picked a Swap card!");
				
				//perform the swap by calling computer.swap(human)
				//if you would like to get what happened during swap and present to user, retrieve the Swap object
				//if you do not wish to present what happened, do not retrieve Swap object and let Java Garbage Collector pick it up
				
				Swap swap = computer.swap(human);
				
				//the following code is optional (only for when you would like to retrieve Swap object):
				//-----------------------------------------------------------------------------------------------------------------
				
				//if the computer chose to use the swap card, tell the user which cards were swapped
				//if the computer did not choose to use swap card, tell user that computer did not want to swap
				
				if(swap.getComputerCard() != -1)
				{
					JOptionPane.showMessageDialog(null, "The computer swaped its Card " + (swap.getComputerCard() + 1) +
						" with your Card " + (swap.getHumanCard() + 1));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The computer has chosen not to swap!");
				}
				
				//this is the end of the optional Swap object code
				//-------------------------------------------------------------------------------------------------------------------
			}
			
			//if the computer picks a peek card, perform the peek move
			
			else if(move == PEEK_CARD)
			{
				//tell user that computer picked a peek card
				JOptionPane.showMessageDialog(null, "The computer picked a Peek card!");
				
				int cardIndex = computer.peek();  //index of card that computer peeked at
				
				//if computer peeked at a card, tell user which card was peeked at
				//if computer did not peek at a card, tell user that computer did not peek
				
				if(cardIndex != -1)
				{
					JOptionPane.showMessageDialog(null, "The computer peeked at its Card " + (cardIndex + 1));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The computer did not peek at any of its cards!");
				}
			}
			
			//code for when the computer did not pick a power card
			
			else
			{
				//if computer would like to place drawn card in discard pile, tell user what happened and what card was discarded
				if(move == PLACE_IN_DISCARD)
				{
					Card card = deck.getTopDraw();
					JOptionPane.showMessageDialog(null, "The computer placed its drawn card in the discard pile!" +
						"\nCard placed in the discard pile: " + card);
				}
				
				//if computer would like to use card, tell user which cards were swapped and what card was placed in discard pile
				else
				{
					Card card = computer.getCard(move);
					JOptionPane.showMessageDialog(null, "The computer swapped its drawn card with its Card " + (move + 1) +
						"\nCard placed in the discard pile: " + card);
				}
				
				//physically make the computer's move and make necessary changes to the deck and computer's hand
				
				computer.makeMove(deck, choice, move);
			}
			return false;  //return false since computer did not declare Rat-A-Tat-Cat
		}
	}
	
	/**
		Method handles situations when the computer picks a draw 2 card during a draw 2 turn
		@param The move that the computer picked the draw 2 card
	*/
	
	//the code in the following method is optional (only used when retrieving the DrawTwo object and want to present what happened to user)
	//-------------------------------------------------------------------------------------------------------------------------------------
	
	private void handleDrawTwoStatus(Move move)
	{
		DrawTwo drawTwo = move.getDrawTwo();  //DrawTwo object  holding what happened during second draw 2 turn
		
		Move firstMove = drawTwo.getMove1();  //the first move of the draw 2 turn
		int move1 = firstMove.getMove();  //integer representing computer's first move of draw 2 turn
		
		Move secondMove = drawTwo.getMove2();  //the second move of the draw 2 turn
		
		//if computer would like to place first drawn card in discard pile, tell user

		if(move1 == PLACE_IN_DISCARD)
		{
			JOptionPane.showMessageDialog(null, "The computer picked the card " + firstMove.getNewCard() +
				" and placed it in the discard pile! The computer will pick another card!");
		}
		
		//if computer picks another draw 2 card as first move of this draw 2 turn, tell user
		//recursively call handleDrawTwoStatus method
		
		else if(move1 == DRAW_2_CARD)
		{
			JOptionPane.showMessageDialog(null, "The computer picked another draw 2 card!");
			handleDrawTwoStatus(firstMove);
		}
		
		//if computer picks a swap card, tell user
		
		else if(move1 == SWAP_CARD)
		{
			JOptionPane.showMessageDialog(null, "The computer picked a swap card!");
			
			//if computer chooses to use swap card (secondMove will equal null), tell user what happened during swap
			//otherwise tell user that computer will pick another card
			
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
		
		//if computer picks a peek card, tell user
		
		else if(move1 == PEEK_CARD)
		{
			JOptionPane.showMessageDialog(null, "The computer picked a peek card!");
			
			//if computer uses peek card (secondMove will equal null), tell user which card was peeked at
			//if computer does not use swap card, tell user that computer will pick another card
			
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
		
		//if computer chooses to use first drawn card, tell user what card the computer swapped the drawn card with
		//tell user what card computer placed in discard pile
		
		else
		{
			JOptionPane.showMessageDialog(null, "The computer picked a card and swapped it with its card " + (move1 + 1) +
				" and placed the card " + firstMove.getOldCard() + " in the discard pile!");
		}
		
		//if computer places its first drawn card in discard pile, handle second move of draw 2 turn
		
		if(secondMove != null)
		{
			int move2 = secondMove.getMove();  //get computer's second move
			
			//if computer would like to place the drawn card in discard pile, tell user
			
			if(move2 == PLACE_IN_DISCARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked the card " + secondMove.getNewCard() +
					" and placed it in the discard pile!");
			}
			
			//if the computer picks another draw 2 card, tell user
			//recursively call handleDrawTwoStatus method
			
			else if(move2 == DRAW_2_CARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked another draw 2 card!");
				handleDrawTwoStatus(secondMove);
			}
			
			//if the computer picks a swap card, tell user
			
			else if(move2 == SWAP_CARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked a swap card!");
				
				Swap swap = secondMove.getSwap();  //retrieve Swap object keeping track of what happened during swap
				
				//if computer chooses to use Swap card, tell user which cards were swapped
				//if computer chooses to not use Swap card, tell user that computer has chosen not to swap
				
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
			
			//if the computer picks a peek card, tell user
			
			else if(move2 == PEEK_CARD)
			{
				JOptionPane.showMessageDialog(null, "The computer picked a peek card!");
				
				int cardIndex = secondMove.getPeek();  //index of card that computer peeked at
				
				//if computer chose to peek at card, tell user which card was peeked at
				//if computer did not peek at card, tell user that computer chose not to peek at any cards
				
				if(cardIndex != -1)
				{
					JOptionPane.showMessageDialog(null, "The computer peeked at its Card " + (cardIndex + 1));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The computer did not peek at any of its cards!");
				}
			}
			
			//if computer decides to use second drawn card, tell user which card was swapped and what card was placed in discard pile
			
			else
			{
				JOptionPane.showMessageDialog(null, "The computer picked another card and swapped it with its card " + (move2 + 1) +
					" and placed the card " + secondMove.getOldCard() + " in the discard pile!");
			}
		}
	}
	
	//this is the end of the optional handleDrawTwoStatus method
	//----------------------------------------------------------------------------------------------------------------------
	
	/**
		Method simulates the end of the game (after a player declared RAT-A-TAT-CAT)
	*/
	
	private void endGame()
	{
		JOptionPane.showMessageDialog(null, "The game is over!");  //display that the game is over
		
		//display the cards in the user's hand
		JOptionPane.showMessageDialog(null, "Your hand: " + human.getHand() +
			"\n\nThe computer's hand: " + computer.getHand());
		
		int humanPowerCards = human.getNumPowerCards();  //number of power cards in human's hand
		int computerPowerCards = computer.getNumPowerCards();  //number of power cards in computer's hand
		
		//if human has power cards in hand, replace them with cards from the draw pile
		
		if(humanPowerCards > 0)
		{
			JOptionPane.showMessageDialog(null, "You had " + humanPowerCards + " power card(s) in your hand!" +
				"\nYou will need to replace these " + humanPowerCards + " power card(s) with " + humanPowerCards + 
				" new cards from the draw pile!");
				
			human.replacePowerCards(deck);  //replace power cards with cards from draw pile
		}
		
		//if computer has power cards in hand, replace them with cards from the draw pile
		
		if(computerPowerCards > 0)
		{
			JOptionPane.showMessageDialog(null, "The computer had " + computerPowerCards + " power card(s) in its hand!" +
				"\nThe computer has replaced these " + computerPowerCards + " power card(s) with " + computerPowerCards +
				" new cards from the draw pile!");
				
			computer.replacePowerCards(deck);  //replacce power cards with cards from draw pile
		}
		
		//if either the human or computer had to replace cards, display the new final hands
		
		if(humanPowerCards > 0 || computerPowerCards > 0)
		{
			JOptionPane.showMessageDialog(null, "Your final hand: " + human.getHand() +
				"\n\nThe computer's final hand: " + computer.getHand());
		}
		
		
		int humanHandValue = human.getFinalHandValue();  //total value of cards in human's hand
		int computerHandValue = computer.getFinalHandValue();  //total value of cards in computer's hand
		
		//if sum of cards in human's hand is less than sum of cards in computer's hand, declare human the winner
		
		if(humanHandValue < computerHandValue)
		{
			JOptionPane.showMessageDialog(null, "Your final hand value: " + humanHandValue +
				"\n\nThe computer's final hand value: " + computerHandValue +
				"\n\nYour hand value is lower, so you win!");
		}
		
		//if sum of cards in computer's hand is less than  sum of cards in human's hand, declare computer the winner
		
		else if(humanHandValue > computerHandValue)
		{
			JOptionPane.showMessageDialog(null, "Your final hand value: " + humanHandValue +
				"\n\nThe computer's final hand value: " + computerHandValue +
				"\n\nThe computer's hand value is lower, so the computer wins!");
		}
		
		//if sum of cards in human's hand equals the sum of cards in computer's hand, declare a tie game
		
		else
		{
			JOptionPane.showMessageDialog(null, "Your final hand value: " + humanHandValue +
				"\n\nThe computer's final hand value: " + computerHandValue +
				"\n\nYour hand value is the same as the computer's hand value! Tie Game! Nobody Wins!");
		}
	}
			
	public static void main(String[] args)
	{
		Game game = new Game();  //instance of game
	}
}