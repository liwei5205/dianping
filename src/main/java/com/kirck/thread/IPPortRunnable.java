package com.kirck.thread;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSONObject;


public class IPPortRunnable implements Runnable{
	 CopyOnWriteArrayList<String> cowlist;

     List<String> list;

     
     public IPPortRunnable(CopyOnWriteArrayList<String> cowlist,List<String> list) {
		super();
		this.cowlist = cowlist;
		this.list = list;
	}

	@Override
	public void run() {
		synchronized (cowlist) {
			for (String iport : list) {
				String[] split = iport.split(":");
				try {
					/*
					 * Connection connect =
					 * Jsoup.connect("http://ip.taobao.com/service/getIpInfo2.php");
					 * connect.data("ip", "myip"); String text = connect.proxy(ip,
					 * Integer.parseInt(port)).timeout(5000).ignoreHttpErrors(true)
					 * .ignoreContentType(true).get().text(); if (text.contains("data")) {
					 * JSONObject parseObject = JSONObject.parseObject(text); JSONObject jsonObject
					 * = parseObject.getJSONObject("data"); if
					 * (!jsonObject.getString("ip").equals("180.154.132.13")) { cowlist.add(iport);
					 * } }
					 */
					String text = Jsoup.connect("http://api.ipify.org/").proxy(split[0], Integer.parseInt(split[1]))
							.timeout(5000).ignoreHttpErrors(true).ignoreContentType(true).get().text();
					if (!"180.154.132.13".equals(text)) {
						cowlist.add(iport);
					}
				} catch (NumberFormatException | IOException e) {
					continue;
				}
			}
		}
	}
	
}
