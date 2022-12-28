package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Controller.Connect_4_Controller;
import Controller.Controller;
import Model.Connect_4_Model;
import Model.Model;

public class PauseDialog extends JPanel implements ActionListener { //dialog tạm dừng game
	
	private static final long serialVersionUID = -7941527661434249942L;
	
	private Color color = new Color (0, 0, 0, 0);
	private Color color2 = Color.white;
	private Font font = new Font("", Font.BOLD, 20);
    
    public RoundButton resumeBtn = new RoundButton("Tiếp tục", 5.0f, 15, 15);
    public RoundButton restartBtn = new RoundButton("Chơi lại", 5.0f, 15, 15);
    public RoundButton optionBtn = new RoundButton("Tuỳ chỉnh", 5.0f, 15, 15);
    public RoundButton quitBtn = new RoundButton("Thoát", 5.0f, 15, 15);
    
    private Model model;
    private SettingDialog settingDialog;
    
    public PauseDialog(Model model) {
    	
    	this.model = model;
    	settingDialog = new SettingDialog(this.model); //tạo setting
    	
    	//trang trí
    	setBackground(color);
        setLayout(new GridLayout(0, 1, 0, 5));
        
        resumeBtn.setPreferredSize(new Dimension(200, 45));
        
        resumeBtn.setButtonColor(color2);
        restartBtn.setButtonColor(color2);
        optionBtn.setButtonColor(color2);
        quitBtn.setButtonColor(color2);
        
        resumeBtn.setFont(font);
        restartBtn.setFont(font);
        optionBtn.setFont(font);
        quitBtn.setFont(font);
        
        resumeBtn.addActionListener(this);
        restartBtn.addActionListener(this);
        optionBtn.addActionListener(this);
        quitBtn.addActionListener(this);
        
        add(resumeBtn);
        add(restartBtn);
        add(optionBtn);
        add(quitBtn);
    }
    
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == quitBtn) {
			
			System.exit(0); //tắt game
		} else if (e.getSource() == resumeBtn) {
			
			Component comp = (Component) e.getSource();
            Window pause = SwingUtilities.getWindowAncestor(comp);
            pause.dispose(); //tắt dialog
		} else if (e.getSource() == restartBtn) {
			
			Object[] options = {"Huỷ", "Không"};
			
				if (JOptionPane.showOptionDialog(this, "Huỷ ván cờ?", "Thông báo!"
						, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						null,    
						options, 
						options[0]) == 0) {
				
					Component comp = (Component) e.getSource();
		            Window pause = SwingUtilities.getWindowAncestor(comp);
		            Window main = SwingUtilities.getWindowAncestor(pause);
		            
		            main.dispose();
		            int mode = settingDialog.getMode();
	            	int difficulty = settingDialog.getDiff();
		            
		            if (mode != -1) { //mode vừa set ko phải chơi đơn
		            	
		            	Model newModel = new Connect_4_Model(mode, difficulty); //lấy mode và difficulty trong phần "tuỳ chỉnh"
		            	Controller controller = new Connect_4_Controller(newModel);
		            } else {
		            	
		            	Model newModel = new Connect_4_Model();
		            	Controller controller = new Connect_4_Controller(newModel);
		            }
			} 
		} else if (e.getSource() == optionBtn) {
			
			Component comp = (Component) e.getSource();
            Window pause = SwingUtilities.getWindowAncestor(comp);
            
            JDialog dialog = new JDialog((Window)pause, "", ModalityType.APPLICATION_MODAL);
            settingDialog.setOpaque(false);
            dialog.getContentPane().add(settingDialog);  
            dialog.setUndecorated(true); 
            dialog.getRootPane().setOpaque(false);
            dialog.setBackground(new Color (0, 0, 0, 0));
            dialog.pack();
            dialog.setLocationRelativeTo((Window) pause); 
            pause.setVisible(false);
            dialog.setVisible(true);  
		}
	}
}
