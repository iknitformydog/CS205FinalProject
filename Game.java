/*
 * CS205
 * Game.java
 * Set up game and play Rat-a-tat-cat until point total reached
 */

//Dev Notes
//Database inserts here?
//How many parameters does a player.doTurn method need?
//Total score attached to player?
//power card functionality?

public class Game {
	
	Player user;
	Player cpu;
	Deck deck;
	? discard;
	
	public Game(int difficulty){
		Player user = new Player(user);
		Player cpu = new Player(difficulty);
	}
	
	public void playGame(){
		while(user.getTotalScore < 60 && cpu.getTotalScore < 60){
			playRound();
		}
		endGame();
	}
	
	public void playRound(){
		Deck deck = new Deck();
		deck.shuffle();
		dealCards(user,cpu);
		while(!deck.isEmpty() && !ratCall){
			user.doTurn(deck,discard); //return set of variables indicating actions
			//pass new state to gameGUI
			cpu.doTurn(deck);
		}
		endRound();
	}
	
	public void endGame(){
		//Check to see who is winner, react accordingly
		//Send data to database
	}
	
	public void endRound(){
		//update each player's total score
	}
	
	public void dealCards(){
		//deal cards one by one to players, check for power cards
	}
	
}
