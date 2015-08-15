package com.gracenote.crawler.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * The CBSMetaInfoHelper is used to get meta-info of episode on CBS web site
 */
public class CBSMetaInfoHelper {

	public static String getEpisodeReqURL(String section, boolean exclude, String season_number, int episode_offset) {
		
		return "http://www.cbs.com/carousels/videosBySection/" + section + 
				"/offset/"+ String.valueOf(episode_offset) +"/limit/100/xs/" + (exclude == false ? "0" : "1") + 
				"/" + (season_number.equals("-1") ? "" :  season_number + "/");
	
	}
	
	public static JSONArray getEpisodesJson(String url) throws Exception {
		
		JSONObject temp = JsonHelper.convertStrToJsonObj(HttpRequestHelper.sendGet(url));
		
		temp = (JSONObject)temp.get("result");
		JSONArray result = (JSONArray)temp.get("data");
		
		return result;
		
	}
	
}
