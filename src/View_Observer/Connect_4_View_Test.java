package View_Observer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import Controller.*;
import GUI.BackgroundPanel;
import GUI.EndDialog;
import GUI.GlassPanePanel;
import GUI.PauseAction;
import GUI.ProfileIcon;
import GUI.RoundButton;
import Model.*;
import Other.Board;
import Other.Tile;
import Other.Tile.Piece;

public class Connect_4_View_Test extends JFrame implements MouseListener, ActionObserver, GameObserver, ActionListener {
	
	private static final long serialVersionUID = 6731302639865330251L;
	
	//swing
	private String player1Name = "Người chơi 1";
	private String player2Name = "Người chơi 2";
	private JLabel actionLb = new JLabel("Đến lượt của: ");
	private JLabel playerLb = new JLabel(player1Name);
	private JLabel player1Lb = new JLabel(player1Name);
	private JLabel player2Lb = new JLabel(player2Name);
	private ProfileIcon player1Icon;
	private ProfileIcon player2Icon;
	private JPanel menuPn = new JPanel();
	private JPanel player1Pn = new JPanel();
	private JPanel player2Pn = new JPanel();
	private JPanel infoPn = new JPanel();
	private JPanel boardPn = new JPanel();
	private JPanel endPanel;
	private RoundButton undoBtn = new RoundButton("", 5f, 20, 20);
	private RoundButton pauseBtn = new RoundButton("", 5f, 20, 20);
	private Font font = new Font("", Font.BOLD, 13);
	
	//menuitem
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMn = new JMenu("File");
    private JMenu aboutMn = new JMenu("Thông tin");
    private JMenu settingMn = new JMenu("Tuỳ chỉnh"); 
    private JMenu modeMn = new JMenu("Chế độ"); 
    private JMenu depthMn = new JMenu("Depth"); 
    private JMenuItem restartItm = new JMenuItem("Chơi lại");
    private JMenuItem undoItm = new JMenuItem("Undo");
    private JMenuItem quitItm = new JMenuItem("Thoát");
    private JMenuItem aboutItm = new JMenuItem("Thông tin nhóm");
    private JCheckBoxMenuItem animationCbItm = new JCheckBoxMenuItem("Animation");
    private JRadioButtonMenuItem modeRdItm = new JRadioButtonMenuItem("Người vs Người");
    private JRadioButtonMenuItem modeRdItm2 = new JRadioButtonMenuItem("Người vs MiniMax");
    private JRadioButtonMenuItem modeRdItm3 = new JRadioButtonMenuItem("Người vs AlphaBeta");
    private JRadioButtonMenuItem depthRdItm = new JRadioButtonMenuItem("1");
    private JRadioButtonMenuItem depthRdItm2 = new JRadioButtonMenuItem("2");
    private JRadioButtonMenuItem depthRdItm3 = new JRadioButtonMenuItem("3");
    private JRadioButtonMenuItem depthRdItm4 = new JRadioButtonMenuItem("4");
    private JRadioButtonMenuItem depthRdItm5 = new JRadioButtonMenuItem("5");
    private JRadioButtonMenuItem depthRdItm6 = new JRadioButtonMenuItem("6");
    private JRadioButtonMenuItem depthRdItm7 = new JRadioButtonMenuItem("7");
    private JRadioButtonMenuItem depthRdItm8 = new JRadioButtonMenuItem("8");
    
	//image
	private Image player1Image;
	private Image player2Image;
	private Image aiImage;
	private Image menuImage;
	private Image infoImage;
	private Image boardImage;
	private Image iconImage;
	private Image iconImage2;
	
	private ImageIcon icon;
	private ImageIcon icon2; 
	
	private BufferedImage profile1Image;
	private BufferedImage profile2Image;
	
	//gameplay
	private Board board;
	private Tile tile = null;
	private int turn = 1;
	private boolean AI = false;
	
	private Controller controller;
	private Model model;
	
	public Connect_4_View_Test (Controller controller, Model model) {
		
		//constructor
		super("Cờ thả");
		this.controller = controller;
		this.model = model;
		this.AI = model.isAI(); //kiểm tra là người chơi 2 || AI
		
		controller.registerObserver((ActionObserver) this);
		controller.registerObserver((GameObserver) this);
		
		this.board = controller.getBoard(); //copy board của Model
		printBoard();
		
		//set font
		player1Lb.setFont(font);
		player2Lb.setFont(font);
		actionLb.setFont(font);
		playerLb.setFont(font);
		
		//load image
		try {
			
			player1Image = ImageIO.read(this.getClass().getResourceAsStream("/Image/player1.png"));
			player2Image = ImageIO.read(this.getClass().getResourceAsStream("/Image/player2.png"));
			aiImage = ImageIO.read(this.getClass().getResourceAsStream("/Image/ai.png"));
			menuImage = ImageIO.read(this.getClass().getResourceAsStream("/Image/menu.png"));
			infoImage = ImageIO.read(this.getClass().getResourceAsStream("/Image/info.png"));
			boardImage = ImageIO.read(this.getClass().getResourceAsStream("/Image/board.png"));
			iconImage = ImageIO.read(this.getClass().getResourceAsStream("/Image/pause.png"));
			iconImage2 = ImageIO.read(this.getClass().getResourceAsStream("/Image/undo.png"));
			Image newIconImage = iconImage2.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH ) ; 
			
			icon = new ImageIcon(iconImage);
			icon2 = new ImageIcon(newIconImage);
			
			profile1Image = ImageIO.read(this.getClass().getResourceAsStream("/Image/profile1.png"));
			profile2Image = ImageIO.read(this.getClass().getResourceAsStream("/Image/profile2.png"));
			
			pauseBtn.setIcon(icon);
			undoBtn.setIcon(icon2);
			
			player1Icon = new ProfileIcon(profile1Image, 60, board.getTile(0, 0).getColorP1());
			player2Icon = new ProfileIcon(profile2Image, 60, board.getTile(0, 0).getColorP2());
		} catch (Exception ex) {
			
			System.out.println(ex);
		}
	}
	
	public void createView() {
		
		//đặt tên
		if (this.AI) {
			
			int mode = this.model.getMode();
			int diff = this.model.getDiff();
			
			switch(mode) {
			
				case 0:
				player2Name = "AI Minimax " + diff;
				break;
				
				case 1:
				player2Name = "AI AlphaBeta " + diff;
				break;
			}
			
			player2Lb.setText(player2Name);
		}
		
		//info
		turn = controller.getTurn();
		switch (turn) {
		
			case 1:
			playerLb.setText(player1Name);
			player1Icon.focusProfile();
			break;
			
			case 2:
			playerLb.setText(player2Name);
			player2Icon.focusProfile();
			break;
		}
		
		//add cho panel
		player1Pn.add(player1Icon);
		player1Pn.add(player1Lb);
		
		player2Pn.add(player2Lb);
		player2Pn.add(player2Icon);
		
		infoPn.add(actionLb);
		infoPn.add(playerLb);
		
		//thêm vào frame
		this.add(player1Pn, BorderLayout.WEST);
		this.add(player2Pn, BorderLayout.EAST);
		this.add(infoPn, BorderLayout.CENTER);
        
		//setting
		this.setResizable(true);
		this.setLocation(0, 0);
		setSize(500, 500);
		setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateAction() {
		
		//update thông báo
		turn = controller.getTurn();
		switch (turn) {
		
			case 1:
			playerLb.setText(player1Name);
			player1Icon.focusProfile();
			player2Icon.deFocusProfile();
			break;
			
			case 2:
			playerLb.setText(player2Name);
			player1Icon.deFocusProfile();
			player2Icon.focusProfile();
			break;
		}
		
	}
	
	public void updateGame() {
		
	}
	
	public void printBoard() {
		
		controller.getBoard().debug();
	}
	
	private void dropPieceNoAnimation() {
		
		setPrev(tile);
		controller.dropPiece(tile);	
		undoBtn.setEnabled(true);
    	undoItm.setEnabled(true);
	}
	
	private void setPrev(Tile tile) {
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
