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
import javax.swing.table.DefaultTableModel;

public class TransHis extends JFrame implements ActionListener{

	private JPanel nPanel, cPanel, sPanel;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private JButton back;
	private JLabel title, totalRev, totalPur;
	private GridBagConstraints gbc;
	private int idUser, totalPendapatan, totalPurchase;
	private JMenuBar menuBar;
	private JMenu user, purchase, trans;
	private JMenuItem signout, product, addUser, addMember, viewTrfH;
	
	Vector<Vector<Object>> tableContents;
	Vector<Object> tableRows, tableHeader;
	
	Connect con = Connect.getConnection();
	
	private void initialize() {
		setTitle("Welcome Admin");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private Vector<Object> createData(int id, int memberId,String date, int bikeId, int qty, int totalPrice) {
		tableRows = new Vector<>();
		tableRows.add(id);
		tableRows.add(memberId);
		tableRows.add(date);
		tableRows.add(bikeId);
		tableRows.add(qty);
		tableRows.add(totalPrice);
		
		return tableRows;
	}
	
	private void initTable() {
		nPanel = new JPanel(new GridBagLayout());
		
		gbc = new GridBagConstraints();		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		
		tableContents = new Vector<Vector<Object>>();
		
		tableHeader = new Vector<Object>();
		
		tableHeader.add("Purchase ID");
		tableHeader.add("Member ID");
		tableHeader.add("Date");
		tableHeader.add("Bike ID");
		tableHeader.add("Quantity");
		tableHeader.add("Total Price");
		
		model = new DefaultTableModel(tableContents, tableHeader);
		fetchData();
		table = new JTable(model){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
				}
			};
			
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(700, 200));
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		nPanel.add(scrollPane, gbc);
	}
	
	private void fetchData() {
		// TODO Auto-generated method stub
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}
			
			String query = String.format("SELECT ph.purchaseID, ph.memberID ,ph.tanggal, pd.bikeID, pd.Qty, ph.totalPrice FROM purchaseHeader ph JOIN purchaseDetail pd ON ph.purchaseID = pd.purchaseID WHERE operatorID = 2", idUser);
			ResultSet rs = con.execQuery(query);

			try {
				while(rs.next()) {
					int purchaseId = rs.getInt("purchaseID");
					int memberId = rs.getInt("memberID");
					String date = rs.getString("tanggal");
					int bikeId = rs.getInt("bikeID");
					int qty = rs.getInt("Qty");
					int totalPrice = rs.getInt("TotalPrice");
					
					Vector<Object> newData = createData(purchaseId, memberId,date, bikeId, qty, totalPrice);
					tableContents.add(newData);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	} 
	
	private void initComp() {
		cPanel = new JPanel(new GridBagLayout());
				
		sPanel = new JPanel(new GridBagLayout());
		
		title = new JLabel("Transaction History");
		title.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
		
		report();
		totalPur = new JLabel("Penjualan Hari ini: " + totalPurchase + " transaksi");
		report2();
		totalRev = new JLabel("Pendapatan Hari ini: Rp" + totalPendapatan);
		
		back = new JButton("Back to Main Menu");
		back.setPreferredSize(new Dimension(555, 30));
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
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		nPanel.add(title, gbc);
		
		gbc.gridy = 0;
		cPanel.add(totalPur, gbc);
		
		gbc.gridy = 1;
		cPanel.add(totalRev, gbc);
		
		gbc.gridy = 0;
		sPanel.add(back, gbc);

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
	
	private void addComp() {
		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(sPanel, BorderLayout.SOUTH);

	}
	
	
	public TransHis(int idUser) {
		// TODO Auto-generated constructor stub
		this.idUser = idUser;
		initTable();
		initComp();
		addComp();
		initialize();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == back) {
			Menu m = new Menu(idUser);
			setVisible(false);
			m.setVisible(true);
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
