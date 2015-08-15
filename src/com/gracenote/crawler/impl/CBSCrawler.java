package com.gracenote.crawler.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gracenote.crawler.abstracts.Crawler;
import com.gracenote.crawler.abstracts.RawFileWriter;
import com.gracenote.crawler.util.CBSMetaInfoHelper;
import com.gracenote.crawler.util.CBSRegexHelper;
import com.gracenote.crawler.vo.EpisodeInfoVO;
import com.gracenote.crawler.vo.ShowInfoVO;


/*
 * Huang-chih Chen, 12/19/2014
 * 
 * This is the main class to parse all shows meta-data from CBS web site (http://www.cbs.com/watch).
 * The program will crawl the show list from the watch page and parse the video page linkage for each show, 
 * then go down to the linkage of each show and parse the meta-info of shows (total season, exclusion...etc). 
 * And the program will start sending HTTP request along with necessary parameters and get a JSON result for 
 * all the episodes of each show. The program will convert the JSON into value object and write the result 
 * into a CSV file. 
 * */
public class CBSCrawler extends Crawler {
	
	private String root_url = null;
	
	private String file_name = null;
	
	private String path = null;
	
	private long sleep_time = 0;
	
	
	public CBSCrawler() {
		
		this.root_url = "http://www.cbs.com/watch";
		this.path = "/Users/chris/Desktop/";
		this.file_name = "videos";
		
	}

	public CBSCrawler(String file_name, String path) {
		
		this.root_url = "http://www.cbs.com/watch";
		this.path = path;
		this.file_name = file_name;
		
	}

	public CBSCrawler(String file_name, String path, long sleep_time) {
		
		this.root_url = "http://www.cbs.com/watch";
		this.path = path;
		this.file_name = file_name;
		this.sleep_time = sleep_time;
		
	}
	
	@SuppressWarnings("rawtypes")
	public boolean parse() {
		
		try{

			long start_time = System.currentTimeMillis();
			
			//create csv file
			RawFileWriter csv = this.createCSVFile(file_name, path);
			ArrayList<EpisodeInfoVO> resultvo_array = new ArrayList<EpisodeInfoVO>();
			
			System.out.println("----------- Start CBS parsing! -----------");
			System.out.println();
			
			//get all shows list (show title, video page linkage) from http://www.cbs.com/watch
			Document doc = this.getDocument(root_url);
			Elements elements = doc.select(".show-nav-link");

			List<ShowInfoVO> show_list = new ArrayList<ShowInfoVO>();
			ShowInfoVO showvo = null;
			
			if (elements!=null && elements.size()>0) {
				
				for (int i=0;i<elements.size();i++) {
					
					showvo = new ShowInfoVO();
					showvo.setShow_title(elements.get(i).text());
					showvo.setUrl(elements.get(i).select("a").first().attr("abs:href") + "video/");
					
					if (showvo.getUrl().contains("shows")) show_list.add(showvo);
					
				}
				
			}

//			int show_count  = 1;
//			for (ShowInfoVO show : show_list) {
//				System.out.println(show_count + " show title:" + show.getShow_title() + ", show url:" + show.getUrl());
//				show_count++;
//			}

			
			int count = 0;
			JSONObject metadata = null;
			JSONArray seasons = null;
			boolean exclude = false;
			EpisodeInfoVO episode = null;
			int episode_offset = 0;
			int episode_count = 100;
			String request_url = null;
			JSONArray result = null;
			
			System.out.println("----------- Start parsing all shows! -----------");
			
			/*
			 * For each show, go to the video page and parse the meta-info to get necessary parameter,
			 * then send HTTP request and get JSON result. Wrap the JSON result in value object and 
			 * write it into a CSV file.
			 */
			for (ShowInfoVO show : show_list) {
				
				count++;
//				if (count <= 0) continue;
				
				System.out.println();
				System.out.println("----------- Show " + count + ", " + show.getShow_title() + " start parsing!" + " -----------");
				
				Document show_doc = null;
				try{

					show_doc = this.getDocument(show.getUrl());
				
				}catch (Exception ex) {
					//skip the show if the video page doesn't exist
					if (ex.getMessage().equals("404")) {
					
						continue;
					
					}else{
						
						throw ex;
					
					}
				}
				
				//Get the show's meta-info from Javascript
				Elements scripts = show_doc.select("script");
				for (int i = 0 ; i < scripts.size() ; i++) {
					
					if (scripts.get(i).toString().contains("video.carousels")) {
						
						exclude = CBSRegexHelper.checkCarouselsExclude(scripts.get(i).toString());
						String meta = CBSRegexHelper.getVideoMetaData(scripts.get(i).toString());
						String season = CBSRegexHelper.getVideoSeason(scripts.get(i).toString());
						metadata = this.convertStrToJsonObj(meta);
						seasons = this.convertStrToJsonArr(season);
						System.out.println("exclude:" + exclude);
						System.out.println("meta:" + meta);
						System.out.println("season:" + season);
						break;
						
					}
				}
				
				//Get the show section parameter
				Iterator iter = metadata.keySet().iterator();
				String section = null;
				while (iter.hasNext()) {
					section = iter.next().toString();
				}
				
				//If no show section found, then skip parsing the show
				if (section.equals("no_match")) continue;
				
				//If show is not full episodes, then skip parsing the show
				if (!((JSONObject)metadata.get(section)).get("title").toString().equals("Full Episodes")) continue;
				
				//Send HTTP request and wrap JSON result in Episode value object
				for (int i = 0 ; i < seasons.size() ; i++) {
					
					episode_offset = 0;
					episode_count = 100;
					
					while (episode_count == 100) {
						
						request_url = CBSMetaInfoHelper.getEpisodeReqURL(section, exclude, ((JSONObject)seasons.get(i)).get("season").toString(), episode_offset);
						result = CBSMetaInfoHelper.getEpisodesJson(request_url);
						
						episode_count = 0;
						
						for (int a = 0 ; a < result.size() ; a++) {
							
							episode = new EpisodeInfoVO();
							episode.setShow_title(this.checkCsvSyntax(((JSONObject)result.get(a)).get("series_title").toString()));
							episode.setEpisode_title(this.checkCsvSyntax(((JSONObject)result.get(a)).get("title").toString()));
							episode.setUrl("http://www.cbs.com"+((JSONObject)result.get(a)).get("url").toString());
							episode.setSeason_num(((JSONObject)result.get(a)).get("season_number").toString());
							episode.setEpisode_num(this.checkCsvSyntax(((JSONObject)result.get(a)).get("episode_number").toString()));
							episode.setStatus(this.checkCsvSyntax(((JSONObject)result.get(a)).get("status").toString()));
							resultvo_array.add(episode);
							episode_count++;
							
						}
						
						/*
						 * If has 100 episodes, then offset 100 to fetch other episodes because the 
						 * HTTP request can only return 100 results each request
						 */
						if(episode_count == 100) episode_offset += 100;
						
					}
					
					System.out.println("season:" + ((JSONObject)seasons.get(i)).get("season").toString() + " parsing finisned, total size:" + resultvo_array.size());
					
					Thread.sleep(sleep_time);
					
				}
				
				System.out.println("----------- " + show.getShow_title() + " parsing finished! -----------");
				
//				if(count == 1) break;
				
			}
			
			System.out.println();
			System.out.println("----------- All shows parsing finished! -----------");
			
			System.out.println();
			System.out.println("----------- File reocrding! -----------");
			
			//Write result into CSV file
			csv.write("show_title,episode_title,url,season_number,episode_number,status\n");
			for (EpisodeInfoVO item:resultvo_array) {
				
				csv.write(item.getShow_title() + "," + item.getEpisode_title() + "," + item.getUrl() + "," + item.getSeason_num() + "," 
						+ item.getEpisode_num() + "," + item.getStatus() + "\n");
			
			}
			csv.close();
			
			System.out.println();
			System.out.println("----------- File reocrding finished! -----------");
			
			System.out.println();
			System.out.println("----------- CBS parsing finished! -----------");
			
			long end_time = System.currentTimeMillis();
			
			System.out.println();
			System.out.println("----------- Total data row: " + resultvo_array.size() + " row(s), Total execution time: " + ((end_time-start_time)/1000) + " sec(s) -----------");
			
		}catch (Exception ex) {
			
			ex.printStackTrace();
			return false;
		
		}

		return true;
		
	}
	
}
