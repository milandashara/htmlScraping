

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
	public JSONObject getWalletInfo() {
		return walletInfo;
	}
	
	public BlockChainAPI(String myAddress) {	
		walletInfoUrl = "http://blockchain.info/address/" + myAddress + "?format=json&limit=1";
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
