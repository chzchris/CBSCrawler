package com.gracenote.crawler.main;

import com.gracenote.crawler.abstracts.Crawler;
import com.gracenote.crawler.impl.CBSCrawler;

/*
 * The CBSCrawlerRunner is the launcher of CBS crawler
 */
public class CBSCrawlerRunner {

	public static void main(String arg[]) {
		
		try{
		
			Crawler cbscrawler = new CBSCrawler("videos", "/Users/chris/Desktop/", 0);
			cbscrawler.parse();
			
		}catch(Exception ex){
			
			ex.printStackTrace();
		
		}
		
	}
	
}
