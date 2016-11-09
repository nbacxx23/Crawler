package Protocols;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotExclusionRule {
	private double acces_time;
	private List<String> disallow = null;
	public RobotExclusionRule(URL url) throws IOException {
		Protocol pro = Selector.selectProtocol(url);
		URL robots;
		try {
			
			robots = new URL(url.getProtocol()+"://"+url.getHost()+"/robots.txt");
			//System.out.println(url.toString());
			StringBuilder result = pro.fetch(robots);
			Date date = new Date();
			Long time_visited = date.getTime();
			String[] disallows=null;
			//Pattern pattern = Pattern.compile("(User\\-agent:\\s+\\*)(.*)(User\\-agent:.*)",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Pattern pattern = Pattern.compile("(Crawl-Delay:\\s+)(.*)",Pattern.CASE_INSENSITIVE);
			Matcher m = pattern.matcher(result);
			if(m.find()){
				//System.out.println(m.group(2));
				this.acces_time = time_visited + Integer.valueOf(m.group(2));
			}
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*public boolean isAllowed(){
		{}
	}*/
	public double getAcces_time() {
		return acces_time;
	}
	public void setAcces_time(double acces_time) {
		this.acces_time = acces_time;
	}
	public List<String> getDisallow() {
		return disallow;
	}
	public void setDisallow(List<String> disallow) {
		this.disallow = disallow;
	}
	
}
