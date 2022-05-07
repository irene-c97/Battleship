package GUI;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import Controllor.BattleshipBoard;


public class PanelLabels extends JPanel {

	private JLabel player1;
	private JLabel turn;
	private JLabel player2;
	private JLabel pl1;
	private JLabel turnPs;
	private JLabel pl2;
		
		
public PanelLabels(int width, int heigth) {
	setPreferredSize(new Dimension(width, heigth));
	setBackground(Color.white);
	
	setLayout(new GridLayout(2,0));
	
	//create labels player1, 2 and turn
	// give labels a name and choose a color (when it is black it is not necessary to write it down, the color will be automatic black
	
	player1 = new JLabel("Score Player 1: ", JLabel.CENTER); // label with the name score player 1
	player1.setForeground(Color.BLACK); // choose color of the player
	
	turn = new JLabel("Turn: ", JLabel.CENTER);
	turn.setForeground(Color.BLACK);
	
	player2 = new JLabel("Score Player 2: ", JLabel.CENTER);
	player2.setForeground(Color.BLACK);
	
	pl1 = new JLabel("", JLabel.CENTER);
	pl1.setForeground(Color.BLACK);
	
	turnPs = new JLabel("", JLabel.CENTER);
	turnPs.setForeground(Color.BLACK);
	
	pl2 = new JLabel("", JLabel.CENTER);
	pl2.setForeground(Color.BLACK);
	
	
	//add labels 
	add(player1);
	add(turn);
	add(player2);
	add(pl1);
	add(turnPs);
	add(pl2);
}

public void scoreSystem(int p1points, int p2points, int currentP) {
	
	// update the labels (linked with battleshipGrid and BattleshipGridPlacement
	pl1.setText("" + p1points);
	pl2.setText("" + p2points);
	turnPs.setText("Player " + currentP);
}
}
