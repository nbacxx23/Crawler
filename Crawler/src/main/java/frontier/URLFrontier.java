package frontier;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Processing.Extractor;
import Processing.PageReader;
import Protocols.RobotExclusionRule;

public class URLFrontier {
	//URL initials
	    public static int maxFront=10;
	    public static int maxBack=300;
	    public static int maxURLmove = 1000;
	    public static int maxBackQueueSize = 600;
	    
	 
		public static List<URL> seed_urls = new ArrayList();
		
		//File d'attente de type T1, choix de priorités
		public static ArrayList<Deque<URL>> list_queues_t1 = new ArrayList();
		
		//Dictionnaire pour relier les files d'attentes de T1 avec les files d'attentes de type T2
		public static Map<String, Integer> hote_deque = new HashMap();
		
		//backqueue, files d'attente de T2
		public static ArrayList<Deque<URL>> backqueue = new ArrayList();
		
		//Dictionnaire pour renseigner la strategie de politesse d'un hôte
		public static Dictionary<String, RobotExclusionRule> hote_polis = new Hashtable();
		
		//une heap pour gerer les temps d'accès
		public static FixSizedPriorityQueue<HeapNode> heap_time = new FixSizedPriorityQueue(600);
		
		//création des files d'attente de type T1, choix de priorités
		public static void createPriorityQueue() throws MalformedURLException{

			for(int i = 0; i<maxFront;i++){
				list_queues_t1.add(new ArrayDeque());
			}
			for(int i = 0; i<maxBack;i++){
				backqueue.add(new ArrayDeque());
			}
			for(URL url : seed_urls){
				int priority = getPriority();
				list_queues_t1.get(priority).add(url);
			}
	       // System.out.println(list_queues_t1.get(45).size());
		}
		
		public void push(URL url){
			int priority = getPriority();
			list_queues_t1.get(priority).push(url);
		}
		
		//Prendre un URL dans heap_time
		public URL pop(){
			if(heap_time.size()==0){
				router();
			}
			if(heap_time.size()==0){
				return null;
			}
			Date date = new Date();
			Long time_now = date.getTime();
			HeapNode top_node = heap_time.poll();
			int backpos = hote_deque.get(top_node.getHost());
			URL url = backqueue.get(backpos).peekFirst();
			if(url ==null){
				return null;
			}
			backqueue.get(backpos).pollFirst();
			if(backqueue.get(backpos).isEmpty()){
				router();
				hote_deque.remove(top_node.getHost());
			}
			else{
				HeapNode hp = new HeapNode();
				hp.setHost(top_node.getHost());
				hp.setTime_access(time_now+60);
				heap_time.add(hp);
				System.out.println("heap_time inserted");
			}
			return url;
		}

		//router urls de file de T1 aux file de T2
		private void router() {
			// TODO Auto-generated method stub
			int priority = maxFront;
			int limit = maxURLmove;
			int size = 0;
			while(priority>0&&limit>0){
				priority--;
				limit--;
				size = list_queues_t1.get(priority).size();
				if(size == 0) continue;
				System.out.println("crawler "+ Thread.currentThread().getId()%100+"is routing");
				while(!list_queues_t1.get(priority).isEmpty()&&size>0){
					size--;
					URL url = list_queues_t1.get(priority).peekFirst();
					if(hote_deque.containsKey(url.getHost())){
						int backpos = hote_deque.get(url.getHost());
						if(backqueue.get(backpos).size()<maxBackQueueSize){
							backqueue.get(backpos).push(url);
							list_queues_t1.get(priority).pollFirst();
						}
						
					}
					else{
						int posB = 0;
						while(posB<maxBack&&!backqueue.get(posB).isEmpty()) posB++;
						if(posB==maxBack){
							list_queues_t1.get(posB).pop();
							list_queues_t1.get(posB).push(url);
							continue;
						}
						//mettre à jour de hote_deque
						hote_deque.put(url.getHost(), posB);
						
						//mettre à jour de hote_polis
						// to do...
						
						if(backqueue.get(posB).size()<maxBackQueueSize){
							backqueue.get(posB).push(url);
						}
						else{
							return;
						}
						//mettre à jour de heap_time
						Date date = new Date();
						Long time_now = date.getTime();
						HeapNode hp = new HeapNode();
						hp.setHost(url.getHost());
						hp.setTime_access(time_now+1);
						heap_time.add(hp);
						list_queues_t1.get(priority).pollFirst();
						System.out.println("insert to heap time.........");
					}
				}
			}
			
		}
		
		public static int getPriority(){
			return (int)Math.random()*maxFront;
		}
}
