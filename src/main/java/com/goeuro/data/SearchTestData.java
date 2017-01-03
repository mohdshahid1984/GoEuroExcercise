package com.goeuro.data;

import com.goeuro.constants.Configuration;

import java.util.Map;

/**
 * Created by mshahid on 02/01/17.
 */
public class SearchTestData
{
    private boolean execute;
    private String travelDate;
    private String returnDate;
    private Configuration.TripType tripType;
    private String language;
    private String currency;
    private boolean searchWithAirBnB;
    private String departureCity;
    private String arrivalCity;
    private Map<Configuration.PassengerType, Integer> passengerList;

    public Map<Configuration.PassengerType, Integer> getPassengerList()
    {
        return passengerList;
    }

    public void setPassengerList(Map<Configuration.PassengerType, Integer> passengerList)
    {
        this.passengerList = passengerList;
    }

    public String getDepartureCity()
    {
        return departureCity;
    }

    public void setDepartureCity(String departureCity)
    {
        this.departureCity = departureCity;
    }

    public boolean isExecute()
    {
        return execute;
    }

    public void setExecute(boolean execute)
    {
        this.execute = execute;
    }

    public String getArrivalCity()
    {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity)
    {
        this.arrivalCity = arrivalCity;
    }

    public String getTravelDate()
    {
        return travelDate;
    }

    public void setTravelDate(String travelDate)
    {
        this.travelDate = travelDate;
    }

    public String getReturnDate()
    {
        return returnDate;
    }

    public void setReturnDate(String returnDate)
    {
        this.returnDate = returnDate;
    }

    public Configuration.TripType getTripType()
    {
        return tripType;
    }

    public void setTripType(Configuration.TripType tripType)
    {
        this.tripType = tripType;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public boolean isSearchWithAirBnB()
    {
        return searchWithAirBnB;
    }

    public void setSearchWithAirBnB(boolean searchWithAirBnB)
    {
        this.searchWithAirBnB = searchWithAirBnB;
    }

}
