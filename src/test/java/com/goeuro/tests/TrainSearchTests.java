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
        logger.debug("Thread " + Thread.currentThread().getId() + "and Driver " + DriverManager.getWebDriver().hashCode());

        HomePage homePage = new HomePage();
        logger.debug("Thread " + Thread.currentThread().getId() + "and Driver " + DriverManager.getWebDriver().hashCode() + "is opening HomePage");
        homePage.openGoEuroHomePage();

        logger.debug("Thread " + Thread.currentThread().getId() + "and Driver " + DriverManager.getWebDriver().hashCode() + "is performing Search");
        homePage.performSearch(data);

        SearchResultsPage searchResultsPage = new SearchResultsPage();
        logger.debug("Thread " + Thread.currentThread().getId() + "and Driver " + DriverManager.getWebDriver().hashCode() + "is extracting train data");
        ArrayList<TrainSearchResultsData> trainSearchResultsDataList = searchResultsPage.getTrainsData();

        logger.debug("Thread " + Thread.currentThread().getId() + " is verifying if Price is sorted");
        VerificationHelper.verify_if_sorted_by_price(trainSearchResultsDataList);

        logger.debug("Thread " + Thread.currentThread().getId() + " is going to execute teardown");
    }

}
