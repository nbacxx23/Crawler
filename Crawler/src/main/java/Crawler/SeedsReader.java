package Crawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Processing.Extractor;
import Processing.PageReader;

public class SeedsReader {
	public static Set<URL> readSeedsFile(String path) throws IOException{
		Set<URL> result = new HashSet();
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line;
		while((line=in.readLine())!=null){
			result.add(new URL(line));
			result.addAll(Extractor.extractSeeds(PageReader.read(line), line));
		}
		return result;
	}
}
