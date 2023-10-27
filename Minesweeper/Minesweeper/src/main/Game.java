package main;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
public class Game {
	private int bombs = 1;
	private int gridSize= 10;
	private int[][] hidden = new int[this.gridSize][this.gridSize];
	private int[][] visible = new int[this.gridSize][this.gridSize];
	private boolean gameOver;
	private Scanner s;

	public Game (Scanner s){
		this.s = s;
	}
	public boolean isGameOver() {
		return gameOver;
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void setupHidden() {
		setupBombs();		
	}
	private void setupBombs() {
		
		Random random = new Random();
		for(int i = 0; i < this.bombs; i++) {
			
			int row = random.nextInt(this.gridSize);
			int col = random.nextInt(this.gridSize);
			this.hidden[row][col] = -1;
			checkAdjBombs(row,col);
		}
		
	}
	
	
	public void displayHidden() {
		System.out.print("\t ");
		for(int i =0; i < gridSize; i ++) {
			System.out.print(String.format("| %d ", i+1));
		}
		System.out.print("| \n");
		for(int i=0; i < gridSize; i++) {
			System.out.print(String.format("%d \t | ", i+1));
			for(int j=0; j<gridSize; j++) {
				if(this.hidden[i][j]!=-1) {
					System.out.print(this.hidden[i][j]);
				}
				else {
					System.out.print("■");
				}
				System.out.print(" | ");
			}
			System.out.println();
		}
	}
	
	public void setupVisible() {
		for(int i = 0; i <gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				this.visible[i][j]=50;
			}
		}
	}
	public void displayVisible() {
		System.out.print("\t ");
		for(int i =0; i < gridSize; i ++) {
			System.out.print(String.format("| %d ", i+1));
		}
		System.out.print("| \n");
		for(int i=0; i < gridSize; i++) {
			System.out.print(String.format("%d \t | ", i+1));
			for(int j=0; j<gridSize; j++) {
				if(this.hidden[i][j]==-1) {
					System.out.print("■");
				}
				else if (this.visible[i][j] == 50) {
					System.out.print("■");
				}
				else {
					System.out.print(this.visible[i][j]);
				}
				System.out.print(" | ");
			}
			System.out.println();
		}
	}
	public void setVisibleFromGuess(int row, int col) {
		this.visible[row][col] = this.hidden[row][col];
		if(this.visible[row][col]==-1) {
			System.out.println("You hit a bomb, so sorry... ");	
			setGameOver(true);
		}
		cascade(row,col);
		displayVisible();
		
	}
	private  void checkAdjBombs(int row, int col) {
		int tempRow = 0;
		int tempCol = 0;
		for(int i = -1; i <=1; i++) {
			for(int j = -1; j<=1; j++) {
				tempRow = row+i; 
				tempCol = col+j;
				// This try/catch is to catch OOB errors for edge cases when counting bombs (eg, 1,1 will cause errors)
				// Continue if error happens. 
				
				try {
					if(this.hidden[tempRow][tempCol]!=-1) {
						this.hidden[tempRow][tempCol]++;
					}
				} catch (Exception e2) {
						
					continue;
				}
				
								
			}
		}
	}
	private void cascade(int startRow, int startCol) {
		Queue<int[]> queue = new LinkedList<>();
	    boolean[][] visited = new boolean[gridSize][gridSize];

	    queue.add(new int[]{startRow, startCol});
	    visited[startRow][startCol] = true;

	    while (!queue.isEmpty()) {
	        int[] current = queue.poll();
	        int row = current[0];
	        int col = current[1];

	        if (hidden[row][col] != 0) {
	            visible[row][col] = hidden[row][col];
	            continue;
	        }

	        visible[row][col] = hidden[row][col];

	        for (int i = -1; i <= 1; i++) {
	            for (int j = -1; j <= 1; j++) {
	                int tempRow = row + i;
	                int tempCol = col + j;
	                try {
	                	if (!visited[tempRow][tempCol]) {
		                    queue.add(new int[]{tempRow, tempCol});
		                    visited[tempRow][tempCol] = true;
		                }
	                }catch(Exception e2) {
	                	continue;
	                }
	                
	            }
	        }
	    }
	}
	
	private int getValidCoordinate(String axis) {
		int coordinate;
		while(true) {
			System.out.println("Please enter a new " + axis +" coordinate (1-10): ");
			String userInput = s.next();
			
			try {
				coordinate = Integer.parseInt(userInput);
				if(coordinate >=1 && coordinate <=10) {
					return coordinate;
					
				}

				else {
					System.out.println("Please make sure that you input an integer between 1-10");
				}
			}
			catch(NumberFormatException e) {
				System.out.println("Please make sure that you input an integer between 1-10");
				
			}
		}
	}
	
	public void playGame() {
		
		System.out.println("\tWelcome to a makeshift Minesweeper!");
		setupHidden();
		setupVisible();
		displayVisible();
		
		while(isGameOver()==false) {
			int y = getValidCoordinate("Y")-1;
			int x = getValidCoordinate("X")-1;
			setVisibleFromGuess(y, x);
			isGameFinished();
			
		}
		
		
	}
	private boolean isGameFinished() {
		int remTiles =0;
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j< gridSize; j++) {
				if(visible[i][j]==50) {
					remTiles++;
				}
			}
		}
		if(remTiles == bombs){
			setGameOver(true);
			System.out.println("Congrats, you beat the game!");
		}
		return isGameOver();
	}
	public boolean askForReplay() {
		System.out.println("Do you want to play again? Y/N");
		String response = s.next().toLowerCase();	
		return response.equals("y");
	}
}


