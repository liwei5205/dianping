package com.kirck.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kirck.commen.RedisConstants;
import com.kirck.commen.SysConstants;
import com.kirck.entity.MerchantBranchEntity;
import com.kirck.entity.MerchantDealEntity;
import com.kirck.service.IDianPingService;
import com.kirck.thread.IPPortRunnable;
import com.kirck.utils.BrowserUtils;
import com.kirck.utils.UUIDUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "浏览器相关controller", tags = { "登录操作接口" })
@RestController
@RequestMapping("driver")
public class DriverController extends BaseController{
	
	@Autowired
	private IDianPingService dianPingService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    
    @GetMapping(value = "/hello")
	@ResponseBody
	@ApiOperation(value = "欢迎", httpMethod = "GET")
	public  String sayHello(String iport) {
    	return "hello";
    }
    
    @GetMapping(value = "/login")
	@ResponseBody
	@ApiOperation(value = "测试登录", httpMethod = "GET")
	public  String login(String userName,String password) {
		ChromeDriver browser = (ChromeDriver) BrowserUtils.openBrowser(SysConstants.SysConfig.CHROMEDRIVER,
				SysConstants.SysConfig.CHROMEDRIVERPATH);
		List<Map<String,Object>> list = BrowserUtils.loginDianPingWUP(browser, userName,password);
		String cookiesPath = RedisConstants.KEYPRE.DIANPING + RedisConstants.OBJTYPE.COOKIES
		+ userName;
		redisTemplate.opsForValue().set(cookiesPath, list);
		return "hello";
    }
    
	@GetMapping(value = "/getBranch")
	@ResponseBody
	@ApiOperation(value = "欢迎", httpMethod = "GET")
	public String getBranch(String dealId) {
		MerchantDealEntity merchantDeal = dianPingService.findDealInfo(dealId);
		
		String html = (String) redisTemplate.opsForValue().get(RedisConstants.KEYPRE.DIANPING
				+ RedisConstants.OBJTYPE.HTML + SysConstants.SysConfig.DEAL + SysConstants.Symbol.COLON + dealId);

		if (StringUtils.isBlank(html)) {
			html = getDealHtml(dealId);
		}

		Document document = Jsoup.parse(html);
		// 获取分店id
		Elements elements = document.getElementsByAttribute("data-shop-id");
		List<MerchantBranchEntity> mbs = new ArrayList<MerchantBranchEntity>();
		List<String> mbIds = new ArrayList<String>();
		T: for (Element element : elements) {
			// 排除地图信息
			if (!element.attr("class").startsWith("J_content_list")) {
				continue;
			}
			Elements elementChildrens = element.children();
			MerchantBranchEntity mb = null;
			for (Element children : elementChildrens) {
				if ("shoptitle".equals(children.attr("class"))) {
					Elements children2 = children.children();
					for (Element element2 : children2) {
						if (element2.hasAttr("href")) {
							String shopId = element2.attr("href")
									.replaceFirst(SysConstants.SysConfig.DIANPINGSHOP + SysConstants.Symbol.SLASH, "");
							mb = dianPingService.findMerchantBranch(shopId);
							if (mb != null) {
								mbIds.add(mb.getId());
								continue T;
							}
							mb = new MerchantBranchEntity();
							mb.setId(UUIDUtils.getNewId());
							mb.setShopId(shopId);
							mb.setBranchName(element2.attr("title"));
						}
					}
				}
				if ("shopdetail".equals(children.attr("class"))) {
					Elements children2 = children.children();
					for (Element element2 : children2) {
						String text = element2.text();
						if (text.contains("地址")) {
							mb.setAddress(text.substring(text.indexOf("：") + 1));
						}
						if (text.contains("电话")) {
							mb.setTelephone(text.substring(text.indexOf("：") + 1));
						}
						if (text.contains("营业时间")) {
							mb.setBusinessHours(text.substring(text.indexOf("：") + 1));
						}
					}
				}
			}
			mbs.add(mb);
			mbIds.add(mb.getId());
		}
		dianPingService.saveMerchantBranch(mbs);
		dianPingService.saveBranchDeal(merchantDeal.getId(), mbIds);
		redisTemplate.delete(RedisConstants.KEYPRE.DIANPING + RedisConstants.OBJTYPE.HTML
				+ SysConstants.SysConfig.DEAL + SysConstants.Symbol.COLON + dealId);
		return "success";
	}

	private String getDealHtml(String dealId) {
		// 打开浏览器
		ChromeDriver browser = (ChromeDriver) BrowserUtils.openBrowser(SysConstants.SysConfig.CHROMEDRIVER,
				SysConstants.SysConfig.CHROMEDRIVERPATH);
		// 添加大众点评cookies
		setCookie(browser);
		browser.get(SysConstants.SysConfig.DIANPINGDEAl + SysConstants.Symbol.SLASH + dealId);
		// 等待跳转
		try {
			while (true) {
				Thread.sleep(2000L);
				if (!browser.getCurrentUrl().startsWith(SysConstants.SysConfig.DIANPINGLOGINURL)) {
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Actions action = new Actions(browser);
		WebDriverWait wait = new WebDriverWait(browser, 5);
		action.moveToElement(wait
				.until(ExpectedConditions.elementToBeClickable(browser.findElement(By.cssSelector("div.list-holder")))))
				.perform();
		wait.until(ExpectedConditions.elementToBeClickable(browser.findElement(By.cssSelector("ul.shoplist"))));
		String html = Jsoup.parse(browser.getPageSource()).html();
		redisTemplate.opsForValue().set(RedisConstants.KEYPRE.DIANPING + RedisConstants.OBJTYPE.HTML
				+ SysConstants.SysConfig.DEAL + SysConstants.Symbol.COLON + dealId, html);
		BrowserUtils.closeBrowser(browser);
		return html;
	}


	@SuppressWarnings("unchecked")
	private void setCookie(WebDriver browser) {
		String cookiesPath = RedisConstants.KEYPRE.DIANPING + RedisConstants.OBJTYPE.COOKIES
				+ SysConstants.SysConfig.USERNAME;
		List<Map<String, Object>> cookies = (List<Map<String, Object>>) redisTemplate.opsForValue().get(cookiesPath);
		if (cookies == null) {
			cookies = BrowserUtils.loginDianPing(browser, SysConstants.SysConfig.USERNAME,
					SysConstants.SysConfig.PASSWORD);
			redisTemplate.opsForValue().set(cookiesPath, cookies);
		} else {
			Options manage = browser.manage();
			manage.deleteAllCookies();
			browser.get(SysConstants.SysConfig.DIANPINGHOMEURL);
			for (Map<String, Object> map : cookies) {
				Cookie cookie = JSONObject.parseObject(JSONObject.toJSONString(map), Cookie.class);
				manage.addCookie(cookie);
			}
		}
	}
	
	@GetMapping(value = "/getProxyIP")
	@ResponseBody
	@ApiOperation(value = "获取代理ip", httpMethod = "GET")
	public String getProxyIP() {
		ChromeDriver browser = (ChromeDriver) BrowserUtils.openBrowser(SysConstants.SysConfig.CHROMEDRIVER,
				SysConstants.SysConfig.CHROMEDRIVERPATH);
		int index = 1;
		
		Set<String> proxyIPs = new HashSet<String>();
		
		while (index < 3) {
			browser.get("https://www.xicidaili.com/wt/" + index++);
			try {
				Thread.sleep(1500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			WebDriverWait webDriverWait = new WebDriverWait(browser, 5);
			WebElement tbody = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.tagName("tbody")));
			List<WebElement> odds = tbody.findElements(By.cssSelector("tr.odd"));
			for (WebElement webElement : odds) {
				String[] split = webElement.getText().split(" ");
				String ip = split[0]+":"+split[1];
				proxyIPs.add(ip);
			}
		}
		
		index = 1;
		while (index < 3) {
			browser.get("https://www.kuaidaili.com/free/inha/" + index++);
			// intr
			try {
				Thread.sleep(1500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			WebDriverWait webDriverWait = new WebDriverWait(browser, 5);
			WebElement tbody = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.tagName("tbody")));
			List<WebElement> odds = tbody.findElements(By.cssSelector("tr"));
			for (WebElement webElement : odds) {
				String[] split = webElement.getText().split(" ");
				String ip = split[0]+":"+split[1];
				proxyIPs.add(ip);
			}
		}
		
		List<List<String>> ips = new ArrayList<List<String>>();
		List<String> list = new ArrayList<String>(proxyIPs);
		int size = proxyIPs.size();
		if(size<50) {
			ips.add(list);
		}else {
			int zheng = size/50;
			int yu = size % 50 ;
			index = 0;
			while(index<zheng) {
				ips.add(list.subList(10*index, 10*(index+1)));
				index ++;
			}
			ips.add(list.subList(10*index, 10*(index)+yu));
		}
		System.out.println(ips.size());
		browser.close();

		CopyOnWriteArrayList<String> copy = new CopyOnWriteArrayList<String>();
		index = 0;
		Vector<Thread> threads = new Vector<Thread>();
		for (List<String> ipList : ips) {
			Thread newThread = new Thread(new IPPortRunnable(copy, ipList), "线程" + index++);
			threads.add(newThread);
		}
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread iThread : threads) {
			try {
				// 等待所有线程执行完毕
				iThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("主线执行。");
		System.out.println(JSONObject.toJSONString(copy));
		redisTemplate.opsForValue().set(RedisConstants.KEYPRE.DIANPING + RedisConstants.OBJTYPE.PORT, copy);
		return "hello";
	}

}
