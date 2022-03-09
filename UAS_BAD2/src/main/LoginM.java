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
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginM extends JFrame implements ActionListener{

	private JPanel nPanel, cPanel, sPanel, bPanel, lPanel;
	private JLabel title, user, pass;
	private JTextField uname;
	private JPasswordField pfield;
	private JButton login;
	private GridBagConstraints gbc;
	private JLabel label;
	
	Connect con = Connect.getConnection();
	
	private String iTemp, pTemp;
	private int idUser;
	
	private void initialize() {
		setTitle("Bike Shop");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	private void initComp() {
		nPanel = new JPanel();
		
		cPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		
		sPanel = new JPanel(new BorderLayout());
		
		bPanel = new JPanel(new GridBagLayout());
		
		lPanel = new JPanel(new GridBagLayout());
		
		title = new JLabel("Log in");
		title.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
		user = new JLabel("Operator ID");
		user.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		pass = new JLabel("Password");
		pass.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		
		uname = new JTextField();
		uname.setPreferredSize(new Dimension(555, 30));
		pfield = new JPasswordField(50);
		pfield.setPreferredSize(new Dimension(50, 30));
		
		login = new JButton("Log in");
		login.setPreferredSize(new Dimension(555, 30));
		login.addActionListener(this);
		
		label = new JLabel(" ");
		label.setPreferredSize(new Dimension(200, 30));
		
		nPanel.add(title);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		cPanel.add(user, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		cPanel.add(uname, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		cPanel.add(pass, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		cPanel.add(pfield, gbc);
		
		gbc.gridy = 0;
		bPanel.add(login, gbc);
		
		lPanel.add(label, gbc);
		
		sPanel.add(bPanel, BorderLayout.NORTH);
		sPanel.add(lPanel, BorderLayout.CENTER);
		
	}
	
	private void addComp() {
		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(sPanel, BorderLayout.SOUTH);

	}

	public LoginM() {
		initComp();
		addComp();
		initialize();
	}
	
	private void Login() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM operator";
		ResultSet rs = con.execQuery(query);
		try {
			while (rs.next()) {
				if (iTemp.equals(rs.getString(1))) {
					if (pTemp.equals(rs.getString(4))) {
						idUser = rs.getInt(1);
						Menu m = new Menu(idUser);
						setVisible(false);
						m.setVisible(true);
						return;
					}	else {
						label.setText("Operator ID atau Password Salah!");
						label.setForeground(Color.red);

					}
				} else {
					label.setText("Operator ID atau Password Salah!");			
					label.setForeground(Color.red);

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		iTemp = uname.getText();
		pTemp = String.valueOf(pfield.getPassword());
//		idTemp = Integer.parseInt(iTemp);
		if (e.getSource() == login) {
			if (iTemp.isEmpty() || pTemp.isEmpty()) {
				label.setText("Semua data harus diisi!");		
				label.setForeground(Color.red);

			} else {
				Login();
			}
		}
		
	}

}
