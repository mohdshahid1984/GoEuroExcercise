package com.goeuro.constants;

/**
 * Created by mshahid on 02/01/17.
 */
public class Configuration
{
    public static final String APPLICATION_URL = "http://www.goeuro.com/";
    public static final String SUGGEST_API = "/suggester-api/v2/position/suggest/en/";
    public static final String SEARCH_URL = "/GoEuroAPI/rest/api/v5/searches";
    public static final String TRAIN_SEARCH_BY_SEARCH_ID =
            "/GoEuroAPI/rest/api/v5/results?travel_mode=train&all_positions=true&include_price_details=true&sort_by=price&sort_variants=price&search_id=";

    public enum Browser
    {
        CHROME,
        FIREFOX
    }

    public enum PassengerType
    {
        ADULT, CHILD, INFANT
    }

    public enum TripType
    {
        OneWay, RoundTrip
    }

    public class General
    {
        public static final int PAGE_LOAD_TIMEOUT = 30;
        public static final int GETTING_ELEMENT_TIMEOUT = 25;
        public static final int SCRIPT_TIMEOUT = 10;
    }

}
