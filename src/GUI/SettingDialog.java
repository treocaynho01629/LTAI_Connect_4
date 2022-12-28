package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Model.Model;

public class SettingDialog extends JPanel implements ActionListener { //dialog tuỳ chỉnh độ khó
	
	private static final long serialVersionUID = 1443896356194395195L;
	
	private Color color = new Color (0, 0, 0, 0);
	private Color color2 = Color.white;
	private Font font = new Font("", Font.BOLD, 20);
    
	private JLabel modeLb;
	private JLabel diffLb;
	private JComboBox<String> modeCbb = new JComboBox<>();
	private JComboBox<String> diffCbb = new JComboBox<>();
	private RoundButton applyBtn = new RoundButton("Xong", 5.0f, 15, 15);
	private RoundButton cancelBtn = new RoundButton("Huỷ", 5.0f, 15, 15);
	
	private int mode;
	private int difficulty;
    
    public SettingDialog(Model model) {
    	
    	this.mode = model.getMode();
    	this.difficulty = model.getDiff();
    	
    	setBorder(BorderFactory.createEmptyBorder(25, 0, 20, 0));
    	setBackground(color);
        setLayout(new GridLayout(0, 2, 10, 10));
        
        modeLb = new JLabel("Chế độ: ");
        diffLb = new JLabel("Depth: ");
        
        modeLb.setFont(font);
        diffLb.setFont(font);
        
        modeCbb.addItem("Đấu đôi");
        modeCbb.addItem("AI Minimax");
        modeCbb.addItem("AI AlphaBeta");
        
        diffCbb.addItem("1");
        diffCbb.addItem("2");
        diffCbb.addItem("3");
        diffCbb.addItem("4");
        diffCbb.addItem("5");
        diffCbb.addItem("6");
        diffCbb.addItem("7");
        diffCbb.addItem("8");
        
        //prefix
        modeCbb.setSelectedIndex(mode + 1);
        diffCbb.setSelectedIndex(difficulty - 1);
        
        applyBtn.setFont(font);
        cancelBtn.setFont(font);
        
        applyBtn.setButtonColor(color2);
        cancelBtn.setButtonColor(color2);
        
        applyBtn.setPreferredSize(new Dimension(30, 45));
        cancelBtn.setPreferredSize(new Dimension(50, 45));
        
        applyBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        
        add(modeLb);
        add(modeCbb);
        add(diffLb);
        add(diffCbb);
        add(applyBtn);
        add(cancelBtn);
    }
    
    public int getMode() {
		
		return mode;
	}

	public int getDiff() {
		
		return difficulty;
	}
    
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == applyBtn) {
			
			//set mode + diff
			this.mode = (modeCbb.getSelectedIndex() - 1);
			this.difficulty = (diffCbb.getSelectedIndex() + 1);
			
			//thông báo
			JOptionPane.showMessageDialog(this,
                    "Đã thay đổi tuỳ chỉnh, sẽ được áp dụng cho ván chơi sau!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			
			//lấy component mẹ
			Component comp = (Component) e.getSource();
            Window setting = SwingUtilities.getWindowAncestor(comp);
            Window pause = SwingUtilities.getWindowAncestor(setting);
            setting.dispose(); //tắt dialog
            pause.setVisible(true);
		} else if (e.getSource() == cancelBtn) {
			
			//lấy component mẹ
			Component comp = (Component) e.getSource();
            Window setting = SwingUtilities.getWindowAncestor(comp);
            Window pause = SwingUtilities.getWindowAncestor(setting);
            setting.dispose(); //tắt dialog
            pause.setVisible(true);
		}
	}
}
