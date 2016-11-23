package Yuxiang.Crawler;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;

public class Read_site{
	public static String read(String url, Set<String> bl, int k){
		String result = "";
		BufferedReader in = null;
		try
		{
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setReadTimeout(10 * 1000);
			connection.connect();
			try{
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			}
			catch(SocketTimeoutException e){
				bl.add(realUrl.getAuthority());
				k = k+1;
			}catch(ProtocolException e){
				bl.add(realUrl.getAuthority());
				k = k+1;
			}
			
			String line;
			while ((line = in.readLine()) != null)
			{
				result += line + "\n";
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			}
		
		return result;
	}
	
}