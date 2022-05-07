package GUI;

import javax.swing.SwingUtilities;

public class MainGUI {

	public static void main(String[] args) {
    
    	SwingUtilities.invokeLater(new Runnable() {public void run() { new StartFrame();
    			}
    		});  
	}
}
	

