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

public class RegisterUser extends JFrame implements ActionListener{

	private JPanel nPanel, cPanel, sPanel, rPanel, bPanel, lPanel;
	private JLabel title, name, email, pass;
	private JTextField uname, emailField;
	private JPasswordField pfield;
	private JButton regist, menu;
	private GridBagConstraints gbc;
	private JRadioButton op, sup;
	private ButtonGroup RoleGrp;
	
	private int idUser;
	
	Connect con = Connect.getConnection();
	
	private void initialize() {
		setTitle("Register New Staff");
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
		
		rPanel = new JPanel();
		
		bPanel = new JPanel(new GridBagLayout());
		
		lPanel = new JPanel(new GridBagLayout());
		
		sPanel = new JPanel(new BorderLayout());
		
		title = new JLabel("Register New Staff");
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
		
		RoleGrp = new ButtonGroup();
		op = new JRadioButton("Operator");
		op.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		op.setActionCommand("Male");
		op.addActionListener(this);
		
		sup = new JRadioButton("Supervisor");
		sup.setActionCommand("Supervisor");
		sup.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		sup.addActionListener(this);

		pass = new JLabel("Password");
		pass.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		pfield = new JPasswordField(50);
		pfield.setPreferredSize(new Dimension(50, 30));
		
		
		nPanel.add(title);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		cPanel.add(name, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		cPanel.add(uname, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		cPanel.add(email, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		cPanel.add(emailField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		RoleGrp.add(op);
		RoleGrp.add(sup);
		rPanel.add(op);
		rPanel.add(sup);
		cPanel.add(rPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		cPanel.add(pass, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		cPanel.add(pfield, gbc);
		
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
	
	
	public RegisterUser(int idUser) {
		this.idUser = idUser;
		initComp();
		addComp();
		initialize();
	}
	
	private boolean insertOperator(String nTemp, String eTemp, String pTemp) {
		// TODO Auto-generated method stub
		
		PreparedStatement ps;
		try {
			ps = con.preparedStatement("INSERT INTO operator (`namaOperator`,`email`, `password`) VALUES (?, ?, ?)");
			ps.setString(1, nTemp);
			ps.setString(2, eTemp);
			ps.setString(3, pTemp);
			ps.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean insertSuper(String nTemp, String eTemp, String pTemp) {
		// TODO Auto-generated method stub
		
		PreparedStatement ps;
		try {
			ps = con.preparedStatement("INSERT INTO supervisor (`namaSupervisor`,`email`, `password`) VALUES (?, ?, ?)");
			ps.setString(1, nTemp);
			ps.setString(2, eTemp);
			ps.setString(3, pTemp);
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
		String nTemp, eTemp, pTemp;
		nTemp = uname.getText(); 
		eTemp = emailField.getText();
		pTemp = String.valueOf(pfield.getPassword());
		
		if (e.getSource() == menu) {
			Menu m = new Menu(idUser);
			setVisible(false);
			m.setVisible(true);
		} else if (e.getSource() == regist){
			if (nTemp.isEmpty() || eTemp.isEmpty() || pTemp.isEmpty() || !(op.isSelected() || sup.isSelected())) {
				JOptionPane.showMessageDialog(this, "Semua Data Harus Diisi!", "Error!", JOptionPane.ERROR_MESSAGE);				

			} else if (!eTemp.contains("@") || !eTemp.contains(".com")) {
				JOptionPane.showMessageDialog(this, "Email harus xxx@xxx.com", "Error!", JOptionPane.ERROR_MESSAGE);				
				
			} else if (pTemp.length() > 20) {
				JOptionPane.showMessageDialog(this, "Password tidak dapat melebihi 20 karakter", "Error!", JOptionPane.ERROR_MESSAGE);				
				
			} else if(op.isSelected()){
				if (insertOperator(nTemp, eTemp, pTemp)) {
					JOptionPane.showMessageDialog(this, "Register berhasil", "Success", JOptionPane.PLAIN_MESSAGE);									
				} else {
					JOptionPane.showMessageDialog(this, "Register Gagal!", "Error!", JOptionPane.ERROR_MESSAGE);				
				}
			} else if (sup.isSelected()) {
				if (insertSuper(nTemp, eTemp, pTemp)) {
					JOptionPane.showMessageDialog(this, "Register berhasil", "Success", JOptionPane.PLAIN_MESSAGE);		
					
				} else {
					JOptionPane.showMessageDialog(this, "Register Gagal!", "Error!", JOptionPane.ERROR_MESSAGE);				
					
				}
			}else {
				JOptionPane.showMessageDialog(this, "Register Gagal!", "Error!", JOptionPane.ERROR_MESSAGE);				
				
			}
		}
		
	}


}
