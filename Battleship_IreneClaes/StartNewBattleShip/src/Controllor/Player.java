package Controllor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



public class Player{
	
	private int number;
	private int guesses;
	private int hits;
	private int points;
	
	public Player(int playerNumber) {
		number = playerNumber; // number of the player 0 or 1
		guesses = hits = points = 0; // initialize the beginning of the statistics 
	}

	public void attack(BattleshipBoard br, int row, int col) {
		// hit or miss
		// made for the changing of the colors of the GUI and for the points/score system 
		guesses++;
		
		if (br.grid[row][col] == br.EMPTY) {
			br.grid[row][col] = br.MISS;
			return;
		}
		
		hits++;
		if (br.grid[row][col] == br.HAS_CARRIER) {
			br.grid[row][col] = br.HIT_CARRIER; // change number of the field in the console + make it possible to change the color in the GUI
			points += br.POINT_CARRIER; // point += means the points from before + the points of the carrier 
		}

		
		
		if (br.grid[row][col] == br.HAS_BATTLESHIP) {
			br.grid[row][col] = br.HIT_BATTLESHIP;
			points += br.POINT_BATTLESHIP;
		}
		
		if (br.grid[row][col] == br.HAS_SUBMARINE) {
			br.grid[row][col] = br.HIT_SUBMARINE;
			points += br.POINT_SUBMARINE;
		}
		
		if (br.grid[row][col] == br.HAS_DESTROYER) {
			br.grid[row][col] = br.HIT_DESTROYER;
			points += br.POINT_DESTROYER;
		}

	}
	
	public void attack2(BattleshipBoardPlacement brP, int row, int col) {
		// hit or miss
		guesses++;
		
		if (brP.grid[row][col] == brP.EMPTY) {
			brP.grid[row][col] = brP.MISS;
			return;
		}
		
		hits++;
		if (brP.grid[row][col] == brP.HAS_CARRIER) {
			brP.grid[row][col] = brP.HIT_CARRIER;
			points += brP.POINT_CARRIER;
		}
		
		if (brP.grid[row][col] == brP.HAS_BATTLESHIP) {
			brP.grid[row][col] = brP.HIT_BATTLESHIP;
			points += brP.POINT_BATTLESHIP;
		}
		
		if (brP.grid[row][col] == brP.HAS_SUBMARINE) {
			brP.grid[row][col] = brP.HIT_SUBMARINE;
			points += brP.POINT_SUBMARINE;
		}
		
		if (brP.grid[row][col] == brP.HAS_DESTROYER) {
			brP.grid[row][col] = brP.HIT_DESTROYER;
			points += brP.POINT_DESTROYER;
		}

	}
	
	// setter method, which can set points at the beginning of the game, in an other class
	public void setPoints(int points) {
		this.points = points;
	}
	// getter method to get the points in an other class 
	public int getPoints() {
		return points; 
	}
	

}
