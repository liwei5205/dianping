package com.kirck.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
import com.kirck.utils.BrowserUtils;
import com.kirck.utils.TitleUtils;
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
	public  String sayHello() {
		ChromeDriver browser = (ChromeDriver) BrowserUtils.openBrowser(SysConstants.SysConfig.CHROMEDRIVER,
				SysConstants.SysConfig.CHROMEDRIVERPATH);
		browser.get("https://www.baidu.com");
		try {
				Thread.sleep(20000L);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		browser.close();
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
		;
		wait.until(ExpectedConditions.elementToBeClickable(browser.findElement(By.cssSelector("ul.shoplist"))));
		String html = Jsoup.parse(browser.getPageSource()).html();
		redisTemplate.opsForValue().set(RedisConstants.KEYPRE.DIANPING + RedisConstants.OBJTYPE.HTML
				+ SysConstants.SysConfig.DEAL + SysConstants.Symbol.COLON + dealId, html);
		BrowserUtils.closeBrowser(browser);
		return html;
	}


	@SuppressWarnings("unchecked")
	private void setCookie(WebDriver browser) {
		String cookiesPath = RedisConstants.KEYPRE.DIANPING+RedisConstants.OBJTYPE.COOKIES+SysConstants.SysConfig.USERNAME;
		List<Map<String,Object>> cookies= (List<Map<String, Object>>) redisTemplate.opsForValue().get(cookiesPath);
		if(cookies==null) {
			cookies = BrowserUtils.loginDianPing(browser, SysConstants.SysConfig.USERNAME, SysConstants.SysConfig.PASSWORD);
            redisTemplate.opsForValue().set(cookiesPath,cookies);
		}
		Options manage = browser.manage();
		manage.deleteAllCookies();
		browser.get(SysConstants.SysConfig.DIANPINGHOMEURL);
		for (Map<String, Object> map : cookies) {
			Cookie cookie = JSONObject.parseObject(JSONObject.toJSONString(map), Cookie.class);
			manage.addCookie(cookie);
		}
	}
	
}
