package com.gracenote.crawler.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/*
 * JsonHelper is use to covert and manipulate JSON object
 */
public class JsonHelper {

	public static JSONObject convertStrToJsonObj(String str) throws Exception {
		
		JSONObject json = (JSONObject)new JSONParser().parse(str);
		return json;
		
	}
	
	public static JSONArray convertStrToJsonArr(String str) throws Exception {
		
		return (JSONArray) new JSONParser().parse(str);
		
	}
	
}
