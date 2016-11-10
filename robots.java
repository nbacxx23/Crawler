package Yuxiang.Crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.plaf.synth.SynthScrollBarUI;

public class robots{
	public static void get_delay(String url) throws IOException{
		BufferedReader in = null;
		URL temp = new URL(url);
		String robots = temp.getProtocol()+"://"+temp.getAuthority() + "/" + "robots.txt";
		Robot_informatin.Name = temp.getProtocol()+"://"+temp.getAuthority();
		URL R = new URL(robots);
		URLConnection connection = R.openConnection();
		connection.connect();
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null)
		{
			if (line.startsWith("Disallow")){
				Robot_informatin.Disallow.add(temp.getProtocol()+"://"+temp.getAuthority() + line.split("Disallow: ")[1]);
			}
			if (line.startsWith("Crawl-Delay")){
				Robot_informatin.delay = Integer.parseInt(line.split("Crawl-Delay: ")[1]);
			}
		}
	}
	public static class Robot_informatin{
		static Integer delay = 0 ;
		static String Name = new String();
		static Set<String> Disallow = new HashSet<String>();
	}
	public static void main(String[] args) throws IOException{
		String url = "https://www.dmoz.org/World/Fran%C3%A7ais/";
		get_delay(url);
		System.out.println(Robot_informatin.Name);
	}
}
