package Protocols;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Crawler.Crawler;

public class Test {

	//public static int num_crawlers = 100;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*ExecutorService executor = Executors.newFixedThreadPool(num_crawlers);
		for(int i=0; i <1000; i++){
			executor.submit(new Test());
        }
		executor.shutdown();*/
		try {
			RobotExclusionRule robot = new RobotExclusionRule(new URL("http://www.dmoz.org/"));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("testtsssss");
	}

}
