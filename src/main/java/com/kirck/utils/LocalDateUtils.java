package com.kirck.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateUtils {
	private final static DateTimeFormatter YYMMDDHH = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
	
	public static String getYYMMDDHH(LocalDateTime localDateTime) {
		return YYMMDDHH.format(localDateTime);
	}
}
