package com.kirck.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.CollectionUtils;

import com.kirck.commen.SysConstants;
import com.kirck.entity.Area;

public class BrowserUtils {
    private static WebDriver browser;


    public static WebDriver openBrowser(String chromedriver, String chromedriverpath) {
    	ChromeOptions options = new ChromeOptions();
    	options.addArguments("start-maximized");
    	options.addArguments("disable-infobars");
    	options.addArguments("--disable-extensions");
    	options.addArguments("--disable-gpu");
    	options.addArguments("--disable-dev-shm-usage");
    	options.addArguments("--no-sandbox");
        System.getProperties().setProperty(chromedriver,chromedriverpath);
        browser = new ChromeDriver(options);
        //等待
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
        return browser;
    }
    
    public static List<Map<String, Object>> loginDianPing(WebDriver webDriver, String username, String password) {
        webDriver.get(SysConstants.SysConfig.DIANPINGLOGINURL);
        //等待2秒用于页面加载，保证Cookie响应全部获取。
        try {
            Thread.sleep(20000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Set<Cookie> cookies=webDriver.manage().getCookies();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Cookie cookie : cookies) {
            list.add(cookie.toJson());
        }
        return list;
    }
    
	public static List<Map<String, Object>> loginDianPingWUP(WebDriver webDriver, String username, String password) {
		webDriver.get(SysConstants.SysConfig.DIANPINGLOGINURL);
		// 显示等待控制对象
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebDriverWait webDriverWait = new WebDriverWait(webDriver, 5);
		WebElement element = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.tagName("iframe")));
		WebDriver frame = webDriver.switchTo().frame(element);
		webDriverWait = new WebDriverWait(frame, 5);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.className("bottom-password-login"))).click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("tab-account"))).click();
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("account-textbox"))).sendKeys(username);
		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("password-textbox"))).sendKeys(password);
		webDriver.findElement(By.id("login-button-account")).click();

		// 等待2秒用于页面加载，保证Cookie响应全部获取。
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

		Set<Cookie> cookies = webDriver.manage().getCookies();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Cookie cookie : cookies) {
			list.add(cookie.toJson());
		}
		return list;

	}

    public static void closeBrowser(WebDriver webDriver) {
        webDriver.close();
    }

	public static WebDriver openBrowserWithProxy(String chromedriver, String chromedriverpath, String proxyIpAndPort) {
		System.getProperties().setProperty(chromedriver, chromedriverpath);
		Proxy proxy = new Proxy();
		proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
		cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
		cap.setCapability(CapabilityType.PROXY, proxy);
		ChromeOptions chromeOptions = new ChromeOptions().merge(cap);
		browser = new ChromeDriver(chromeOptions);
		// 等待
		browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		browser.manage().timeouts().pageLoadTimeout(15,TimeUnit.SECONDS);
		browser.manage().timeouts().setScriptTimeout(15,TimeUnit.SECONDS);
		return browser;
	}
	
}
