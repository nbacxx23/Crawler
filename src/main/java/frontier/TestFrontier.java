package frontier;

import java.io.IOException;
import java.net.URL;

import Crawler.SeedsReader;

public class TestFrontier {
	public static void main(String[] args) throws IOException{
		URLFrontier frontier = new URLFrontier();
		frontier.seed_urls.addAll(SeedsReader.readSeedsFile("seeds.txt"));
		frontier.createPriorityQueue();
		URL url= null;
		while((url = frontier.pop())!=null)
		System.out.println(url.toString());
	}
}
