package com.kirck.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.kirck.commen.NumberConstants;
import com.kirck.commen.RedisConstants;
import com.kirck.commen.SysConstants;
import com.kirck.entity.MerchantDealEntity;
import com.kirck.service.IDianPingService;
import com.kirck.utils.BrowserUtils;
import com.kirck.utils.UUIDUtils;

@Component
public class JobCenter {
	
    @Autowired
    private IDianPingService dianPingService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;
    
    @Scheduled(cron = "0/15 * * * * *")
    public void job(){
        System.out.println("Job"+new Date());
    }
    
	@Scheduled(cron = "0 55 13,8 * * ?")
	public void job2() {
		// 打开浏览器
		ChromeDriver webDriver = (ChromeDriver) BrowserUtils.openBrowser(SysConstants.SysConfig.CHROMEDRIVER,
				SysConstants.SysConfig.CHROMEDRIVERPATH);
		// 设置缓存
		setCookie(webDriver);
		String[] categoryIds = { "15", "21", "19", "164", "165", "167", "14", "26" };
		String[] citys = { "wuhan", "shanghai" };
		// http://t.dianping.com/list/shanghai-category_15?desc=1&sort=new
		List<MerchantDealEntity> merchantDeals = new ArrayList<MerchantDealEntity>();
		String url = "";
		List<String> dealIds = new ArrayList<String>();
		for (String city : citys) {
			CATEGORY: for (String categoryId : categoryIds) {
				List<MerchantDealEntity> categoryDeals = new ArrayList<MerchantDealEntity>();
				int index = NumberConstants.DIGIT_ZERO;
				String lastDealPath = RedisConstants.KEYPRE.DIANPING + RedisConstants.OBJTYPE.DEAL + city
						+ RedisConstants.SPLITTER + RedisConstants.OBJTYPE.CATEGORY + categoryId
						+ RedisConstants.SPLITTER;
				// 查找最新的折扣信息记录
				String urlId = (String) redisTemplate.opsForValue().get(lastDealPath);
				while (index < 2) {
					url = SysConstants.SysConfig.DIANPINGLIST + SysConstants.Symbol.SLASH + city
							+ SysConstants.Symbol.DASH + SysConstants.SysConfig.CATEGORY + SysConstants.Symbol.UNDERLINE
							+ categoryId + SysConstants.Symbol.STRING_QUESTION + SysConstants.SysConfig.NEWSORT
							+ index++;
					System.out.println("url:" + url);
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
					categoryDeals.addAll(circeMerchantDeals);
					if (circeMerchantDeals.size() != 40) {
						break CATEGORY;
					}
				}
				// 将第一条记录塞入缓存
				redisTemplate.opsForValue().set(lastDealPath, categoryDeals.get(0).getDianpingUrlId());
				merchantDeals.addAll(categoryDeals);
			}
			Collections.reverse(merchantDeals);
		}
		dianPingService.saveOrUpdate(merchantDeals);
		BrowserUtils.closeBrowser(webDriver);
	}
    
	private List<MerchantDealEntity> parseDeal(ChromeDriver webDriver, String lastUrlId, List<String> dealIds) {
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
				break;
			}
			if (dealIds.contains(urlId)) {
				continue;
			}
			merchantDeal.setId(UUIDUtils.getNewId());
			merchantDeal.setPrice(new BigDecimal(webElement.findElement(By.tagName("em")).getText()));
			merchantDeal.setStorePrice(new BigDecimal(webElement.findElement(By.tagName("del")).getText()));
			merchantDeal.setDianpingUrlId(urlId);
			merchantDeals.add(merchantDeal);
			dealIds.add(urlId);
		}
		return merchantDeals;
	}

	@SuppressWarnings("unchecked")
	private void setCookie(ChromeDriver browser) {
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
