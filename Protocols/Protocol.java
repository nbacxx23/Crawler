package Protocols;

import java.net.URL;

public abstract class Protocol {
	public abstract void fetch(URL url);
	public  abstract URL newUrl(String url);
}
