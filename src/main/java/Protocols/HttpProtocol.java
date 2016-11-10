package Protocols;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class HttpProtocol extends Protocol{
	
	@Override
	public StringBuilder fetch(URL url) {
		// TODO Auto-generated method stub
		try {
			HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.connect();

			int code = connection.getResponseCode();
		    //System.out.println("code="+code);
			if(code!=200) return null;
		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    
		    StringBuilder result = new StringBuilder();
		    String line;
		    while((line=rd.readLine())!=null){
		    	result.append(line+"\n");
		    }
		    connection.disconnect();
		    return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}
	}

	@Override
	public URL newUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
