package com.kirck.job;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.kirck.commen.NumberConstants;
import com.kirck.commen.RedisConstants;
import com.kirck.commen.SysConstants;
import com.kirck.entity.MerchantDealEntity;
import com.kirck.service.IDianPingService;
import com.kirck.utils.BrowserUtils;
import com.kirck.utils.TitleUtils;
import com.kirck.utils.UUIDUtils;

@EnableAsync
@Component
public class JobCenter {
	
    @Autowired
    private IDianPingService dianPingService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;
    
    @Async
    @Scheduled(cron = "0 0 0/1 * * *")
    public void job(){
        System.out.println("1小时打印Job"+new Date());
    }
    
	@Async
	@Scheduled(cron = "0 25 10,22 * * ?")
	public void job2() {
		// 打开浏览器
		ChromeDriver webDriver = (ChromeDriver) BrowserUtils.openBrowser(SysConstants.SysConfig.CHROMEDRIVER,
				SysConstants.SysConfig.CHROMEDRIVERPATH);
		// 设置缓存
		setCookie(webDriver);
		String[] citys = { "wuhan", "shanghai" };

		// 存储最后插入的集合
		List<MerchantDealEntity> merchantDeals = new ArrayList<MerchantDealEntity>();
		String url = "";
		for (String city : citys) {
			List<MerchantDealEntity> cityDeals = new ArrayList<MerchantDealEntity>();

			// 用于过滤不同类型导致的重复
			// 拥有过滤分页更新时重复
			Set<String> dealIds = new HashSet<String>();
			// 根据分类获取的一组团购
			int index = NumberConstants.DIGIT_ZERO;
			String lastDealPath = RedisConstants.KEYPRE.DIANPING + RedisConstants.OBJTYPE.DEAL + city
					+ RedisConstants.SPLITTER;
			// 查找上次最后的折扣信息记录
			String urlId = (String) redisTemplate.opsForValue().get(lastDealPath);
			while (index < 40) {
				url = SysConstants.SysConfig.DIANPINGLIST + SysConstants.Symbol.SLASH + city + SysConstants.Symbol.DASH
						+ SysConstants.SysConfig.CATEGORY + SysConstants.Symbol.UNDERLINE + 1
						+ SysConstants.Symbol.STRING_QUESTION + SysConstants.SysConfig.NEWSORT + index++;
				try {
					Thread.sleep(2000L);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 跳转到最新信息链接
				webDriver.get(url);
				// 等待是否跳转成功
				try {
					while (true) {
						Thread.sleep(2000L);
						if (!webDriver.getCurrentUrl().startsWith(SysConstants.SysConfig.DIANPINGLOGINURL)) {
							break;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 解析团购信息
				List<MerchantDealEntity> circeMerchantDeals = parseDeal(webDriver, urlId != null ? urlId : "-1",
						dealIds);
				cityDeals.addAll(circeMerchantDeals);
				if (CollectionUtils.isEmpty(circeMerchantDeals) || circeMerchantDeals.size() != 40) {
					break;
				}

			}
			// 将第一条记录塞入缓存
			if (!CollectionUtils.isEmpty(cityDeals)) {
				redisTemplate.opsForValue().set(lastDealPath, cityDeals.get(0).getDianpingUrlId());
			}
			merchantDeals.addAll(cityDeals);
		}
		if (!CollectionUtils.isEmpty(merchantDeals)) {
			System.out.println("merchantDeals:" + JSONObject.toJSONString(merchantDeals));
			dianPingService.saveOrUpdate(merchantDeals);
		}
		BrowserUtils.closeBrowser(webDriver);
	}
    
	private List<MerchantDealEntity> parseDeal(WebDriver webDriver, String lastUrlId, Set<String> dealIds) {
		// 团购信息存储
		List<MerchantDealEntity> merchantDeals = new ArrayList<MerchantDealEntity>();
		WebElement element = webDriver.findElement(By.cssSelector("div.tg-tab-box.tg-floor.on"));
		// 获取属性值
		List<WebElement> elements2 = element.findElements(By.cssSelector("div.tg-floor-item-wrap"));
		for (WebElement webElement : elements2) {
			MerchantDealEntity merchantDeal = new MerchantDealEntity();
			String href = webElement.findElement(By.cssSelector("a.tg-floor-img")).getAttribute("href");
			String urlId = href.substring(href.lastIndexOf('/') + NumberConstants.DIGIT_ONE);
			if (lastUrlId.equals(urlId)) {
				dealIds.add(urlId);
				break;
			}
			if (dealIds.contains(urlId)) {
				continue;
			}
			merchantDeal.setId(UUIDUtils.getNewId());
			merchantDeal.setDealTitle(webElement.findElement(By.tagName("h3")).getText() + "的"
					+ TitleUtils.getTitle(webElement.findElement(By.tagName("h4")).getText()));
			merchantDeal.setCreateDate(LocalDateTime.now());
			merchantDeal.setPrice(new BigDecimal(webElement.findElement(By.tagName("em")).getText()));
			merchantDeal.setStorePrice(new BigDecimal(webElement.findElement(By.tagName("del")).getText()));
			merchantDeal.setDianpingUrlId(urlId);
			merchantDeals.add(merchantDeal);
			dealIds.add(urlId);
		}
		return merchantDeals;
	}

	@SuppressWarnings("unchecked")
	private void setCookie(WebDriver browser) {
		String cookiesPath = RedisConstants.KEYPRE.DIANPING + RedisConstants.OBJTYPE.COOKIES
				+ SysConstants.SysConfig.USERNAME;
		List<Map<String, Object>> cookies = (List<Map<String, Object>>) redisTemplate.opsForValue().get(cookiesPath);
		if (cookies == null) {
			cookies = BrowserUtils.loginDianPingWUP(browser, SysConstants.SysConfig.USERNAME,
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

	@Async
	@Scheduled(cron = "30 11 17,21 * * ?")
	public void job3() {
		//获取当前存储的线程池
		@SuppressWarnings("unchecked")
		List<Map<String,String>> list = (List<Map<String, String>>) redisTemplate.opsForValue().get(RedisConstants.KEYPRE.DIANPING+RedisConstants.OBJTYPE.PORT);
		//先校验这些线程是否还可用
		List<Map<String,String>> badList = new ArrayList<Map<String,String>>();
		for (Map<String, String> map : list) {
			ChromeDriver webDriver = (ChromeDriver) BrowserUtils.openBrowserWithProxy(SysConstants.SysConfig.CHROMEDRIVER,
					SysConstants.SysConfig.CHROMEDRIVERPATH,map.get("ip")+":"+map.get("port"));
			webDriver.get("http://2019.ip138.com/ic.asp");
			try {
					Thread.sleep(2000L);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Document parse = Jsoup.parse(webDriver.getPageSource());
			String text = parse.text();
			System.out.println(text);
			if(StringUtils.isNotBlank(text)&&text.contains("您的IP地址")&&!text.contains("180.154.132.13")) {
				System.out.println(map.get("ip")+":"+map.get("port"));
			}else {
				badList.add(map);
			}
			webDriver.get(SysConstants.SysConfig.DIANPINGHOMEURL);
		}
		list.removeAll(badList);
		redisTemplate.opsForValue().set(RedisConstants.KEYPRE.DIANPING+RedisConstants.OBJTYPE.PORT, list);

	}
}
