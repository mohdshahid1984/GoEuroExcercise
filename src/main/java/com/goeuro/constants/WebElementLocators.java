package com.goeuro.constants;

/**
 * Created by mshahid on 02/01/17.
 */
public class WebElementLocators
{
    public class HomePage
    {
        //((FirefoxDriver) driver).findElementsByXPath("//div[@class='resultTabs']//div[@class='twelve columns']//div[contains(@class, 'Results')]//div[contains(@class,'Result__')]")
        //((FirefoxDriver) driver).findElementsByClassName("Result__result___ebQmu")

        public static final String SEARCH_FROM_TEXT_FIELD = "from_filter";
        public static final String SEARCH_TO_TEXT_FIELD = "to_filter";
        public static final String SEARCH_BUTTON = "search-form__submit-btn";
        public static final String AUTO_COMPLETE_MENU_ITEM = "//li[@class='ui-menu-item']/a[text()=\"%s\"]";
        public static final String DEPARTURE_DATE_FIELD = "departure_date";
        public static final String RETURN_DATE_FIELD = "return_date";
        public static final String CALENDAR_NEXT_MONTH = "ui-datepicker-next";
        public static final String ROUND_TRIP = "trip_type_1";
        public static final String ONE_WAY = "trip_type_2";
        public static final String CALENDER_DAYS = "td";
        public static final String HOTEL_CHECKBOX = "hotel-checkbox";
    }

    public class SearchResultsPage
    {
        public static final String PRODUCT_GRID = "Result__result___ebQmu";
        public static final String PAGINATION_NEXT = ".//*[@data-key=\"dw.paging.next\"]";
    }

}
