package pl.woodbase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class SqlConn {
	
	private String connectionUrl = "jdbc:sqlserver://WEEKE-W2K12;" +  
			   "databaseName=woodbase;user=sa;password=Weekedbp1;";
	
	//polaczenie z baza
	public Connection getConnection() throws SQLException {
		
		String connectionUrl = this.connectionUrl;
		Connection conn = DriverManager.getConnection(connectionUrl);
		
		if(conn != null) {
			System.out.println("Po³¹czono z baz¹");
		}
		
		return conn;
	}
	
	public DefaultTableModel getTable() {
		
		Vector<Vector<Object>> data = null;
		Vector<String> columnNames = null;
		Connection conn;
		Statement stmt;
		ResultSet rs;
		ResultSetMetaData md;
		
		try {
		conn = getConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT * FROM dbo.WORKLIST");
		md = rs.getMetaData();
		
		//pobieranie nazw kolumn z bazy
		
		columnNames = new Vector<String>();
		int columnCount = md.getColumnCount();
		for(int column =1; column <= columnCount; column++) {
			
			columnNames.add(md.getColumnName(column));
			
		}
		
		//pobieranie danych pod odpowiednie nazwy kolumn
		data = new Vector<Vector<Object>>();
		while(rs.next()) {
			Vector<Object> vect = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vect.add(rs.getObject(columnIndex));
			}
			data.add(vect);
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return new DefaultTableModel(data, columnNames);
	}
	
	public DefaultTableModel getTable(int id) {
		Vector<Vector<Object>> data = null;
		Vector<String> columnNames = null;
		Connection conn;
		Statement stmt;
		ResultSet rs;
		ResultSetMetaData md;
		
		try {
		conn = getConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT * FROM dbo.WORKLIST WHERE REFID LIKE '%" + id + "%'");
		md = rs.getMetaData();
		
		//pobieranie nazw kolumn z bazy
		
		columnNames = new Vector<String>();
		int columnCount = md.getColumnCount();
		for(int column =1; column <= columnCount; column++) {
			
			columnNames.add(md.getColumnName(column));
			
		}
		
		//pobieranie danych pod odpowiednie nazwy kolumn
		data = new Vector<Vector<Object>>();
		while(rs.next()) {
			Vector<Object> vect = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vect.add(rs.getObject(columnIndex));
			}
			data.add(vect);
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return new DefaultTableModel(data, columnNames);
	}
	
	public void addRecord(int id, String path) {
		
		Connection conn = null;
		Statement stmt;
		String sqlq = "INSERT INTO dbo.WORKLIST(REFID, MPR) VALUES(" +id+ ",'" +path+ "')";
		
		try {
		conn = getConnection();
		stmt = conn.createStatement();
		stmt.executeUpdate(sqlq);
	
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		
	}
	
	public void deleteRecord(String id, String path) {
		
		Connection conn = null;
		Statement stmt;
		String sqlq = "DELETE FROM dbo.WORKLIST WHERE REFID ='" +id+ "' AND MPR='" +path+"'" ;
		
		try {
		conn = getConnection();
		stmt = conn.createStatement();
		stmt.executeUpdate(sqlq);
	
		} catch (SQLException e) {	
			e.printStackTrace();
		}
	}
	
	public String getConnectionUrl() {
		
		return connectionUrl;
	}
	
	public void setConnectionUrl(String connectionUrl) {
		
		this.connectionUrl = connectionUrl;
	}
	
}

