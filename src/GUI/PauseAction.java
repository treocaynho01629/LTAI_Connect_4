package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

import Model.Model;

public class PauseAction extends AbstractAction { //action khi pause
	
	private static final long serialVersionUID = 6513293973390395222L;
	
    private PauseDialog pauseDialog;
    private Image dialogImage;
    private Model model;
    private JPanel glassPane;

    public PauseAction(Model model) {
    	
    	this.model = model;
    	pauseDialog = new PauseDialog(this.model);
    	
        try {
    		
    		dialogImage = ImageIO.read(this.getClass().getResourceAsStream("/Image/dialog.png"));
        } catch (Exception ex) {
    		
    		System.out.println(ex);
    	}
    }

    public void actionPerformed(ActionEvent e) {
    	
        Component comp = (Component) e.getSource();
        if (comp == null) {
        	
            return;
        }

        //tạo nền đen mờ
        glassPane = new GlassPanePanel(dialogImage);

        RootPaneContainer win = (RootPaneContainer) SwingUtilities.getWindowAncestor(comp);
        Window main = SwingUtilities.getWindowAncestor(comp);
        win.setGlassPane(glassPane); 
        glassPane.setVisible(true); 

        //hiện pause dialog
        JDialog dialog = new JDialog((Window)win, "", ModalityType.APPLICATION_MODAL);
        pauseDialog.setOpaque(false);
        dialog.getContentPane().add(pauseDialog);  
        dialog.setUndecorated(true); 
        dialog.getRootPane().setOpaque (false);
        dialog.setBackground(new Color (0, 0, 0, 0));
        dialog.pack();
        Point point = main.getLocation();
        dialog.setLocation(new Point(point.x + main.getWidth() / 2 - pauseDialog.getWidth() / 2
        		, point.y + main.getHeight() / 2 - 55));
        dialog.setVisible(true);  

        glassPane.setVisible(false);
    }
}
