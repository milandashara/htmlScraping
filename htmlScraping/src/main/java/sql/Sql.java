package sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Sql {

	public static void main(String[] args) throws Exception {
		
		dropTable();
		createTable();
		insertTableData("jacob", 1);
		TableData td = new TableData();
		td.setTransactionId("jacob");
		td.setResult("win");
		td.setBetAmount(0.002);
		td.setPayoutAmount(0.01);
		updateTableData(td);
	}

	public static void updateTableData(TableData td) throws SQLException {
		Connection c = Sql.getConnection();
		Statement stmt = c.createStatement();
		String sql = "UPDATE TableData set "
					 + "result = '" +td.getResult()
					 +"', betAmount = " +td.getBetAmount()
					 +", payoutAmt = " +td.getPayoutAmount()
					 +", transactionFee = " +td.getTransactionFee()
					 +" where transactionId='"+td.getTransactionId()+"';";
		stmt.executeUpdate(sql);
		System.out.println("sql: " + sql);
		stmt.close();
		c.close();
	}

	public static void insertTableData(String transactionId, int roundNum) throws SQLException {		
		Connection c = Sql.getConnection();
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO TableData (transactionId, roundNumber, timeStamp) VALUES ('"
						+ transactionId
						+ "'," + roundNum
						+ ",'" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis())) +"' );";
		stmt.executeUpdate(sql);
		System.out.println("sql: " + sql);
		stmt.close();
		c.close();	
	}

	public static Connection getConnection() {	
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			File file=new File("");
			String path=file.getAbsolutePath()+"\\src\\main\\java\\bitZillion.sqlite";
			c = DriverManager.getConnection("jdbc:sqlite:"+path);
			//c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\??\\git\\htmlScraping\\htmlScraping\\src\\main\\java\\bitZillion.db3");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
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
				+ " transactionFee        REAL , "
				+ " roundNumber        REAL , "
				+ "timeStamp   TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
				//+ " payoutAmount         REAL NOT NULL);";
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
	}

	public static void dropTable() throws SQLException {
		
		Connection c = getConnection();
		Statement stmt = c.createStatement();
		String sql = "drop   table  TableData";
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
	}

}
