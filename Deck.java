
import javax.swing.*;
import java.util.*;

public class Deck {

	

	String count;
	String rank;
	ArrayList<Card> cardDeck=new ArrayList<Card>();
	
	ListIterator<Card> iter;
	
	String count1="A";
	String count2="B";
	String count3="C";
	String count4="D";
	String count5="E";
	String count6="F";
	String count7="G";
	String count8="H";
	String count9="I";


/*************constructor********************/
/**
	Constructor of a Deck object representing a deck of playing cards.
**/
	public Deck(){
		
		///makes a deck of 54 cards
		//card9 occurs 9 times-rat card
		//0-8:occur each 4 times
		//card pick2 occurs 3 times
		//card peek occurs 3 times
		//card swap occurs 3 times

		
			
		//makes cards 0-8
		for(int i=0;i<10;i++){	
			String type=""+i;
			
			if(i==9){
				for(int b=0;b<9;b++){
					
					
					if(b==0)
						count=count1;
					if(b==1)
						count=count2;
					if(b==2)
						count=count3;
					if(b==3)
						count=count4;
					if(b==4)
						count=count5;
					if(b==5)
						count=count6;
					if(b==6)
						count=count7;
					if(b==7)
						count=count8;
					if(b==8)
						count=count9;
					
               //////This needs to send in a String pictureName
               ////to the Card constructor
					Card cardInDeck=new Card(type,count,"cards/0A.gif");
					cardDeck.add(cardInDeck);		
				}//for
			}//if
			else{
				for(int c=0;c<4;c++){
				
				
					if(c==0)
						count=count1;
					if(c==1)
						count=count2;
					if(c==2)
						count=count3;
					if(c==3)
						count=count4;
		
					Card cardInDeck=new Card(type,count);
					cardDeck.add(cardInDeck);
				}//for
			}//else
		}//outerFor
		
		
 		//makes 3 pick2 Cards	
 		for(int p=1;p<4;p++){
 			String pStr=""+p;	
 			String typeIn="pick2";
 			
 			Card cardInDeck=new Card(pStr,typeIn);
 			cardDeck.add(cardInDeck);					
 		}//for
// 
// 		//makes 3 swap Cards	
 		for(int s=1;s<4;s++){
 			String sStr=""+s;	
 			String powerIn="swap";
 			
 			Card cardInDeck=new Card(sStr,powerIn);
 			cardDeck.add(cardInDeck);				
 		}//for
// 
 		//makes 3 peek Cards	
 		for(int k=1;k<4;k++){
 			String kStr=""+k;	
 			String powerIn="peek";
 			
 			Card cardInDeck=new Card(kStr,powerIn);
 			cardDeck.add(cardInDeck);						
 		}//for
				
		iter=cardDeck.listIterator();
		
	}//Deck
	
/*********************************/
/**
	method sends deck to a string
**/
	public String toString(){
		
		String testDeckToString="";
		int cardDeckLength=cardDeck.size();
		
		for(int y=0;y<cardDeckLength;y++){
			Card cardNext=(Card)iter.next();
			testDeckToString+=(cardNext + ", ");
		}//for
		return testDeckToString;
	}//toString

/*********************************/
/**
	method gets the top card and removes it from the deck
**/
	public Card getTopCard(){
	
		iter=cardDeck.listIterator();
		//get deck's top card
		Card topCard=iter.next();
		//remove topCard from deck
		cardDeck.remove(topCard);
		this.shuffle();
		iter=cardDeck.listIterator();
		return topCard;
	}//getTopCard

/*********************************/
/**
	method shuffles the deck
**/
	public void shuffle(){
		iter=cardDeck.listIterator();
		Collections.shuffle(cardDeck);
		
	}//shuffle
/*********************************/
/**
	method checks if deck is empty.
**/
	public boolean isEmpty(){
		boolean answer=cardDeck.isEmpty();	
		return answer;
	}//isEmpty


/***************test main******************/
	public static void main(String[] args){
		Deck testDeck=new Deck();
		//testDeck.shuffle();
		System.out.println(testDeck);
	
	}//main
/*********************************/
}
