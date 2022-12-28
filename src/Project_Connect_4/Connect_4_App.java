package Project_Connect_4;

import javax.swing.SwingUtilities;

import Controller.Connect_4_Controller;
import Controller.Controller;
import Model.Connect_4_Model;
import Model.Model;

public class Connect_4_App {
	
	public static void main(String[] args) {
		
		 SwingUtilities.invokeLater(new Runnable() {
			 
            public void run() {
            	
            	Model model = new Connect_4_Model();
				Controller controller = new Connect_4_Controller(model);
            }
        });
	}
}
