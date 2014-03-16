
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BlockChainAPI {

	static JSONObject walletInfo;
	private String walletSendURL="http://blockchain.info/merchant/:0/payment?password=:1&to=:3&amount=:4&shared=false";
	/*https://blockchain.info/merchant/$guid/payment?
	 * password=$main_password
	 * &second_password=$second_password
	 * &to=$address
	 * &amount=$amount
	 * &from=$from
	 * &shared=$shared
	 * &fee=$fee
	 * &note =$note
	 */
	
	public String sendBitCoin(String fromGuid, String password1, String password2, String toAddress, Integer amount)
	{
		walletSendURL = walletSendURL.replaceAll(":0", fromGuid);
		walletSendURL = walletSendURL.replaceAll(":1", password1);
		walletSendURL = walletSendURL.replaceAll(":2", password2);
		walletSendURL = walletSendURL.replaceAll(":3", toAddress);
		walletSendURL = walletSendURL.replaceAll(":4", String.valueOf(amount));
		
		System.out.println("sendBitCoin url : " + walletSendURL);
		String transactionId="";
		try {		
			JSONObject sendBitCoinInfo = new JSONObject(readUrl(walletSendURL));
			transactionId = sendBitCoinInfo.get("tx_hash").toString();
		} catch (MalformedURLException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (JSONException e) {
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		System.out.println("Transaction Id : "+ transactionId);
		transactionId = transactionId.substring(0, 16);//1s 16 character
		System.out.println("16 digit Transaction Id : "+transactionId);
		
		return transactionId;
	}
	
	private static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}

	public void setWalletInfo(String wallet) {
	
		String walletInfoUrl = "http://blockchain.info/address/" + wallet + "?format=json&limit=1";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			walletInfo = new JSONObject(readUrl(walletInfoUrl));
		} catch (MalformedURLException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (JSONException e) {
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}			
		System.out.println("Set Wallet Data: " + sdf.format(Calendar.getInstance().getTime()) + ": Wallet Info URL: " + walletInfoUrl);
	}
	
	public void getWalletBalance() {
		
		
		for(Object key: walletInfo.keySet()) {
			if (walletInfo.get(key.toString()) instanceof JSONArray){
				JSONArray jsonKey = walletInfo.getJSONArray(key.toString());
				if(key.toString().equals("final_balance")) {
					System.out.println(key.toString()+ ": " + jsonKey);
				}
			} else {
				Object jsonKey = walletInfo.get(key.toString());
				if(key.toString().equals("final_balance")) {
					System.out.println(key.toString()+ ": " + (new Double(jsonKey.toString()) / new Double(100000000)));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		String myWallet 		= "1GNGRNWFsjfgRbUVXNCX99vFiDVS62k7k3";
		BlockChainAPI BlockChain = new BlockChainAPI();
		BlockChain.setWalletInfo(myWallet);
		BlockChain.getWalletBalance();
		
	}
	
}
