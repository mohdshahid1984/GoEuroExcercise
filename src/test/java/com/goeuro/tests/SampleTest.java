package com.goeuro.tests;

import com.goeuro.data.SearchTestData;
import com.goeuro.dataproviders.annotations.MethodArguments;
import com.goeuro.drivers.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

/**
 * Created by mshahid on 31/12/16.
 */
public class SampleTest
{
    private WebDriver driver;
    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser){
        System.out.println("Setup of ---Thread Id = " + Thread.currentThread().getId()  + "browser = " + browser);
        driver = new DriverFactory().getDriver(browser);

    }

    @MethodArguments("dataFilePath=src/test/resources/TestData.xml")
    @Test(dataProvider = "train_search_test_data_provider", dataProviderClass = com.goeuro.dataproviders.XMLDataProvider.class)
    public void sampleTest(SearchTestData testData){
        System.out.println("         ----Thread Id = " + Thread.currentThread().getId());
    }
    @AfterMethod
    public void tearDown(){
        System.out.println("TearDown of ---Thread Id = " + Thread.currentThread().getId());
    }
}
