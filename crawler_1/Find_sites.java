package Yuxiang.Crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Find_sites{
	/*
	public static Set<String> extract(String nextlink,File file) throws IOException{
		Set<String> LinkSet = new HashSet<String>();
		String oldLinkHost = nextlink;
		BufferedReader reader= new BufferedReader(new FileReader(file));
		String line = new String();
		Pattern pattern = Pattern.compile("<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>");
		Matcher matcher = null;
		while ((line = reader.readLine()) != null){
			matcher = pattern.matcher(line);
			if (matcher.find()) {
				String newLink = matcher.group(1).trim();
				if (newLink.contains(" ")){
					newLink = newLink.split(" ")[0];
				}
				if (!newLink.startsWith("http")) {
					if (newLink.startsWith("/"))
						newLink = oldLinkHost + newLink;
					else
						newLink = oldLinkHost + "/" + newLink;
				}
				
				if(newLink.endsWith("/"))
					newLink = newLink.substring(0, newLink.length() - 1);
				LinkSet.add(newLink);
				
			}
	}
		return LinkSet;
	*/
	public static Set<String> extract(String result, String oldLinkHost) throws MalformedURLException{
		Set<String> linkSet = new HashSet<String>();
		URL temp = new URL(oldLinkHost);
		oldLinkHost = temp.getProtocol() +"://"+ temp.getAuthority()+"/";
		Document doc = Jsoup.parse(result);
		Elements html_list = doc.select("a");
		for(Element i:html_list){
			String url = i.attr("href");
			if (!url.startsWith("http")) {
				if (url.startsWith("/"))
					url = oldLinkHost + url.substring(1, url.length());
				else
					url = oldLinkHost +  url;
			}
			linkSet.add(url);
		}
		return linkSet;
	}
	
}
	