package com.goeuro.base;

import com.goeuro.constants.Configuration;
import com.goeuro.drivers.factory.DriverFactory;
import com.goeuro.drivers.manager.DriverManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

/**
 * Created by mshahid on 02/01/17.
 */
public class TestBase
{
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite public void setup()
    {
        System.out.println("TRAVIS_OS_NAME = "+System.getProperty("os.name"));
        System.out.println("SUIT SetUP, Thread.currentThread().getId()=" + Thread.currentThread().getId());
        logger.debug("setup, Thread.currentThread().getId() = " + Thread.currentThread().getId());
        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");

    }

    @Parameters("browser") @BeforeMethod public void testSetup(String browser)
    {
        System.out.println("====== Test SetUP====Thread.currentThread().getId()=" + Thread.currentThread().getId());

        WebDriver webDriver = DriverFactory.getDriver(browser);
        DriverManager.setWebDriver(webDriver);

        System.out.println("====== Test SetUP====driver.hashCode() = " + DriverManager.getWebDriver().hashCode());
        logger.debug("---Thread.currentThread().getId() = " + Thread.currentThread().getId() + "and Driver is = " + DriverManager.getWebDriver().hashCode());

        DriverManager.getWebDriver().manage().timeouts().implicitlyWait(Configuration.General.GETTING_ELEMENT_TIMEOUT, TimeUnit.SECONDS);
        DriverManager.getWebDriver().manage().timeouts().setScriptTimeout(Configuration.General.SCRIPT_TIMEOUT, TimeUnit.SECONDS);
        DriverManager.getWebDriver().manage().timeouts().pageLoadTimeout(Configuration.General.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
    }

    @AfterMethod public void tearDown()
    {
        System.out.println("====== Test TearDown====Thread.currentThread().getId()=" + Thread.currentThread().getId());
        System.out.println("====== Test TearDown====driver.hashCode()=" + DriverManager.getWebDriver().hashCode());
        logger.debug("---Thread.currentThread().getId() = " + Thread.currentThread().getId() + "and Driver is = " + DriverManager.getWebDriver().hashCode());

        DriverManager.getWebDriver().quit();
    }

}
