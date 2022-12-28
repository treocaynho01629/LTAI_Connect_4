package GUI;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class ProfileIcon extends JLabel implements ActionListener { //tạo ảnh người chơi
	
	private static final long serialVersionUID = -1530816368656545342L;

	private BufferedImage image;
	private Color color;
	private int size = 60;
	private int count = 0;
	
	private Timer focusTimer;
	private Timer deFocusTimer;
	
	public ProfileIcon(BufferedImage image, int size, Color color) {
		
		this.image = image;
		this.size = size;
		this.color = color;
	    
	    focusTimer = new Timer(5, this);
	    deFocusTimer = new Timer(5, this);
		
	    Image newImg = this.image.getScaledInstance(this.size, this.size, java.awt.Image.SCALE_SMOOTH);
		BufferedImage profileImg = makeProfileIcon(newImg, this.color);
		this.setIcon(new ImageIcon(profileImg));
	}
	
	public void focusProfile() { //phóng to ảnh
		
		focusTimer.start();
	}
	
	public void deFocusProfile() { //thu nhỏ ảnh
		
		deFocusTimer.start();
	}

	private BufferedImage makeProfileIcon(Image image, Color color) {
		
	    int w = this.size;
	    int h = this.size;
	    BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2 = output.createGraphics();
	    
	    g2.setComposite(AlphaComposite.Src);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(Color.WHITE);
	    g2.fill(new RoundRectangle2D.Float(0, 0, w, h , 200, 200));
	    
	    g2.setComposite(AlphaComposite.SrcAtop);
	    g2.drawImage(image, 0, 0, null);
	    
	    g2.setColor(color);
	    g2.setStroke(new BasicStroke(10));
	    g2.drawOval(0, 0, w, h);
	    
	    g2.dispose();
	    
	    return output;
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == focusTimer) {
			
			if (count >= 5) {
        		
        		focusTimer.stop();
        	}
        	
			count++;
        	size += 2;
        	Image newImg = this.image.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
    		BufferedImage profileImg = makeProfileIcon(newImg, this.color);
    		setIcon(new ImageIcon(profileImg));
		} else {
			
			if (count <= 0) {
        		
        		deFocusTimer.stop();
        	}
        	
			count --;
        	size -= 2;
        	Image newImg = this.image.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
    		BufferedImage profileImg = makeProfileIcon(newImg, this.color);
    		setIcon(new ImageIcon(profileImg));
		}
	}
}
