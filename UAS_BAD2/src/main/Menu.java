package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Menu extends JFrame implements ActionListener{

	private JPanel mainPanel, cPanel;
	private JMenuBar menuBar;
	private JMenu user, purchase, trans;
	private JMenuItem signout, product, addUser, addMember, viewTrfH;
	private JLabel label;
	private GridBagConstraints gbc;
	
	private int idUser, totalPendapatan, totalPurchase;
	
	Connect con = Connect.getConnection();
	
	private void initialize() {
		setTitle("Bike Shop");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void initComp() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		cPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		
		label = new JLabel("Bike Shop");
		label.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
		
		menuBar = new JMenuBar();
		user = new JMenu("User");
		purchase = new JMenu("Purchase");
		trans = new JMenu("Transaction");
		
		signout = new JMenuItem("Sign Out");
		signout.addActionListener(this);
		addUser = new JMenuItem("Add new user");
		addUser.addActionListener(this);
		addMember = new JMenuItem("Add new member");
		addMember.addActionListener(this);
		
		product = new JMenuItem("Bike List");
		product.addActionListener(this);
		
		
		viewTrfH = new JMenuItem("View Transaction History");
		viewTrfH.addActionListener(this);
	}
	
	private void addComp() {
		user.add(signout);
		user.add(addUser);
		user.add(addMember);
		menuBar.add(user);
		
		purchase.add(product);
		menuBar.add(purchase);
		
		trans.add(viewTrfH);
		menuBar.add(trans);
		mainPanel.add(menuBar, BorderLayout.NORTH);
		
		report();
		report2();
		
		cPanel.add(label, gbc);
		
		add(mainPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);

	}
	
	public Menu(int idUser) {
		this.idUser = idUser;
		initComp();
		addComp();
		initialize();
	}
	
	private void report() {
		// TODO Auto-generated method stub
		String query = String.format("SELECT COUNT(*) as sales FROM purchaseDetail pd JOIN purchaseHeader ph ON pd.purchaseID = ph.purchaseID WHERE operatorID = %d", idUser);
		ResultSet rs = con.execQuery(query);
		try {
			while (rs.next()) {
				totalPurchase = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void report2() {
		// TODO Auto-generated method stub
		String query = String.format("SELECT SUM(totalPrice) as revenue FROM purchaseHeader WHERE operatorID = %d", idUser);
		ResultSet rs = con.execQuery(query);
		try {
			while (rs.next()) {
				totalPendapatan = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == product) {
			BuyBike bb = new BuyBike(idUser);
			setVisible(false);
			bb.setVisible(true);
		} else if (e.getSource() == viewTrfH) {
			TransHis th = new TransHis(idUser);
			setVisible(false);
			th.setVisible(true);
		} else if (e.getSource() == signout) {
			int approval;
			approval = JOptionPane.showConfirmDialog(this, String.format("Total Penjualan: %d Transaksi %n Total Pendapatan: Rp %d %n Approved?", totalPurchase, totalPendapatan));
			if (approval == JOptionPane.YES_OPTION) {
				LoginM log = new LoginM();
				setVisible(false);
				log.setVisible(true);				
			}
		} else if (e.getSource() == addMember) {
			RegistMember rm = new RegistMember(idUser);
			setVisible(false);
			rm.setVisible(true);
		} else if (e.getSource() == addUser) {
			RegisterUser ru = new RegisterUser(idUser);
			setVisible(false);
			ru.setVisible(true);
		}
	}

}
