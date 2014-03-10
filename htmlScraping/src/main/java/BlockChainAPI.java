

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BlockChainAPI {

	private String walletInfoUrl;
	private JSONObject walletInfo;
	private String walletSendURL="https://blockchain.info/merchant/$guid/payment?password=:1&second_password=:2&to=:3&amount=:4&from=:5&shared=false";
	public JSONObject getWalletInfo() {
		return walletInfo;
	}
	
	public BlockChainAPI(String myAddress) {	
		walletInfoUrl = "http://blockchain.info/address/" + myAddress + "?format=json&limit=1";
	}
	
	public String sendBitCoin(String password1,String password2,String toAddress,double amount,String fromAddress)
	{
		walletSendURL.replaceAll(":1", password1);
		walletSendURL.replaceAll(":2", password2);
		walletSendURL.replaceAll(":3", toAddress);
		walletSendURL.replaceAll(":4", String.valueOf(amount));
		walletSendURL.replaceAll(":5", fromAddress);
		
		System.out.println("sendBitCoin url : "+walletSendURL);
		JSONObject sendBitCoinInfo;
		String transactionId="";
		try {		
			sendBitCoinInfo = new JSONObject(readUrl(walletInfoUrl));
			transactionId=sendBitCoinInfo.get("tx_hash").toString();
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
		transactionId=transactionId.substring(0, 16);//1s 16 character
		System.out.println("16 digit Transaction Id : "+transactionId);
		
		return transactionId;
	}
	
	public void refreshWalletInfo() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println(sdf.format(Calendar.getInstance().getTime()) + ": Wallet Info URL: " + walletInfoUrl);
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
	
	public String toString() {
		for(Object key: walletInfo.keySet()) {
			if (walletInfo.get(key.toString()) instanceof JSONArray){
				JSONArray jsonKey = walletInfo.getJSONArray(key.toString());
				System.out.println(key.toString()+ ": " + jsonKey);
			} else {
				Object jsonKey = walletInfo.get(key.toString());
				System.out.println(key.toString()+ ": " + jsonKey);
			}
		}
		return null;
	}
	
}
