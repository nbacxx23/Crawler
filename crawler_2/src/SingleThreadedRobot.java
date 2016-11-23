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

        long start = System.currentTimeMillis();

        long end = start + seconds*1000;

        while(candidates.peek() != null){
            if(end > System.currentTimeMillis()){
                processURL(candidates.poll());
            }
            else{
                System.out.println("Time over!");
                break;
            }

        }
        System.out.println("Finish!");

    }

}