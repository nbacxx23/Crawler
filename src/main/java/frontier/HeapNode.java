package frontier;

public class HeapNode implements Comparable {
	private String host;
	private double time_access;
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		HeapNode node = (HeapNode)o;
		if(this.time_access == node.getTime_access()){
			return 0;
		}else if(this.time_access>node.getTime_access()){
			return 1;
		}else
			return -1;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public double getTime_access() {
		return time_access;
	}
	public void setTime_access(double time_access) {
		this.time_access = time_access;
	}
	
}
