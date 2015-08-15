package com.gracenote.crawler.abstracts;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gracenote.crawler.util.FileHelper;
import com.gracenote.crawler.util.JsonHelper;
import com.gracenote.crawler.util.SyntaxChecker;
import com.gracenote.crawler.util.WebParseHelper;

/*
 * This class is the parent class of all crawler.
 * All crawlers must extend this class
 */
public abstract class Crawler {

	public abstract boolean parse();	
	
	public RawFileWriter createCSVFile(String file_name, String path) throws Exception {
		
		return FileHelper.createCSVFile(file_name, path);
		
	}
	
	public String checkCsvSyntax(String str) {
		
		return SyntaxChecker.checkCsvSyntax(str);
		
	}
	
	public String getUrlLink(Element element) throws Exception {
		
		return WebParseHelper.getUrlLink(element);
		
	}
	
	public Document getDocument(String url) throws Exception {
		
		return WebParseHelper.getDocument(url);
		
	}
	
	public JSONObject convertStrToJsonObj(String str) throws Exception {
		
		return JsonHelper.convertStrToJsonObj(str);
		
	}
	
	public JSONArray convertStrToJsonArr(String str) throws Exception {
		
		return JsonHelper.convertStrToJsonArr(str);
		
	}
	
}
