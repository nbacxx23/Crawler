import java.io.*;
import java.net.ContentHandler;
import java.net.ContentHandlerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.javadoc.Doc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class Robot{
    /* Protected fields and methods */

    protected final String pages = "/Users/xchen/Documents/AIC/TC3/project_crawler/pages/";

    /*a priority queue of the URLs to download next*/
    protected Queue<String> candidates;

    /*the set of all URLs already crawled*/
    protected Set<String> done;

    protected final int initialQueueSize = 30;

    /**The first argument is the user-agent name of the robot, as used in the User-Agent HTTP header.
    The second argument is a delay, in milliseconds, between two successive queries to the same Web server.*/
    protected Robot(String ua, long delay) {
        userAgentName=ua;
        delayBetweenRequests=delay;
    }

    /*define the comparison order of the elements in the candidates priority queue*/
    protected abstract Comparator<String> comparator();

    /*called for each URL of the initial seed*/
    protected abstract void initialize(String url);

    /*called each time a URL is processed. url is the current URL, while s is the set of URLs
    of all hyperlinks contained in url.*/
    protected abstract void dealWith(String url, Set<String> s);


    /*called to start the crawl and should not return until the end of the crawl.
    The seconds parameter indicates the number of seconds to allocate to the crawl.
    After this delay has passed, the crawl should stop. The implementation of executionLoop
    in the Robot class just does some initialization,and calls the initialize method on each URL of the seed*/
    protected void executionLoop(Set<String> seed,long seconds)
    {
        candidates.clear();
        done.clear();

        for(String s:seed)
            initialize(s);
        candidates.addAll(seed);
    }

    /*called by the robot each time a URL has to be processed. It makes sure crawling ethics are respected,
      retrieves all links from this URL, and calls dealWith*/
    protected final void processURL(String url) {

        Set<String> links=retrieveLinks(url);

        synchronized(done) {
            if(!done.contains(url)) {
                dealWith(url,links);
                try {
                    String content = readHtml(url);
                    if(content!=null) {
                        writeHtml(content, url, new File(pages));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                done.add(url);
            }
        }
    }


    protected String readHtml(String url) throws IOException {

        String result = "";

        BufferedReader br;

        try

        {
            URL realUrl = new URL(url);

            URLConnection connection = realUrl.openConnection();

            connection.setReadTimeout(15*1000);

            connection.connect();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while ((line = br.readLine()) != null)
            {
                result += line + "\n";
            }
        }
        catch (IOException e) {

            e.printStackTrace();

        }

        return result;
    }


    public void writeHtml(String content,String url,File outdir) {

        if(!outdir.exists()){
            outdir.mkdir();
        }

        Document doc = Jsoup.parse(content);

        String name = doc.title();


        try{
            FileWriter fw = new FileWriter (new File(outdir.getPath()+'/'+name+".html"));
            BufferedWriter bw = new BufferedWriter (fw);
            PrintWriter html = new PrintWriter (bw);
            html.println(content);
            html.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Private fields and methods */
    private final long delayBetweenRequests;
    private Map<String, Set<String>> exclusions=new Hashtable<String, Set<String>>();;
    private Map<String, Long> lastRequest=new Hashtable<String,Long>();
    private final String userAgentName;

    private static class HTMLLinkExtractionHandler extends ContentHandler {
        @Override
        public Object getContent(URLConnection urlc) throws IOException {
            InputStreamReader in = new InputStreamReader(
                    urlc.getInputStream(),
                    "ISO-8859-1");
            StringBuffer s = new StringBuffer();
            Set<String> urls = new HashSet<String>();

            try {
                int c=in.read();

                while(c != -1) {
                    s.append((char) c);
                    c=in.read();
                }
            } finally {
                in.close();
            }

            Matcher m=Pattern.compile("<\\s*(a|base|area)\\s[^>]*href\\s*=\\s*['\"]?(.*?)[#\\s'\">]",
                    Pattern.CASE_INSENSITIVE).matcher(s);

            if(!m.find()) {
                m=Pattern.compile("<\\s*(frame)\\s[^>]*src\\s*=\\s*['\"]?(.*?)[#\\s'\">]",
                        Pattern.CASE_INSENSITIVE).matcher(s);
                if(!m.find()) {
                    m=Pattern.compile("<\\s*(meta)\\s[^>]*http-equiv\\s*=\\s*['\"]?(Refresh)['\"]?\\s+"+
                            "content\\s*=[^>]*URL\\s*=\\s*(.*?)[#\\s'\">]", Pattern.CASE_INSENSITIVE).
                            matcher(s);
                    if(!m.find()) {
                        m=Pattern.compile("(location)\\s*=\\s*['\"]?(.*?)[#\\s'\">]",
                                Pattern.CASE_INSENSITIVE).matcher(s);
                    }
                }
            }

            URL context=urlc.getURL();

            m.reset();

            while(m.find()) {
                try {
                    if (m.group(1).equals("base"))
                        context=new URL(m.group(2));
                    else {
                        URL u=new URL(context,m.group(2));
                        String proto=u.getProtocol();

                        String us=u.toString();

                        if(us.equals(urlc.getURL().toString()) || !proto.equals("http"))
                            continue;

                        if(u.getPath().isEmpty()) // Add a final / to empty paths
                            us=us+"/";

                        urls.add(us);
                    }
                } catch (MalformedURLException e) { /* Ignore this malformed URL */ }
            }

            return urls;
        }
    }

    static {
        URLConnection.setContentHandlerFactory(new ContentHandlerFactory() {
            public ContentHandler createContentHandler(String mimetype) {
                if(mimetype.equals("text/html") || mimetype.equals("application/xhtml+xml"))
                    return new HTMLLinkExtractionHandler();
                else
                    return null;
            }
        });

        System.setProperty("sun.net.client.defaultConnectTimeout","5000");
        System.setProperty("sun.net.client.defaultReadTimeout","5000");
    }

    @SuppressWarnings("unchecked")
    private final Set<String> retrieveLinks (String u) {
        URL url=null;

        try {
            url=new URL(u.toString());
        } catch(MalformedURLException e) {
            return null;
        }

        String host = url.getHost();
        int port = url.getPort();

        if (port == -1)
            port = 80;

        String fullhost = host + ":" + port;

        if(!exclusions.containsKey(fullhost)) {
            addExclusions(host,port);
        }

        Set<String> excl = exclusions.get(fullhost);

        if (excl != null)
            for(String item:excl)
                if (url.getFile().startsWith(item))
                    return null;

        if (lastRequest.containsKey(fullhost)) {
            long delay=delayBetweenRequests -
                    (System.currentTimeMillis()-lastRequest.get(fullhost));

            while(delay>0) {
                try {Thread.sleep(delay+1);} catch (InterruptedException e)
                { /* Ignore */ }

                delay=delayBetweenRequests -
                        (System.currentTimeMillis()-lastRequest.get(fullhost));
            }
        }

        lastRequest.put(fullhost,System.currentTimeMillis());

        URLConnection urlc;
        try {
            urlc = url.openConnection();
        } catch (IOException e) {
            // Connection impossible
            return null;
        }

        urlc.setRequestProperty("User-Agent", userAgentName);
        urlc.setRequestProperty("Accept", "application/xhtml+xml, text/html; q=0.9");

        try {
            return (Set<String>) urlc.getContent(new Class[] {Set.class});
        } catch (IOException e) {
            return null;
        }
    }

    private void addExclusions(String host, int port) {
        String fullhost=host+":"+port;
        HashSet<String> s=new HashSet<String>();

        try {
            URLConnection urlc = (new URL("http",host,port,"/robots.txt")).openConnection();
            urlc.setRequestProperty("User-Agent",userAgentName);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlc.getInputStream(),"ISO-8859-1"));

            String line;

            Pattern ua = Pattern.compile("^User-agent:\\s*(\\*|(?i).*"+userAgentName+".*)\\s*(#.*)?$");
            Pattern blank = Pattern.compile("^\\s*$");
            Pattern disallow = Pattern.compile("^Disallow:\\s*([^\\s#]+).*");

            while((line = in.readLine()) != null) {
                if (ua.matcher(line).matches()) {
                    while ((line = in.readLine()) !=null && !blank.matcher(line).matches()) {
                        Matcher m = disallow.matcher(line);
                        if(m.matches()) {
                            s.add(m.group(1));
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) { /* Ignore this exception */ }

        exclusions.put(fullhost,s);
    }


}