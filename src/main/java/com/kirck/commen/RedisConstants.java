package com.kirck.commen;

public class RedisConstants {
	/*
	 * 缓存时间一个小时
	 */
	public static int EXPIRE3600 = 3600;
	/**
	 * 缓存时间一分钟
	 */
	public static int EXPIRE60 = 60;

	/**
	 * 分隔符
	 */
	public static String SPLITTER = ":";

	public interface KEYPRE {
		String KIRCK = "kirck" + SPLITTER;
		String KIRCK007 = "KIRCK007" + SPLITTER;
		String DIANPING = "dianping" + SPLITTER;
		String LIST = "list" + SPLITTER;
		String CITY = "city" + SPLITTER;

	}

	public interface OBJTYPE {
		String USER = "user" + SPLITTER;
		String ACCOUNT = "account" + SPLITTER;
		String COOKIES = "cookies" + SPLITTER;
		String CATEGORY = "category" + SPLITTER;
		String HTML = "html" + SPLITTER;
	}
}
