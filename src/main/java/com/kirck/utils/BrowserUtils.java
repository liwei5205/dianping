package com.kirck.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.alibaba.fastjson.JSONObject;
import com.kirck.commen.RedisConstants;
import com.kirck.commen.SysConstants;

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
        //显示等待控制对象
/*        WebDriverWait webDriverWait=new WebDriverWait(webDriver,10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.linkText("账号登录"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("tab-account"))).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("account-textbox"))).sendKeys(userName);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("password-textbox"))).sendKeys(password);
        webDriver.findElement(By.id("login-button-account")).click();*/
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
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return browser;
	}
	
}
