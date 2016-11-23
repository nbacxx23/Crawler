package Yuxiang.Crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.plaf.synth.SynthScrollBarUI;

public class Robots{
	
	public class NameDelayPair{
		private Long delay;
		private String name;
		private Set<String> Disallow;
		
		public Long getDelay() {
			return delay;
		}
		public void setDelay(Long delay) {
			this.delay = delay;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Set<String> getDisallow() {
			return Disallow;
		}
		public void setDisallow(Set<String> disallow) {
			Disallow = disallow;
		}
	}
	
	
	public NameDelayPair get_delay(String url) throws IOException{
		NameDelayPair pair = new NameDelayPair();
		BufferedReader in = null;
		URL temp = new URL(url);
		String robots = temp.getProtocol()+"://"+temp.getAuthority() + "/" + "robots.txt";
		pair.setName(temp.getProtocol()+"://"+temp.getAuthority());
		pair.setDelay(0L);
		URL R = new URL(robots);
		URLConnection connection = R.openConnection();
		connection.connect();
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null)
		{
			if (line.startsWith("Crawl-Delay")){
				pair.setDelay(Long.parseLong(line.split("Crawl-Delay: ")[1]));
			}
		}
		return pair;
	}

	
}