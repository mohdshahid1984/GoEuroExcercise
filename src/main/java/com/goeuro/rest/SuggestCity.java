package com.goeuro.rest;

import com.goeuro.constants.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by mshahid on 02/01/17.
 */
public class SuggestCity
{
    public long getCityCode(String city){
        JSONArray jsonObject = (JSONArray)new RequestSender().sendGETTRequest((Configuration.SUGGEST_API + city.split(" ")[0]));
        //jsonObject.get("")
        return (long)((JSONObject)jsonObject.get(0)).get("_id");
    }
}
