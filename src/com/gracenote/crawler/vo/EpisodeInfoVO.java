package com.gracenote.crawler.vo;

/*
 * The EpisodeInfoVO is used to contain meta-data of each episode.
 * There is 5 column in this VO.
 * 	1. Show title - show_title
 * 	2. Episode title - episode_title
 *  3. URL - url
 *  4. Season number - season_num
 *  5. Episode number - episode_num 
 *  6. Status - status
 */
public class EpisodeInfoVO {

	private String show_title = null;
	
	private String episode_title = null;
	
	private String url = null;
	
	private String season_num = null;
	
	private String episode_num = null;
	
	private String status = null;

	/**
	 * @return the show_title
	 */
	public String getShow_title() {
		return show_title;
	}

	/**
	 * @param show_title the show_title to set
	 */
	public void setShow_title(String show_title) {
		this.show_title = show_title;
	}

	/**
	 * @return the episode_title
	 */
	public String getEpisode_title() {
		return episode_title;
	}

	/**
	 * @param episode_title the episode_title to set
	 */
	public void setEpisode_title(String episode_title) {
		this.episode_title = episode_title;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the season_num
	 */
	public String getSeason_num() {
		return season_num;
	}

	/**
	 * @param season_num the season_num to set
	 */
	public void setSeason_num(String season_num) {
		this.season_num = season_num;
	}

	/**
	 * @return the episode_num
	 */
	public String getEpisode_num() {
		return episode_num;
	}

	/**
	 * @param episode_num the episode_num to set
	 */
	public void setEpisode_num(String episode_num) {
		this.episode_num = episode_num;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
