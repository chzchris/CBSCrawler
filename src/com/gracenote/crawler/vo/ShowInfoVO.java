package com.gracenote.crawler.vo;

/*
 * The ShowInfoVO is used to contain show information parsed from CBS watch page.
 * It has only two field: Show title (show_title) and URL (url)
 */
public class ShowInfoVO {

	private String show_title = null;
	
	private String url = null;

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
	
	
}
