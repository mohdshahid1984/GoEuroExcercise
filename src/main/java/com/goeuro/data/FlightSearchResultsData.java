package com.goeuro.data;

/**
 * Created by mshahid on 02/01/17.
 */
public class FlightSearchResultsData extends CommonData
{
    private SegmentDetail[] onwardSegmentDetails;
    private SegmentDetail[] returnSegmentDetails;

    public SegmentDetail[] getReturnSegmentDetails()
    {
        return returnSegmentDetails;
    }

    public void setReturnSegmentDetails(SegmentDetail[] returnSegmentDetails)
    {
        this.returnSegmentDetails = returnSegmentDetails;
    }

    public SegmentDetail[] getOnwardSegmentDetails()
    {
        return onwardSegmentDetails;
    }

    public void setOnwardSegmentDetails(SegmentDetail[] onwardSegmentDetails)
    {
        this.onwardSegmentDetails = onwardSegmentDetails;
    }

    public class SegmentDetail extends CommonData.SegmentDetail
    {
        String airLineCode;
        String flightNumber;

        public String getAirLineCode()
        {
            return airLineCode;
        }

        public void setAirLineCode(String airLineCode)
        {
            this.airLineCode = airLineCode;
        }

        public String getFlightNumber()
        {
            return flightNumber;
        }

        public void setFlightNumber(String flightNumber)
        {
            this.flightNumber = flightNumber;
        }
    }

}
