package com.kirck.commen;

public class SysConstants {

	// 特殊符号
	public interface Symbol {
		/* 垂线 */
		public static final String VERTICAL = "|";
		/* 反斜线 */
		public static final String SLASH_OPPOSITE = "\\";
		/* 双反斜线 */
		public static final String SLASH_OPPOSITE_DOUBLE = "\\\\";
		/* 双斜线 */
		public static final String SLASH = "/";
		/* 中杠 */
		public static final String DASH = "-";
		/* 中杠 */
		public static final String UNDERLINE = "_";
		/* 逗号 */
		public static final String COMMA = ",";
		/* and 符 */
		public static final String STR_AND = "&";
		/* 双引号 */
		public static final String DOUBLE_QUOTATION = "\"";
		/* 句号 */
		public static final String POINT = ".";
		/* 分号 */
		public static final String SEMI = ";";
		/* 分号 */
		public static final String PLUS = "+";

		public static final char CHAR_WHITE_SPACE = ' ';

		public static final String STRING_WHITE_SPACE = " ";

		public static final String STRING_QUESTION = "?";

		public static final String STRING_DENG = "=";

		public static final String COLON = ":";

	}

	//sysCofig
	public interface SysConfig {
		String FIREFOXDRIVER = "webdriver.gecko.driver";
		String CHROMEDRIVER = "webdriver.chrome.driver";
		String FIREFOXPATH = "/opt/java/geckodriver";
		//String CHROMEDRIVERPATH = "D:/project/chromedriver.exe";
		String CHROMEDRIVERPATH = "/opt/java/chromedriver";
		String DIANPINGLOGINURL = "https://account.dianping.com/login";
		String DIANPINGHOMEURL = "http://www.dianping.com";
		//"http://t.dianping.com/list/shanghai-category_1?desc=1&sort=new&pageIndex=0"
		String DIANPINGLIST = "http://t.dianping.com/list";
		String DIANPINGDEAl = "http://t.dianping.com/deal";
		String CATEGORY = "category";
		String SHOP = "shop";
		String DIANPINGSHOP = DIANPINGHOMEURL+Symbol.SLASH+SHOP;
		String NEWSORT = "desc=1&sort=new&pageIndex=";
		String USERNAME = "18571844624";
		String PASSWORD = "Qq276532727";
		String DEAL = "deal";
	}

}
