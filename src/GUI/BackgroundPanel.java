package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import Other.Tile;

public class BackgroundPanel extends JPanel implements ActionListener { //panel có ảnh nền

	private static final long serialVersionUID = 1584502625601705051L;
	
	private Image background;
	private boolean backgroundOnly = true;
	private Timer tileTimer;
	private Timer arrowTimer;
	
	private int velocity = 0;
	private int tileSize = 75;
	private int pieceSize = 60;
	private int x = 0;
	private int y = -tileSize;
	private Color color;
	
	private Image arrowImage;
	private Image pieceImage;
	
	public BackgroundPanel(Image background)
    {
        this.background = background;
    }
	
    public BackgroundPanel(Image background, boolean backgroundOnly)
    {
        this.background = background;
        this.backgroundOnly = backgroundOnly;
        
        if (!backgroundOnly) {
        	
        	arrowTimer = new Timer(25, this);
        	tileTimer = new Timer(10, this);
        }
       
    }
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        
        if (!backgroundOnly) {
        	
        	 Graphics2D g2D = (Graphics2D) g;
             
         	 g2D.setColor(color);
             g2D.fillOval(x + (tileSize - pieceSize) / 2, y + (tileSize - pieceSize) / 2, pieceSize, pieceSize);
             g2D.drawImage(pieceImage, x, y, null);
             g2D.drawImage(arrowImage, x, y, null);
        }
    }
    
    public void drawArrow(int i) { //vẽ mũi tên bàn cờ
    	
    	try {
 			
         	arrowImage = ImageIO.read(this.getClass().getResourceAsStream("/Image/arrow.png")); 
 		} catch (Exception ex) {
 			
 			System.out.println(ex);
 		}
    	
    	velocity = 1;
    	pieceSize = 0;
    	x = 235 + (70 * i + 3);
    	y = - 15;
    	
    	arrowTimer.start();
    }
    
    public void removeArrow() { //xoá mũi tên
    	
    	arrowTimer.stop();
    	
    	velocity = 0;
    	pieceSize = 60;
    	x = 0;
    	y = -tileSize;
    	
    	repaint();
    }
    
    public void dropAnimation(Tile tile, int turn) { //animation thả quân cờ
    	
    	if (turn == 1) {
    		
    		try {
     			
             	pieceImage = ImageIO.read(this.getClass().getResourceAsStream("/Image/pieceP1.png")); 
     		} catch (Exception ex) {
     			
     			System.out.println(ex);
     		}
    		color = tile.getColorP1();
    	} else {
    		
    		try {
     			
             	pieceImage = ImageIO.read(this.getClass().getResourceAsStream("/Image/pieceP2.png")); 
     		} catch (Exception ex) {
     			
     			System.out.println(ex);
     		}
    		color = tile.getColorP2();
    	}
    	
    	x = tile.getI() * 70;
        tileTimer.start();
    }
    
    public void stopAnimation() { //dừng animation
    	
    	tileTimer.stop();
    	
    	arrowImage = null;
		x = 0;
		y = -tileSize;
		velocity = 0;
		
		repaint();
    }
    
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getSource() == tileTimer) {
    		
    		if (y >= background.getHeight(this) - tileSize) {
        		
        		tileTimer.stop();
        		x = 0;
        		y = -tileSize;
        		velocity = 0;
        		
        		repaint();
        	} else {
        		
        		y = y + velocity;
            	velocity += 3;
            	
            	repaint();
        	}
    	} else if (e.getSource() == arrowTimer) {
    		
    		if (y >= -10) {
        		
        		velocity = velocity * -1;
        	} else if (y <= -23) {
        		
        		velocity = velocity * -1;
        	}
        	
        	y = y + velocity;
        	repaint();
    	}
    }
 
    public Dimension getPreferredSize()
    {
        return new Dimension(background.getWidth(this), background.getHeight(this));
    }
}
