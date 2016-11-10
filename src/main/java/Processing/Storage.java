package Processing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Storage extends Thread {
	public void write_html(String k, String result)
	{
		try{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
			int num_thread = Math.toIntExact(Thread.currentThread().getId())%100; 
			FileWriter fw = new FileWriter (new File("B:/LaVieEnFrance/Master/M2-1/REI/project/Crawler/output/page/"+sdf.format(date) +"_"+num_thread+ ".html"));
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter html = new PrintWriter (bw);	
			html.println(result);
			html.close();
			bw.close();
			fw.close();
		}	
		catch (IOException e) {
			e.printStackTrace();
			}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
	
}
