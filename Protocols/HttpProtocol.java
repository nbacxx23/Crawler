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
	
	public static HashMap<String, Integer > cache = new HashMap();
	public static HashMap<String, ArrayList<String>> cache_robots = new HashMap();

	@Override
	public void fetch(URL url) {
		// TODO Auto-generated method stub
		
		//the robots.txt has been obtained for a host name
		
		String host = url.getHost();
        if(cache.containsKey(host)){
        	ArrayList<String> array = cache_robots.get(host);
        	String test =url.getPath().replace(host, "");
        	System.out.println(test);
        	if(array.contains(url.getPath().replace(host, ""))){
        		System.out.println("interdit");
        		return;//aborted by the robots.txt
        	}
        	else{
        		cache.put(host, cache.get(host)+1);//the times used
        		
        		try {
        			HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
        			connection.setRequestMethod("GET");
        			connection.setUseCaches(false);
        			connection.setDoOutput(true);
        			
        		    //Get Response  
        		    InputStream is = connection.getInputStream();
        		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        		    String line;
        		    while ((line = rd.readLine()) != null) {
        		      
        		    }
        		    rd.close();
        		    //System.out.print(response.toString());
        		    connection.disconnect();
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}
        }
        else{
        	cache.put(host, 1);
        	String path ="http://"+host + "/robots.txt";
        	URL url_robot;
			try {
				url_robot = new URL(path);
				ArrayList<String> disallow = new ArrayList();
				try {
	    			HttpURLConnection connection =  (HttpURLConnection) url_robot.openConnection();
	    			connection.setRequestMethod("GET");
	    			connection.setUseCaches(false);
	    			connection.setDoOutput(true);
	    			
	    		    //Get Response  
	    		    InputStream is = connection.getInputStream();
	    		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
	    		    String line;
	    		    while ((line = rd.readLine()) != null) {
	    		    	String[] arr = line.split(" ");
	        		      if(arr[0].equals("Disallow:")){
	        		    	  disallow.add(arr[1]);
	        		      }
	    		    }
	    		    rd.close();
	    		    connection.disconnect();
	    		    cache_robots.put(host, disallow);
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }

		
	}

	@Override
	public URL newUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
