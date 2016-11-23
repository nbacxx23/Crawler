import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xchen on 2016/11/11.
 */
public class Crawling {

    static int maxThread = 50;

    public static String seedsFile = "/Users/xchen/Documents/AIC/TC3/projet_crawler/seeds.txt";

    protected static String recordFile = "/Users/xchen/Documents/AIC/TC3/projet_crawler/records.txt";

    public static void main(String[] args) {

        Set<String> seed = new HashSet<String>();

        try {
            FileReader fr = new FileReader(new File(seedsFile));

            BufferedReader br = new BufferedReader(fr);

            String seedUrl;
            while ((seedUrl = br.readLine()) != null){
                seed.add(seedUrl);
            }
            br.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(seed.size());
        /*
        try {
            FileWriter fw = new FileWriter (new File(recordFile));

            BufferedWriter bw = new BufferedWriter (fw);

            PrintWriter record = new PrintWriter (bw);
        }catch (IOException e) {
            e.printStackTrace();
        }
        */
        GraphExtractionRobot crawl = new GraphExtractionRobot("Googlebot",10,2000000000,seed);

        for (int i = 1; i <= maxThread; i++){
            new Thread(crawl).start();
            //System.out.println("Finish the "+i+"th Thread");
        }


        //GraphExtractionRobot crawl = new GraphExtractionRobot("yushan",10,2);
        //crawl.executionLoop(seed, 10);

		/*for(int i=1;i<crawl.number.size();i++){
			System.err.printf(crawl.number.get(i),crawl.racin.get(crawl.number.get(i)));
		}*/

    }

}
