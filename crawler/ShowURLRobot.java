package fr.paristech.telecom.inf396.crawler;


import java.util.Set;

public class ShowURLRobot extends BFSRobot {

	protected ShowURLRobot(String ua, long delay, int hdepthmax) {
		super(ua, delay, hdepthmax);
		// TODO Auto-generated constructor stub
	}
	
	protected void dealWith(String url, Set<String> s){
		super.dealWith(url, s, this.hdepthmax);
		if(s!=null){
		for(String web:s){
			System.out.println(web);
		}
		}
		System.out.println("Finish!");
	}


}
