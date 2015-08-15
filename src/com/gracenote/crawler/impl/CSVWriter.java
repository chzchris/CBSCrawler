package com.gracenote.crawler.impl;

import java.io.FileWriter;

import com.gracenote.crawler.abstracts.RawFileWriter;

/*
 * The CSVWriter is used to help manipulate CSV file 
 */
public class CSVWriter extends RawFileWriter {

	private FileWriter csv = null;
	
	public CSVWriter(String file_name, String path) throws Exception {
		this.csv = new FileWriter(path + file_name + ".csv");
	}
	
	public boolean write(String str) throws Exception {
		
		this.csv.write(str);
		return true;
		
	}
	
	public boolean close() throws Exception {
		
		this.csv.flush();
		this.csv.close();
		return true;
	
	}
	
	public boolean flush() throws Exception {
		
		this.csv.flush();
		return true;
	
	}
	
}
