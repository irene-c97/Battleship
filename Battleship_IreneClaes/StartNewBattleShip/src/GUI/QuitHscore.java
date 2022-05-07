package GUI;
import javax.swing.*;
import java.awt.*;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class QuitHscore extends JButton{
	private JButton quit;
	private JButton hScore;
	private StartFrame stf;

	
public QuitHscore(int width, int heigth) {
	
	// first thing of the GUI
	setPreferredSize(new Dimension(width, heigth));
	setBackground(Color.white);
	
	setLayout(new GridLayout(1,0));
	
	//create labels player1, 2 and turn
	quit = new JButton("Quit Game");
	quit.setForeground(Color.BLACK);
	quit.setLayout(new BorderLayout());
	quit.addActionListener(Listener -> System.exit(0)); 
	
	hScore = new JButton("High Scores");
	hScore.setForeground(Color.BLACK);
	hScore.setLayout(new BorderLayout());
	
	

	//add labels 
	add(quit);
	add(hScore);
	
    hScore.addActionListener((ActionListener) new ActionListener() {
    	
    	public void actionPerformed(ActionEvent e) {
    		hScoreF(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // here we listen for the second JFrame being closed so we can bring back the first frame
                	setVisible(true);
                }
            });
            // hide the frame when clicked on the button
            setVisible(false); 
    	}
    });
}

private void hScoreF(WindowAdapter windowAdapter) {
	// making the frame
	JFrame sP = new JFrame("Highscore");
	sP.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	sP.setSize(400, 500);
	sP.setVisible(true);
	sP.addWindowListener(windowAdapter);
	
	// making a text area
    JTextArea jhSF = new JTextArea();
    jhSF.setEditable(false);
    jhSF.setLineWrap(true);
    jhSF.setWrapStyleWord(true);
    sP.add(jhSF);
    jhSF.setFont(new Font("Arial", Font.BOLD, 30)); // chose type of text and set layout
    
    // posible to read and write the text file
    BufferedReader reader = null;
	BufferedWriter output  = null;
	
    ArrayList<String> line = new ArrayList<String>(); // Make a array so we can sort the numbers in the array

	try {
        reader = new BufferedReader(new FileReader("Highscore.txt"));
        String currentLine = reader.readLine();
        while (currentLine != null) {                // read the score file line by line
    
                line.add(currentLine);
                
                currentLine = reader.readLine();
        }
    
     Collections.sort(line); // sort line, this is sorted by the smallest integer
     Collections.reverse(line); // this ensures that it is sorted from largest to smallest 
    
    output = new BufferedWriter(new FileWriter("Highscore.txt"));
    for (String lines : line) {
    	output.write(lines); // write the lines down 
    	output.newLine();
	}

    } catch (IOException e) {
		e.printStackTrace();
    }
	finally { // closing reader and output
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


}
