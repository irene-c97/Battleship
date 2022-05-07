package Controllor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class BattleshipBoardPlacement {

	
//variable
	
	//Field status
	public int[][] grid;
	public int rowBoard;
	public int colBoard;
	
	
	// The playerField can be empty or can have a boat, it can be hit or not
	public final int EMPTY = 0;		// empty field
	public final int NOGUESSES = 0;	// no guesses yet
	public final int HAS_CARRIER = 5;
	public final int HAS_BATTLESHIP = 4;
	public final int HAS_SUBMARINE = 3;
	public final int HAS_DESTROYER = 2;
	public static final int HIT_CARRIER = 6;
	public static final int HIT_BATTLESHIP = 7;
	public static final int HIT_SUBMARINE = 8;
	public static final int HIT_DESTROYER = 9;
	public static final int MISS = 1;
	
	// points
	public static final int POINT_CARRIER = 4;
	public static final int POINT_BATTLESHIP = 5;
	public static final int POINT_SUBMARINE = 7;
	public static final int POINT_DESTROYER = 9;

	public int cX1Carrier;
	public int cY1Carrier;
	public int cX2Carrier;
	public int cY2Carrier;

	public int cX1Battleship;
	public int cY1Battleship;
	public int cX2Battleship;
	public int cY2Battleship;

	public int cX1Submarine;
	public int cY1Submarine;
	public int cX2Submarine;
	public int cY2Submarine;
	
	public int cX1Destroyer;
	public int cY1Destroyer;
	public int cX2Destroyer;
	public int cY2Destroyer;
		
// Constructor 
	
	public BattleshipBoardPlacement(int x, int y) {
		rowBoard = x; // needed for when the number in the text file changes 
		colBoard = y; // same as rowBoard
		grid = new int[rowBoard][colBoard];
		
		// methods used by the constructor 
		initializeField(); 
		placeTextShips();
		isGameOver();
	}
	
//methods
	// initialize the board to the empty state; make sure all fields are empty		

public void initializeField() {
	for (int i = 0; i < rowBoard; i++) { 
		for (int j = 0; j < colBoard; j++) {
			grid[i][j] = EMPTY;	}
	}
}
	
	public void placeTextShips() {
		
		try(
			
				// make it possible to read the text file
			Scanner scan = new Scanner(new File("newShipPlacement.txt"))) { // scan the new made file after pressing on start game 
			
			// read first line = row and columns (square field)
			rowBoard = colBoard = scan.nextInt();
			
						
			
			while(scan.hasNextLine() ) {
				// while loop that scan all lines, in the text file was a white space, that one I did deleted, otherwise it wont run 
				
				String coordinate = scan.next(); // give the whole line				
				String[] coordinates = coordinate.split("(;)|(\\*)"); // scan the coordinates with Regular expressions to select each part of a line 
				String boat = coordinates[0]; // the first column of the line is the name of the boat
				if (boat.equals("Carrier")) {
					cX1Carrier = (Integer.valueOf(coordinates[1])-1); // the 2nd column is the number of the first x coordinate (i did minus 1 for each coordinate because if you have 8 rows and columns and you start with 0, 8 can not be possible)
					cY1Carrier = (Integer.valueOf(coordinates[2])-1);
					cX2Carrier = (Integer.valueOf(coordinates[3])-1);
					cY2Carrier = (Integer.valueOf(coordinates[4])-1);
					
					// I made a for loop to place the ship on the empty board, just like i did in BattleshipBoard, for every boat
					// first check if the boat is vertical or horizontal 
					// if it is vertical, then +i to the Y-coordinate, else +i to the X-coordinate
					for (int i = 0; i < 5; i++) {
						if (cX1Carrier == cX2Carrier) { //vertical
							grid[cX1Carrier][cY1Carrier+i] = HAS_CARRIER;
						} else if (cX1Carrier < cX2Carrier) { //horizontal 
							grid[cX1Carrier+i][cY1Carrier] = HAS_CARRIER;
						}
					}
					
					// after I found the coordinates, I added a for loop 
					// first the for loop has to check if the boats are vertical or horizontal 
					// the same as before, if it is vertical, then +i to the Y-coordinate, else +i to the X-coordinate
					// but now we needed to check if there was already an boat on this position
					// so i made an inner for loop for every orientation: if the boat is vertical check for each box of the boat if it is already filled (!=0)
					// the same for the horizontal position
					// after it is ok to place the boat, then we did an else statement that place the boat on the board
					// when there is already a boat, it will give an error message if the boats are placed on top of each other
					// the title of the messagedialog is "error", and the error_message is an error-symbol, which make clear that there was an error 
					// i did not find how to go back to the frame, so the whole game is closed
					
				} else if (boat.equals("Battleship")) { // search for coordinates
					cX1Battleship = (Integer.valueOf(coordinates[1])-1);
					cY1Battleship = (Integer.valueOf(coordinates[2])-1);
					cX2Battleship = (Integer.valueOf(coordinates[3])-1);
					cY2Battleship = (Integer.valueOf(coordinates[4])-1);
							
						for (int i = 0; i < 4; i++) {
							if (cX1Battleship == cX2Battleship) { // check if it is vertical
								if (grid[cX1Battleship][cY1Battleship+i] != 0) { // check if there is a boat already
									JOptionPane.showMessageDialog(null, "Boats are placed on top of each other", "Error", JOptionPane.ERROR_MESSAGE);
									System.exit(0);	// show error message if there is a boat already and close the game
								} else { // if there is no boat, place the boat in the vertical direction
								grid[cX1Battleship][cY1Battleship+i] = HAS_BATTLESHIP;
								}
							} else if (cX1Battleship < cX2Battleship) { //horizontal 
								if (grid[cX1Battleship+i][cY1Battleship] != 0) { // check if there is a boat
									JOptionPane.showMessageDialog(null, "Boats are placed on top of each other", "Error", JOptionPane.ERROR_MESSAGE);
									System.exit(0);	// error 
								} else { // place the boat in the horizontal direction 
								grid[cX1Battleship+i][cY1Battleship] = HAS_BATTLESHIP;
								}
							}	
						}
					// same code, but for the battleship
				} else if (boat.equals("Submarine")) {
					cX1Submarine = (Integer.valueOf(coordinates[1])-1);
					cY1Submarine = (Integer.valueOf(coordinates[2])-1);
					cX2Submarine = (Integer.valueOf(coordinates[3])-1);
					cY2Submarine = (Integer.valueOf(coordinates[4])-1);
					
					for (int i = 0; i < 3; i++) {
						if (cX1Submarine == cX2Submarine) { //vertical
							if (grid[cX1Submarine][cY1Submarine+i] != 0) {
								JOptionPane.showMessageDialog(null, "Boats are placed on top of each other", "Error", JOptionPane.ERROR_MESSAGE);
								System.exit(0);	
							} else {
							grid[cX1Submarine][cY1Submarine+i] = HAS_SUBMARINE;
							}
						} else if (cX1Submarine < cX2Submarine) { //horizontal 
							if (grid[cX1Submarine+i][cY1Submarine] != 0) {
								JOptionPane.showMessageDialog(null, "Boats are placed on top of each other", "Error", JOptionPane.ERROR_MESSAGE);
								System.exit(0);	
							} else {
							grid[cX1Submarine+i][cY1Submarine] = HAS_SUBMARINE;
							}			
							}
					}
					
				} else if (boat.equals("Destroyer")) {
					cX1Destroyer = (Integer.valueOf(coordinates[1])-1);
					cY1Destroyer = (Integer.valueOf(coordinates[2])-1);
					cX2Destroyer = (Integer.valueOf(coordinates[3])-1);
					cY2Destroyer = (Integer.valueOf(coordinates[4])-1);
						
					for (int i = 0; i < 2; i++) {
						if (cX1Destroyer == cX2Destroyer) { //vertical
							if (grid[cX1Destroyer][cY1Destroyer+i] != 0) {
								JOptionPane.showMessageDialog(null, "Boats are placed on top of each other", "Error", JOptionPane.ERROR_MESSAGE);
								System.exit(0);	
							} else {
							grid[cX1Destroyer][cY1Destroyer+i] = HAS_DESTROYER;
							}
						} else if (cX1Destroyer < cX2Destroyer) { //horizontal 
							if (grid[cX1Destroyer+i][cY1Destroyer] != 0) {
								JOptionPane.showMessageDialog(null, "Boats are placed on top of each other", "Error", JOptionPane.ERROR_MESSAGE);
								System.exit(0);	
							} else {
							grid[cX1Destroyer+i][cY1Destroyer] = HAS_DESTROYER;
						}
						}
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// this method is made to check if the field was already hit, but if I run it in BattleshipGridPlacement it always gave a sing that it was already clicked, so i did not use it
	public boolean isValidMove() {
		// checks if the move is ok
		for (int x = 0; x < rowBoard; x++) {
			 for (int y = 0; y < colBoard; y++) {
				 if (grid[x][y] == EMPTY || grid[x][y] == HAS_CARRIER || grid[x][y] == HAS_BATTLESHIP || grid[x][y] == HAS_SUBMARINE || grid[x][y] == HAS_DESTROYER) {
						return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean isGameOver() {
		// is game over when all the ships where hit 
		for (int x = 0; x < rowBoard; x++) {
			for(int y = 0; y < colBoard; y++) {
				if (grid[x][y] == 5 || grid[x][y] == 4 || grid[x][y] == 3 || grid[x][y] == 2) {
					return false;
				}
			}
		}
		return true;
	}


	
	public void emptyBoard() {
		//reset the board 
		for (int x = 0; x < rowBoard; x++) {
			 for (int y = 0; y < colBoard; y++) {
				 grid[x][y] = 0;
			 }
		}
	}
	public void printGrid(){ 
    	   for (int x = 0; x < rowBoard; x++)  {
    	      for (int y = 0; y < colBoard; y++) {
 	    	    	  System.out.print(grid[x][y]);
    	      }
    	    System.out.println();
    	   }
    	   
	}
	
}
