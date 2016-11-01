package Protocols;

import java.net.MalformedURLException;
import java.net.URL;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FtpProtocol http = new FtpProtocol();
		try {
			http.fetch(new URL("ftp://lihonglin.cf"));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
