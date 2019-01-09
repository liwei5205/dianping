package com.kirck.utils;

public class TitleUtils {
	public static String getTitle(String title) {
		if(title.contains("元的")) {
			title = title.substring(title.indexOf("元的")+2);
		}
		if(title.contains("1张")) {
			title = title.substring(0, title.indexOf("1张"));
			return title;
		}
		if(title.contains("，")) {
			title = title.substring(0, title.indexOf("，"));
			return title;
		}
		if(title.contains("。")) {
			title = title.substring(0, title.indexOf("。"));
			return title;
		}
		return title;
	}
}
