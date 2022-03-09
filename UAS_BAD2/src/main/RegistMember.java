package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class RegistMember extends JFrame implements ActionListener{
	
	private JPanel nPanel, cPanel, sPanel, bPanel, lPanel;
	private JLabel title, name, email;
	private JTextField uname, emailField;;
	private JButton regist, menu;
	private GridBagConstraints gbc;
	
	private int idUser;
	
	Connect con = Connect.getConnection();
	
	private void initialize() {
		setTitle("Register New Member");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
	private void initComp() {
		nPanel = new JPanel();
		
		cPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
				
		bPanel = new JPanel(new GridBagLayout());
		
		lPanel = new JPanel(new GridBagLayout());
		
		sPanel = new JPanel(new BorderLayout());
		
		title = new JLabel("Register New Member");
		title.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		name = new JLabel("Nama");
		name.setFont(new Font("Comic Sans MS", Font.BOLD, 14));

		email = new JLabel("Email");
		email.setFont(new Font("Comic Sans MS", Font.BOLD, 14));

		regist = new JButton("Register");
		regist.addActionListener(this);
		menu = new JButton("Back to Menu");
		menu.addActionListener(this);
		
				
		uname = new JTextField();
		uname.setPreferredSize(new Dimension(555, 30));
		
		emailField = new JTextField();
		emailField.setPreferredSize(new Dimension(555, 30));

		nPanel.add(title);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		cPanel.add(name, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		cPanel.add(uname, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		cPanel.add(email, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		cPanel.add(emailField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		bPanel.add(regist, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		bPanel.add(menu, gbc);
		
		sPanel.add(bPanel, BorderLayout.NORTH);
		
		sPanel.add(lPanel, BorderLayout.SOUTH);		
	}
	
	private void addComp() {
		
		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(sPanel, BorderLayout.SOUTH);
	}
	
	public RegistMember(int idUser) {
		this.idUser = idUser;
		initComp();
		addComp();
		initialize();
	}
	
	private boolean insertMember(String nTemp, String eTemp) {
		// TODO Auto-generated method stub
		
		PreparedStatement ps;
		try {
			ps = con.preparedStatement("INSERT INTO memberLoyalty (`namaMember`,`email`) VALUES (?, ?)");
			ps.setString(1, nTemp);
			ps.setString(2, eTemp);
			ps.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String nTemp, eTemp;
		nTemp = uname.getText(); 
		eTemp = emailField.getText();
		
		if (e.getSource() == menu) {
			Menu m = new Menu(idUser);
			setVisible(false);
			m.setVisible(true);
		} else if (e.getSource() == regist){
			if (nTemp.isEmpty() || eTemp.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Semua Data Harus Diisi!", "Error!", JOptionPane.ERROR_MESSAGE);				

			} else if (!eTemp.contains("@") || !eTemp.contains(".com")) {
				JOptionPane.showMessageDialog(this, "Email harus xxx@xxx.com", "Error!", JOptionPane.ERROR_MESSAGE);				

			}else if(insertMember(nTemp, eTemp)){
				JOptionPane.showMessageDialog(this, "Register berhasil", "Success", JOptionPane.PLAIN_MESSAGE);				
				
			} else {
				JOptionPane.showMessageDialog(this, "Register Gagal!", "Error!", JOptionPane.ERROR_MESSAGE);				
				
			}
		}
		
	}

}
