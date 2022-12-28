package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

public class RoundButton extends JButton implements MouseListener { //nút viền tròn

	private static final long serialVersionUID = 7644488726633861617L;
	
	String text;
    boolean mouseIn = false;
    Color color = null;
    Color color2 = Color.BLACK;
    float size = 1.0f;
    int arcw = 12;
    int arch = 8;
    
    public RoundButton(String s) {
    	
        super(s);
        setBorderPainted(false);
        setFocusable(false);
    }

    public RoundButton(String s, float size, int arcw, int arch) {
    	
        super(s);
        this.size = size;
    	this.arcw = arcw;
    	this.arch = arch;
    	this.addMouseListener(this);
    	
    	setOpaque(false);
        setBorderPainted(false);
        setFocusable(false);
    }
    
    public void setButtonColor(Color color) {
    	
    	this.color = color;
    	this.addMouseListener(this);
    	
    	setContentAreaFilled(false);
    	repaint();
    }

    public void paintComponent(Graphics g) {
    	
        Graphics2D g2 = (Graphics2D) g;
        float size = this.size;
        Color color = this.color;
        
        if (mouseIn) {
        	
        	size = this.size * 4/5;
        	color = new Color(93, 232, 225);
        	setForeground(Color.white);
        } else {
        	
        	size = this.size;
        	color = this.color;
        	setForeground(Color.black);
        }
        
        if (color != null) {
        	
        	g.setColor(color);
            g.fillRoundRect(1, 1, (getWidth() - 3), (getHeight() - 3), arcw, arch);
        }
        
        if (getModel().isPressed()) {
        	
            g.setColor(new Color(232, 226, 211));
            g.fillRoundRect(1, 1, (getWidth() - 3), (getHeight() - 3), arcw, arch);
        }
        
        super.paintComponent(g);

        g2.setColor(color2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(size));
        g2.draw(new RoundRectangle2D.Double(size / 2, size / 2, (getWidth() - size),
                (getHeight() - size), arcw, arch));

        g2.dispose();
    }

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
		mouseIn = true;
	}

	public void mouseExited(MouseEvent e) {
		
		mouseIn = false;
	}
}

