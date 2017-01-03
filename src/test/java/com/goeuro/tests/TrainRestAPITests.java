package com.goeuro.tests;

import com.goeuro.constants.Configuration;
import com.goeuro.data.SearchTestData;
import com.goeuro.dataproviders.annotations.MethodArguments;
import com.goeuro.rest.RequestSender;
import com.goeuro.rest.SearchRequest;
import com.goeuro.rest.SuggestCity;
import com.goeuro.utils.VerificationHelper;
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
        String searchRequest = new SearchRequest().getSearchRequest(data);
        JSONObject resp = (JSONObject) new RequestSender().sendRequest(Configuration.SEARCH_URL, searchRequest);
        String searchId = (String) resp.get("searchId");
        System.out.println("===============" + searchId + "====================");
        WaitUtils.delay_N_Sec(2);
        JSONObject searchResponse = (JSONObject) new RequestSender().sendGETTRequest(Configuration.TRAIN_SEARCH_BY_SEARCH_ID+ searchId);
        VerificationHelper.verify_if_sorted_by_price(searchResponse,data.getTripType());
    }


}


