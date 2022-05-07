package GUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;

import Controllor.BattleshipBoard;
import Controllor.BattleshipBoardPlacement;


public class StartFrame {
	
	
    private static final int PANELWIDHT = 520;
    private static final int PANELHEIGHT = 520;	    
    
    private BattleshipGrid battleshipGrid;
    private BattleshipGridPlacement battleshipGrid2;
    private PanelLabels pLabels;
    private QuitHscore qh;
    
    private int scoringMode;
    int rows;
    int cols;
    
	
	public StartFrame() {

		// set up components 
		// overall frame
		JFrame frame = new JFrame();
		// Buttons
		JButton buttonRules = new JButton("Rules");		
		JButton buttonExit = new JButton("Exit");
		JButton buttonStartGame = new JButton("Start Game");
		JButton buttonHighSc = new JButton("High Score");
		JButton shipPlacement = new JButton("Choose Ship Placement");
		
		// labels
		JLabel labelTitel = new JLabel("Welcome to Battleship", JLabel.CENTER);
		labelTitel.setFont(new Font("Arial", Font.PLAIN, 30));
		JLabel labelSubTitel = new JLabel("Please select your options and get started", JLabel.CENTER);
		labelSubTitel.setFont(new Font("Arial", Font.PLAIN, 18));
		JLabel labelSize = new JLabel("Choose rows and columns: ");
		JLabel chooseScore = new JLabel("Choose Scoring System: ");
		JLabel empty = new JLabel("");
		JLabel empty2 = new JLabel("");


		// spinners and Radiobuttons
		JSpinner spinRows = new JSpinner(new SpinnerNumberModel(6,6,20,1));
		JSpinner spinColumns = new JSpinner(new SpinnerNumberModel(6,6,20,1));

		JRadioButton std = new JRadioButton("Standard", true);
		JRadioButton wS = new JRadioButton("With Settlement");
		ButtonGroup cScore = new ButtonGroup();
		cScore.add(std);
		cScore.add(wS);
 		
		//layout, making a lot of panels to select a specific place on the startframe 
		JPanel north = new JPanel(new GridLayout(2,0));
		north.add(labelTitel);
		north.add(labelSubTitel);
		

		JPanel centerNorth = new JPanel(new GridLayout(0,3));
		centerNorth.add(shipPlacement);
		centerNorth.add(chooseScore);
		centerNorth.add(labelSize);
		
		JPanel centerSouth = new JPanel(new GridLayout(2,3));
		centerSouth.add(empty);
		centerSouth.add(std);
		centerSouth.add(spinRows);
		centerSouth.add(empty2);
		centerSouth.add(wS);
		centerSouth.add(spinColumns);
		
		JPanel center = new JPanel(new GridLayout(2,2));
		center.add(centerNorth, BorderLayout.NORTH);
		center.add(centerSouth, BorderLayout.SOUTH);
		
		JPanel southNorth = new JPanel(new GridLayout());
		southNorth.add(buttonStartGame, BorderLayout.CENTER);
		
		JPanel southSouth = new JPanel(new GridLayout(0,3));
		southSouth.add(buttonRules, BorderLayout.CENTER);
		southSouth.add(buttonHighSc, BorderLayout.CENTER);
		southSouth.add(buttonExit, BorderLayout.CENTER);

		JPanel south = new JPanel(new GridLayout(2,0));
		south.add(southNorth);
		south.add(southSouth);
		
		
		// actionListeners to do something with the buttons, spinners, ...
		buttonExit.addActionListener(Listener -> System.exit(0)); 
		
        buttonStartGame.addActionListener((ActionListener) new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		boardFrame((int)spinRows.getValue(), (int)spinColumns.getValue(), new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // here we listen for the second JFrame being closed so we can bring back the first frame
                    	frame.setVisible(true);
                    }
                });
                // hide the frame when clicked on the button
                frame.setVisible(false);   
        	}
        });
        
        buttonRules.addActionListener((ActionListener) new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		rulesFrame(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // here we listen for the second JFrame being closed so we can bring back the first frame
                    	frame.setVisible(true);
                    }
                });
                // hide the frame when clicked on the button
                frame.setVisible(false); 
        	}
        });
        
        buttonHighSc.addActionListener((ActionListener) new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		hScoreFrame(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // here we listen for the second JFrame being closed so we can bring back the first frame
                    	frame.setVisible(true);
                    }
                });
                // hide the frame when clicked on the button
                frame.setVisible(false); 
        	}
        });
        
        shipPlacement.addActionListener((ActionListener) new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		shipPlaceFrame(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // here we listen for the second JFrame being closed so we can bring back the first frame
                    	frame.setVisible(true);
                    }
                });
                // hide the frame when clicked on the button
                frame.setVisible(false); 
        	}
        });
        
		ActionListener radioListener = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(std.isSelected()) {
        			scoringMode = 1;
        		}
        		if (wS.isSelected()) {
        			scoringMode = 2;
        		}
        	}
        };
        
        std.addActionListener(radioListener);
        wS.addActionListener(radioListener);
        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(600, 250));
		frame.setTitle("BattleShip Game: selection screen");
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(north, BorderLayout.PAGE_START);
		frame.getContentPane().add(center, BorderLayout.CENTER);
		frame.getContentPane().add(south, BorderLayout.PAGE_END);
        frame.setVisible(true);
	}
    private void rulesFrame(WindowAdapter windowAdapter) {
    	
    	JFrame rF = new JFrame("Rules");
    	rF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	rF.setSize(1000, 700);
    	rF.setVisible(true);
    	rF.addWindowListener(windowAdapter);

        JTextArea jrF = new JTextArea();
        jrF.setEditable(false);
        jrF.setLineWrap(true);
        jrF.setWrapStyleWord(true);
        rF.add(jrF);
        jrF.setFont(new Font("Arial", Font.BOLD, 13));

        
        File file = new File("Rules.txt"); 	// choose a file that you want to use
        Scanner scan = null; // scan the file
		try { // used because if the system cannot find the file, there will be no big error and the code will run
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        while (scan.hasNextLine()) {
            String line = scan.nextLine() + "\n"; // make the string and scan the file, all the line, "\n" is used so the enters will be placed
            jrF.append(line); // add the line in the frame 
        }
    }
    
    private void hScoreFrame(WindowAdapter windowAdapter) {
    	JFrame sP = new JFrame("Highscore");
    	sP.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	sP.setSize(400, 500);
    	sP.setVisible(true);
    	sP.addWindowListener(windowAdapter);
    	
        JTextArea jhSF = new JTextArea();
        sP.add(jhSF);
        jhSF.setEditable(false);
        jhSF.setLineWrap(true);
        jhSF.setWrapStyleWord(true);
        jhSF.setFont(new Font("Arial", Font.BOLD, 30));

        BufferedReader reader = null;
		BufferedWriter output  = null;
		
        ArrayList<String> line = new ArrayList<String>();

		try {
	        reader = new BufferedReader(new FileReader("Highscore.txt"));
	        String currentLine = reader.readLine();
	        while (currentLine != null) {                // read the score file line by line
	    
	                line.add(currentLine);
	                
	                currentLine = reader.readLine();
	        }
	    
	     Collections.sort(line);
	     Collections.reverse(line);
	    
	    output = new BufferedWriter(new FileWriter("Highscore.txt"));
	    for (String lines : line) {
	    	output.write(lines);
	    	output.newLine();
		}

	    } catch (IOException e) {
			e.printStackTrace();
	    }
		finally {
         try {
        	 if (reader != null) {
        		 reader.close();
        	 }
        	 if (output != null) {
        		 output.close();
        	 }

         } catch (IOException e) {
        	 e.printStackTrace();
         }
		}

        
        File file = new File("highScore.txt"); 	// choose a file that you want to use
        Scanner scan = null; // scan the file
		try { // used because if the system cannot find the file, there will be no big error and the code will run
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        while (scan.hasNextLine()) {
            String line2 = scan.nextLine() + "\n"; // make the string and scan the file, all the line, "\n" is used so the enters will be placed
            jhSF.append(line2); // add the line in the frame 
        }
    
   	}
	
    private void boardFrame(int row, int col, WindowAdapter windowAdapter) {
    	
    	pLabels = new PanelLabels(PANELWIDHT, 50);
    	qh = new QuitHscore(PANELWIDHT, 50);
    	battleshipGrid = new BattleshipGrid(PANELWIDHT, PANELHEIGHT, new BattleshipBoard(row, col), pLabels, qh, 2, scoringMode);

    			JFrame fr = new JFrame("Battleship");
    	    	fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	    	fr.getContentPane().setLayout(new BorderLayout());
    	    	fr.getContentPane().add(battleshipGrid, BorderLayout.CENTER);
    	    	fr.getContentPane().add(pLabels, BorderLayout.PAGE_START);
    	    	fr.getContentPane().add(qh, BorderLayout.PAGE_END);
    	    	fr.setSize(535, 655);
    	    	fr.addWindowListener(windowAdapter);
    	    	fr.pack();
    	    	fr.setVisible(true);
    	    	
    	}
    				
    private void shipPlaceFrame(WindowAdapter windowAdapter) {
    	
    	JFrame sP = new JFrame("Choose Ship Placement");
    	sP.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	sP.setBackground(Color.white);
    	sP.setSize(450, 300);
    	sP.setVisible(true);
    	sP.addWindowListener(windowAdapter);

    	
    	JButton stGame = new JButton("Start Game");
    	
        JTextArea txtPF = new JTextArea();
        txtPF.setEditable(true);
        txtPF.setLineWrap(true);
        txtPF.setWrapStyleWord(true);
        txtPF.setBackground(Color.white);
        txtPF.setFont(new Font("Arial", Font.BOLD, 30));
    	
    	JPanel buttonS = new JPanel(new GridLayout());
    	JPanel txtP = new JPanel(new GridLayout());
    	
    	buttonS.add(stGame);
    	buttonS.setBackground(Color.white);
    	txtP.add(txtPF);
    	txtP.setBackground(Color.white);

        File fileShip = new File("ShipPlacement.txt"); 	// choose a file that you want to use
        Scanner scanShip = null; // scan the file
		try { // used because if the system cannot find the file, there will be no big error and the code will run
			scanShip = new Scanner(fileShip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        while (scanShip.hasNextLine()) {
            String line = scanShip.nextLine(); // make the string and scan the file, all the line, "\n" is used so the enters will be placed
            txtPF.append("\n" + line); // add the line in the frame 
        }
    	
    	sP.getContentPane().add(txtP, BorderLayout.PAGE_START);
    	sP.getContentPane().add(buttonS, BorderLayout.PAGE_END);
    	stGame.addActionListener((ActionListener) new ActionListener() {
         	
         public void actionPerformed(ActionEvent e) {
         		
         		if(e.getSource() == stGame) {
         			try { 
         				PrintWriter out = new PrintWriter(new FileWriter("newShipPlacement.txt"));
         				txtPF.write(out);
         			}
         			catch (IOException e1) {
         				e1.printStackTrace();
         			}
         		}
         		
         		boardFrame2(rows, cols, new WindowAdapter() {
                     @Override
                     public void windowClosing(WindowEvent e) {
                         // here we listen for the second JFrame being closed so we can bring back the first frame
                     	sP.setVisible(true);
                     }
                 });
                 // hide the frame when clicked on the button
                 sP.setVisible(false); 
         	}
         });
    }
    
    private void boardFrame2(int row, int col, WindowAdapter windowAdapter) {
    	
    	try(Scanner scan = new Scanner(new File("ShipPlacement.txt"))) {
    		// read first line = row and columns (square field)
    		rows = cols = row = col = scan.nextInt();
    			
    	} catch (FileNotFoundException e) {
    			e.printStackTrace();
    	}

    	pLabels = new PanelLabels(PANELWIDHT, 50);
    	qh = new QuitHscore(PANELWIDHT, 50);
    	battleshipGrid2 = new BattleshipGridPlacement(PANELWIDHT, PANELHEIGHT, new BattleshipBoardPlacement(row, col), pLabels, qh, 2, scoringMode);

    			JFrame fr = new JFrame("Battleship");
    	    	fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	    	fr.getContentPane().setLayout(new BorderLayout());
    	    	fr.getContentPane().add(battleshipGrid2, BorderLayout.CENTER);
    	    	fr.getContentPane().add(pLabels, BorderLayout.PAGE_START);
    	    	fr.getContentPane().add(qh, BorderLayout.PAGE_END);
    	    	fr.setSize(535, 655);
    	    	fr.addWindowListener(windowAdapter);
    	    	fr.pack();
    	    	fr.setVisible(true);   	
    	}
}