package com.goeuro.tests;

import com.goeuro.base.TestBase;
import com.goeuro.data.SearchTestData;
import com.goeuro.data.TrainSearchResultsData;
import com.goeuro.dataproviders.annotations.MethodArguments;
import com.goeuro.drivers.manager.DriverManager;
import com.goeuro.pages.HomePage;
import com.goeuro.pages.SearchResultsPage;
import com.goeuro.utils.VerificationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by mshahid on 02/01/17.
 */
public class TrainSearchTests extends TestBase
{
    private static final Logger logger = LoggerFactory.getLogger(TrainSearchTests.class);

    @MethodArguments("dataFilePath=src/test/resources/TestData.xml")
    @Test(dataProvider = "train_search_test_data_provider", dataProviderClass = com.goeuro.dataproviders.XMLDataProvider.class)
    public void testSortingOfTrainPrices(SearchTestData data)
    {
        logger.debug("---Thread.currentThread().getId() = " + Thread.currentThread().getId() + "and Driver is = " + DriverManager.getWebDriver().hashCode());

        HomePage homePage = new HomePage();
        homePage.openGoEuroHomePage();

        logger.debug("---Thread.currentThread().getId() = " + Thread.currentThread().getId() + "and Driver is = " + DriverManager.getWebDriver().hashCode()
                             + "----Duplicate1");
        homePage.performSearch(data);
        logger.debug("---Thread.currentThread().getId() = " + Thread.currentThread().getId() + "and Driver is = " + DriverManager.getWebDriver().hashCode()
                             + "----Duplicate2");

        SearchResultsPage searchResultsPage = new SearchResultsPage();
        logger.debug("---Thread.currentThread().getId() = " + Thread.currentThread().getId() + "and Driver is = " + DriverManager.getWebDriver().hashCode()
                             + "----Duplicate3");
        ArrayList<TrainSearchResultsData> trainSearchResultsDataList = searchResultsPage.getTrainsData();

        VerificationHelper.verify_if_sorted_by_price(trainSearchResultsDataList);
        logger.debug("---Thread.currentThread().getId() = " + Thread.currentThread().getId() + "and Driver is = " + DriverManager.getWebDriver().hashCode()
                             + "----Duplicate4");

        System.out.println("======FINISH Test====Thread.currentThread().getId()=" + Thread.currentThread().getId());
        System.out.println("======FINISH Test====driver.hashCode()=" + DriverManager.getWebDriver().hashCode());
    }

}
