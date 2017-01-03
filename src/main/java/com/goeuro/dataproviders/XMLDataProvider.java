package com.goeuro.dataproviders;

import com.goeuro.constants.Configuration;
import com.goeuro.constants.TestDataLocators;
import com.goeuro.data.SearchTestData;
import com.goeuro.utils.GeneralUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by mshahid on 02/01/17.
 */
public class XMLDataProvider
{
    @DataProvider(name = "train_search_test_data_provider",parallel = true)
    public static Iterator<Object[]> trainSearchXMLDataProvider(Method method){
        Map<String,String> methodArguments= GeneralUtils.resolveMethodArguments(method);
        String dataFilePath = methodArguments.get("dataFilePath");

        SAXBuilder xmlBuilder = new SAXBuilder();
        Document document = null;
        try
        {
            document = xmlBuilder.build(dataFilePath);
        }catch (JDOMException e){
            Assert.fail("check if xml is correct at dataFilePath=" + dataFilePath);
            e.printStackTrace();
        }catch (IOException io){
            Assert.fail("check if xml is correct at dataFilePath="+dataFilePath);
            io.printStackTrace();
        }

        List<Object[]> searchTestDataList= populateSearchTestData(document.getRootElement().getChildren());

        return searchTestDataList.iterator();
    }

    private static List<Object[]> populateSearchTestData(List<Element> testElements){
        List<Object[]> searchTestDataList = new ArrayList<Object[]>();
        for (Element element:testElements){
            if (Boolean.parseBoolean(element.getChildText(TestDataLocators.EXECUTE)))
            {
                SearchTestData data = new SearchTestData();

                data.setExecute(true);
                data.setArrivalCity(element.getChildText(TestDataLocators.ARRIVAL_CITY));
                data.setCurrency(element.getChildText(TestDataLocators.CURRENCY));
                data.setDepartureCity(element.getChildText(TestDataLocators.DEPARTURE_CITY));
                data.setLanguage(element.getChildText(TestDataLocators.LANGUAGE));
                data.setTravelDate(element.getChildText(TestDataLocators.TRAVEL_DATE));
                data.setTripType(Configuration.TripType.valueOf(element.getChildText(TestDataLocators.TRIP_TYPE).trim()));
                /**
                 * if TripType is roundtrip then only set return Date
                 */
                if (Configuration.TripType.RoundTrip.compareTo(data.getTripType())==0)
                    data.setReturnDate(element.getChildText(TestDataLocators.RETURN_DATE));

                data.setSearchWithAirBnB(Boolean.parseBoolean(element.getChildText(TestDataLocators.RETURN_DATE)));
                Map<Configuration.PassengerType, Integer> passengerTypeIntegerMap  = getPassengerList(element.getChildText(TestDataLocators.PASSENGER_LIST));
                if (passengerTypeIntegerMap.size()<1){
                    HashMap<Configuration.PassengerType,Integer> temp = new HashMap();
                    temp.put(Configuration.PassengerType.ADULT,1);
                    passengerTypeIntegerMap = temp;
                }
                data.setPassengerList(passengerTypeIntegerMap);

                searchTestDataList.add(new Object[]{(Object)data});
            }
        }

        return searchTestDataList;
    }

    private static Map<Configuration.PassengerType, Integer> getPassengerList(String passengerString){
        Map<Configuration.PassengerType, Integer> passengerMap = new HashMap<Configuration.PassengerType, Integer>();
        for (String string:passengerString.trim().split(",")){
            passengerMap.put(Configuration.PassengerType.valueOf(string.split("-")[0].trim().toUpperCase()), Integer.parseInt(string.split("-")[1].trim()));
        }
        return passengerMap;
    }
}
