package fr.paristech.telecom.inf396.crawler;


import java.util.Set;
import java.util.PriorityQueue;
import java.util.HashSet;

public abstract class SingleThreadedRobot extends Robot {

	protected SingleThreadedRobot(String ua, long delay) {
		super(ua, delay);
		candidates = new PriorityQueue<String>(initialQueueSize,comparator());
		done = new HashSet<String>();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void executionLoop(Set<String> seed,long seconds)
	{	
		for(String s:seed){
			initialize(s);	
		}
		candidates.addAll(seed);
		
		long start = System.currentTimeMillis()/1000;
		
		long end = start + seconds;
				
		while(candidates.peek() != null){
			if(end > System.currentTimeMillis()/1000){
				processURL(candidates.poll());
			}
			else{
				System.out.println("Time over!");
				break;
			}
			
		}
		System.out.println("Finish!");
		/*for(String c:candidates)
		{	int i = 0;
			for(String d:done){
				if (d==c){
					i = i+1;
				}				
			}
			if(i==0)processURL(c);	
			
		}*/
	}
	
}
