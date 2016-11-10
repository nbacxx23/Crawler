package Processing;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import URLSeenTest.SeenTest;
import frontier.URLFrontier;

public class Extractor {
	
	public static Set<URL> extract(String result, String oldLinkHost) throws MalformedURLException{
		Set<URL> linkSet = new HashSet();
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
			linkSet.add(new URL(url));
		}
		return linkSet;
	}
	
	public static Set<URL> extractSeeds(String result, String oldLinkHost) throws MalformedURLException{
		Set<URL> linkSet = new HashSet();
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
			url ="http://" + new URL(url).getHost()+"/";
			linkSet.add(new URL(url));
		}
		return linkSet;
	}
	
	
}
