package com.gracenote.crawler.util;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/*
 *The WebParseHelper is used to standardize the web parsing method 
 */
public class WebParseHelper {

	public static String getUrlLink(Element element) throws Exception {
		
		String link = element.select("a").first().attr("href");
		return link;
		
	}
	
	public static Document getDocument(String url) throws Exception {
		
		try{
			
			Document doc = Jsoup.connect(url).get();
			return doc;
			
		}catch(HttpStatusException httpex){
			
			throw new Exception(String.valueOf(httpex.getStatusCode()));
		
		}catch(Exception ex){
			
			throw ex;
		}
		
	}
	
}
