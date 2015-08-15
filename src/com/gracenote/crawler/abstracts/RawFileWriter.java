package com.gracenote.crawler.abstracts;

/*
 * Parent class of all raw data file writer
 */
public abstract class RawFileWriter {

	public abstract boolean write(String input) throws Exception;

	public abstract boolean close() throws Exception;
	
	public abstract boolean flush() throws Exception;
	
}
