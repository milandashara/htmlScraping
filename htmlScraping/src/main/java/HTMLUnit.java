import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import sql.TableData;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class HTMLUnit {

	public static TableData storeData(String strUrl,String transactionIdToSearch,int roundNum) throws Exception {
		
		  final WebClient webClient = new WebClient(BrowserVersion.getDefault());		 
		 final HtmlPage page = webClient.getPage(strUrl);
		 Thread.sleep(5000);//wait to load full page
		 final HtmlTable table = page.getHtmlElementById("play-result");
		 int rowCount=table.getRowCount();
		 System.out.println("Row Count :"+rowCount);
		 
		 HashMap<String, TableData> tableMap=new HashMap<String, TableData>();
		 for (final HtmlTableRow row : table.getRows()) {
		     System.out.println("Scrapping Table Rows");
		     String transactionId=row.getCells().get(1).asText();
		     String result=row.getCells().get(5).asText();
		     String betAmount=row.getCells().get(6).asText().split("\\s+")[0];
		     String payoutAmount=row.getCells().get(7).asText().split("\\s+")[0];
		     
		     TableData tableData=new TableData();
		     tableData.setTransactionId(transactionId);
		     tableData.setResult(result);
		     tableData.setBetAmount(new Double(betAmount));
		     tableData.setPayoutAmount(new Double(payoutAmount));
		     tableMap.put(transactionId, tableData);
		     /*for (final HtmlTableCell cell : row.getCells()) {
		         System.out.println("   Found cell: " + cell.asText());
		     }*/
		 }
		 System.out.println("found :"+tableMap.containsKey(transactionIdToSearch));
		 if(tableMap.containsKey(transactionIdToSearch))//found
		 {
			 
			 TableData tableData=tableMap.get(transactionIdToSearch);
			 System.out.println("Table Data:"+tableData.toString());
			 tableData.setRoundNum(roundNum);
			 Connection c=getConnection();
			 insertTableData(tableData, c);
			 c.close();
		 }
		 return  tableMap.get(transactionIdToSearch);
	}

	public static Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:C:\\Users\\milan\\git\\htmlScraping\\htmlScraping\\src\\main\\java\\testDB.db");
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
				+ " result        TEXT NOT NULL, "
				+ " betAmount        REAL NOT NULL, "
				+ " payoutAmt        REAL NOT NULL, "
				+ " roundNumber        REAL NOT NULL, "
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

	public static void insertTableData(TableData td, Connection c)
			throws SQLException {

		System.out.println("insertTableData");

		Statement stmt = c.createStatement();
		String sql = "INSERT INTO TableData (transactionId,result,betAmount,payoutAmt,roundNumber) "
				+ "VALUES ('"
				+ td.getTransactionId()
			//	+ "', '"
				//+ td.getPayoutTransactionId()
				+ "', '"
				+ td.getResult()
				+ "', "
				+ td.getBetAmount() + "," + td.getPayoutAmount() + "," + td.getRoundNum()+" );";

		stmt.executeUpdate(sql);

		stmt.close();

	}

	public static void main(String[] args) throws Exception {
		//dropTable();
		 //createTable();
		storeData("http://bitzillions.com/satoshibones","c1a248d8927199f2...",1);
	}


}
