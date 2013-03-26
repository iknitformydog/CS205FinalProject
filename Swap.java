public class Swap
{
	private int computerCard;  //index of card in computer's hand that was swapped
	private int humanCard;  //index of card in human's hand that was swapped
	
	/**
		Constructor creates a new Swap object
		@param computerCard The index of card in computer's hand that was swapped
		@param humanCard The index of card in human's hand that was swapped
	*/
		
	public Swap(int computerCard, int humanCard)
	{
		this.computerCard = computerCard;
		this.humanCard = humanCard;
	}
	
	/**
		Method returns the index of computer's hand where the swap occurred
		@return The index of card in computer hand
	*/
	
	public int getComputerCard()
	{
		return computerCard;
	}
	
	/**
		Method returns the index of human's hand where the swap occurred
		@return The index of card in human's hand
	*/
	
	public int getHumanCard()
	{
		return humanCard;
	}
}