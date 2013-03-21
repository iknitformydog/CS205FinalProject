import javax.swing.*;

public class Card
{
	private String type;
	private char index;
	private String pictureName;
	
	public Card(String type, char index, String pictureName)
	{
		setType(type);
		setIndex(index);
		setPictureName(pictureName);
	}
	
	/**
		Method sets the card's type
		@param The card's type
	*/
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	/**
		Method returns the card's type
		@return The card's type
	*/
	
	public String getType()
	{
		return type;
	}
	
	/**
		Method sets the card's index
		@param The card's index
	*/
	
	public void setIndex(char index)
	{
		this.index = index;
	}
	
	/**
		Method returns the card's index
		@return The card's index
	*/
	
	public char getIndex()
	{
		return index;
	}
	
	/**
		Method sets the card's picture name
		@param The card's picture name
	*/
	
	public void setPictureName(String pictureName)
	{
		this.pictureName = pictureName;
	}
	
	/**
		Method returns the card's picture name
		@return The card's picture name
	*/
	
	public String getPictureName()
	{
		return pictureName;
	}
	
	/**
		Method returns the card's value
		@return The card's value if the card is a cat or rat card
		@return -1 if the card is a power card
	*/
	
	public int getValue()
	{
		if(isPowerCard())
		{
			return -1;
		}
		else
		{
			return (Integer.parseInt(type));
		}
	}
	
	/**
		Method returns a boolean value indicating whether the card is a power card
		@return true if the card is a power card
		@return false if the card is not a power card
	*/
	
	public boolean isPowerCard()
	{
		if(type.equals("pick2") || type.equals("swap") || type.equals("peek"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
		
	/**
		Method returns the card's picture as an ImageIcon
		@return The card's picture
	*/
	
	public ImageIcon getCardImage()
	{
		return new ImageIcon(pictureName);
	}
	
	/**
		Method returns the state of the card as a String
		@return The card's state
	*/
	
	public String toString()
	{
		StringBuilder card = new StringBuilder();
		card.append(type);
		card.append(index);
		return card.toString();
	}
}