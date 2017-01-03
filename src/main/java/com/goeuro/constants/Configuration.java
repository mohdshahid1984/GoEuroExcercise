package com.goeuro.constants;

/**
 * Created by mshahid on 02/01/17.
 */
public class Configuration
{
    public static final String APPLICATION_URL = "http://www.goeuro.com/";
    public static final String SUGGEST_API="/suggester-api/v2/position/suggest/en/";

    public enum Browser
    {
        CHROME,
        FIREFOX;
    }
    public class General {
        public static final int PAGE_LOAD_TIMEOUT = 30;
        public static final int GETTING_ELEMENT_TIMEOUT = 25;
        public static final int SCRIPT_TIMEOUT = 10;
    }

    public enum PassengerType{
        ADULT,CHILD,INFANT;
    }

    public enum TripType{
        OneWay, RoundTrip;
    }

}
