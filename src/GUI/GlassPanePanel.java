package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class GlassPanePanel extends JPanel { //nền đen
	
	private static final long serialVersionUID = 705727789973261402L;
	
	private Image image = null;
	private Color GP_BG = new Color(0, 0, 0, 175); //175 độ mờ nền
	
	public GlassPanePanel() {
		
		setOpaque(false); 
	    setBackground(GP_BG);  
	}
	
	public GlassPanePanel(Image image) {
		
		this.image = image;
		setOpaque(false); 
	    setBackground(GP_BG); 
	}

	protected void paintComponent(Graphics g) {
    	
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
        
        if (this.image != null) {
        	
            g.drawImage(image, getWidth() / 2 - (image.getWidth(null) / 2), getHeight() / 2 - (image.getHeight(null) / 2), null);
        }
    }
}
