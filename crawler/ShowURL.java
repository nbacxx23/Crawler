package fr.paristech.telecom.inf396.crawler;

import java.util.HashSet;
import java.util.Set;

public class ShowURL {
	
	static int maxThread = 3;
	
	public static void main(String[] args){
			
		Set<String> seed = new HashSet<String>();
		
		seed.add("http://example.com");
		
		GraphExtractionRobot crawl = new GraphExtractionRobot("yushan",10,10,seed,10);
		
		for (int i = 1; i <= maxThread; i++){
			new Thread(crawl).start(); 
			//System.out.println("Finish the "+i+"th Thread");
		}
		
		
		//(new ShowURLRobot("yushan",1,2)).executionLoop(seed, 5);
		
		//GraphExtractionRobot crawl = new GraphExtractionRobot("yushan",10,2);
		//crawl.executionLoop(seed, 10);
		
		/*for(int i=1;i<crawl.number.size();i++){
			System.err.printf(crawl.number.get(i),crawl.racin.get(crawl.number.get(i)));
		}*/
		
	}
	
}
