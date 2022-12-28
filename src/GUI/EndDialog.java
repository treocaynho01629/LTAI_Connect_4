package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Controller.Connect_4_Controller;
import Controller.Controller;
import Model.Connect_4_Model;
import Model.Model;

public class EndDialog extends JPanel implements ActionListener { //màn hình kết thúc
	
	private static final long serialVersionUID = 1443896356194395195L;
	
	private Color color = new Color (0, 0, 0, 0); //trong suốt
	private Color color2 = Color.white;
	private Font font = new Font("", Font.BOLD, 20);
    
	private JPanel playerPn = new JPanel(new BorderLayout(30, 0));
	private JPanel buttonPn = new JPanel();
	private JLabel player1Lb = new JLabel("Người chơi 1");
	private JLabel player2Lb = new JLabel("Người chơi 2");
	private BufferedImage profile1Image;
	private BufferedImage profile2Image;
	private ProfileIcon player1Icon;
	private ProfileIcon player2Icon;
	private RoundButton acceptBtn = new RoundButton("Chơi lại", 5.0f, 15, 15);
	private RoundButton quitBtn = new RoundButton("Thoát", 5.0f, 15, 15);
	private Image image;
	private JLabel endLb;
	
	private Model model;
	private int winner = -1;
	private Timer delayTimer;
	
    public EndDialog(Model model, int winner) {
    	
    	this.model = model;
    	this.winner = winner;
    	
    	setBorder(BorderFactory.createEmptyBorder(25, 0, 20, 0));
    	setBackground(color);
        setLayout(new BorderLayout(0, 30));
        
        playerPn.setOpaque(false);
        buttonPn.setOpaque(false);
        
        player1Lb.setFont(font);
        player2Lb.setFont(font);
        
        player1Lb.setForeground(Color.WHITE);
        player2Lb.setForeground(Color.WHITE);
        
        acceptBtn.setFont(font);
        quitBtn.setFont(font);
        acceptBtn.setPreferredSize(new Dimension(150, 45));
        quitBtn.setPreferredSize(new Dimension(150, 45));
        
        acceptBtn.setButtonColor(color2);
        quitBtn.setButtonColor(color2);
        
        acceptBtn.addActionListener(this);
        quitBtn.addActionListener(this);
        
        try {
        	
        	if (this.winner != -1) {
        		
        		image = ImageIO.read(this.getClass().getResourceAsStream("/Image/win.png"));
        	} else {
        		
        		image = ImageIO.read(this.getClass().getResourceAsStream("/Image/draw.png"));
        	}
          	
          	endLb = new JLabel(new ImageIcon(image));
          	
          	profile1Image = ImageIO.read(this.getClass().getResourceAsStream("/Image/profile1.png"));
  			profile2Image = ImageIO.read(this.getClass().getResourceAsStream("/Image/profile2.png"));
          	
          	player1Icon = new ProfileIcon(profile1Image, 70, model.getBoard().getTile(0, 0).getColorP1());
  			player2Icon = new ProfileIcon(profile2Image, 70, model.getBoard().getTile(0, 0).getColorP2());
          } catch (Exception ex) {
          	
          	System.out.println(ex);
        }
        
        if (model.isAI()) {
			
			int mode = this.model.getMode();
			int diff = this.model.getDiff();
			
			switch(mode) {
			
				case 0:
				player2Lb.setText("AI Minimax " + diff);
				break;
				
				case 1:
				player2Lb.setText("AI AlphaBeta " + diff);
				break;
			}
		}
        
        JPanel player1Pn = new JPanel();
        JPanel player2Pn = new JPanel();
        
        player1Pn.setOpaque(false);
        player2Pn.setOpaque(false);
        
        player1Pn.add(player1Icon);
        player1Pn.add(player1Lb);
        player2Pn.add(player2Lb);
        player2Pn.add(player2Icon);
        
        playerPn.setPreferredSize(new Dimension(500, 90));
        playerPn.add(player1Pn, BorderLayout.WEST);
        playerPn.add(player2Pn, BorderLayout.EAST);
        
        buttonPn.add(acceptBtn);
        buttonPn.add(quitBtn);
        
        add(endLb, BorderLayout.NORTH);
        add(playerPn, BorderLayout.CENTER);
        add(buttonPn, BorderLayout.SOUTH);
    }
    
    public void showWinner() { //phóng to ảnh người thắng
    	
    	delayTimer = new Timer(250, this);
    	
    	delayTimer.start();
    }
    
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == acceptBtn) {
			
			Component comp = (Component) e.getSource();
            Window end = SwingUtilities.getWindowAncestor(comp);
            Window main = SwingUtilities.getWindowAncestor(end);
            
            main.dispose();
            int mode = model.getMode();
        	int difficulty = model.getDiff();
            
            if (mode != -1) { //mode vừa set ko phải chơi đơn
            	
            	Model newModel = new Connect_4_Model(mode, difficulty); 
            	Controller controller = new Connect_4_Controller(newModel);
            } else {
            	
            	Model newModel = new Connect_4_Model();
            	Controller controller = new Connect_4_Controller(newModel);
            }
		} else if (e.getSource() == quitBtn) {
		
			System.exit(0);
		} else if (e.getSource() == delayTimer) {
			
			delayTimer.stop();
			switch (this.winner) {
	    	
	    	case -1:
	    		player1Icon.focusProfile();
	    		player2Icon.focusProfile();
	    		player1Lb.setForeground(model.getBoard().getTile(0, 0).getColorP1());
	    		player2Lb.setForeground(model.getBoard().getTile(0, 0).getColorP2());
	    		break;
	    		
	    	case 1:
	    		player1Icon.focusProfile();
	    		player1Lb.setForeground(model.getBoard().getTile(0, 0).getColorP1());
	    		break;
	    		
	    	case 2:
	    		player2Icon.focusProfile();
	    		player2Lb.setForeground(model.getBoard().getTile(0, 0).getColorP2());
	    		break;
	    	}
		}
	}
}
