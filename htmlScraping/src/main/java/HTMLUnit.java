import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;

import sql.Sql;
import sql.TableData;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class HTMLUnit {
	
	public static TableData searchBitZillion(String strUrl,String transactionIdToSearch,int roundNum) throws Exception {
		
		 final WebClient webClient = new WebClient(BrowserVersion.getDefault());		 
		 final HtmlPage page = webClient.getPage(strUrl);
		 Thread.sleep(2000);//wait to load full page. 
		 final HtmlTable table = page.getHtmlElementById("play-result");
		 int rowCount = table.getRowCount();
		 System.out.println("bitZillion Row Count: " + rowCount);
		 
		 for (final HtmlTableRow row : table.getRows()) {
			 if (transactionIdToSearch.equals(row.getCells().get(1).asText().substring(0, 16))) {

				 TableData tableData = new TableData();
				 tableData.setTransactionId(row.getCells().get(1).asText().substring(0, 16));
			     tableData.setResult(row.getCells().get(5).asText());
			     tableData.setBetAmount(new Double(row.getCells().get(6).asText().split("\\s+")[0]));
			     tableData.setPayoutAmount(new Double(row.getCells().get(7).asText().split("\\s+")[0]));
			     tableData.setRoundNum(roundNum);
			     
			     System.out.println("found: " + tableData.getTransactionId());	
			     return tableData;
			 };
		 }
		return null;
	}
	
	public static void storeBitZillionResults(TableData tableData) {
		System.out.println("Storing the following Table Data: "+tableData.toString());
		Connection c = Sql.getConnection();
		try {
			Sql.updateTableData(tableData, c);
			c.close();
		} catch (Exception e) {
			System.out.println("InsertData exeception: " +e.getMessage());
			e.printStackTrace();
		}		
	}

	public static void storeTransactionId(String transactionId, int roundNum,Timestamp timeStamp) {
		System.out.println("Inserting the following Table Data: " + transactionId +" round number :" + roundNum);
		Connection c = Sql.getConnection();
		try {
			Sql.insertTableData(transactionId,roundNum,timeStamp, c);
			c.close();
		} catch (Exception e) {
			System.out.println("InsertData exeception :" +e.getMessage());
			e.printStackTrace();
		}		
	}

}
		