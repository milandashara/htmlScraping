import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import sql.Sql;
import sql.TableData;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class HTMLUnit {
	
	public static void main(String[] args) throws Exception {
		Double betFactor = 1.965;
		String urlToRead = "http://bitzillions.com/satoshibones";
		String transactionId = "a11f0ecbfe326094";
		TableData tableData = HTMLUnit.searchBitZillion(urlToRead, transactionId, 1,betFactor);
		HTMLUnit.storeBitZillionResults(tableData);
	}
	
	public static TableData searchBitZillion(String strUrl,String transactionIdToSearch,int roundNum,double betFactor) throws Exception {
		
		 final WebClient webClient = new WebClient(BrowserVersion.getDefault());		
		 
		 HtmlPage page = null;
		 HtmlTable table = null;
		 TableData tableData = null;
		 Integer cnt = 1;
		 while (tableData == null) {
			 page = null;
			 page = webClient.getPage(strUrl);			  
			 //Check if page is loaded
			 if (cnt <= 5) {
				 Thread.sleep(5000);
			 } else if (cnt <= 10) {
				 Thread.sleep(10000);
			 } else if (cnt <= 20) {
				 Thread.sleep(20000);
			 } else if (cnt <= 30) {
				 Thread.sleep(30000);
			 } else {
				 System.out.println("It has been 10 minutes please seach for the following transaction manually: " + transactionIdToSearch);
			 }
			 /*while (page.getReadyState() != DomNode.READY_STATE_COMPLETE) {
				 Thread.sleep(5000);//Wait for page to load.
			 }*/
			 table = page.getHtmlElementById("play-result");
			 //Load Table data
			 for (final HtmlTableRow row : table.getRows()) {
				 if (transactionIdToSearch.equals(row.getCells().get(1).asText().substring(0, 16))) {
					 
					 Double betAmt=new Double(row.getCells().get(6).asText().split("\\s+")[0]);
					 Double payoutAmt=new Double(row.getCells().get(7).asText().split("\\s+")[0]);
					 Double transactionFee=((betAmt * betFactor) - payoutAmt);
					 tableData = new TableData();
					 tableData.setTransactionId(row.getCells().get(1).asText().substring(0, 16));
				     tableData.setResult(row.getCells().get(5).asText());
				     tableData.setBetAmount(betAmt);
				     tableData.setPayoutAmount(transactionFee);
				     tableData.setRoundNum(roundNum);
				     tableData.setTransactionFee(transactionFee);
				     System.out.println("Transaction found on attempt: " + cnt + ": " + tableData.getTransactionId());	
				     return tableData;
				 };
			 }
			 System.out.println("Transaction not found attempt: " + cnt + " :time: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis())));
			 cnt++;
			 page.refresh();
		 }
		 return null;
	}
	
	public static void storeBitZillionResults(TableData tableData) {
		try {
			Sql.updateTableData(tableData);
		} catch (Exception e) {
			System.out.println("InsertData exeception: " +e.getMessage());
			e.printStackTrace();
		}		
	}

	public static void storeTransactionId(String transactionId, int roundNum) {
		try {
			Sql.insertTableData(transactionId, roundNum);
		} catch (Exception e) {
			System.out.println("InsertData exeception :" +e.getMessage());
			e.printStackTrace();
		}		
	}

}
		