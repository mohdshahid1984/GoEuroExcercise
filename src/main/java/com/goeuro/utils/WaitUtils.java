package com.goeuro.utils;

import com.goeuro.drivers.manager.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshahid on 02/01/17.
 */
public class WaitUtils
{
    static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);

    public static WebElement waitForElementToBeVisibleBy(String locatorStrategy, String locator, long timeOutInSeconds)
    {
        //logger.debug("---Thread.currentThread().getId() = "+Thread.currentThread().getId() +"and Driver is = " + DriverManager.getWebDriver().hashCode());

        WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), timeOutInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(GeneralUtils.getBy(locatorStrategy, locator)));

        return DriverManager.getWebDriver().findElement(GeneralUtils.getBy(locatorStrategy, locator));
    }

    public static List<WebElement> waitForElementsToBeVisibleBy(String locatorStrategy, String locator, long timeOutInSeconds)
    {
        //logger.debug("---Thread.currentThread().getId() = "+Thread.currentThread().getId() +"and Driver is = " + DriverManager.getWebDriver().hashCode());

        try
        {
            WebDriverWait wait = new WebDriverWait(DriverManager.getWebDriver(), timeOutInSeconds);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(GeneralUtils.getBy(locatorStrategy, locator)));

            return DriverManager.getWebDriver().findElements(GeneralUtils.getBy(locatorStrategy, locator));
        }
        catch (org.openqa.selenium.TimeoutException to)
        {
            return new ArrayList<>();
        }
    }

    public static void delay_N_Sec(int n)
    {
        for (int i = 0; i < n; i++)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {

                e.printStackTrace();
            }
        }
    }
}
