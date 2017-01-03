package com.goeuro.pages;

import com.goeuro.constants.Configuration;
import com.goeuro.constants.WebElementLocators;
import com.goeuro.data.FlightSearchResultsData;
import com.goeuro.data.SearchTestData;
import com.goeuro.drivers.manager.DriverManager;
import com.goeuro.utils.GeneralUtils;
import com.goeuro.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by mshahid on 02/01/17.
 */
public class HomePage
{
    static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    public void performSearch(SearchTestData data){
        logger.debug("---Thread.currentThread().getId() = "+Thread.currentThread().getId() +"and Driver is = " + DriverManager.getWebDriver().hashCode());

        fillOrigin(data.getDepartureCity());
        fillDestination(data.getArrivalCity());
        chooseTripType(data.getTripType());
        fillDates(data);
        fillPassengerCount();
        if (!data.isSearchWithAirBnB())
            DriverManager.getWebDriver().findElement(By.className(WebElementLocators.HomePage.HOTEL_CHECKBOX)).click();
        performSearch();
    }
    public void openBaseUrl(String url){
        DriverManager.getWebDriver().get(url);
    }
    private void fillPassengerCount(){

    }
    private void performSearch(){
        WebElement buttonElement = DriverManager.getWebDriver().findElement(By.id(WebElementLocators.HomePage.SEARCH_BUTTON));
        buttonElement.click();
    }
    private void fillOrigin(String departureCity){
        DriverManager.getWebDriver().findElement(By.id(WebElementLocators.HomePage.SEARCH_FROM_TEXT_FIELD)).sendKeys(departureCity);
        WaitUtils.waitForElementToBeVisibleBy(GeneralUtils.XPATH, String.format(WebElementLocators.HomePage.AUTO_COMPLETE_MENU_ITEM,departureCity), 5).click();
    }

    private void fillDestination(String arrivalCity){
        DriverManager.getWebDriver().findElement(By.id(WebElementLocators.HomePage.SEARCH_TO_TEXT_FIELD)).sendKeys(arrivalCity);
        WaitUtils.waitForElementToBeVisibleBy(GeneralUtils.XPATH, String.format(WebElementLocators.HomePage.AUTO_COMPLETE_MENU_ITEM,arrivalCity), 5).click();
    }

    private void fillDates(SearchTestData data){
        fillDepartureDate(data.getTravelDate());
        if (Configuration.TripType.RoundTrip.compareTo(data.getTripType())==0){
            fillReturnDate(data.getTravelDate(),data.getReturnDate());
        }

    }
    private void fillReturnDate(String depDate, String date){
        DriverManager.getWebDriver().findElement(By.id(WebElementLocators.HomePage.RETURN_DATE_FIELD)).click();
        navigateToDesiredMonth(depDate, date);
        selectDesiredDate(date);
    }
    private void fillDepartureDate(String date){
        DriverManager.getWebDriver().findElement(By.id(WebElementLocators.HomePage.DEPARTURE_DATE_FIELD)).click();
        navigateToDesiredMonth(date);
        selectDesiredDate(date);
    }
    private void navigateToDesiredMonth(String depDate, String date){
        int clicksRequired = GeneralUtils.numberOfMonthsFromCurrent(depDate, date);
        for (int i=1;i<=clicksRequired;i++)
            DriverManager.getWebDriver().findElement((By.className(WebElementLocators.HomePage.CALENDAR_NEXT_MONTH))).click();
    }
    private void navigateToDesiredMonth(String date){
        navigateToDesiredMonth("",date);
    }

    private void selectDesiredDate(String date){

        int day = GeneralUtils.getDay(date);

        List<WebElement> webElementList = DriverManager.getWebDriver().findElements(By.tagName(WebElementLocators.HomePage.CALENDER_DAYS));

        for (int i=day-1;i<webElementList.size();i++){
            if (!webElementList.get(i).getText().trim().isEmpty() && Integer.parseInt(webElementList.get(i).getText())==day)
            {
                webElementList.get(i).click();
                break;
            }
        }

    }

    private void chooseTripType(Configuration.TripType tripType){
        if (Configuration.TripType.OneWay.compareTo(tripType)==0)
            DriverManager.getWebDriver().findElement(By.id(WebElementLocators.HomePage.ONE_WAY)).click();
        if (Configuration.TripType.RoundTrip.compareTo(tripType)==0){
            DriverManager.getWebDriver().findElement(By.id(WebElementLocators.HomePage.ROUND_TRIP)).click();
        }
    }
}
