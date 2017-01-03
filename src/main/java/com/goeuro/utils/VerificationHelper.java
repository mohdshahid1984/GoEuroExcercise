package com.goeuro.utils;

import com.goeuro.data.CommonData;
import com.goeuro.data.TrainSearchResultsData;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshahid on 01/01/17.
 */
public class VerificationHelper
{
    public static void verify_if_sorted_by_price(List<? extends CommonData> data){
        boolean sorted = true;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i-1).getPrice()>data.get(i).getPrice()){
                sorted = false;
                break;
            }
        }

        Assert.assertTrue(sorted, "data is not sorted");
    }
}
