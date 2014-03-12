
import java.sql.Timestamp;
import java.util.HashMap;

import sql.TableData;

public class Main {

	public static void main(String[] args) throws Exception {

		int roundNum = 0;
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

		/** INITIALIZE BEGIN */
		String myWallet 		= "1Htt5pMEX6PjAM9ZhmuxAGoixk48xt5iL9";
		String bones50Wallet 	= "1bonesF1NYidcd5veLqy1RZgF4mpYJWXZ";
		String urlToRead 		= "http://bitzillions.com/satoshibones";

		String password1="";
		String password2="";
		String bitZillionAddress=myWallet;
		String fromAddress=bones50Wallet;

		BlockChainAPI BlockChain = new BlockChainAPI();
		/** INITIALIZE END */

		/** BEGIN GAME */
		Timestamp timeStamp=new Timestamp(System.currentTimeMillis());
		System.out.println("Game starts: " + timeStamp);
				
		//bet 10 Rounds
		for (int i = 0; i < 10; i++) {

			roundNum = i+1;
			System.out.println("Round: " + roundNum);

			//1. Bet by transferring bitcoin from myWallet to bitZillions and storing transactionId in the database with a timestamp.
			String transactionId = BlockChain.sendBitCoin(password1, password2, bitZillionAddress, bets.get(roundNum), fromAddress);
			
			//2. Store the transactionId and round in the database
			HTMLUnit.storeTransactionId(transactionId, roundNum, timeStamp);
			
			
			//3. Check for the transactionId on bitZillions.
			//Begin Loop: Make this a loop that loops every 5 seconds for up to 1 minute.
			TableData tableData = new TableData();
			
			long endTime = System.currentTimeMillis()+60000;
			while(System.currentTimeMillis() < endTime) {
				tableData = HTMLUnit.searchBitZillion(urlToRead, transactionId.substring(0, 16), roundNum);
				if(tableData != null) {
					tableData.setTransactionId(transactionId);
					break;
				}
				Thread.sleep(5000);
			}
			
			//3. Store dat0a related to transactionId
			HTMLUnit.storeBitZillionResults(tableData);

			//4. Check if game has ended.
			if(tableData.getResult().equals("win")) {
				break;
			}
		}
		
		/** BEGIN GAME */
		Timestamp endtimeStamp=new Timestamp(System.currentTimeMillis());
		
		System.out.println("Game Ends: " + endtimeStamp);
		/** GAME ENDS */

	}

}
