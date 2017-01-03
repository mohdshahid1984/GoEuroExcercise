package com.goeuro.data;

import java.time.LocalTime;

/**
 * Created by mshahid on 02/01/17.
 */
public class CommonData
{
    protected double price;
    protected SegmentDetail[] onwardsSegmentDetail;
    protected SegmentDetail[] returnSegmentDetail;
    int onwardStops;
    int returnStops;

    public class SegmentDetail{
        LocalTime departTime;
        LocalTime arrivalTime;

        public LocalTime getArrivalTime()
        {
            return arrivalTime;
        }

        public void setArrivalTime(LocalTime arrivalTime)
        {
            this.arrivalTime = arrivalTime;
        }

        public LocalTime getDepartTime()
        {
            return departTime;
        }

        public void setDepartTime(LocalTime departTime)
        {
            this.departTime = departTime;
        }
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public SegmentDetail[] getOnwards()
    {
        return onwardsSegmentDetail;
    }

    public void setOnwards(SegmentDetail[] onwardsSegmentDetail)
    {
        this.onwardsSegmentDetail = onwardsSegmentDetail;
    }

    public SegmentDetail[] getBackward()
    {
        return returnSegmentDetail;
    }

    public void setBackward(SegmentDetail[] returnSegmentDetail)
    {
        this.returnSegmentDetail = returnSegmentDetail;
    }

}
