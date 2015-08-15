package com.gracenote.crawler.test;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gracenote.crawler.util.CBSRegexHelper;
import com.gracenote.crawler.util.WebParseHelper;



public class Test {

	@SuppressWarnings("unused")
	public static void main(String arg[]) {
		
		try{
			
//		    String inputStr = "keyArray=[1, 2, 3]";
//		    String patternStr = "(?s)(keyArray)\\s??=\\s??\\[(.*?)\\]";
			
//		    String inputStr = "video.carousels = true";
//		    String patternStr = "(?s)(video.carousels)\\s??=\\s??(?=false)";
//			
//		    Pattern pattern = Pattern.compile(patternStr);
//		    Matcher matcher = pattern.matcher(inputStr);
//		    boolean matchFound = matcher.find();
//		    System.out.println(matchFound);
//		    while (matchFound) {
//		    	
//		    	System.out.println(matcher.start() + "-" + matcher.end());
//		    	for (int i = 0 ; i <= matcher.groupCount() ; i++) {
//		        
//		    		String groupStr = matcher.group(i);
//		    		System.out.println(i + ":" + groupStr);
//		    		
//		    	}
//		      
//		    	if (matcher.end() + 1 <= inputStr.length()) {
//		    		matchFound = matcher.find(matcher.end());
//		          
//		    	}else{
//		    		
//		            break;
//		          
//		    	}
//		      
//		    }
			
			
			String url = "http://www.cbs.com/shows/extant/video/";
			Document doc = WebParseHelper.getDocument(url);
			Elements script = doc.select("script");
			
			for (int i = 0 ; i < script.size() ; i++) {
				if (script.get(i).toString().contains("video.carousels")) {
					
					boolean excluse = CBSRegexHelper.checkCarouselsExclude(script.get(i).toString());
					String meta = CBSRegexHelper.getVideoMetaData(script.get(i).toString());
					String season = CBSRegexHelper.getVideoSeason(script.get(i).toString());
					System.out.println("excluse:"+excluse);
					System.out.println("meta:"+meta);
					System.out.println("season:"+season);
					JSONArray seasons = (JSONArray) new JSONParser().parse(season);
//					Pattern pattern = Pattern.compile(patternStr);
//					Matcher matcher = pattern.matcher(script.get(i).toString());
//					boolean matchFound = matcher.find();
//					System.out.println(matchFound);
//				    if (matchFound == true) {
//				    	System.out.println(matcher.group(2));
//				    }
					break;
					
				}
			}
			
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
		
		}
		
	}
}
	