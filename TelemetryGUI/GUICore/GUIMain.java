import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.UIManager;

public class GUIMain {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		//sets UI to default 
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e){}
		
		
		ConstructGUI constructGUI = new ConstructGUI();
		constructGUI.initializeGUI();
		
	}

}