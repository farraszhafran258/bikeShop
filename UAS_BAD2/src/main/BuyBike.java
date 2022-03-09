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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BuyBike extends JFrame implements ActionListener{

	private JPanel nPanel, cPanel, sPanel, bPanel;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private JTextField bikeId, Qty;
	private JButton buy, back;
	private JLabel buyB, quantity, error;
	private GridBagConstraints gbc;
	
	private JMenuBar menuBar;
	private JMenu user, purchase, trans;
	private JMenuItem signout, product, addUser, addMember, viewTrfH;
	
	Vector<Vector<Object>> tableContents;
	Vector<Object> tableRows, tableHeader;
	
	int id = 0, qty = 0, totalPurchase, totalPendapatan;
	
	Connect con = Connect.getConnection();
	
	private int idUser;
	
	private void initialize() {
		setTitle("Choose Bike");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
	private Vector<Object> createData(int id, String name, int price, int stock) {
		tableRows = new Vector<>();
		tableRows.add(id);
		tableRows.add(name);
		tableRows.add(price);
		tableRows.add(stock);
		
		return tableRows;
	}
	
	private void initTable() {
		nPanel = new JPanel();
		
		tableContents = new Vector<Vector<Object>>();
		
		tableHeader = new Vector<Object>();
		
		tableHeader.add("Bike ID");
		tableHeader.add("Bike Name");
		tableHeader.add("Bike Price");
		
		dtm = new DefaultTableModel(tableContents, tableHeader);
		fetchData();
		table = new JTable(dtm){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
				}
			};
			
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(800, 200));
		
		nPanel.add(scrollPane);

	}
	
	private void fetchData() {
		// TODO Auto-generated method stub
			while (dtm.getRowCount() > 0) {
				dtm.removeRow(0);
			}
			
			String query = "SELECT * FROM bike";
			ResultSet rs = con.execQuery(query);

			try {
				while(rs.next()) {
					int id = rs.getInt("bikeID");
					String name = rs.getString("bikeName");
					int price = rs.getInt("bikePrice");
					int stock = rs.getInt("stock");
					
					Vector<Object> newData = createData(id, name, price, stock);
					tableContents.add(newData);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	

	
	private void initComp() {
		cPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
				
		bPanel = new JPanel(new GridBagLayout());
		
		sPanel = new JPanel(new BorderLayout());
		
		buyB = new JLabel("Choose Bike ID: ");
		quantity = new JLabel("Quantity: ");
		error = new JLabel("");
		error.setForeground(Color.RED);
		
		bikeId = new JTextField();
		bikeId.setPreferredSize(new Dimension(200, 30));
		Qty = new JTextField();
		Qty.setPreferredSize(new Dimension(200, 30));
		
		buy = new JButton("Buy");
		buy.setPreferredSize(new Dimension(150, 30));
		buy.addActionListener(this);
		back = new JButton("Back to main");
		back.setPreferredSize(new Dimension(150, 30));
		back.addActionListener(this);
		
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
		
		user.add(signout);
		user.add(addUser);
		user.add(addMember);
		menuBar.add(user);
		
		purchase.add(product);
		menuBar.add(purchase);
		
		trans.add(viewTrfH);
		menuBar.add(trans);
		
		setJMenuBar(menuBar);
		
		report();
		report2();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		cPanel.add(buyB, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		cPanel.add(bikeId, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		cPanel.add(quantity, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		cPanel.add(Qty, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		bPanel.add(buy, gbc);
	
		gbc.gridx = 1;
		gbc.gridy = 0;
		bPanel.add(back, gbc);
		
		sPanel.add(bPanel, BorderLayout.NORTH);
	}
	
	private void addComp() {
		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(sPanel, BorderLayout.SOUTH);
	}	
	
	public BuyBike(int idUser) {
		// TODO Auto-generated constructor stub
		this.idUser = idUser;
		initTable();
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
				return;
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
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private boolean checkBike() {
		String bikeid = bikeId.getText();
		
		String query = "SELECT * FROM bike";
		
		ResultSet rs = con.read(query);
		try {
			while (rs.next()) {
				if (bikeid.equals(rs.getString(1))) {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return true;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == buy) {
			String qTemp = " ", iTemp = "";
			qTemp = Qty.getText();
			iTemp = bikeId.getText();
			try {
				qty = Integer.parseInt(qTemp);
				
			} catch (Exception e2) {
				qty = -1;
				
			}
			try {
				id = Integer.parseInt(iTemp);
				
			} catch (Exception e2) {
				id = -1;
				
			}
			if (iTemp.isEmpty() || qTemp.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Semua data harus diisi", "Error!", JOptionPane.ERROR_MESSAGE);
			} else if (numCheck(iTemp)){
				JOptionPane.showMessageDialog(this, "Bike ID harus numerik", "Error!", JOptionPane.ERROR_MESSAGE);
			} else if (numCheck(qTemp)) {
				JOptionPane.showMessageDialog(this, "Quantity harus numerik", "Error!", JOptionPane.ERROR_MESSAGE);
			} else if (checkBike()) {
				JOptionPane.showMessageDialog(this, "Bike ID harus tersedia", "Error!", JOptionPane.ERROR_MESSAGE);
			} else if (qty < 1) {
				JOptionPane.showMessageDialog(this, "Quantity harus lebih dari 1", "Error!", JOptionPane.ERROR_MESSAGE);
			} else {
				Cart c = new Cart(idUser, id, qty);
				setVisible(false);	
				c.setVisible(true);
			}			
			
		} else if(e.getSource() == back){
			Menu m = new Menu(idUser);
			setVisible(false);
			m.setVisible(true);
		} else if (e.getSource() == product) {
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
