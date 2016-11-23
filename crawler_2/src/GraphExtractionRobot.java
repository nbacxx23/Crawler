import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class GraphExtractionRobot extends BFSRobot implements Runnable{

    protected int index = 0;

    protected Set<String> seed;

    long seconds;

    public HashMap<String,Integer> number = new HashMap<String,Integer>();

    protected GraphExtractionRobot(String ua, long delay, int hdepthmax, Set<String> seed) {
        super(ua, delay, hdepthmax);
        //this.seconds = seconds;
        this.seed = seed;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        this.executionLoop(seed, seconds);
    }

    protected void initialize(String url){
        hdepth.put(url, 0);
        number.put(url, 0);

    }
    protected void dealWith(String url, Set<String> s ){

        super.dealWith(url, s, hdepthmax);

        if(s!=null){

            for(String web:s){

                if ((!done.contains(web)) && (web!=url)){
                    index = index+1;
                    System.out.println(index+"\t"+web);
                    number.put(web,index);
                    //System.err.println(number.get(url)+" "+number.get(web));
                }

            }
        }
        //System.err.println();
    }




}