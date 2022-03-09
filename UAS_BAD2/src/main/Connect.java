package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE_NAME = "bikeShop";
	private final String HOST = "localhost:3306";
	
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s",  HOST, DATABASE_NAME);
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement ps;
	
	private static Connect connect;
	
	private Connect() {
		// TODO Auto-generated constructor stub
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public static synchronized Connect getConnection() {
			/**
			* If the connect is null then:
			*   - Create the instance from Connect class
			*   - Otherwise, just assign the previous instance of this class
			*/
			return connect = (connect == null) ? new Connect() : connect;
	    }
	
	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insertPokemonAdmin(String name, int lvl, String type) {
		try {
			ps = con.prepareStatement("INSERT INTO pokemon ( `PokemonName`, `PokemonLevel`, `PokemonType`) VALUES (?,?,?)");
			ps.setString(1, name);
			ps.setInt(2, lvl);
			ps.setString(3, type);
			ps.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return false;
	}
	
	public boolean register(String username, String name, int age, String email, String gender, String pass) {
		try {
			ps = con.prepareStatement("INSERT INTO user ( `Username`, `Name`, `Age`, `Email`, `Gender`, `Password`) VALUES (?,?,?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, name);
			ps.setInt(3, age);
			ps.setString(4, email);
			ps.setString(5, gender);
			ps.setString(6, pass);
			ps.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	 public void executeUpdate(String query) {
		 try {
		    st.executeUpdate(query);
		 } catch (SQLException e) {
		    e.printStackTrace();
		 }
	}
		  
		  //prepared statement method
	public PreparedStatement preparedStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
	public ResultSet read(String query) {
		ResultSet rs = null;
		try {
			rs= st.executeQuery(query);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}

}
