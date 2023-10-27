package main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		boolean replay = true;
		while(replay) {
			Game game = new Game(scan);
			game.playGame();
			replay = game.askForReplay();
		}
		System.out.println("Thanks for playing, hope you had fun!");
		scan.close();
		
	}

}
