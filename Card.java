
//Peter Crosby


public class Card {


	public String count;
	public String type;
	
	public String power;
	
/*********************************/	
/**
	method gets count of a card.
**/
	public String getCount(){
		
		return count;
	}//getSuit
	
/*********************************/
/**
	method sets count of a card.
	@param suitIn 
**/	
	public void setCount(String countIn){
	
		this.count=countIn;
	}//setSuit

/*********************************/
/**
	method gets rank of the card.
**/
	public String getType(){
	
		return type;
	}//getRank
/*********************************/
/**
	method gets power of the card.
**/
	public String getPower(){
	
		return power;
	}//getRank
/*********************************/
/**
	method gets rank of the card.***************************  Returns integer value
**/
	public int getIntRank(){
		int rankInt=Integer.parseInt(type);
		return rankInt;
	}//getRank

/*********************************/
/**
	method sets the rank of a car.
	@param rankIn
**/

	public void setType(String typeIn){
	
		this.type=typeIn;
	}//setRank
/*********************************/
/**
	method sets the rank of a car.
	@param rankIn
**/

	public void setPower(String powerIn){
	
		this.power=powerIn;
	}//setRank


/*********************************/
/**
	method gets rank and suit of card.
**/
	public String getCardInfo(){
	
			
		return getType()+getCount();
	}//getCardInfo

/*********************************/
/**
	method sends card info toString.
**/
	public String toString(){


		return getType()+getCount();
	}//toString

/*********************************/
/**
	method checks if two cards are equal.
	@param cardX -card to be checked
**/
	public boolean equals(Card cardX){
	
		if (type==cardX.getType() && count==cardX.getCount())
			return true;
		return false;
	}//equals

/*************constructor********************/
/**
	Constructor of a new Card object based on parameters rankIn and suitIn.
	@param typeIn -desired type
	@param suitIn -desired suit
**/
	public Card(String typeIn,String countIn){
		setType(typeIn);
		setCount(countIn);
	
	}//constructor

/*************power card constructor********************/
/**
	Constructor of a new Card object based on parameter power.
	@param powerIn -desired powerIn
	@
**/
	public Card(String powerIn){
		setPower(powerIn);
		
	
	}//constructor
/*********************************/
	public static void main(String[] args){

		String rank0="0";
		int score;
		String rank1="1";
		String rank2="2";
		String rank8="8";
		String rank9="9";
		String pick="pick2";
		String swap="swap";
		String peek="peek";
		//card 1 
		Card card1=new Card(rank1,"A");
		System.out.println(card1);

		//card2
		Card card2=new Card(rank2,"C");
		System.out.println(card2);


		//card3
		Card card3=new Card(rank8,"D");
		System.out.println(card3);

		//powerCard pick2
		Card cardPick2=new Card(pick,"B");
		System.out.println(cardPick2);		
		//powerCard swap
		Card cardSwap=new Card(swap,"B");
		System.out.println(cardSwap);
		//powerCard peek
		Card cardPeek=new Card(peek,"B");
		System.out.println(cardPeek);
		
	}//main	
	
	
}
