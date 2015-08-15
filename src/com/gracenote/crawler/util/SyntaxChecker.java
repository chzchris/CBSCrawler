package com.gracenote.crawler.util;

/*
 * The SyntaxChecker is to check illegal syntax in a file
 */
public class SyntaxChecker {
	
	public static String checkCsvSyntax(String str) {
		
		if (str == null) str = "";
		str = str.replace(",", "\"");
		
		return str;
		
	}
	
}
