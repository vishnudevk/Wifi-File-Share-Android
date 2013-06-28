package com.wifi.logic.utils;

import java.security.acl.LastOwnerException;

public class StringUtil {

	/**
	 * Get filename from full derectory address of file passed
	 * @param dir
	 * @return
	 */
	public static String getFileName(String dir) {
		int s=0;
		s = dir.lastIndexOf("/");
		if(s==0){
			s=dir.lastIndexOf("\\");
		}
		return dir.substring(s+1, dir.length());
		
	}
}
