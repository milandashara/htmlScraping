
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
		HashMap<Integer, Integer> bets = new HashMap<Integer, Integer>();
		bets.put(1, 100000); bets.put(2, 100000); bets.put(3, 100000);
		bets.put(4, 100000); bets.put(5, 100000); bets.put(6, 100000);
		bets.put(7, 100000); bets.put(8, 100000); bets.put(9, 100000);
		bets.put(10, 100000);

		/** INITIALIZE BEGIN */
		String myWallet 		= "1GNGRNWFsjfgRbUVXNCX99vFiDVS62k7k3";
		String walletId 		= "e6fdd3bf-453e-4be6-935e-25f199b0b872";
		
		String bones50Wallet 	= "1bonesF1NYidcd5veLqy1RZgF4mpYJWXZ";
		String urlToRead 		= "http://bitzillions.com/satoshibones";

		String password1="";
		String password2="";
		String toAddress=bones50Wallet;
		
		Double betFactor = 1.965;
		
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		 
		BlockChainAPI BlockChain = new BlockChainAPI();
		/** INITIALIZE END */

		/** BEGIN GAME */	
		System.out.println("Game starts: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis())));
				
		//bet 10 Rounds
		for (int i = 0; i < 10; i++) {

			roundNum = i+1;
			System.out.println("Round: " + roundNum);

			//1. Get wallet balance; confirmed and unconfirmed.
			BlockChain.setWalletInfo(myWallet);
			BlockChain.getWalletBalance();
			
			//2. Bet by transferring bitcoin from myWallet to bitZillions and storing transactionId in the database with a timestamp.
			String transactionId = BlockChain.sendBitCoin(walletId, password1, password2, toAddress, bets.get(roundNum));
			
			//3. Store the transactionId and roundNum in the database
			HTMLUnit.storeTransactionId(transactionId, roundNum);
			
			//4. Check for the transactionId on bitZillions.
			TableData tableData = HTMLUnit.searchBitZillion(urlToRead, transactionId, roundNum,betFactor);

			//5. Store results related to transactionId
			HTMLUnit.storeBitZillionResults(tableData);

			//6. Check if game has ended.
			if(tableData.getResult().equals("win")) {
				break;
			}
		}
		
		System.out.println("Game Ends on: " + roundNum + " at: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis())));
		/** GAME ENDS */

	}

}
