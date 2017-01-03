package com.goeuro.utils;

import com.goeuro.constants.Configuration;
import com.goeuro.data.CommonData;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.util.List;

/**
 * Created by mshahid on 01/01/17.
 */
public class VerificationHelper
{
    public static void verify_if_sorted_by_price(List<? extends CommonData> data)
    {
        boolean sorted = true;
        for (int i = 1; i < data.size(); i++)
        {
            if (data.get(i - 1).getPrice() > data.get(i).getPrice())
            {
                sorted = false;
                break;
            }
        }

        Assert.assertTrue(sorted, "data is not sorted");
    }

    public static void verify_if_sorted_by_price(JSONObject searchResponse,Configuration.TripType tripType){
        List<JSONObject> itins = (List<JSONObject>) searchResponse.get("itineraries");
        long previousPrice = (Long) ((JSONObject) ((JSONObject) searchResponse.get("outbounds")).get(itins.get(0).get("outboundLegId"))).get("price");
        for (int i = 1; i < itins.size(); i++)
        {
            long currentPrice;
            JSONObject itn = itins.get(i);
            if (Configuration.TripType.OneWay.compareTo(tripType) == 0)
            {
                Assert.assertEquals(itn.size(), 1, "only outbound leg is expected for oneWay trip at itin index = " + i);
                ((JSONObject) ((JSONObject) searchResponse.get("outbounds")).get(itn.get("outboundLegId"))).get("price");
                currentPrice = (Long) ((JSONObject) ((JSONObject) searchResponse.get("outbounds")).get(itn.get("outboundLegId"))).get("price");
            }
            else
            {
                Assert.assertEquals(itn.size(), 2, "outbound and inbound both legs are expected for roundTrip at itin index = " + i);
                long outBoundPrice = (Long) ((JSONObject) ((JSONObject) searchResponse.get("outbounds")).get(itn.get("outboundLegId"))).get("price");
                long inboundPrice = (Long) ((JSONObject) ((JSONObject) searchResponse.get("inbounds")).get(itn.get("inboundLegId"))).get("price");
                Assert.assertEquals(outBoundPrice, inboundPrice, "outbound and inbound price should be equal at index = " + i);
                currentPrice = outBoundPrice;
            }
            Assert.assertTrue(currentPrice >= previousPrice, "price is nor sorted at index = " + i);
            previousPrice = currentPrice;
        }
    }
}
