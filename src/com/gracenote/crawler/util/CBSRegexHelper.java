package com.gracenote.crawler.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * The CBSParseHelper is to standardize the pattern matching principle 
 * of Javascript element on CBS web site to get matching result
 */
public class CBSRegexHelper {

	private static String exclude_patternStr = "(?s)(video.carousels_exclude_show)\\s??=\\s??(?=true)";
	
	private static String meta_patternStr = "(?s)(video.section_metadata)\\s??=\\s??\\{(.*?)\\}";
	
	private static String season_patternStr = "(?s)(video.seasons)\\s??=\\s??\\{(.*?)\\]";
	
	
	public static boolean checkCarouselsExclude(String input) throws Exception {
		
		Pattern pattern = Pattern.compile(exclude_patternStr);
		Matcher matcher = pattern.matcher(input);
		
		return matcher.find();
		
	}
	
	@SuppressWarnings("unchecked")
	public static String getVideoMetaData(String input) throws Exception {
		
		Pattern pattern = Pattern.compile(meta_patternStr);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			
			return "{" + matcher.group(2) + "}}";
			
		}else{
			
			JSONObject jsonobj  = new JSONObject();
			jsonobj.put("no_match", "no_match");
			return jsonobj.toJSONString();
			
		}

	}
	
	@SuppressWarnings("unchecked")
	public static String getVideoSeason(String input) throws Exception {
		
		Pattern pattern = Pattern.compile(season_patternStr);
		Matcher matcher = pattern.matcher(input);
		
		if (matcher.find()) {
			
			if (matcher.group(2).split("\\[").length<2) {

				JSONArray array  = new JSONArray();
				HashMap<String,String> temp = new HashMap<String, String>();
				temp.put("season", "-1");
				array.add(temp);
				return array.toJSONString();
				
			}else{
				
				return "["+matcher.group(2).split("\\[")[1].trim()+"]";
				
			}

		}else{
			
			throw new Exception("No video metadata found");
			
		}
		
	} 
	
}
