package main;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		boolean replay = true;
		while(replay) {
			Game game = new Game();
			game.playGame();
			replay = game.askForReplay();
		}
		System.out.println("Thanks for playing, hope you had fun!");
		
		
	}

}
