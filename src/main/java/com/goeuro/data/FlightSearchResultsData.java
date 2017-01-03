package com.goeuro.data;


/**
 * Created by mshahid on 02/01/17.
 */
public class FlightSearchResultsData extends CommonData
{
    SegmentDetail[] onwardSegmentDetails;
    SegmentDetail[] returnSegmentDetails;

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

    public class SegmentDetail extends CommonData.SegmentDetail{
        public String getAirLineCode()
        {
            return airLineCode;
        }

        public void setAirLineCode(String airLineCode)
        {
            this.airLineCode = airLineCode;
        }

        public String getFlighNumber()
        {
            return flighNumber;
        }

        public void setFlighNumber(String flighNumber)
        {
            this.flighNumber = flighNumber;
        }

        String airLineCode;
        String flighNumber;
    }


}
