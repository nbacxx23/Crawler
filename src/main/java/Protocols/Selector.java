package Protocols;

import java.net.URL;

public class Selector {
	public static Protocol selectProtocol(URL url){
		if(url.getProtocol().equals("http")||url.getProtocol().equals("https")){
			return new HttpProtocol();
		}
		else if(url.getProtocol().equals("ftp")){
			return new FtpProtocol();
		}
		else 
			return null;
	}
}
