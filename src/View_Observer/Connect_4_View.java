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

public class Connect_4_View extends JFrame implements MouseListener, ActionObserver, GameObserver, ActionListener {
	
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
	
	//dimension
	private Dimension playerSize = new Dimension(235, 404);
	private Dimension infoSize = new Dimension(960, 68);
	private Dimension menuSize = new Dimension(960, 68);
	private Dimension boardSize = new Dimension(490, 404);
	
	//gameplay
	private Board board;
	private Tile tile = null;
	private Tile prevTile = null; //turn vừa đi
	private Tile prevPrevTile = null; //turn trước turn vừa đi :v
	private boolean disable = false; //disable bàn cờ
	private boolean end = false; //game end
	private boolean animation = true; //animation
	private int turn = 1;
	private boolean AI = false;
	
	private Controller controller;
	private Model model;
	
	//timer
	private Timer delayTimer;
	private Timer delayAITimer;
	private Timer endTimer;
	
	//dialog
	private PauseAction pauseAction; //pause button + dialog
	private EndDialog endDialog; //end dialog
	
	public Connect_4_View (Controller controller, Model model) {
		
		//constructor
		super("Cờ thả");
		this.controller = controller;
		this.model = model;
		this.pauseAction = new PauseAction(model);
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
			
			player1Pn = new BackgroundPanel(player1Image);
			
			if (this.AI) {
				
				player2Pn = new BackgroundPanel(aiImage);
			} else {
				
				player2Pn = new BackgroundPanel(player2Image);
			}
			
			menuPn = new BackgroundPanel(menuImage, false);
			boardPn = new BackgroundPanel(boardImage, false);
			infoPn = new BackgroundPanel(infoImage);
			
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
		
		//button
		JPanel undoBtnPn = new JPanel();
		undoBtnPn.setBorder(BorderFactory.createEmptyBorder(2, 15, 0, 0));
		undoBtnPn.setOpaque(false);
		undoBtn.setButtonColor(new Color(114, 212, 170));
		undoBtn.addActionListener(this);
		undoBtn.setPreferredSize(new Dimension(60, 60));
		undoBtnPn.add(undoBtn);
		
		JPanel pauseBtnPn = new JPanel();
		pauseBtnPn.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 15));
		pauseBtnPn.setOpaque(false);
		pauseBtn.setButtonColor(new Color(114, 212, 170));
		pauseBtn.addActionListener(pauseAction);
		pauseBtn.setPreferredSize(new Dimension(60, 60));
		pauseBtnPn.add(pauseBtn);
		
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
		menuPn.setLayout(new BorderLayout());
		menuPn.add(pauseBtnPn, BorderLayout.EAST);
		menuPn.add(undoBtnPn, BorderLayout.WEST);
		
		undoBtn.setEnabled(false);
		
		player1Pn.add(player1Icon);
		player1Pn.add(player1Lb);
		
		player2Pn.add(player2Lb);
		player2Pn.add(player2Icon);
		
		infoPn.add(actionLb);
		infoPn.add(playerLb);
		
		//set size
		player1Pn.setPreferredSize(playerSize);
		player2Pn.setPreferredSize(playerSize);
		infoPn.setPreferredSize(infoSize);
		menuPn.setPreferredSize(menuSize);
		
		//board
		boardPn.setLayout(new GridLayout(6, 7));
		boardPn.setPreferredSize(boardSize);
		
		for (int j = 0; j < 6; j++) {
			
			for (int i = 0; i < 7; i++) {
				
				boardPn.add(board.getTile(i, j));
				board.getTile(i, j).addActionListener(this);
				board.getTile(i, j).addMouseListener(this);
			}
		}
		
		//thêm vào frame
		this.add(boardPn, BorderLayout.CENTER);
		this.add(player1Pn, BorderLayout.WEST);
		this.add(player2Pn, BorderLayout.EAST);
		this.add(infoPn, BorderLayout.SOUTH);
		this.add(menuPn, BorderLayout.NORTH);
		
		//menu
		restartItm.addActionListener(this);
		undoItm.addActionListener(this);
		quitItm.addActionListener(this);
		aboutItm.addActionListener(this);
		animationCbItm.addActionListener(this);
		undoItm.setEnabled(false);
		
		fileMn.add(restartItm);
        fileMn.add(undoItm);
        fileMn.add(settingMn);
        fileMn.add(quitItm);
        
        settingMn.add(animationCbItm);
        settingMn.add(modeMn);
        settingMn.add(depthMn);
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(modeRdItm);
        bg.add(modeRdItm2);
        bg.add(modeRdItm3);
        
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(depthRdItm);
        bg2.add(depthRdItm2);
        bg2.add(depthRdItm3);
        bg2.add(depthRdItm4);
        bg2.add(depthRdItm5);
        bg2.add(depthRdItm6);
        bg2.add(depthRdItm7);
        bg2.add(depthRdItm8);
        
        modeMn.add(modeRdItm);
        modeMn.add(modeRdItm2);
        modeMn.add(modeRdItm3);
        
        depthMn.add(depthRdItm);
        depthMn.add(depthRdItm2);
        depthMn.add(depthRdItm3);
        depthMn.add(depthRdItm4);
        depthMn.add(depthRdItm5);
        depthMn.add(depthRdItm6);
        depthMn.add(depthRdItm7);
        depthMn.add(depthRdItm8);

        aboutMn.add(aboutItm);

        menuBar.add(fileMn);
        menuBar.add(aboutMn);

        setJMenuBar(menuBar);
        
        //prefix menu
        animationCbItm.setSelected(true);
        modeMn.getItem(model.getMode() + 1).setSelected(true);
        depthMn.getItem(model.getDiff() - 1).setSelected(true);
		
		//setting
		this.setResizable(false);
		this.setLocation(150, 100);
		setSize(976, 601);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//random người chơi
		if (turn == 2 && AI) {
			
			//nếu random ra người chơi 2 && là AI >> AI đi trước
//			controller.dropPiece(model.getAI().makeMove());

			if (animation) {
				
				disableBoard(); //ko cho người chơi spam :v
			    delayAITimer = new Timer(300, this);
			    
				delayAITimer.restart();
			} else {
				
				this.tile = model.getAI().makeMove();
				dropPieceNoAnimation();
			}
		}
	}
	
	public void updateAction() {
		
		//update thông báo
		if (end) {
			
			endScreen();
		} else {
			
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
			
			if (turn == 2 && AI) {
				
				//nếu tới lượt người chơi 2 && là AI >> thực hiện nước đi của AI
//				controller.dropPiece(model.getAI().makeMove());
				
				if (animation) {
					
					disableBoard();
				    delayAITimer = new Timer(150, this);
					
					delayAITimer.restart();
				} else {
					
					this.tile = model.getAI().makeMove();
					dropPieceNoAnimation();
				}
			}
		}
	}
	
	public void updateGame() {
		
		//update các function liên quan tới gameplay
		controller.setTurn((turn % 2) + 1); //phép toán đổi từ player 1 >> 2 | 2 >> 1 sau mỖi lượt chơi
		
		if (controller.isWin() || controller.isDraw()) {
			
			//kiểm tra người thắng || hoà >> kết thúc game
			end = true;
		}
		
		printBoard();
	}
	
	public void printBoard() {
		
		controller.getBoard().debug();
		System.out.println("\t     Player: " + ((turn % 2) + 1));
	}
	
	private void dropPiece() {
		
		disableBoard(); //disable bàn cờ tránh spam
		
		//tính time animation
		int top = board.getTopEmpty(this.tile.getI()) + 1;
		int velocity = 0;
		int frame = 0;
		int delay = 6 + top;
		
		for (int i = -75; i <= top * 75 ; i+= velocity) {
			
			velocity += 3;
			frame++;
		}
		
		int ms = delay * frame;
		
		//play animation
		((BackgroundPanel) boardPn).dropAnimation(this.tile, controller.getTurn());
		
	    //delay đợi animation thả quân cờ hoàn thành thì mới thêm quân
//		delayTimer = new Timer((board.getTopEmpty(this.tile.getI()) + 1) * 43, this);
		delayTimer = new Timer(ms, this);
		delayTimer.start();
	}
	
	private void dropPieceNoAnimation() {
		
		setPrev(tile);
		controller.dropPiece(tile);	
		undoBtn.setEnabled(true);
    	undoItm.setEnabled(true);
	}
	
	private void setPrev(Tile tile) {
		
		int dropped = board.getTopEmpty(tile.getI());
		
		if (dropped != -1) {
			
			this.prevPrevTile = this.prevTile;
			this.prevTile = new Tile(tile.getI(), dropped);
		}
	}
	
	private void endScreen() {
		
		if (controller.isWin()) {
			
			//update game
			undoBtn.setEnabled(false);
			undoItm.setEnabled(false);
			actionLb.setText("Người thắng: ");
			
			//delay flash
			ActionListener connect4Delay = new ActionListener() {

		        public void actionPerformed(ActionEvent e) {
		        	
		        	board.connectFour();
		        }
		    };
		    
			Timer delay = new Timer(500, connect4Delay);
			delay.setRepeats(false);
			delay.start();
			
			endDialog = new EndDialog(model, board.getWinner());
		} else if (controller.isDraw()) {
			
			undoBtn.setEnabled(false);
			actionLb.setText("Hoà!");
			playerLb.setText("");
			
			endDialog = new EndDialog(model, -1);
		}
		
		pauseBtn.removeActionListener(pauseAction);
		endTimer = new Timer(2000, this);
		
		endTimer.start();
	}
	
	private void disableBoard() {
		
		disable = true;
	}
	
	private void enableBoard() {
		
		disable = false;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == delayTimer) { //timer action
			
        	delayTimer.stop(); //stop
        	//set undo
        	setPrev(tile);
        	undoBtn.setEnabled(true);
        	undoItm.setEnabled(true);
        	//drop
        	controller.dropPiece(tile);	 
        	enableBoard();
		} else if (e.getSource() == delayAITimer) {
			
			delayAITimer.stop();
			this.tile = model.getAI().makeMove();
        	dropPiece();
		} else if (e.getSource() == endTimer) {

			endTimer.stop();
			
			//end dialog
			endPanel = new GlassPanePanel();
			this.setGlassPane(endPanel);
			endPanel.setVisible(true);
			
			JDialog dialog = new JDialog(this, "", Dialog.ModalityType.APPLICATION_MODAL);
			endDialog.setOpaque(false);
	        dialog.getContentPane().add(endDialog);  
	        dialog.setUndecorated(true); 
	        dialog.getRootPane().setOpaque (false);
	        dialog.setBackground(new Color (0, 0, 0, 0));
	        dialog.pack();
	        endDialog.setVisible(true);
	        endDialog.showWinner();
	        
	        Point point = this.getLocation();
	        dialog.setLocation(new Point(point.x + this.getWidth() / 2 - endDialog.getWidth() / 2
	        		, point.y + this.getHeight() / 2 - endDialog.getHeight() / 2 - 20));
	        dialog.setVisible(true);
		} else if (e.getSource() == undoBtn || e.getSource() == undoItm) {
			
			if (AI) { //nếu AI undo 2 turn
				
				if (prevTile != null && prevPrevTile != null) {
					
					Tile tile1 = board.getTile(prevTile.getI(), prevTile.getJ());
					Tile tile2 = board.getTile(prevPrevTile.getI(), prevPrevTile.getJ());
					
					tile1.setPiece(Piece.NONE);
					tile2.setPiece(Piece.NONE);
				}
			} else { //nếu vs Người undo 1 turn
				
				if (prevTile != null) {
					
					Tile tile = board.getTile(prevTile.getI(), prevTile.getJ());
					
					controller.setPiece(tile, Piece.NONE);
				}
			}
			
			undoBtn.setEnabled(false); //lock undo
			undoItm.setEnabled(false);
		} else if (e.getSource() == restartItm) {
			
			Object[] options = {"Huỷ", "Không"};
			
			if (JOptionPane.showOptionDialog(this, "Huỷ ván cờ?", "Thông báo!"
					, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null,    
					options, 
					options[0]) == 0) {
			
	            this.dispose();
	            int mode = -1;
            	int difficulty = -1;
            	
            	for (int i = 0; i < modeMn.getItemCount(); i++) {
            		
            		if (modeMn.getItem(i).isSelected()) {
            			
            			mode = i - 1;
            		}
            	}
            	
            	for (int i = 0; i < depthMn.getItemCount(); i++) {
            		
            		if (depthMn.getItem(i).isSelected()) {
            			
            			difficulty = i + 1;
            		}
            	}
	            
	            if (mode != -1) { //mode vừa set ko phải chơi đơn
	            	
	            	Model newModel = new Connect_4_Model(mode, difficulty); //lấy mode và difficulty trong phần "tuỳ chỉnh"
	            	Controller controller = new Connect_4_Controller(newModel);
	            } else {
	            	
	            	Model newModel = new Connect_4_Model();
	            	Controller controller = new Connect_4_Controller(newModel);
	            }
			} 
		} else if (e.getSource() == quitItm) {
			
			System.exit(0);
		} else if (e.getSource() == aboutItm) {
			
			String info = "Game cờ thả \nNhóm 9 \nThành viên: \n- Hà Đức Trọng - 19130248 \n- Nguyễn Công Bình - 19130018 \n- Tống Trúc Phụng Trân - 19130240";
            JOptionPane.showMessageDialog(this, info, "Thông tin", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == animationCbItm) {
			
			this.animation = animationCbItm.isSelected();
		} else if (!end && !disable) {
			
//			System.out.println(this.getSize()); //test gui size
			//else >> các nút còn lại là quân cờ?
			//game chưa kết thúc >> thêm quân
			
			if (animation) {
				
				this.tile = (Tile)e.getSource();
				dropPiece();
			} else {
				
				this.tile = (Tile)e.getSource();
				dropPieceNoAnimation();
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
		Tile tempTile = (Tile)e.getSource();
		int i = tempTile.getI();
		
		((BackgroundPanel) menuPn).drawArrow(i);
	}

	public void mouseExited(MouseEvent e) {
	
		((BackgroundPanel) menuPn).removeArrow();
	}
}
