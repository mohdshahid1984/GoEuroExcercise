package com.goeuro.drivers.factory;

import com.goeuro.constants.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by mshahid on 02/01/17.
 */
public class DriverFactory
{
    public static WebDriver getDriver(String browser){
        WebDriver webDriver = null;
        switch (Configuration.Browser.valueOf(browser)){
            case CHROME:
                webDriver = new ChromeDriver();
                break;
            case FIREFOX:
                webDriver = new FirefoxDriver();
                break;
            default:
                webDriver = new ChromeDriver();
                break;

        }
        return webDriver;
    }
}
