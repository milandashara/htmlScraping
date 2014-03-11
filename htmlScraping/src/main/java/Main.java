
import java.sql.Timestamp;
import java.util.HashMap;

import sql.TableData;

public class Main {

	public static void main(String[] args) throws Exception {

		int round = 0;
		/*
		 * HashMap<Integer, Double> bets = new HashMap<Integer, Double>();
		 * bets.put(1, 0.004); bets.put(2, 0.008145); bets.put(3, 0.016586);
		 * bets.put(4, 0.033773); bets.put(5, 0.068770); bets.put(6, 0.140035);
		 * bets.put(7, 0.285149); bets.put(8, 0.580640); bets.put(9, 1.182339);
		 * bets.put(10, 2.407560);
		 */
		HashMap<Integer, Double> bets = new HashMap<Integer, Double>();
		bets.put(1, 0.001);
		bets.put(2, 0.001);
		bets.put(3, 0.001);
		bets.put(4, 0.001);
		bets.put(5, 0.001);
		bets.put(6, 0.001);
		bets.put(7, 0.001);
		bets.put(8, 0.001);
		bets.put(9, 0.001);
		bets.put(10, 0.001);

		String myWallet = "1Htt5pMEX6PjAM9ZhmuxAGoixk48xt5iL9";
		String bones50Wallet = "1bonesF1NYidcd5veLqy1RZgF4mpYJWXZ";
		// String urlToRead = "http://www.google.co.in/";
		// String urlToRead =
		// "http://htmlunit.sourceforge.net/javascript-howto.html";
		String urlToRead = "http://bitzillions.com/satoshibones";

		/**
		 * INITIALIZE BEGIN Perform the following tasks prior to new processing
		 * IO
		 */
		BlockChainAPI BlockChain = new BlockChainAPI(myWallet);
		HTMLUnit bitZill = new HTMLUnit();
		/** INITIALIZE END */

		/*
		 * execute round 1 bet (noted in bets hashmap) send amount to
		 * bones50Wallet from blockchain wallet using blockChainAPI (create
		 * dummy wallet for this and send small amounts) pull transaction id
		 * from blockchain for round one bet using blockChainAPI check bones
		 * site for win or lose based on transaction id when checking bones site
		 * store only the transaction id associated with the bet from blockchain
		 * store a time stamp in SQLite store the round number in SQLite if
		 * bones says lose and round < 10 then increase round +1 and bet the
		 * next round amount if bones says win then stop
		 */

		/** BEGIN GAME */
		String password1="";
		String password2="";
		String bitzillions="";
		String fromAddress="";
		
		System.out.println("Game starts :");
		
		//before starting game please ensure that you have Table created and database path is set in HTMLUnit.java
		
		//bet 10 Rounds
		for (int i = 0; i < 10; i++) {
			
			
			BlockChain.refreshWalletInfo();
			//System.out.print(BlockChain.toString());
			
			//1. bet by transfering bitcoin from blockchain to bitzillions
			//2. Extract last transaction id may be...sendFrom will return transaction id
			//String transactionId="0a8d95dbea80cf65...";
			round=i+1;
			System.out.println("Round :"+round);
			Timestamp timeStamp=new Timestamp(System.currentTimeMillis());
			String transactionId=BlockChain.sendBitCoin(password1, password2, bitzillions, bets.get(round), fromAddress,timeStamp.toString());
			
			//3. wait for some time for transaction to appear on bitzillions. Hey Jacob please configure seconds. 
			//I am not sure how much seconds I need to wait.
			Thread.sleep(8000);
			
			//4. store data related to transaction id
			TableData tableData=HTMLUnit.storeData(urlToRead, transactionId, round);
			if(tableData != null)
			{
				if(tableData.getResult().equals("win"))
				{
					break;
				}
			}
		}
		System.out.println("Game Ends :");
		/** GAME ENDS */

		/** TRADE BEGIN */
		// cryptsy.getOrderBook().setOrderBook(TableService.populatePathOrders(cryptsy));
		// cryptsy.getOrderBook().setOrderBook(TableService.populateSpreadOrders(cryptsy));
		// System.out.println("");//cryptsy.getOrderBook().getOrderBook());
		// System.out.println(cryptsy.getOrderBook().toString());
		/** TRADE END */
	}

}
