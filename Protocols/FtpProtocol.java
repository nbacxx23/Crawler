package Protocols;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;

import org.apache.commons.net.ftp.FTPClient;

public class FtpProtocol extends Protocol {

	@Override
	public void fetch(URL url) {
		// TODO Auto-generated method stub
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(url.getHost(),21);
			String[] replies = ftp.getReplyStrings();
	        if (replies != null && replies.length > 0) {
	            for (String aReply : replies) {
	                System.out.println("SERVER: " + aReply);
	            }
	        }
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public URL newUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

}
