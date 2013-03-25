import java.util.*;

public class Deck
{
	public final static int TOTAL_NUM_CARDS = 54;
	private ArrayList<Card> drawPile;
	private ArrayList<Card> discardPile;
	private boolean isNewDeck;
	
	public Deck()
	{
		drawPile = new ArrayList<Card>();
		discardPile = new ArrayList<Card>();
		isNewDeck = false;
		loadDeck();
		shuffle(drawPile);
		addToDiscard(removeTopDraw());
	}
			
	/**
		Method shuffles a pile of cards
		@param The pile of cards to be shuffled
	*/
	
	private void shuffle(ArrayList<Card> pile)
	{
		Collections.shuffle(pile);
	}
	
	/**
		Method returns the top card in the draw pile
		@return The top card in the draw pile
	*/
	
	public Card getTopDraw()
	{
		return drawPile.get(drawPile.size() - 1);
	}
	
	/**
		Method returns the top card in the discard pile
		@return The top card in the discard pile
	*/
	
	public Card getTopDiscard()
	{
		return discardPile.get(discardPile.size() - 1);
	}
	
	/**
		Method returns the top card of the draw pile and removes it from the pile
		@return The top card of the draw pile
	*/
	
	public Card removeTopDraw()
	{
		if(drawPile.size() == 0)
		{
			createNewDrawPile();
			isNewDeck = true;
		}
		return drawPile.remove(drawPile.size() - 1);
	}
	
	/**
		Method returns the top card of the discard pile and removes it from the pile
		@return The top card of the discard pile
	*/
	
	public Card removeTopDiscard()
	{
		return discardPile.remove(discardPile.size() - 1);
	}
	
	/**
		Method adds a card to the draw pile
		@param The card to add to the draw pile
	*/
	
	public void addToDraw(Card card)
	{
		drawPile.add(card);
	}
	
	/**
		Method adds a card to the discard pile
		@param The card to add to the discard pile
	*/
	
	public void addToDiscard(Card card)
	{
		discardPile.add(card);
	}
	
	/**
		Method returns a boolean value indicating whether the draw pile is empty
		@return true if the draw pile is empty
		@return false if the draw pile is not empty
	*/
	
	public boolean drawIsEmpty()
	{
		if(drawPile.isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
		Method returns a boolean value indicating whether the discard pile is empty
		@return true if the discard pile is empty
		@return false if the discard pile is not empty
	*/
	
	public boolean discardIsEmpty()
	{
		if(discardPile.isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
		Method removes cards from the discard pile, removes all flags, adds them to the draw pile, and shuffles the draw pile
	*/
	
	public void createNewDrawPile()
	{
		Card card;
		for(int i = discardPile.size() - 1 ; i >= 0 ; i--)
		{
			card = discardPile.remove(i);
			card.removeFlag();
			drawPile.add(card);
		}
		shuffle(drawPile);
	}
	
	/**
		Method returns a boolean value indicating whether the deck draw pile recently ran out of cards
		@return true If the draw pile recently ran out of cards
		@return false If the draw pile did not recently run out of cards
	*/
	
	public boolean isNewDeck()
	{
		if(isNewDeck)
		{
			isNewDeck = false;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
		Method returns an unordered ArrayList of all cards in the deck
		@return An unordered ArrayList of all cards in the deck
	*/
	
	public ArrayList<Card> getAllCards()
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		Card card;
		for(int i = 0 ; i < drawPile.size() ; i++)
		{
			card = drawPile.get(i);
			cards.add(card);
		}
		for(int i = 0 ; i < discardPile.size() ; i++)
		{
			card = discardPile.get(i);
			cards.add(card);
		}
		return cards;
	}
		
	/**
		The method returns the draw and discard piles as a String
		@return The draw and discard piles as a String
	*/
	
	public String toString()
	{
		return "Draw Pile: " + drawPile.toString() + "\nDiscard Pile: " + discardPile.toString();
	}
	
	/**
		Method initializes the deck
	*/

	private void loadDeck()
	{
		drawPile.add(new Card("0", 'A', "cards/0A.gif"));
		drawPile.add(new Card("0", 'B', "cards/0B.gif"));
		drawPile.add(new Card("0", 'C', "cards/0C.gif"));
		drawPile.add(new Card("0", 'D', "cards/0D.gif"));
		drawPile.add(new Card("1", 'A', "cards/1A.gif"));
		drawPile.add(new Card("1", 'B', "cards/1B.gif"));
		drawPile.add(new Card("1", 'C', "cards/1C.gif"));
		drawPile.add(new Card("1", 'D', "cards/1D.gif"));
		drawPile.add(new Card("2", 'A', "cards/2A.gif"));
		drawPile.add(new Card("2", 'B', "cards/2B.gif"));
		drawPile.add(new Card("2", 'C', "cards/2C.gif"));
		drawPile.add(new Card("2", 'D', "cards/2D.gif"));
		drawPile.add(new Card("3", 'A', "cards/3A.gif"));
		drawPile.add(new Card("3", 'B', "cards/3B.gif"));
		drawPile.add(new Card("3", 'C', "cards/3C.gif"));
		drawPile.add(new Card("3", 'D', "cards/3D.gif"));
		drawPile.add(new Card("4", 'A', "cards/4A.gif"));
		drawPile.add(new Card("4", 'B', "cards/4B.gif"));
		drawPile.add(new Card("4", 'C', "cards/4C.gif"));
		drawPile.add(new Card("4", 'D', "cards/4D.gif"));
		drawPile.add(new Card("5", 'A', "cards/5A.gif"));
		drawPile.add(new Card("5", 'B', "cards/5B.gif"));
		drawPile.add(new Card("5", 'C', "cards/5C.gif"));
		drawPile.add(new Card("5", 'D', "cards/5D.gif"));
		drawPile.add(new Card("6", 'A', "cards/6A.gif"));
		drawPile.add(new Card("6", 'B', "cards/6B.gif"));
		drawPile.add(new Card("6", 'C', "cards/6C.gif"));
		drawPile.add(new Card("6", 'D', "cards/6D.gif"));
		drawPile.add(new Card("7", 'A', "cards/7A.gif"));
		drawPile.add(new Card("7", 'B', "cards/7B.gif"));
		drawPile.add(new Card("7", 'C', "cards/7C.gif"));
		drawPile.add(new Card("7", 'D', "cards/7D.gif"));
		drawPile.add(new Card("8", 'A', "cards/8A.gif"));
		drawPile.add(new Card("8", 'B', "cards/8B.gif"));
		drawPile.add(new Card("8", 'C', "cards/8C.gif"));
		drawPile.add(new Card("8", 'D', "cards/8D.gif"));
		drawPile.add(new Card("9", 'A', "cards/9A.gif"));
		drawPile.add(new Card("9", 'B', "cards/9B.gif"));
		drawPile.add(new Card("9", 'C', "cards/9C.gif"));
		drawPile.add(new Card("9", 'D', "cards/9D.gif"));
		drawPile.add(new Card("9", 'E', "cards/9E.gif"));
		drawPile.add(new Card("9", 'F', "cards/9F.gif"));
		drawPile.add(new Card("9", 'G', "cards/9G.gif"));
		drawPile.add(new Card("9", 'H', "cards/9H.gif"));
		drawPile.add(new Card("9", 'I', "cards/9I.gif"));
		drawPile.add(new Card("pick2", 'A', "cards/pick2A.gif"));
		drawPile.add(new Card("pick2", 'B', "cards/pick2B.gif"));
		drawPile.add(new Card("pick2", 'C', "cards/pick2C.gif"));
		drawPile.add(new Card("swap", 'A', "cards/swapA.gif"));
		drawPile.add(new Card("swap", 'B', "cards/swapB.gif"));
		drawPile.add(new Card("swap", 'C', "cards/swapC.gif"));
		drawPile.add(new Card("peek", 'A', "cards/peekA.gif"));
		drawPile.add(new Card("peek", 'B', "cards/peekB.gif"));
		drawPile.add(new Card("peek", 'C', "cards/peekC.gif"));
	}
}