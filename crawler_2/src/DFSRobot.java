import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public abstract class DFSRobot extends SingleThreadedRobot {

    protected int hdepthmax = 0;

    int n;

    HashMap<String, Integer> hdepth = new HashMap<String, Integer>();

    protected DFSRobot(String ua, long delay, int hdepthmax) {
        super(ua, delay);
        this.hdepthmax = hdepthmax;
        //this.hdepth = hdepth;
        // TODO Auto-generated constructor stub
    }

    protected Comparator<String> comparator(){
        return new Comparator<String>(){

            @Override
            public int compare(String arg0, String arg1) {
                // TODO Auto-generated method stub
                if(hdepth.get(arg0)>hdepth.get(arg1)){
                    return -1;
                }
                else if(hdepth.get(arg0)<hdepth.get(arg1)){
                    return 1;
                }
                else{
                    return 0;
                    //return arg0.compareTo(arg1);
                }
            }

        };

    }

    protected void initialize(String url){
        hdepth.put(url, 0);
    }

    protected void dealWith(String url, Set<String> s, int hdepthmax){
        n = hdepth.get(url);
        if(s!=null){
            for(String c:s){
                if( n<hdepthmax && !done.contains(c) && !candidates.contains(c)){
                    hdepth.put(c,n+1);
                    candidates.add(c);
                }

            }
        }
    }



}