package Processing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PageReader {
	public static String read(String url){
		String result = "";
		BufferedReader in = null;
		try
		{
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setReadTimeout(15*1000);
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
