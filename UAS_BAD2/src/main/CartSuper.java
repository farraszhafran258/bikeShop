package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CartSuper extends JFrame implements ActionListener {

	private JPanel nPanel, bPanel, lPanel, pPanel, rPanel;
	private JLabel qty, tot, pay, change, stock, image, member;
	private JTextField memberId, totPay, jumlah;
	private JButton bayar, hitung;
	private GridBagConstraints gbc;
	private JCheckBox nonMember;

	Connect con = Connect.getConnection();
	
	private String gambar = "", sTemp = "", pTemp = "";
	private int bikeStock = 0, bikePrice = 0, quantity = 0, bikeId = 0, idUser, kembalian = 0, purchaseId, superId;
	
	
	private void initialize() {
		// TODO Auto-generated method stub
		setTitle("Cart");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
	
	
	private void initComp() {
		// TODO Auto-generated method stub
		nPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		
		gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
				
		bPanel = new JPanel(new GridBagLayout());
		lPanel = new JPanel(new GridBagLayout());
		pPanel = new JPanel(new GridBagLayout());
		rPanel = new JPanel(new GridBagLayout());
		
		getBikeData();
		
		qty = new JLabel("Quantity: " );
		tot = new JLabel("Total Price: Rp");
		pay = new JLabel("Total Payment: ");
		change = new JLabel("Change: Rp" + kembalian );
		stock = new JLabel("Stock: " + bikeStock);
		
		bayar = new JButton("Pay Now");
		bayar.addActionListener(this);
		hitung = new JButton("Calculate");
		hitung.addActionListener(this);
		
		member = new JLabel("Member ID");
		
		jumlah = new JTextField();
		jumlah.setPreferredSize(new Dimension(100, 25));
		
		totPay = new JTextField();
		totPay.setPreferredSize(new Dimension(100, 25));
		
		memberId = new JTextField();
		memberId.setPreferredSize(new Dimension(50, 25));
		
		nonMember = new JCheckBox("Non-Member");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lPanel.add(qty, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		lPanel.add(jumlah, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		lPanel.add(tot, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		pPanel.add(pay, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		pPanel.add(totPay, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		pPanel.add(change, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		pPanel.add(member, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		pPanel.add(memberId, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		pPanel.add(nonMember, gbc);
		
		
		try {
			ImageIcon img = new ImageIcon(getClass().getResource(gambar));
			image = new JLabel(img);
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			rPanel.add(image, gbc);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		rPanel.add(stock, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		bPanel.add(bayar, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		bPanel.add(hitung, gbc);
		
		nPanel.add(lPanel);
		nPanel.add(rPanel);
		nPanel.add(pPanel);
		nPanel.add(bPanel);
	}
	
	private void addComp() {
		// TODO Auto-generated method stub
		add(nPanel);
	}
	
	private boolean insertPurchase(int idUser, int totPrice, int superId) {
		// TODO Auto-generated method stub
		PreparedStatement ps;
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
		
		try {
			ps = con.preparedStatement("INSERT INTO purchaseHeader (`tanggal`, `operatorID`, `totalPrice`, `SupervisorID`) VALUES (?, ?, ?, ?)");
			ps.setTimestamp(1, sqlDate);
			ps.setInt(2, idUser);
			ps.setInt(3, totPrice);
			ps.setInt(4, superId);
			ps.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return false;
	}
	
	private boolean insertPurchaseMember(int idUser, int totPrice, int member) {
		// TODO Auto-generated method stub
		PreparedStatement ps;
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
		
		try {
			ps = con.preparedStatement("INSERT INTO purchaseHeader (`tanggal`, `operatorID`, `totalPrice`, `memberID`, `supervisorID`) VALUES (?, ?, ?, ?, ?)");
			ps.setTimestamp(1, sqlDate);
			ps.setInt(2, idUser);
			ps.setInt(3, totPrice);
			ps.setInt(4, member);
			ps.setInt(5, superId);
			ps.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void getPurchaseID() {
		// TODO Auto-generated method stub
		ResultSet rs = con.execQuery("SELECT purchaseID FROM purchaseHeader ORDER BY purchaseID DESC LIMIT 1");
		try {
			while (rs.next()) {
				purchaseId = rs.getInt("purchaseID");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private boolean insertDetail(int purchaseId, int bikeId, int quantity) {
		// TODO Auto-generated method stub
		PreparedStatement ps;		
		try {
			ps = con.preparedStatement("INSERT INTO purchaseDetail (`purchaseID`, `bikeID`, `Qty`) VALUES (?, ?, ?)");
			ps.setInt(1, purchaseId);
			ps.setInt(2, bikeId);
			ps.setInt(3, quantity);
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void getBikeData() {
		// TODO Auto-generated method stub
//		String query = ;
		
		ResultSet rs = con.execQuery(String.format("SELECT bikePrice, img, stock FROM bike WHERE bikeID = %d", bikeId));
		try {
			while (rs.next()) {
				bikePrice = rs.getInt("bikePrice");
				gambar = rs.getString("img");
				bikeStock = rs.getInt("stock");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static boolean numCheck(String str) {
		int checkNum = 0;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isLetter(str.charAt(i))) {
				checkNum += 1;
			}
		}
		if (checkNum == 0) {
			return false;
		} else {
			return true;
		}
		  
	}
	
	public CartSuper(int superId, int idUser, int bikeId, int quantity) {
		// TODO Auto-generated constructor stub
		this.superId = superId;
		this.idUser = idUser;
		this.bikeId = bikeId;
		this.quantity = quantity;
		initComp();
		addComp();
		initialize();
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String tp, q;
		tp = totPay.getText();
		q = jumlah.getText();
		int tpTemp, totPrice, mTemp, qTemp;
		try {
			tpTemp = Integer.parseInt(tp);
			
		} catch (Exception e2) {
			// TODO: handle exception
			tpTemp = -1;
		}	
		
		try {
			mTemp = Integer.parseInt(memberId.getText());
			
		} catch (Exception e2) {
			// TODO: handle exception
			mTemp = -1;
		}
		try {
			qTemp = Integer.parseInt(q);
		} catch (Exception e2) {
			// TODO: handle exception
			qTemp = -1;
		}
		totPrice = bikePrice * qTemp;
		
		if (e.getSource() == bayar) {
			if (numCheck(q)) {
				JOptionPane.showMessageDialog(this, "Quantity harus numerik", "Warning!", JOptionPane.ERROR_MESSAGE);
				
			} else if (numCheck(tp)) {
				JOptionPane.showMessageDialog(this, "Nominal harus numerik", "Warning!", JOptionPane.ERROR_MESSAGE);
				
			}else if (qTemp < 1) {
				JOptionPane.showMessageDialog(this, "Quantity harus lebih dari 1", "Warning!", JOptionPane.ERROR_MESSAGE);
				
			}else if (tpTemp < totPrice) {
				JOptionPane.showMessageDialog(this, "Uang kurang", "Warning!", JOptionPane.ERROR_MESSAGE);
			} else {
				kembalian = tpTemp - (bikePrice*quantity);
				change.setText("Change: Rp" + kembalian);
				if (nonMember.isSelected()) {
					insertPurchase(idUser, totPrice, superId);					
				} else {
					insertPurchaseMember(idUser, totPrice, mTemp);
				}
				getPurchaseID();
				insertDetail(purchaseId, bikeId, quantity);
				JOptionPane.showMessageDialog(this, "Pembelian berhasil", "Success", JOptionPane.PLAIN_MESSAGE);
				Menu m = new Menu(idUser);
				setVisible(false);
				m.setVisible(true);
			}
		} else if (e.getSource() == hitung) {
			tot.setText("Total Payment: Rp" + bikePrice * qTemp);
		}
		
	}

}
