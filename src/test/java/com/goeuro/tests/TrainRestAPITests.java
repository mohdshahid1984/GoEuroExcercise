package com.goeuro.tests;

import com.goeuro.constants.Configuration;
import com.goeuro.data.SearchTestData;
import com.goeuro.dataproviders.annotations.MethodArguments;
import com.goeuro.rest.RequestSender;
import com.goeuro.rest.SearchRequest;
import com.goeuro.utils.VerificationHelper;
import com.goeuro.utils.WaitUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Created by mshahid on 02/01/17.
 */
public class TrainRestAPITests
{
    private static final Logger logger = LoggerFactory.getLogger(TrainRestAPITests.class);

    @MethodArguments("dataFilePath=src/test/resources/TestData.xml")
    @Test(dataProvider = "train_search_test_data_provider", dataProviderClass = com.goeuro.dataproviders.XMLDataProvider.class)
    public void trainSortingTests(SearchTestData data)
    {
        logger.debug("Thread " + Thread.currentThread().getId() + " is creating Search Request");

        String searchRequest = new SearchRequest().getSearchRequest(data);
        logger.debug("SearchRequest =  " + searchRequest);

        logger.debug("Thread " + Thread.currentThread().getId() + " is creating SearchID");
        JSONObject resp = (JSONObject) new RequestSender().sendRequest(Configuration.SEARCH_URL, searchRequest);
        String searchId = (String) resp.get("searchId");
        logger.debug("searchId =  " + searchId);

        WaitUtils.delay_N_Sec(2);
        logger.debug("Thread " + Thread.currentThread().getId() + " is fetching results using SearchID");
        JSONObject searchResponse = (JSONObject) new RequestSender().sendGETTRequest(Configuration.TRAIN_SEARCH_BY_SEARCH_ID + searchId);

        logger.debug("Thread " + Thread.currentThread().getId() + " is verifying if Price is sorted");
        VerificationHelper.verify_if_sorted_by_price(searchResponse, data.getTripType());
    }

}


