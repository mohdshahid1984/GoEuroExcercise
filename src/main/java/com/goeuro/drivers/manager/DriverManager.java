package com.goeuro.drivers.manager;

import org.openqa.selenium.WebDriver;

/**
 * Created by mshahid on 01/01/17.
 */
public class DriverManager
{
    private static final ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    public static WebDriver getWebDriver()
    {
        return webDriverThreadLocal.get();
    }

    public static void setWebDriver(WebDriver webDriverThreadLocal)
    {
        DriverManager.webDriverThreadLocal.set(webDriverThreadLocal);
    }
}
