package com.gracenote.crawler.util;

import com.gracenote.crawler.impl.CSVWriter;

/*
 * The FileHelper is to used to get CSV Writer or other different storages
 * for the future.
 */
public class FileHelper {

	public static CSVWriter createCSVFile(String file_name, String path) throws Exception {
		
		return new CSVWriter(file_name, path);
		
	}
	
}
