package Controllor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
	
public class BattleshipBoard {

		
	//variable
		
		//Field status
		public int[][] grid; // 2 arrays makes the board
		public int rowBoard; // needed for the JSPinners
		public int colBoard; // needed for the JSPinners
		
		
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
	

		
	// Constructor 
		
		public BattleshipBoard(int x, int y) {
			rowBoard = x;
			colBoard = y;
			grid = new int[rowBoard][colBoard];

			initializeField();
			placeAllShips();
			isGameOver();
		}
		
	//methods
			// initialize the board to the empty state; make sure all fields are empty	(empty means = 0)	
		public void initializeField() {
			for (int i = 0; i < rowBoard; i++) { 
				for (int j = 0; j < colBoard; j++) {
					grid[i][j] = EMPTY;	}
				}
		}

		public void placeAllShips(){
		
			// select random positions for the ships
		    Random random = new Random(); // make random integers
		    
		    // create new variables  
			 // select orientation and position for the carrier
		    int r; // row coordinate
		    int c; // column coordinate
		    boolean orientation; // vertical = true, horizontal = false
		    
		   //initialize Carrier     
		    // ask for 3 new randoms (orientation, c and r)
		    	orientation = random.nextBoolean(); //vertical = true, horizontal = false (see ships statements, y++ = vertical, else x++ = horizontal
	            r = random.nextInt(rowBoard-4); // choose int -4 because otherwise it would go out of the screen   
	            c = random.nextInt(colBoard-4);  
	            // with the for loop the boat is placed on the empty field
	            for (int i = 0; i < 5; i++)  {
	            	if (orientation) {
	            		grid[r][c+i] = HAS_CARRIER; // place the random numbers for c and r in the grid, for loop add 1 each time (5times)
	            	} else {
	            		grid[r+i][c] = HAS_CARRIER;
	            		
	            	}}
		   
			// initialize Battleship
	            // ask for 3 new variables and check with the do - while loop if the chosen numbers and boolean is ok, if not chose new variables
				do {
				orientation = random.nextBoolean();
				r = random.nextInt(rowBoard-3);                
	            c = random.nextInt(colBoard-3); 
				} while ((grid[r][c] != 0) || (orientation && ((grid[r][c+1] != 0) || (grid[r][c+2] != 0) || (grid[r][c+3] != 0))) 
							|| (!orientation && ((grid[r+1][c] != 0) || (grid[r+2][c] != 0) || (grid[r+3][c] != 0))));
				// for loop to place the boats on the field 
				for (int i = 0; i < 4; i++) {
					if (orientation) { 
								grid[r][c+i] = HAS_BATTLESHIP;
					}else {
								grid[r+i][c] = HAS_BATTLESHIP;
					}
				}
			
					
			// initialize Submarine
				do {
				orientation = random.nextBoolean();
				r = random.nextInt(rowBoard-2);                
	            c = random.nextInt(colBoard-2); 
				} while ((grid[r][c] != 0) || (orientation && ((grid[r][c+1] != 0) || (grid[r][c+2] != 0))) 
							|| (!orientation && ((grid[r+1][c] != 0) || (grid[r+2][c] != 0))));
				for (int i = 0; i < 3; i++) {
					if (orientation) {
								grid[r][c+i] = HAS_SUBMARINE; 
					}else {
								grid[r+i][c] = HAS_SUBMARINE;
					}		
				}	

				// initialize Destroyer
				do {
				orientation = random.nextBoolean();
				r = random.nextInt(rowBoard-1);                
	            c = random.nextInt(colBoard-1);  
				} while ((grid[r][c] != 0) || (orientation && ((grid[r][c+1] != 0))) 
							|| (!orientation && ((grid[r+1][c] != 0))));
				for (int i = 0; i < 2; i++) {
					if (orientation) {
						grid[r][c+i] = HAS_DESTROYER;
					}else {
						grid[r+i][c] = HAS_DESTROYER;
					}
				}		
		}
	

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
	
		
 
	

	

