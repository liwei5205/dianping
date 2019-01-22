package dianping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class TestDemo {
	
	
	public static void main(String[] args) {
		
		CopyOnWriteArrayList<Map<String, String>> cow = new CopyOnWriteArrayList<Map<String,String>>();
		List<String> ips = new ArrayList<String>();
		ips.add("42.200.119.154");
		ips.add("42.200.119.152");
		ips.add("42.200.119.153");
		List<String> ips2 = new ArrayList<String>();
		ips.add("42.200.118.154");
		ips.add("42.200.118.152");
		ips.add("42.200.118.153");
		Runnable task1 = new Runnable() {

			@Override
			public void run() {
				try {
					wait(1000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (String ip : ips) {
					Map<String,String> map = new HashMap<String,String>();
					map.put("ip", ip);
					cow.add(map);
				}
			} 
			 
		 };
		 
		 Runnable task2 = new Runnable() {

				@Override
				public void run() {
					try {
						wait(1000L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (String ip : ips2) {
						Map<String,String> map = new HashMap<String,String>();
						map.put("ip", ip);
						cow.add(map);
					}
				} 
				 
			 };
			  new Thread(task1).start();  
			  new Thread(task2).start();
			 System.out.println(JSONObject.toJSONString(cow)); 
	}
	
	
}
