package GUI;
import Controllor.BattleshipBoard;
import Controllor.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;


public class BattleshipGrid extends JPanel{

	private final int PANELWIDTH;
	private final int PANELHEIGHT;
	
	private BattleshipBoard board;
	private PanelLabels pLa;
	private QuitHscore quith;
	private JLabel statuslabel;
	private StartFrame stf;
	private Player[] players;
	private Player pl;
	public int currentPlayer = 0;
	public int scorePlayer1;
	public int scorePlayer2;
	private int highscore = 0;
	
	private int wdt;
	private int hgt;
	private int wdt2;
	private int hgt2;
	private int space = 1;
	private int mx;
	private int my;

	// used to make a method for the scores
	private final int SCORINGM1 = 1;
	private final int SCORINGM2 = 2;


	
	public BattleshipGrid(int width, int heigth, BattleshipBoard battleshipBoard, PanelLabels pL, QuitHscore qhs, int numPlayers, int scoringMethod) {
		
		// width and heigth of the board
		PANELWIDTH = width;
		PANELHEIGHT = heigth;
		
		// refers to other classes
		board = battleshipBoard;
		pLa = pL;
		quith = qhs;
		
		
		// select new players, the 'numPlayers' refers to 0 or 1, 0 is player 1 and 1 is player 2
		players = new Player[numPlayers];
		
		// make an array of players, i can be 0 or 1
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player(i+1);
		}
	
		// scoring method, in with settlement start the player with 5 points
		if (scoringMethod == SCORINGM2) {
			players[0].setPoints(5);
			players[1].setPoints(0);
		} else {
			players[0].setPoints(0);
			players[1].setPoints(0);
		}
		

		// scoringsystem linked to the labels in panelLabels
		pLa.scoreSystem(players[0].getPoints(), players[1].getPoints(), 1);
		
		
		// made so that every column and row can be selected with the Jspinners and that the board change his size 
		wdt = PANELWIDTH/board.rowBoard;
		hgt = PANELHEIGHT/board.colBoard;
		// same, for using the JSpinners
		wdt2 = PANELWIDTH/(2*board.rowBoard);
		hgt2 = PANELWIDTH/(2*board.colBoard);
		

		
		setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
		
		// make a mouse click event so that when you click on the board, with pixels, recalculated to a interger for the rows and columns, that the color can change to a specific color for each boat
		addMouseListener(new MouseAdapter() { 
			public void mouseClicked(MouseEvent e) {
				attackBoard(e.getX(), e.getY()); // attack mode
				
				repaint(); // standard used with rectangles
				
				// select with player is player after a mouseclick
				// players.length refers to 0 or 1, because currentPlayer ++ will always be more, if we did not make an if statement
				// the if statement says that when currentPlayer is 1, after that it will be 0 again (almost a loop)
				currentPlayer++;
				if (currentPlayer == players.length) {
					currentPlayer = 0;	
				}
				
				
				scorePlayer1 = players[0].getPoints();
				scorePlayer2 = players[1].getPoints();
				
				// selected score system after each click it will be updated
				pLa.scoreSystem(scorePlayer1, scorePlayer2, currentPlayer+1);

				// method is Game over of BattleshipBoard
				if (board.isGameOver()) {
					
					// when the game is over, then save the highscore
					saveHighScore();
					
					// if statement which says which player won
					if(scorePlayer1 > scorePlayer2) {
						JOptionPane.showMessageDialog(null, "Player 1 wins");
					} else {
						JOptionPane.showMessageDialog(null, "Player 2 wins");
					} 
					System.exit(0);
					}
			}
		});
		
	}
			
	
	// make the board visual 
	@Override
	public void paintComponent(Graphics g) {
		setBackground(Color.white);
		super.paintComponent(g);
		drawRect(g);
	}
	
	// i used rectangles to make a board
	private void drawRect(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0,  0,  PANELWIDTH, PANELHEIGHT);
		
		// fillRect means, I use a filled rectangle 
		for (int i = 0; i < board.rowBoard; i++) {
			for (int j = 0; j < board.colBoard; j++) {
				g.setColor(Color.gray);
				if (board.grid[i][j] == board.MISS) {
					g.setColor(Color.BLUE);
				}
				if (board.grid[i][j] == board.HIT_CARRIER) {
					g.setColor(Color.RED);
				}
				if (board.grid[i][j] == board.HIT_BATTLESHIP) {
						g.setColor(Color.CYAN);
				}
				if (board.grid[i][j] == board.HIT_SUBMARINE) {
					g.setColor(Color.YELLOW);
				}
				if (board.grid[i][j] == board.HIT_DESTROYER) {
					g.setColor(Color.MAGENTA);
				}
				g.fillRect(space+i*wdt, space+j*hgt, wdt-2*space, hgt-2*space);
			}
		}
	}
	
	private void attackBoard(int mouseX, int mouseY) {
		
		mx = Math.floorDiv(mouseX, wdt);
		my = Math.floorDiv(mouseY, hgt);	
		
		players[currentPlayer].attack(board, mx, my);	

	}	
	
	// method to save the highscore
	public void saveHighScore() {
		
		try {
			
			// read the whole file 
	        BufferedReader reader = new BufferedReader(new FileReader("Highscore.txt"));
	        String line = reader.readLine();
	        while (line != null) {                // read the score file line by line
	            try {
	                
	                if (scorePlayer1 > highscore) { // and keep track of the largest
	                    highscore = scorePlayer1;
	                } else if (scorePlayer2 > highscore){
	                    highscore = scorePlayer2;
	                }
	                
	            } catch (NumberFormatException e) {
	        		e.printStackTrace();
	            }
	            line = reader.readLine();
	        }
	        reader.close();

	    } catch (IOException e) {
			e.printStackTrace();
	    }
	
	
	 // append the last score to the end of the file
    try {
        BufferedWriter output = new BufferedWriter(new FileWriter("Highscore.txt", true));
        output.newLine();
        output.append("" + highscore);
        output.close();

        // when there are errors, the rest of the program will still work 
	    } catch (IOException e) {
			e.printStackTrace();
    }
}
	
	// getter
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	// getter
	public Player getPlayer(int num) {
		return players[num];
	}
	

}
