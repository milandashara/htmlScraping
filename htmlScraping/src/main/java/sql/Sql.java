package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Sql {

	
	public static Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\milan\\git\\htmlScraping\\htmlScraping\\src\\main\\java\\bitZillion.db3");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		return c;
	}

	public static void createTable() throws SQLException {
		Connection c = getConnection();
		Statement stmt = c.createStatement();
		String sql = "CREATE TABLE TableData "
				+ "(transactionId           TEXT  PRIMARY KEY  NOT NULL, "
				//+ " payoutTransactionId            TEXT     NOT NULL, "
				+ " result        TEXT , "
				+ " betAmount        REAL , "
				+ " payoutAmt        REAL , "
				+ " roundNumber        REAL , "
				+ "timeStamp   TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
				//+ " payoutAmount         REAL NOT NULL);";
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
	}
	
	public static void dropTable() throws SQLException
	{
		Connection c = getConnection();
		Statement stmt = c.createStatement();
		String sql = "drop   table  TableData";
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
	}

	public static void insertTableData(TableData td, Connection c) throws SQLException {

		System.out.println("insertTableData");
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO TableData (transactionId,result,betAmount,payoutAmt,roundNumber) "
				+ "VALUES ('"
				+ td.getTransactionId()
				//+ "', '"
				//+ td.getPayoutTransactionId()
				+ "', '"
				+ td.getResult()
				+ "', "
				+ td.getBetAmount() 
				+ "," 
				+ td.getPayoutAmount() 
				+ "," 
				+ td.getRoundNum()+" );";
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	public static void updateTableData(TableData td, Connection c) throws SQLException {

		System.out.println("updateTableData");
		Statement stmt = c.createStatement();
		/*String sql = "INSERT INTO TableData (transactionId,result,betAmount,payoutAmt,roundNumber) "
				+ "VALUES ('"
				+ td.getTransactionId()
				//+ "', '"
				//+ td.getPayoutTransactionId()
				+ "', '"
				+ td.getResult()
				+ "', "
				+ td.getBetAmount() 
				+ "," 
				+ td.getPayoutAmount() 
				+ "," 
				+ td.getRoundNum()+" );";*/
		
		String sql = "UPDATE TableData set result='"+td.getResult()+"',betAmount="+td.getBetAmount()+",payoutAmt="+td.getPayoutAmount()+" where transactionId='"+td.getTransactionId()+"';";
		System.out.println("sql " + sql);
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	public static void main(String[] args) throws Exception {
		dropTable();
		createTable();
		//storeData("http://bitzillions.com/satoshibones","c1a248d8927199f2...",1);
		insertTableData("milan", 1,
				new Timestamp(System.currentTimeMillis()), getConnection());
		TableData td=new TableData();
		td.setTransactionId("milan");
		td.setResult("win");
		td.setBetAmount(0.002);
		td.setPayoutAmount(0.01);
		updateTableData(td, getConnection());
	}

	public static void insertTableData(String transactionId, int roundNum,
			Timestamp timeStamp, Connection c) throws SQLException {
		System.out.println("insertTableData");
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO TableData (transactionId,roundNumber,timeStamp) "
				+ "VALUES ('"
				+ transactionId
				//+ "', '"
				//+ td.getPayoutTransactionId()
				+ "'," 
				+ roundNum
				+ ",'" 
				+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timeStamp)
				+"' );";
		
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
		
	}

}
