import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public abstract class BFSRobot extends MultiThreadedRobot {

    int hdepthmax;

    int ndepth;

    HashMap<String, Integer> hdepth = new HashMap<String, Integer>();

    protected BFSRobot(String ua, long delay, int hdepthmax) {
        super(ua, delay);
        this.hdepthmax = hdepthmax;
        // TODO Auto-generated constructor stub
    }

    protected Comparator<String> comparator(){
        return new Comparator<String>(){
            @Override
            public int compare(String arg0, String arg1) {
                // TODO Auto-generated method stub
                if(hdepth.get(arg0)>hdepth.get(arg1)){
                    return 1;
                }
                else if(hdepth.get(arg0)<hdepth.get(arg1)){
                    return -1;
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

        ndepth = hdepth.get(url);

        if(s!=null){
            for(String urlFetch:s){
                if(urlFetch!=url){
                    if( (ndepth < hdepthmax) &&(!done.contains(urlFetch))){
                        hdepth.put(urlFetch,ndepth+1);
                        candidates.add(urlFetch);
                    }
                    else{
                        System.out.println("Depth over!");
                    }
                }
            }

        }
    }

}