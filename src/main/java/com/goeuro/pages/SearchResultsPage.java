package com.goeuro.pages;

import com.goeuro.constants.WebElementLocators;
import com.goeuro.data.SearchTestData;
import com.goeuro.data.TrainSearchResultsData;
import com.goeuro.drivers.manager.DriverManager;
import com.goeuro.utils.GeneralUtils;
import com.goeuro.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshahid on 02/01/17.
 */
public class SearchResultsPage
{

    static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    public void verifyIfTrainsAreAvailable(SearchTestData data)
    {
        Assert.assertTrue(DriverManager.getWebDriver().getCurrentUrl().contains("train"),
                          "Trains aren't available between " + data.getDepartureCity() + "and " + data.getArrivalCity());
    }

    public void clickASpecificPage(WebElement ele, int pageno)
    {
        ele.findElement(By.xpath(".//div[. = '" + pageno + "']")).click();
    }

    private boolean IsNextElementPresent()
    {
        try
        {
            DriverManager.getWebDriver().findElement(By.xpath(WebElementLocators.SearchResultsPage.PAGINATION_NEXT));
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private void addPricesToList(List<TrainSearchResultsData> list, List<WebElement> price)
    {
        for (WebElement el : price)
        {
            String elval = (el.getText()).substring(1);
            String[] elvalparts = elval.split("\\.");
            TrainSearchResultsData data = new TrainSearchResultsData();
            data.setPrice((Integer.parseInt(elvalparts[0]) + (double) Integer.parseInt(elvalparts[1]) / 100));
            list.add(data);
        }
    }

    public ArrayList<TrainSearchResultsData> getTrainsData()
    {
        WaitUtils.waitForElementToBeVisibleBy(GeneralUtils.CLASS_NAME, "Result__priceContainer___3s9kI", 30);
        WaitUtils.delay_N_Sec(10);
        WebElement resultsTab = DriverManager.getWebDriver().findElement(By.className("Results__tabsBody___2LwJ4"));
        ArrayList<TrainSearchResultsData> list = new ArrayList<>();
        int page = 1;
        addPricesToList(list, resultsTab.findElements(By.className("Result__priceContainer___3s9kI")));
        System.out.println("Page1 Done");
        if (resultsTab.findElements(By.className("Paging__pagingContainer___289JA")).size() > 0)
        {
            while (resultsTab.findElements(By.className("Paging__pagingContainer___289JA")).size() > 0 && IsNextElementPresent())
            {

                if (((RemoteWebDriver) DriverManager.getWebDriver()).getCapabilities().getBrowserName().equalsIgnoreCase("chrome"))
                {
                    Actions actions = new Actions(DriverManager.getWebDriver());
                    actions.moveToElement(DriverManager.getWebDriver().findElement(By.xpath(WebElementLocators.SearchResultsPage.PAGINATION_NEXT))).click()
                           .perform();
                }
                else
                    resultsTab.findElement((By.xpath(WebElementLocators.SearchResultsPage.PAGINATION_NEXT))).click();
                System.out.println("Clicked for Next Page PageNumber = " + ++page);
                WaitUtils.delay_N_Sec(10);
                WebElement resultsTab_page = DriverManager.getWebDriver().findElement(By.className("Results__tabsBody___2LwJ4"));
                List<WebElement> price = resultsTab_page.findElements(By.className("Result__priceContainer___3s9kI"));
                addPricesToList(list, price);
                resultsTab = resultsTab_page;
            /*WebDriverWait waiting = new WebDriverWait(DriverManager.getWebDriver(),20);
            waiting.until(ExpectedConditions.invisibilityOfElementLocated(By.className("Results__tabsBodyLoading___2EGIU Results__tabsBody___2LwJ4")));
            waiting.until(ExpectedConditions.invisibilityOfElementLocated(By.className("Results__hiddenOverlay___1LLcU")));
            waiting.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(By.className("Results__tabsBody___2LwJ4"),
                                                                                 By.className("Paging__pagingContainer___289JA")));
            WaitUtils.waitForElementsToBeVisibleBy(GeneralUtils.CLASS_NAME,"Result__priceContainer___3s9kI",15);*/

            }
        }

        for (int k = 0; k < list.size(); k++)
        {
            if (k % 10 == 0)
                System.out.println();
            System.out.print(list.get(k) + " ");
        }
        System.out.println("list.size() = ==  =" + list.size());

        return list;

    }
}
