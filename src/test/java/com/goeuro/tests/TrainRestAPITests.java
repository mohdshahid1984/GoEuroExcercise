package com.goeuro.tests;

import com.goeuro.constants.Configuration;
import com.goeuro.data.SearchTestData;
import com.goeuro.dataproviders.annotations.MethodArguments;
import com.goeuro.rest.RequestSender;
import com.goeuro.rest.SuggestCity;
import com.goeuro.utils.WaitUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mshahid on 02/01/17.
 */
public class TrainRestAPITests
{
    @MethodArguments("dataFilePath=src/test/resources/TestData.xml")
    @Test(dataProvider = "train_search_test_data_provider", dataProviderClass = com.goeuro.dataproviders.XMLDataProvider.class)
    public void trainSortingTests(SearchTestData data)
    {
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

        String re =
                "{\"searchOptions\":{\"departurePosition\":{\"id\":378655},\"arrivalPosition\":{\"id\":375859},\"travelModes\":[\"Flight\",\"Train\",\"Bus\"],\"departureDate\":\"2017-01-03\",\"passengers\":[{\"age\":12,\"discountCards\":[]}],\"userInfo\":{\"identifier\":\"0.a6cpmbn8unpx0ya8kbjhbbj4i\",\"domain\":\".com\",\"locale\":\"en\",\"currency\":\"EUR\"},\"abTestParameters\":[]}}";
        String multiReq =
                "{\"searchOptions\":{\"departurePosition\":{\"id\":378655},\"arrivalPosition\":{\"id\":375859},\"travelModes\":[\"Flight\",\"Train\",\"Bus\"],\"departureDate\":\"2017-01-09\",\"returnDate\":\"2017-01-11\",\"passengers\":[{\"age\":12,\"discountCards\":[]},{\"age\":12,\"discountCards\":[]},{\"age\":3,\"discountCards\":[]},{\"age\":0,\"discountCards\":[]}],\"userInfo\":{\"identifier\":\"0.a6cpmbn8unpx0ya8kbjhbbj4i\",\"domain\":\".com\",\"locale\":\"en\",\"currency\":\"EUR\"},\"abTestParameters\":[]}}";
        JSONObject request = (JSONObject) JSONValue.parse(multiReq);
        String req = JSONValue.toJSONString(searchOptions);
        System.out.println(JSONValue.toJSONString(searchOptions));

        JSONObject resp = (JSONObject) new RequestSender().sendRequest("/GoEuroAPI/rest/api/v5/searches", req);

        String searchId = (String) resp.get("searchId");

        System.out.println("===============" + searchId + "====================");
        WaitUtils.delay_N_Sec(2);
        JSONObject searchResp = (JSONObject) new RequestSender().sendGETTRequest(
                "/GoEuroAPI/rest/api/v5/results?travel_mode=train&all_positions=true&include_price_details=true&sort_by=price&sort_variants=price&search_id="
                        + searchId);
        List<JSONObject> itins = (List<JSONObject>) searchResp.get("itineraries");
        long previousPrice = (Long) ((JSONObject) ((JSONObject) searchResp.get("outbounds")).get(itins.get(0).get("outboundLegId"))).get("price");
        for (int i = 1; i < itins.size(); i++)
        {
            long currentPrice;
            JSONObject itn = itins.get(i);
            if (Configuration.TripType.OneWay.compareTo(data.getTripType()) == 0)
            {
                Assert.assertEquals(itn.size(), 1, "only outbound leg is expected for oneWay trip at itin index = " + i);
                ((JSONObject) ((JSONObject) searchResp.get("outbounds")).get(itn.get("outboundLegId"))).get("price");
                currentPrice = (Long) ((JSONObject) ((JSONObject) searchResp.get("outbounds")).get(itn.get("outboundLegId"))).get("price");
            }
            else
            {
                Assert.assertEquals(itn.size(), 2, "outbound and inbound both legs are expected for roundTrip at itin index = " + i);
                long outBoundPrice = (Long) ((JSONObject) ((JSONObject) searchResp.get("outbounds")).get(itn.get("outboundLegId"))).get("price");
                long inboundPrice = (Long) ((JSONObject) ((JSONObject) searchResp.get("inbounds")).get(itn.get("inboundLegId"))).get("price");
                Assert.assertEquals(outBoundPrice, inboundPrice, "outbound and inbound price should be equal at index = " + i);
                currentPrice = outBoundPrice;
            }
            Assert.assertTrue(currentPrice >= previousPrice, "price is nor sorted at index = " + i);
            previousPrice = currentPrice;
        }
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


