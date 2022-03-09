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

public class LoginSuper extends JFrame implements ActionListener{

	private JPanel nPanel, cPanel, sPanel, bPanel, lPanel;
	private JLabel title, user, pass;
	private JTextField uname;
	private JPasswordField pfield;
	private JButton login, cancel;
	private GridBagConstraints gbc;
	private JLabel label;
	
	Connect con = Connect.getConnection();
	
	private String iTemp, pTemp;
	private int idUser, superId, quantity, bikeId;
	
	private void initialize() {
		setTitle("Login Supervisor");
		setSize(900, 500);
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
		user = new JLabel("Supervisor ID");
		user.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		pass = new JLabel("Password");
		pass.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		
		uname = new JTextField();
		uname.setPreferredSize(new Dimension(400, 30));
		pfield = new JPasswordField();
		pfield.setPreferredSize(new Dimension(400, 30));
		
		login = new JButton("Log in");
		login.setPreferredSize(new Dimension(400, 30));
		login.addActionListener(this);
		
		cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(400, 30));
		cancel.addActionListener(this);
		
		label = new JLabel(" ");
		label.setPreferredSize(new Dimension(400, 30));
		
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

	public LoginSuper(int idUser, int bikeId, int quantity) {
		this.idUser = idUser;
		this.bikeId = bikeId;
		this.quantity = quantity;
		initComp();
		addComp();
		initialize();
	}
	
	private void LoginS() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM supervisor";
		ResultSet rs = con.execQuery(query);
		try {
			while (rs.next()) {
				if (iTemp.equals(rs.getString(1))) {
					if (pTemp.equals(rs.getString(4))) {
						superId = rs.getInt(1);
						CartSuper cs = new CartSuper(superId, idUser, bikeId, quantity);
						setVisible(false);
						cs.setVisible(true);
						return;
					}	else {
						label.setText("Supervisor ID atau Password Salah!");
						label.setForeground(Color.red);
					}
				} else {
					label.setText("Supervisor ID atau Password Salah!");
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
				label.setText("Semua Data Harus Diisi!");
				label.setForeground(Color.red);

			} else {
				LoginS();
			}
		} else if (e.getSource() == cancel) {
			Cart c = new Cart(idUser, bikeId, quantity);
			setVisible(false);
			c.setVisible(true);
		}
		
	}

}
