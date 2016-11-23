package Yuxiang.Crawler;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.io.FileUtils;

import Yuxiang.Crawler.Robots.NameDelayPair;

public class Crawling implements Callable<Set<String>> {
	
	static Set<String> finished_links = new HashSet<String>();
	static LinkedBlockingDeque<String> queue =new LinkedBlockingDeque<String>();
	static HashMap<String, Long> rules= new HashMap<String, Long>();
	static HashMap<String, Long> running_time = new HashMap<String, Long>();
	static Set<String> tmp = new HashSet<String>();
	static HashMap<String, Queue<String>> waiting_urls = new HashMap<String, Queue<String>>();
	static Set<String> blacklist = new HashSet<String>();
	static Integer k = 0;
	static Integer error = 0;
	public Set<String> call() throws Exception {
		String nextlink = queue.poll();
		URL url_tmp = new URL(nextlink);
		//check finished list and blacklist
		while(finished_links.contains(nextlink) || blacklist.contains(url_tmp.getAuthority()))
		{
			nextlink = queue.poll();
			url_tmp = new URL(nextlink);
		}
		Set<String> Set_links = new HashSet<String>();
		Set<String> Set_tmp = new HashSet<String>();
		String result = new String();
		//read a web page
		result = Read_site.read(nextlink, blacklist, error);
		//write file 
		FileUtils.write(new File("/home/yuxiang/Documents/pages8/"+Thread.currentThread().getName()+k+".html"), result, "utf-8");
		k +=1;
		System.out.println("write file \t" +k+".html");
		finished_links.add(nextlink);

		Set_links.addAll(Find_sites.extract(result, nextlink));
		//extract url
		for(String url: Set_links){
			URL tmp = new URL(url);
			String Host = tmp.getAuthority();
			if(rules.containsKey(Host)){
				waiting_urls.get(Host).add(url);
			}
			else{
				//get rules from robots.txt
				Robots r = new Robots();
				NameDelayPair pair = r.get_delay(url);
				if(pair.getDelay().equals(0L)){
					queue.add(url);
				}
				else{
					rules.put(Host, pair.getDelay());
					running_time.put(Host, System.currentTimeMillis());
					Queue<String> tmp_queue = new LinkedList<String>();
					tmp_queue.add(url);
					waiting_urls.put(Host, tmp_queue);
				}
			}
		}
		//renew the delay
		if(!rules.isEmpty()){
			for(Map.Entry<String, Long> Host: running_time.entrySet()){
				if(System.currentTimeMillis()-Host.getValue()>(rules.get(Host.getKey())*1000+10000)){
					queue.addFirst(waiting_urls.get(Host.getKey()).poll());
					running_time.put(Host.getKey(), System.currentTimeMillis());
				}
			}
		}
		return finished_links;
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException{
		
		//queue.addAll(FileUtils.readLines(new File("/home/yuxiang/Documents/record.txt"), "utf-8"));
		//String url1 = "http://www.rueducommerce.fr/";
		queue.add("http://www.dmoz.org/");
		queue.add("http://news.china.com/");
		queue.add("https://www.yahoo.com/");
		queue.add("https://www.reddit.com/");
		queue.add("https://twitter.com/");
		queue.add("https://www.ft.com/");
		String link = queue.poll();
		String result = Read_site.read(link, blacklist,k);
		queue.addAll(Find_sites.extract(result, link));
		link = queue.poll();
		result = Read_site.read(link, blacklist,error);
		queue.addAll(Find_sites.extract(result, link));
		 link = queue.poll();
		 result = Read_site.read(link, blacklist,error);
		queue.addAll(Find_sites.extract(result, link));
		 link = queue.poll();
		 result = Read_site.read(link, blacklist,error);
		queue.addAll(Find_sites.extract(result, link));
		FileWriter fw = new FileWriter (new File("/home/yuxiang/Documents/records.txt"));
		BufferedWriter bw = new BufferedWriter (fw);
		PrintWriter record = new PrintWriter (bw);
		//crawler parallel
		ExecutorService executor = Executors.newFixedThreadPool(30);
        List<Future<Set<String>>> list = new ArrayList<Future<Set<String>>>();
        for(int i=0; i < 1000; i++){
            Future<Set<String>> future = executor.submit(new Crawling());
    		list.add(future);
        }
        
		for(String i:finished_links){
			record.println(i + "\n");
		}
		record.close();
		executor.shutdown();
	}
	
}
