import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class MultiThreadedRobot extends Robot {

    protected MultiThreadedRobot(String ua, long delay) {
        super(ua, delay);
        candidates = new PriorityBlockingQueue<String>(initialQueueSize, comparator());
        done = new HashSet<String>();

    }
    // TODO Auto-generated constructor stub


    @Override
    protected void executionLoop(Set<String> seed, long seconds) {
        for (String s : seed) {
            initialize(s);
        }

        candidates.addAll(seed);


        while (candidates.peek() != null) {


            processURL(candidates.poll());

        }
    }

}