package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controllor.BattleshipBoard;
import Controllor.BattleshipBoardPlacement;
import Controllor.Player;

public class BattleshipGridPlacement extends JPanel{
	
	// this class is copy paste of the class BattleshipGrid except that this class is linked to BattleshipBoardPlacement in the controllor package instead of BattleshipBoard
	// this method is not efficient, but this was easier to link my buttons 
	
	private final int PANELWIDTH;
	private final int PANELHEIGHT;
	
	private BattleshipBoardPlacement boardP;
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

	private final int SCORINGM1 = 1;
	private final int SCORINGM2 = 2;

	
	public BattleshipGridPlacement(int width, int heigth, BattleshipBoardPlacement battleshipBoardPlacement, PanelLabels pL, QuitHscore qhs, int numPlayers, int scoringMethod) {
		
		PANELWIDTH = width;
		PANELHEIGHT = heigth;
		
		boardP = battleshipBoardPlacement;
		pLa = pL;
		quith = qhs;
		
		players = new Player[numPlayers];
		
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player(i+1);
		}
		
		if (scoringMethod == SCORINGM2) {
			players[0].setPoints(5);
			players[1].setPoints(0);
		} else {
			players[0].setPoints(0);
			players[1].setPoints(0);
		}
		

		
		pLa.scoreSystem(players[0].getPoints(), players[1].getPoints(), 1);

		
		wdt = PANELWIDTH/boardP.rowBoard;
		hgt = PANELHEIGHT/boardP.colBoard;
		
		wdt2 = PANELWIDTH/(2*boardP.rowBoard);
		hgt2 = PANELWIDTH/(2*boardP.colBoard);
		

		
		setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
		
		addMouseListener(new MouseAdapter() { // make a mouseAdapter so we can use 
			public void mouseClicked(MouseEvent e) {
				attackBoard(e.getX(), e.getY());
				
				repaint();
				
				currentPlayer++;
				if (currentPlayer == players.length) {
					currentPlayer = 0;	
				}
				
				scorePlayer1 = players[0].getPoints();
				scorePlayer2 = players[1].getPoints();
				
				pLa.scoreSystem(scorePlayer1, scorePlayer2, currentPlayer+1);


				if (boardP.isGameOver()) {
					
					saveHighScore();
					
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
			
	
	
	@Override
	public void paintComponent(Graphics g) {
		setBackground(Color.white);
		super.paintComponent(g);
		drawRect(g);
	}
	

	private void drawRect(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0,  0,  PANELWIDTH, PANELHEIGHT);
		
		for (int i = 0; i < boardP.rowBoard; i++) {
			for (int j = 0; j < boardP.colBoard; j++) {
				g.setColor(Color.gray);
				if (boardP.grid[i][j] == boardP.MISS) {
					g.setColor(Color.blue);
				}
				if (boardP.grid[i][j] == boardP.HIT_CARRIER) {
					g.setColor(Color.red);
				}
				if (boardP.grid[i][j] == boardP.HIT_BATTLESHIP) {
						g.setColor(Color.cyan);
				}
				if (boardP.grid[i][j] == boardP.HIT_SUBMARINE) {
					g.setColor(Color.yellow);
				}
				if (boardP.grid[i][j] == boardP.HIT_DESTROYER) {
					g.setColor(Color.MAGENTA);
				}
				g.fillRect(space+i*wdt, space+j*hgt, wdt-2*space, hgt-2*space);
			}
		}
	}
	
	private void attackBoard(int mouseX, int mouseY) {
		
		mx = Math.floorDiv(mouseX, wdt); //This function causes the mouse click (pixsel) is divided by the height or the width
		my = Math.floorDiv(mouseY, hgt); //  of the board and then rounded down to an integer.
		
		players[currentPlayer].attack2(boardP, mx, my);	

	}	
	
	public void saveHighScore() {
		
		try {
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
    try { BufferedWriter output = new BufferedWriter(new FileWriter("Highscore.txt", true));
        
    	output.newLine();
        output.append("" + highscore);
        output.close();

	    } catch (IOException e) {
			e.printStackTrace();
	    }
	}
	// make getter of currentPlayer
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Player getPlayer(int num) {
		return players[num];
	}


}
