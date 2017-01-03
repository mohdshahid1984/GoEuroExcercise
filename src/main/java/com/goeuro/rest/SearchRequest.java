package com.goeuro.rest;

import com.goeuro.constants.Configuration;
import com.goeuro.data.SearchTestData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mshahid on 03/01/17.
 */
public class SearchRequest
{
    public String getSearchRequest(SearchTestData data){
        SuggestCity suggestCity = new SuggestCity();
        long departureCityCode = suggestCity.getCityCode(data.getDepartureCity());
        long arrivalCityCode = suggestCity.getCityCode(data.getArrivalCity());

        JSONObject searchOptions = new JSONObject();

        JSONObject departurePositionId = new JSONObject();
        departurePositionId.put("id", departureCityCode);

        JSONObject arrivalPositionId = new JSONObject();
        arrivalPositionId.put("id", arrivalCityCode);

        HashMap<String, Object> map = new HashMap<>();
        map.put("departurePosition", departurePositionId);
        map.put("arrivalPosition", arrivalPositionId);
        JSONArray travelModes = new JSONArray();
        travelModes.add("Train");
        travelModes.add("Flight");
        travelModes.add("Bus");
        map.put("travelModes", travelModes);
        String[] date = data.getTravelDate().split("-");
        map.put("departureDate", date[2] + "-" + date[1] + "-" + date[0]);
        map.put("passengers", passengers(data));
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("identifier", "0.a6cpmbn8unpx0ya8kbjhbbj4i");
        userInfo.put("domain", ".com");
        userInfo.put("currency", "EUR");
        userInfo.put("locale", "en");
        map.put("userInfo", userInfo);
        if (Configuration.TripType.RoundTrip.compareTo(data.getTripType()) == 0)
        {
            String[] returnDate = data.getReturnDate().split("-");
            map.put("returnDate", returnDate[2] + "-" + returnDate[1] + "-" + returnDate[0]);
        }
        searchOptions.put("searchOptions", map);


        return JSONValue.toJSONString(searchOptions);
    }

    private JSONArray passengers(SearchTestData data)
    {
        Object a = Configuration.PassengerType.ADULT;
        Configuration.PassengerType b = (Configuration.PassengerType) a;
        JSONArray array = new JSONArray();
        Map<Configuration.PassengerType, Integer> passengers = data.getPassengerList();
        for (Map.Entry entry : passengers.entrySet())
        {
            for (int count = 1; count <= (Integer) entry.getValue(); count++)
            {
                System.out.println(((Configuration.PassengerType) entry.getKey()).name());
                HashMap<String, Object> pass = new HashMap<>();
                switch (((Configuration.PassengerType) entry.getKey()))
                {
                    case ADULT:
                        pass.put("discountCards", new JSONArray());
                        pass.put("age", 22);//Age should come from dataprovider
                        break;
                    case CHILD:
                        pass.put("discountCards", new JSONArray());
                        pass.put("age", 3);//Age should come from dataprovider
                        break;
                    case INFANT:
                        pass.put("discountCards", new JSONArray());
                        pass.put("age", 0);//Age should come from dataprovider
                        break;
                    default:
                        pass.put("discountCards", new JSONArray());
                        pass.put("age", 22);//Age should come from dataprovider
                        break;
                }
                array.add(new JSONObject(pass));
            }
        }

        return array;
    }
}
