package Other;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class Tile extends JButton { //1 ô trong bàn cờ 
	
	private static final long serialVersionUID = -5308920348838168292L;

	public enum Piece {
		
		//2 loại quân: ĐỎ, XANH | 1 ô trống | 1 màu khác xác định hàng thắng
		P1, P2, NONE, WIN
	} 
	
	private int i, j;
	private Piece piece = Piece.NONE;
	
	private Color colorP1 = new Color(62, 194, 141);
	private Color colorP2 = new Color(227, 90, 75);
	
	private Image tileImage;
	private Image tileP1Image;
	private Image tileP2Image;
	
	private Timer winTimer;
	private int count = 0;
	
	public Tile (int i, int j) {
		
		this.i = i;
		this.j = j;
		
		//timer
		ActionListener flashingWin = new ActionListener() {

	        public void actionPerformed(ActionEvent e) {
	        	
	        	if (count == 6) {
        			
        			count = 0;
        		} else {
        			
        			if (count > 3) {
    	        		
    	        		setBackground(getBackground().darker());
    	        		
    	        	} else if (count < 2) {
    	        		
    	        		setBackground(getBackground().brighter());
    	        	}
        			
        			count++;
        		}
	        }
	    };
	    
	    winTimer = new Timer(150, flashingWin);
		
		//trang trí ô
		try {
			
			tileImage = ImageIO.read(this.getClass().getResourceAsStream("/Image/tile.png")); 
			tileP1Image = ImageIO.read(this.getClass().getResourceAsStream("/Image/tileP1.png")); 
			tileP2Image = ImageIO.read(this.getClass().getResourceAsStream("/Image/tileP2.png")); 
			this.setIcon(new ImageIcon(tileImage));
		} catch (Exception ex) {
			
			System.out.println(ex);
		}
				
		setContentAreaFilled(false); //full transparent
		setBorder(new LineBorder(new Color(87, 195, 225), 2));
		setBorderPainted(true);
		setColor(); //tô màu ô | quân
	}
	
	public int getI() {
		
		return i;
	}
	
	public int getJ() {
		
		return j;
	}
	
	public Piece getPiece() {
		
		return piece;
	}
	
	public Color getColorP1() {
		
		return colorP1;
	}
	
	public Color getColorP2() {
		
		return colorP2;
	}	
	
	public void setPiece(Piece piece) {
		
		//set ô theo quân cờ
		this.piece = piece;
		setColor();
	}
	
	public void setColor() {
		
		//tô màu ô
		switch(piece) {
		
		case P1:
			setOpaque(true);
			setBackground(colorP1);
			this.setIcon(new ImageIcon(tileP1Image));
			break;
		
		case P2:
			setOpaque(true);
			setBackground(colorP2);
			this.setIcon(new ImageIcon(tileP2Image));
			break;
		
		case NONE:
			setOpaque(false); //transparent
			setBackground(Color.WHITE);
			this.setIcon(new ImageIcon(tileImage));
			break;
			
		case WIN:
			
			setOpaque(true);
			winTimer.start();
			break;	
		}
	}
}
