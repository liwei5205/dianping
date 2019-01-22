package com.kirck.thread;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSONObject;


public class IPPortRunnable implements Runnable{
	 CopyOnWriteArrayList<Map<String,String>> cowlist;

     List<Map<String,String>> list;

     
     public IPPortRunnable(CopyOnWriteArrayList<Map<String, String>> cowlist, List<Map<String, String>> list) {
		super();
		this.cowlist = cowlist;
		this.list = list;
	}

	@Override
	public void run() {
		for (Map<String, String> iport : list) {
			synchronized (cowlist) {
				System.getProperties().setProperty("http.proxyHost", iport.get("ip"));
				System.getProperties().setProperty("http.proxyPort", iport.get("port"));
				try {
					String text = Jsoup.connect("http://2019.ip138.com/ic.asp")
							.userAgent("Mozilla")
							.timeout(4000).get().text();
						if (text.contains("您的IP是") && !text.contains("180.154.132.13")) {
							cowlist.add(iport);
						}
				
				} catch (IOException e) {
					System.out.println(iport.get("ip") + "连接异常");
				}
			}
		}
	}
	
}
